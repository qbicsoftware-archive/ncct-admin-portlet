package life.qbic.portal.model.db.operations;

import life.qbic.portal.model.db.elements.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBDataRetrieval {
    private final Logger logger = LogManager.getLogger(DBDataRetrieval.class);
    private final DBConfig config;
    
    public DBDataRetrieval(DBConfig config){
        this.config = config;
    }

    /**
     * Returns a Map of Vocabulary terms from a Table containing id and name field
     *
     * @param t The table name as enum
     * @return a map of Vocabulary terms with names as keys and ids as values
     */
     Map<String, Integer> getVocabularyMapForTable(TableName t) {
        String sql = "SELECT * from " + t.toString();
        Map<String, Integer> res = new HashMap<>();
        Connection conn = Utils.login(config);
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res.put(rs.getString("name"), rs.getInt("id"));
            }
            statement.close();
        } catch (SQLException e) {
            logger.error("SQL operation unsuccessful: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException n) {
            logger.error("Could not reach SQL database.");
        }
        Utils.logout(conn);
        return res;
    }

    /**
     * this does not store the key id, thus see function @getTopicalAssignmentIDFromName
     *
     * @return
     */
    public Map<String, String> getTopicalAssignmentVocabularyMap() {
        String sql = "SELECT * from topical_assignment";
        Map<String, String> res = new HashMap<>();
        Connection conn = Utils.login(config);
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res.put(rs.getString("name"), rs.getString("number"));
            }
            statement.close();
        } catch (SQLException e) {
            logger.error("SQL operation unsuccessful: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException n) {
            logger.error("Could not reach SQL database.");
        }
        Utils.logout(conn);
        return res;
    }


    /**
     * get project objects from the database, includes all related data like experiments, batches,
     * persons
     *
     * @return list of project objects
     */
    public List<Project> getProjects() {
        List<Project> res = new ArrayList<>();
        String sql = "SELECT * from project";
        Connection conn = Utils.login(config);
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String qbicID = rs.getString("qbic_id");
                String dfgID = rs.getString("dfg_id");
                String title = rs.getString("title");
                BigDecimal totalCost = rs.getBigDecimal("total_cost");
                String description = rs.getString("description");
                String keywords = rs.getString("keywords");
                String sequencingAim = rs.getString("sequencing_aim");
                int contactID = rs.getInt("contact_person_id");
                int topicalID = rs.getInt("topical_assignment_id");
                int classificationID = rs.getInt("classification_id");

                Person contactPerson = getPersonFromID(contactID);
                String topicalAssignment = getTopicalAssignmentNameFromID(topicalID);
                String classification = Vocabulary.getClassificationName(classificationID);
                File tempFile = null;
                Project project =
                        new Project(id, qbicID, dfgID, title, totalCost, description, tempFile,
                                classification, keywords, sequencingAim, contactPerson, topicalAssignment);

                if(getPeopleForProjectID("project_has_applicants", "applicant_id", id).size() > 0) {
                    project.setApplicants(getPeopleForProjectID("project_has_applicants", "applicant_id", id));
                }
                project.setCooperationPartners(getPeopleForProjectID("project_has_cooperation_partners",
                        "cooperation_partner_id", id));
                project.setExperiments(getExperimentsWithProjectID(id));
                res.add(project);
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        return res;
    }

    private List<Experiment> getExperimentsWithProjectID(int projectID) {
        List<Experiment> res = new ArrayList<>();
        String sql = "SELECT * from experiment WHERE project_id = " + projectID;
        Connection conn = Utils.login(config);
        try {

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int numOfSamples = rs.getInt("number_of_samples");
                String coverage = rs.getString("coverage");
                String genomeSize = rs.getString("genome_size");
                BigDecimal costs = rs.getBigDecimal("costs");

                int materialID = rs.getInt("material_id");
                int speciesID = rs.getInt("species_id");
                int techID = rs.getInt("technology_type_id");
                int application_id = rs.getInt("application_id");
                int nucleicAcidID = rs.getInt("nucleic_acid_id");
                int libraryID = rs.getInt("library_id");

                String material = Vocabulary.getMaterialName(materialID);
                String species = Vocabulary.getSpeciesName(speciesID);
                String technology = Vocabulary.getTechnologyTypeName(techID);
                String application = Vocabulary.getTechInstrumentName(application_id);
                String nucleicAcid = Vocabulary.getNucleicAcidName(nucleicAcidID);
                String library = Vocabulary.getMaterialName(libraryID);

                Experiment exp = new Experiment(id, numOfSamples, coverage, costs, genomeSize, material,
                        species, technology, application, nucleicAcid, library);
                exp.setBatches(getBatchesForExperimentID(id));
                res.add(exp);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        return res;
    }

    private List<Batch> getBatchesForExperimentID(int expId) {
        List<Batch> res = new ArrayList<>();
        String sql = "SELECT * from batch WHERE experiment_id = " + expId;
        Connection conn = Utils.login(config);
        try {

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Date deliveryDate = rs.getDate("estimated_delivery_date");
                int numberOfSamples = rs.getInt("number_samples");


                Batch b = new Batch(id, deliveryDate, numberOfSamples);
                res.add(b);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            logger.error(e);
        }
        return res;
    }

    private List<Person> getPeopleForProjectID(String junctionTable, String junctionPersonID, int id) {

        String sql = "SELECT * FROM person INNER JOIN "+ junctionTable +" ON person.id = " + junctionTable + "." + junctionPersonID + " WHERE project_id = " + id;
        Connection conn = Utils.login(config);
        List<Person> res = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res.add(parsePerson(rs));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error(e);
                e.printStackTrace();
            }
        }
        return res;
    }

    private String getTopicalAssignmentNameFromID(int topicalID) {
        String contactSql = "SELECT * from topical_assignment WHERE id = ?";
        String res = null;
        Connection conn = Utils.login(config);
        try {
            PreparedStatement statement = conn.prepareStatement(contactSql);
            statement.setInt(1, topicalID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                res = rs.getString("name");
            }
            statement.close();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error(e);
                e.printStackTrace();
            }
        }
        return res;
    }


    private Person parsePerson(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String lastname = rs.getString("lastname");
        String firstname = rs.getString("firstname");
        String institution = rs.getString("institution");
        String city = rs.getString("city");
        String email = rs.getString("email");
        String phone = rs.getString("phonenumber");
        return new Person(id, firstname, lastname, email, city, phone, institution);
    }


    private Person getPersonFromID(int contactID) {
        String contactSql = "SELECT * from person WHERE id = ?";
        Person res = null;
        Connection conn = Utils.login(config);
        try {
            PreparedStatement statement = conn.prepareStatement(contactSql);
            statement.setInt(1, contactID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                res = parsePerson(rs);
            }
            statement.close();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error(e);
                e.printStackTrace();
            }
        }
        return res;
    }

    public Person getPerson(String firstName, String lastName, String institution, String city, String email, String phone) {
        String contactSql = "SELECT * from person WHERE lastname = ?" +
                "AND firstname = ? " +
                "AND  institution = ? " +
                "AND city = ? " +
                "AND email = ? " +
                "AND phonenumber = ? ";
        Person res = null;
        Connection conn = Utils.login(config);
        try {
            PreparedStatement statement = conn.prepareStatement(contactSql);
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, institution);
            statement.setString(4, city);
            statement.setString(5, email);
            statement.setString(6, phone);

            ResultSet rs = statement.executeQuery();

            //TODO if this is longer than 1, something is wrong
            while (rs.next()) {
                res = parsePerson(rs);
            }
            statement.close();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error(e);
                e.printStackTrace();
            }
        }

        if (res == null){
            res = getPerson(firstName, lastName, institution, city);
            res.setCity(city);
            res.setInstitution(institution);
        }
        return res;
    }

    public Person getPerson(String firstName, String lastName, String institution, String city) {
        String contactSql = "SELECT * from person WHERE lastname = ?" +
                "AND firstname = ? " +
                "AND  institution = ? " +
                "AND city = ? " ;
        Person res = null;
        Connection conn = Utils.login(config);
        try {
            PreparedStatement statement = conn.prepareStatement(contactSql);
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, institution);
            statement.setString(4, city);

            ResultSet rs = statement.executeQuery();

            //TODO this could be longer than one, if for example multiple email addresses and/or phone numbers are stored.
            while (rs.next()) {
                res = parsePerson(rs);
            }
            statement.close();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error(e);
                e.printStackTrace();
            }
        }

        if (res == null){
            res = new Person(firstName, lastName, city, institution);
        }
        return res;
    }

    public void initVocabularies() {
        new Vocabulary(getTopicalAssignmentVocabularyMap(), getVocabularyMapForTable(TableName.library),
                getVocabularyMapForTable(TableName.application),
                getVocabularyMapForTable(TableName.species),
                getVocabularyMapForTable(TableName.technology_type),
                getVocabularyMapForTable(TableName.material),
                getVocabularyMapForTable(TableName.nucleic_acid),
                getVocabularyMapForTable(TableName.classification));
    }


}
