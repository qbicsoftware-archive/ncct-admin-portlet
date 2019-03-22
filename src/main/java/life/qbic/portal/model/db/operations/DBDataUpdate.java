package life.qbic.portal.model.db.operations;

import life.qbic.portal.model.db.elements.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.stream.Collectors;

public class DBDataUpdate {


    private final Logger logger = LogManager.getLogger(DBDataUpdate.class);

    private final DBConfig config;

    public DBDataUpdate(DBConfig config) {
        this.config = config;
    }


    public boolean updateProjectWithConnection(Project oldProject, Project project) throws Exception {
        logger.info("Try to update project.");
        Connection connection = Utils.login(config);
        boolean success = false;
        // We will commit all queries together later
        connection.setAutoCommit(false);

        try {
            // first we check if the transmitted contact person is in the db (has an id)
            //TODO what happens if we want to correct a typo? How can we represent that in the table?, this is important to not
            //TODO clutter tables with useless information
            Person contact = project.getContactPerson();
            int contactID = contact.getID();
            boolean isNewContact = false;
            if (contactID < 0) {
                contactID = Utils.addPerson(contact, connection);
                logger.info("Entry to update does not exist.");
                isNewContact = true;

            }

            // project table insert
            updateProject(project, contactID, connection);

            if(isNewContact){
                deletePerson(oldProject.getContactPerson(), connection);
            }
            // cooperation and applicants are added, if they don't exist (see above); connections are
            // added
            // to the respective tables as well
            //TODO what happens if we want to correct a typo? How can we represent that in the table?, this is important to not
            //TODO clutter tables with useless information, somewhat covered with attempting to delete unused entries and just insert a row with new information, not technically an update though

            //1. insert new people
            //Add applicants only present in new project but not old one
            for (Person applicant : project.getApplicants().stream().filter(applicant -> !oldProject.getApplicants().contains(applicant)).collect(Collectors.toList())) {
                int applicantID = applicant.getID();

                if (applicantID < 0) {
                    applicantID = Utils.addPerson(applicant, connection);
                }
                Utils.insertTableJunctionPersonAssociations(applicantID, project.getId(), connection, TableName.project_has_applicants, "applicant_id");
            }

            //Add cooperation partners only present in new project but not old one
            for (Person cooperator : project.getCooperationPartners().stream().filter(coop -> !oldProject.getCooperationPartners().contains(coop)).collect(Collectors.toList())) {
                int cooperatorID = cooperator.getID();

                if (cooperatorID < 0) {
                    cooperatorID = Utils.addPerson(cooperator, connection);
                }
                Utils.insertTableJunctionPersonAssociations(cooperatorID, project.getId(), connection, TableName.project_has_cooperation_partners, "cooperation_partner_id");
            }

            //2. delete old people from junction table
            // TODO maybe try to delete them from table as well, if they are used elsewhere nothing should happen, if not, than the tables would get cleaned up....

            for (Person applicant : oldProject.getApplicants().stream().filter(applicant -> !project.getApplicants().contains(applicant)).collect(Collectors.toList())) {

                deleteAssociationWithPerson(applicant, oldProject.getId(), "applicant_id", connection, TableName.project_has_applicants);
            }


            for (Person cooperationPartner : oldProject.getCooperationPartners().stream().filter(coop -> !project.getCooperationPartners().contains(coop)).collect(Collectors.toList())) {
                deleteAssociationWithPerson(cooperationPartner, oldProject.getId(), "cooperation_partner_id", connection, TableName.project_has_cooperation_partners);
            }


            //TODO: next: edit experiments! technically no update, currently no way of properly tracking which experiments are new and which ones are updated....
            //TODO: DB wise not super efficient, as new entries are added /deleted unnecessarily, ....
            // insert experiment tables and add/delete respective batches
            // inserts new experiments
            for (Experiment exp : project.getExperiments().stream().filter(experiment -> !oldProject.getExperiments().contains(experiment)).collect(Collectors.toList())) {
                int expID = Utils.addExperiment(exp, oldProject.getId(), connection);
                Utils.createBatches(exp.getBatches(), expID, connection);
            }

            for (Experiment experiment : oldProject.getExperiments().stream().filter(experiment -> !project.getExperiments().contains(experiment)).collect(Collectors.toList())) {
                deleteExperiment(experiment, connection);
            }

            //TODO batches, has to be evaluated for each previously existing experiment, the newly added experiments ones are covered above,... all removed experiments the batches are removed above
            //Intersection of old and new project IDs
            //NVM: if batches are changed, they are accounted for as new experiment, because equals method returns false

            connection.commit();
            System.out.println(connection.getCatalog());
            logger.info("Project has been successfully added to all related tables.");
            success = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Exception occurred while adding project. Rolling back.", ex);
            connection.rollback();
            throw new Exception(ex);
        } finally {
            connection.close();
        }
        return success;
    }

    private void updateProject(Project project, int contactID, Connection connection) throws SQLException {
        logger.info("Trying to update project with DFG ID " + project.getDfgID());
        String sql =
                "UPDATE project " +
                        "SET qbic_id = ?," +
                        "dfg_id = ?," +
                        "title = ?," +
                        "total_cost = ?," +
                        "description = ?," +
                        "classification_id = ?," +
                        "keywords = ?," +
                        "sequencing_aim = ?," +
                        "contact_person_id = ?," +
                        "topical_assignment_id = ?," +
                        "declaration_of_intent = ? " +
                        "WHERE project.id = " + project.getId();

        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, project.getQbicID());
        statement.setString(2, project.getDfgID());
        statement.setString(3, project.getTitle());
        statement.setBigDecimal(4, project.getTotalCost());
        statement.setString(5, project.getDescription());
        statement.setInt(6, Vocabulary.getClassificationID(project.getClassification()));
        statement.setString(7, project.getKeywords());
        statement.setString(8, project.getSequencingAim());
        statement.setInt(9, contactID);
        statement.setInt(10, Utils.getTopicalAssignmentIDFromName(project.getTopicalAssignment(), config));
        statement.setString(11, null);
        statement.execute();
        statement.close();

    }

    public void deleteAssociationWithPerson(Person person, int projectID, String personIdName, Connection connection, TableName tableName) throws SQLException {
        logger.info("Trying to delete person " + person.getFirstName() + " " + person.getLastName() + " from junction table");
        String sql =
                "DELETE FROM " + tableName.toString() +
                        " WHERE project_id = " + projectID +
                        " AND " + personIdName + " = " + person.getID();

        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.execute();
        statement.close();

        deletePerson(person, connection);


    }

    private void deletePerson(Person person, Connection connection) throws SQLException {
        //TODO this relies on correct Fk restraints to be set!
        try {

            String sqlDeleteFromPersonTable =
                    "DELETE FROM " + TableName.person +
                            " WHERE id = " + person.getID();
            PreparedStatement statementDelete = connection.prepareStatement(sqlDeleteFromPersonTable, Statement.RETURN_GENERATED_KEYS);
            statementDelete.execute();
            statementDelete.close();

        } catch (SQLIntegrityConstraintViolationException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    private void deleteExperiment(Experiment experiment, Connection connection) throws SQLException {
        deleteBatch(experiment.getID(), connection);

        String sql =
                "DELETE FROM " + TableName.experiment +
                        " WHERE id = " + experiment.getID();

        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.execute();
        statement.close();

    }

    /**
     * Deletes all batches of a specific experiment
     */
    private void deleteBatch(int experimentID, Connection connection) throws SQLException {
        String sql =
                "DELETE FROM " + TableName.batch +
                        " WHERE experiment_id = " + experimentID;

        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.execute();
        statement.close();
    }


}





