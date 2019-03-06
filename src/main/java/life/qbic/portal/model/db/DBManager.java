package life.qbic.portal.model.db;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author afriedrich
 */
public class DBManager {
    private DBConfig config;

    Logger logger = LogManager.getLogger(DBManager.class);

    public DBManager(DBConfig config) {
        this.config = config;
    }

    private void logout(Connection conn) {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            logger.error("Logout failed!: " + e);
            e.printStackTrace();
        }
    }

    private Connection login() {
        String DB_URL = "jdbc:mariadb://" + config.getHostname() + ":" + config.getPort() + "/"
                + config.getSql_database();

        Connection conn = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, config.getUsername(), config.getPassword());
        } catch (Exception e) {
            logger.error("Login failed!: " + e);
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Returns a Map of Vocabulary terms from a Table containing id and name field
     *
     * @param t The table name as enum
     * @return a map of Vocabulary terms with names as keys and ids as values
     */
    public Map<String, Integer> getVocabularyMapForTable(TableName t) {
        String sql = "SELECT * from " + t.toString();
        Map<String, Integer> res = new HashMap<>();
        Connection conn = login();
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
        logout(conn);
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
        Connection conn = login();
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
        logout(conn);
        return res;
    }

    public String getProjectName(String projectIdentifier) {
        String sql = "SELECT short_title from projects WHERE openbis_project_identifier = ?";
        String res = "";
        Connection conn = login();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, projectIdentifier);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                res = rs.getString(1);
            }
        } catch (SQLException e) {
            logger.error("SQL operation unsuccessful: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException n) {
            logger.error("Could not reach SQL database, resuming without project names.");
        }
        logout(conn);
        return res;
    }

    /**
     * returns a list of available persons
     *
     * @return
     */
    public List<Person> getAllPersons() {
        String sql = "SELECT * FROM person";
        List<Person> res = new ArrayList<>();
        Connection conn = login();
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String email = rs.getString("email");
                String city = rs.getString("city");
                String phonenumber = rs.getString("phonenumber");
                String institution = rs.getString("institution");
                res.add(new Person(id, firstname, lastname, email, city, phonenumber, institution));
            }
            statement.close();
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logout(conn);
        return res;
    }

    /**
     * returns a map of principal investigator first+last names along with the pi_id. only returns
     * active investigators
     *
     * @return
     */
    public Map<String, Integer> getPersonNamesWithIDs() {
        String sql = "SELECT id, first_name, family_name FROM person WHERE active = 1";
        Map<String, Integer> res = new HashMap<String, Integer>();
        Connection conn = login();
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int pi_id = rs.getInt("id");
                String first = rs.getString("first_name");
                String last = rs.getString("family_name");
                res.put(first + " " + last, pi_id);
            }
            statement.close();
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logout(conn);
        return res;
    }

    public boolean genericInsertIntoTable(String table, Map<String, Object> values) {
        List<String> keys = new ArrayList<String>(values.keySet());
        String key_string = String.join(", ", keys);
        String[] ar = new String[keys.size()];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = "?";
        }
        String val_string = String.join(", ", ar);
        String sql = "INSERT INTO " + table + " (" + key_string + ") VALUES(" + val_string + ")";
        // return false;
        Connection conn = login();
        try (
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            for (String key : keys) {
                i++;
                Object val = values.get(key);
                if (val instanceof String)
                    statement.setString(i, (String) val);
                if (val instanceof Integer)
                    statement.setInt(i, (int) val);
            }
            boolean res = statement.execute();
            logout(conn);
            return res;
        } catch (SQLException e) {
            logger.error("SQL operation unsuccessful: " + e.getMessage());
        }
        logout(conn);
        return false;
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
        Connection conn = login();
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
                try {
                    tempFile = blobToTempFile(rs.getBlob("declaration_of_intent"));
                    System.out.println(tempFile.getTotalSpace());
                } catch (IOException e) {
                    logger.error("could not fetch blob as a file.");
                }
                Project project =
                        new Project(id, qbicID, dfgID, title, totalCost, description, tempFile,
                                classification, keywords, sequencingAim, contactPerson, topicalAssignment);

                System.out.println("Applicants: " + getPeopleForProjectID("project_has_applicants", "applicant_id", id).size());
                System.out.println("Projec id" + id);
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

    private File blobToTempFile(Blob blob) throws SQLException, IOException {
        InputStream in = blob.getBinaryStream();
        byte[] buff = new byte[4096];  // how much of the blob to read/write at a time
        int len = 0;
        File tempFile = File.createTempFile("declarationOfIntent", "xml");
        tempFile.deleteOnExit();
        OutputStream out = new FileOutputStream(tempFile);
        while ((len = in.read(buff)) != -1) {
            out.write(buff, 0, len);
        }
        out.close();
        return tempFile;
    }

    private List<Experiment> getExperimentsWithProjectID(int projectID) {
        List<Experiment> res = new ArrayList<>();
        String sql = "SELECT * from experiment WHERE project_id = " + projectID;
        Connection conn = login();
        try {

            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setInt(1, projectID);
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
        System.out.println("Experiments: " + res.size());
        return res;
    }

    private List<Batch> getBatchesForExperimentID(int expId) {
        List<Batch> res = new ArrayList<>();
        String sql = "SELECT * from batch WHERE experiment_id = " + expId;
        Connection conn = login();
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

    private List<Person> getPeopleForProjectID(String junctionTable, String junctionPersonID,
                                               int id) {

        String sql = "SELECT * FROM person INNER JOIN "+ junctionTable +" ON person.id = " + junctionTable + "." + junctionPersonID + " WHERE project_id = " + id;
        Connection conn = login();
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

    private int getTopicalAssignmentIDFromName(String name) {
        String contactSql = "SELECT id from topical_assignment WHERE name = ?";
        int res = -1;
        Connection conn = login();
        try {
            PreparedStatement statement = conn.prepareStatement(contactSql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                res = rs.getInt("id");
            }
            statement.close();
        } catch (SQLException e) {
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
        Connection conn = login();
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
        Connection conn = login();
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

    public boolean createProjectWithConnections(Project project) throws SQLException {
        Connection connection = login();
        boolean success = false;
        // We will commit all queries together later
        connection.setAutoCommit(false);

        try {
            // first we check if the transmitted contact person is in the db (has an id), otherwise we
            // create it
            Person contact = project.getContactPerson();
            int contactID = contact.getID();
            if (contactID < 0) {
                contactID = addPerson(contact, connection);
            }
            // project table insert
            int projectID = addProject(project, contactID, connection);
            // cooperation and applicants are added, if they don't exist (see above); connections are
            // added
            // to the respective tables as well
            for (Person applicant : project.getApplicants()) {
                int applicantID = applicant.getID();
                if (applicantID < 0) {
                    applicantID = addPerson(applicant, connection);
                }
                insertApplicantJunction(applicantID, projectID, connection);
                // insertJunctionTableConnection(TableName.project_has_applicants, "applicant_id",
                // "project_id", applicantID, projectID, connection);
            }
            for (Person cooperator : project.getCooperationPartners()) {
                int cooperatorID = cooperator.getID();
                if (cooperatorID < 0) {
                    cooperatorID = addPerson(cooperator, connection);
                }
                insertCooperationJunction(cooperatorID, projectID, connection);
            }
            // insert experiment tables and add respective batches
            for (Experiment exp : project.getExperiments()) {
                int expID = addExperiment(exp, projectID, connection);
                createBatches(exp.getBatches(), expID, connection);
            }
            connection.commit();
            logger.error("Project has been successfully added to all related tables.");
            success = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Exception occured while adding project. Rolling back.");
            connection.rollback();
        } finally {
            connection.close();
        }
        return success;
    }

    private int addProject(Project project, int contactID, Connection connection)
            throws SQLException, IOException {
        int res = -1;
        logger.info("Trying to add project " + project.getQbicID() + " to the DB");
        String sql =
                "INSERT INTO project (qbic_id, dfg_id, title, total_cost, description, declaration_of_intent, keywords, sequencing_aim, contact_person_id, topical_assignment_id, classification_id) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, project.getQbicID());
        statement.setString(2, project.getDfgID());
        statement.setString(3, project.getTitle());
        statement.setBigDecimal(4, project.getTotalCost());
        statement.setString(5, project.getDescription());
        statement.setString(6, project.getClassification());

        FileInputStream inputStream = null;
        try {
            if(project.getDeclarationOfIntent() != null) {
                inputStream = new FileInputStream(project.getDeclarationOfIntent());
                statement.setBlob(6, inputStream);
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            logger.error("could not save " + project.getDeclarationOfIntent().getName() + " as blob");
            e.printStackTrace();
        }

        statement.setString(7, project.getKeywords());
        statement.setString(8, project.getSequencingAim());
        statement.setInt(9, contactID);
        statement.setInt(10, getTopicalAssignmentIDFromName(project.getTopicalAssignment()));
        statement.setInt(11, Vocabulary.getClassificationID(project.getClassification()));
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

    private int addPerson(Person person, Connection connection) throws SQLException {
        int res = -1;
        logger.info("Trying to add person " + person.getFirstName() + " " + person.getLastName()
                + " to the DB");
        String sql = "INSERT INTO person (lastname, firstname, institution, city, email, phonenumber) "
                + "VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, person.getLastName());
        statement.setString(2, person.getFirstName());
        statement.setString(3, person.getInstitution());
        statement.setString(4, person.getCity());
        statement.setString(5, person.getEmail());
        statement.setString(6, person.getPhone());
        statement.execute();
        ResultSet rs = statement.getGeneratedKeys();
        statement.close();
        if (rs.next()) {
            res = rs.getInt(1);
        }
        return res;
    }

    private void insertApplicantJunction(int applicantID, int projectID, Connection connection)
            throws SQLException {
        logger.info("Adding connection between applicant and project");
        String sql = "INSERT INTO project_has_applicants (applicant_id, project_id) " + "VALUES(?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, applicantID);
        statement.setInt(2, projectID);
        statement.execute();
        statement.close();
    }

    private void insertCooperationJunction(int coopID, int projectID, Connection connection)
            throws SQLException {
        logger.info("Adding connection between cooperator and project");
        String sql =
                "INSERT INTO project_has_cooperation_partners (cooperation_partner_id, project_id) "
                        + "VALUES(?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, coopID);
        statement.setInt(2, projectID);
        statement.execute();
        statement.close();
    }

    private void endQuery(Connection c, PreparedStatement p) {
        if (p != null)
            try {
                p.close();
            } catch (Exception e) {
                logger.error("PreparedStatement close problem");
            }
        if (c != null)
            try {
                logout(c);
            } catch (Exception e) {
                logger.error("Database Connection close problem");
            }
    }

    public Map<String, Integer> fetchPeople() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            map = getPersonNamesWithIDs();
        } catch (NullPointerException e) {
            map.put("No Connection", -1);
        }
        return map;
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
