package life.qbic.portal.model.db.operations;

import life.qbic.portal.model.db.elements.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public final class Utils {

    private static final Logger logger = LogManager.getLogger(Utils.class);



    static void logout(Connection conn) {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            logger.error("Logout failed!: " + e);
            e.printStackTrace();
        }
    }

    static Connection login(DBConfig config) {
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

    static int getTopicalAssignmentIDFromName(String name, DBConfig config) {
        String contactSql = "SELECT id from topical_assignment WHERE name = ?";
        int res = -1;
        Connection conn = Utils.login(config);
        try {
            PreparedStatement statement = conn.prepareStatement(contactSql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                res = rs.getInt("id");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
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

    static int addPerson(Person person, Connection connection) throws SQLException {
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

//    static void insertApplicantJunction(int applicantID, int projectID, Connection connection)
//            throws SQLException {
//        logger.info("Adding connection between applicant and project");
//        String sql = "INSERT INTO project_has_applicants (applicant_id, project_id) " + "VALUES(?, ?)";
//        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//        statement.setInt(1, applicantID);
//        statement.setInt(2, projectID);
//        statement.execute();
//        statement.close();
//    }

    static void insertTableJunctionPersonAssociations(int coopID, int projectID, Connection connection, TableName tableName, String personID)
            throws SQLException {
        logger.info("Adding connection between cooperator and project");
        String sql =
                "INSERT INTO " + tableName.toString() + " (" + personID + ", project_id) "
                        + "VALUES(?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, coopID);
        statement.setInt(2, projectID);
        statement.execute();
        statement.close();
    }

    static void createBatches(List<Batch> batches, int expID, Connection connection)
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

    static int addExperiment(Experiment exp, int projectID, Connection connection)
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
