package life.qbic.portal.model.db.operations;

import life.qbic.portal.model.db.elements.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class DBDataInsert {

    private final Logger logger = LogManager.getLogger(DBDataInsert.class);

    private final DBConfig config;

    public DBDataInsert(DBConfig config){
        this.config = config;
    }

    public boolean createProjectWithConnections(Project project) throws SQLException {
        Connection connection = Utils.login(config);
        boolean success = false;
        // We will commit all queries together later
        connection.setAutoCommit(false);

        try {
            // first we check if the transmitted contact person is in the db (has an id), otherwise we
            // create it
            Person contact = project.getContactPerson();
            int contactID = contact.getID();
            if (contactID < 0) {
                contactID = Utils.addPerson(contact, connection);
            }
            // project table insert
            int projectID = addProject(project, contactID, connection);
            // cooperation and applicants are added, if they don't exist (see above); connections are
            // added
            // to the respective tables as well
            for (Person applicant : project.getApplicants()) {
                int applicantID = applicant.getID();
                if (applicantID < 0) {
                    applicantID = Utils.addPerson(applicant, connection);
                }
                Utils.insertTableJunctionPersonAssociations(applicantID, projectID, connection, TableName.project_has_applicants, "applicant_id");
            }
            for (Person cooperator : project.getCooperationPartners()) {
                int cooperatorID = cooperator.getID();
                if (cooperatorID < 0) {
                    cooperatorID = Utils.addPerson(cooperator, connection);
                }
                Utils.insertTableJunctionPersonAssociations(cooperatorID, projectID, connection, TableName.project_has_cooperation_partners, "cooperation_partner_id");
            }
            // insert experiment tables and add respective batches
            for (Experiment exp : project.getExperiments()) {
                int expID = addExperiment(exp, projectID, connection);
                createBatches(exp.getBatches(), expID, connection);
            }
            connection.commit();
            logger.info("Project has been successfully added to all related tables.");
            success = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Exception occurred while adding project. Rolling back.");
            connection.rollback();
        } finally {
            connection.close();
        }
        return success;
    }

    private int addProject(Project project, int contactID, Connection connection)
            throws SQLException {
        int res = -1;
        logger.info("Trying to add project with DFG ID " + project.getDfgID() + " to the DB");
        String sql =
                "INSERT INTO project (qbic_id, dfg_id, title, total_cost, description, classification_id, keywords, sequencing_aim, contact_person_id, topical_assignment_id, declaration_of_intent) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?. ?)";
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
        ResultSet rs = statement.getGeneratedKeys();
        statement.close();
        if (rs.next()) {
            res = rs.getInt(1);
        }
        return res;
    }

    private void createBatches(List<Batch> batches, int expID, Connection connection)
            throws SQLException {
        logger.info("Trying to add sample batches to the DB");
        String sql = "INSERT INTO batch (estimated_delivery_date, number_samples, experiment_id) "
                + "VALUES(?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (Batch batch : batches) {
            statement.setDate(1, batch.getEstimatedDelivery());
            statement.setInt(2, batch.getNumOfSamples());
            statement.setInt(3, expID);
            statement.addBatch();
        }
        statement.executeBatch();
    }

    private int addExperiment(Experiment exp, int projectID, Connection connection)
            throws SQLException {
        int res = -1;
        logger.info("Trying to add experiment to the DB");
        String sql =
                "INSERT INTO experiment (number_of_samples, coverage, costs, genome_size, material_id, species_id, technology_type_id, application_id, nucleic_acid_id, library_id, project_id) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, exp.getNumOfSamples());
        statement.setString(2, exp.getCoverage());
        statement.setBigDecimal(3, exp.getCosts());
        statement.setString(4, exp.getGenomeSize());
        statement.setInt(5, Vocabulary.getMaterialID(exp.getMaterial()));
        statement.setInt(6, Vocabulary.getSpeciesID(exp.getSpecies()));
        statement.setInt(7, Vocabulary.getTechnologyTypeID(exp.getTechnologyType()));
        statement.setInt(8, Vocabulary.getTechInstrumentID(exp.getApplication()));
        statement.setInt(9, Vocabulary.getNucleicAcidID(exp.getNucleicAcid()));
        statement.setInt(10, Vocabulary.getLibraryID(exp.getLibrary()));
        statement.setInt(11, projectID);
        statement.execute();
        ResultSet rs = statement.getGeneratedKeys();
        statement.close();
        if (rs.next()) {
            res = rs.getInt(1);
        }
        return res;
    }






}
