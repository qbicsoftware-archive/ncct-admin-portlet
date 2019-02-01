package life.qbic.portal.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
      // TODO Auto-generated catch block
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
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return conn;
  }

  // private Connection login(String db) {
  // String DB_URL = "jdbc:mariadb://" + config.getHostname() + ":" + config.getPort() + "/" + db;
  //
  // Connection conn = null;
  //
  // try {
  // Class.forName("org.mariadb.jdbc.Driver");
  // conn = DriverManager.getConnection(DB_URL, config.getUsername(), config.getPassword());
  // } catch (Exception e) {
  // // TODO Auto-generated catch block
  // e.printStackTrace();
  // }
  // return conn;
  // }

  // public Person getPersonForProject(String projectIdentifier, String role) {
  // String sql =
  // "SELECT * FROM persons LEFT JOIN projects_persons ON persons.id = projects_persons.person_id "
  // + "LEFT JOIN projects ON projects_persons.project_id = projects.id WHERE "
  // + "projects.openbis_project_identifier = ? AND projects_persons.project_role = ?";
  // Person res = null;
  //
  // Connection conn = login();
  // try (PreparedStatement statement = conn.prepareStatement(sql)) {
  // statement.setString(1, projectIdentifier);
  // statement.setString(2, role);
  //
  // ResultSet rs = statement.executeQuery();
  //
  // while (rs.next()) {
  // String zdvID = rs.getString("username");
  // String first = rs.getString("first_name");
  // String last = rs.getString("family_name");
  // String email = rs.getString("email");
  // String tel = rs.getString("phone");
  // int instituteID = -1;// TODO fetch correct id
  // res = new Person(zdvID, first, last, email, tel, instituteID);
  // }
  // } catch (SQLException e) {
  // e.printStackTrace();
  // logout(conn);
  // // LOGGER.debug("Project not associated with Investigator. PI will be set to 'Unknown'");
  // }
  //
  // logout(conn);
  // return res;
  // }

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
  //
  // public int isProjectInDB(String projectIdentifier) {
  // logger.info("Looking for project " + projectIdentifier + " in the DB");
  // String sql = "SELECT * from projects WHERE openbis_project_identifier = ?";
  // int res = -1;
  // Connection conn = login();
  // try {
  // PreparedStatement statement = conn.prepareStatement(sql);
  // statement.setString(1, projectIdentifier);
  // ResultSet rs = statement.executeQuery();
  // if (rs.next()) {
  // res = rs.getInt("id");
  // logger.info("project found!");
  // }
  // } catch (SQLException e) {
  // logger.error("SQL operation unsuccessful: " + e.getMessage());
  // e.printStackTrace();
  // }
  // logout(conn);
  // return res;
  // }

  // public int addProjectToDB(String projectIdentifier, String projectName) {
  // int exists = isProjectInDB(projectIdentifier);
  // if (exists < 0) {
  // logger.info("Trying to add project " + projectIdentifier + " to the person DB");
  // String sql = "INSERT INTO projects (openbis_project_identifier, short_title) VALUES(?, ?)";
  // Connection conn = login();
  // try (PreparedStatement statement =
  // conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
  // statement.setString(1, projectIdentifier);
  // statement.setString(2, projectName);
  // statement.execute();
  // ResultSet rs = statement.getGeneratedKeys();
  // if (rs.next()) {
  // logout(conn);
  // logger.info("Successful.");
  // return rs.getInt(1);
  // }
  // } catch (SQLException e) {
  // logger.error("SQL operation unsuccessful: " + e.getMessage());
  // e.printStackTrace();
  // }
  // logout(conn);
  // return -1;
  // }
  // return exists;
  // }

  // public boolean hasPersonRoleInProject(int personID, int projectID, String role) {
  // logger.info("Checking if person already has this role in the project.");
  // String sql =
  // "SELECT * from projects_persons WHERE person_id = ? AND project_id = ? and project_role = ?";
  // boolean res = false;
  // Connection conn = login();
  // try {
  // PreparedStatement statement = conn.prepareStatement(sql);
  // statement.setInt(1, personID);
  // statement.setInt(2, projectID);
  // statement.setString(3, role);
  // ResultSet rs = statement.executeQuery();
  // if (rs.next()) {
  // res = true;
  // logger.info("person already has this role!");
  // }
  // } catch (SQLException e) {
  // logger.error("SQL operation unsuccessful: " + e.getMessage());
  // e.printStackTrace();
  // }
  // logout(conn);
  // return res;
  // }

  // public void addPersonToProject(int projectID, int personID, String role) {
  // if (!hasPersonRoleInProject(personID, projectID, role)) {
  // logger.info("Trying to add person with role " + role + " to a project.");
  // String sql =
  // "INSERT INTO projects_persons (project_id, person_id, project_role) VALUES(?, ?, ?)";
  // Connection conn = login();
  // try (PreparedStatement statement =
  // conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
  // statement.setInt(1, projectID);
  // statement.setInt(2, personID);
  // statement.setString(3, role);
  // statement.execute();
  // logger.info("Successful.");
  // } catch (SQLException e) {
  // logger.error("SQL operation unsuccessful: " + e.getMessage());
  // e.printStackTrace();
  // }
  // logout(conn);
  // }
  // }

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
        String classification = rs.getString("classification");
        String declarationOfInterest = rs.getString("declaration_of_intent");
        String keywords = rs.getString("keywords");
        String sequencingAim = rs.getString("sequencing_aim");
        int contactID = rs.getInt("contact_person_id");
        int topicalID = rs.getInt("topical_assignment_id");

        rs.close();
        statement.close();
        Person contactPerson = getPersonFromID(contactID);
        String topicalAssignment = getTopicalAssignmentNameFromID(topicalID);

        Project project =
            new Project(id, qbicID, dfgID, title, totalCost, description, declarationOfInterest,
                classification, keywords, sequencingAim, contactPerson, topicalAssignment);
        project.setApplicants(getPeopleForProjectID("project_has_applicants", "applicant_id", id));
        project.setCooperationPartners(getPeopleForProjectID("project_has_cooperation_partners",
            "cooperation_partner_id", id));
        project.setExperiments(getExperimentsWithProjectID(id));
        res.add(project);
      }
    } catch (SQLException e) {
      // TODO: handle exception
    }
    return res;
  }

  private List<Experiment> getExperimentsWithProjectID(int projectID) {
    List<Experiment> res = new ArrayList<>();
    String sql = "SELECT * from experiment WHERE project_id = ";
    Connection conn = login();
    try {

      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setInt(1, projectID);
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
        int instrumentID = rs.getInt("technology_instument_id");
        int nucleicAcidID = rs.getInt("nucleic_acid_id");
        int libraryID = rs.getInt("library_id");

        String material = Vocabulary.getMaterialName(materialID);
        String species = Vocabulary.getSpeciesName(speciesID);
        String technology = Vocabulary.getTechnologyTypeName(techID);
        String instrument = Vocabulary.getTechInstrumentName(instrumentID);
        String nucleicAcid = Vocabulary.getNucleicAcidName(nucleicAcidID);
        String library = Vocabulary.getMaterialName(libraryID);
        rs.close();
        statement.close();

        Experiment exp = new Experiment(id, numOfSamples, coverage, costs, genomeSize, material,
            species, technology, instrument, nucleicAcid, library);
        exp.setBatches(getBatchesForExperimentID(id));
        res.add(exp);
      }
    } catch (SQLException e) {
      // TODO: handle exception
    }
    return res;
  }

  private List<Batch> getBatchesForExperimentID(int expId) {
    List<Batch> res = new ArrayList<>();
    String sql = "SELECT * from batch WHERE experiment_id = ";
    Connection conn = login();
    try {

      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setInt(1, expId);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        int id = rs.getInt("id");
        Date deliveryDate = rs.getDate("estimated_delivery_date");
        int numberOfSamples = rs.getInt("number_samples");
        rs.close();
        statement.close();

        Batch b = new Batch(id, deliveryDate, numberOfSamples);
        res.add(b);
      }
    } catch (SQLException e) {
      // TODO: handle exception
    }
    return res;
  }

  private List<Person> getPeopleForProjectID(String junctionTable, String junctionPersonID,
      int id) {
    String sql = "SELECT * FROM person LEFT JOIN ? ON person.id = ?.? WHERE project_id = ?";
    Connection conn = login();
    List<Person> res = new ArrayList<Person>();
    try {
      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setString(1, junctionTable);
      statement.setString(2, junctionTable);
      statement.setString(3, junctionPersonID);
      statement.setInt(4, id);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        res.add(parsePerson(rs));
      }
    } catch (SQLException e) {

    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
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
        // TODO Auto-generated catch block
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
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
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
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
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
      logger.error("project has been successfully added to all related tables.");
      success = true;
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error("exception occured while adding project. rolling back.");
      connection.rollback();
    } finally {
      connection.setAutoCommit(true);
      connection.close();
    }
    return success;
  }

  private int addProject(Project project, int contactID, Connection connection)
      throws SQLException {
    int res = -1;
    logger.info("Trying to add project " + project.getQbicID() + " to the DB");
    String sql =
        "INSERT INTO project (qbic_id, dfg_id, title, total_cost, description, classification, declaration_of_intent, keywords, sequencing_aim, contact_person_id, topical_assignment_id) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    statement.setString(1, project.getQbicID());
    statement.setString(2, project.getDfgID());
    statement.setString(3, project.getTitle());
    statement.setBigDecimal(4, project.getTotalCost());
    statement.setString(5, project.getDescription());
    statement.setString(6, project.getClassification());
    statement.setString(7, project.getDeclarationOfInterest());
    statement.setString(8, project.getKeywords());
    statement.setString(9, project.getSequencingAim());
    statement.setInt(10, contactID);
    statement.setInt(11, getTopicalAssignmentIDFromName(project.getTopicalAssignment()));
    statement.execute();
    ResultSet rs = statement.getGeneratedKeys();
    statement.close();
    if (rs.next()) {
      res = rs.getInt(1);
    }
    return res;
  }

  private void addFileToProject(int projectID, File file)
      throws FileNotFoundException, SQLException {
    Connection connection = login();
    String sql = "UPDATE project SET declaration_of_intent=? WHERE id=?";
    FileInputStream inputStream = new FileInputStream(file);
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setBlob(1, inputStream);
      statement.close();
    } catch (SQLException e) {
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  private Date convertDate(java.util.Date d) {
    return new Date(d.getTime());
  }
  
  private void createBatches(List<Batch> batches, int expID, Connection connection)
      throws SQLException {
    logger.info("Trying to add sample batches to the DB");
    String sql = "INSERT INTO batch (estimated_delivery_date, number_samples, experiment_id) "
        + "VALUES(?, ?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    for (Batch batch : batches) {
      Date sqlDate = convertDate(batch.getEstimatedDelivery());
      statement.setDate(1, sqlDate);
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
        "INSERT INTO experiment (number_of_samples, coverage, costs, genome_size, material_id, species_id, technology_type_id, technology_instrument_id, nucleic_acid_id, library_id, project_id) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    statement.setInt(1, exp.getNumOfSamples());
    statement.setString(2, exp.getCoverage());
    statement.setBigDecimal(3, exp.getCosts());
    statement.setString(4, exp.getGenomeSize());
    statement.setInt(5, Vocabulary.getMaterialID(exp.getMaterial()));
    statement.setInt(6, Vocabulary.getSpeciesID(exp.getSpecies()));
    statement.setInt(7, Vocabulary.getTechnologyTypeID(exp.getTechnologyType()));
    statement.setInt(8, Vocabulary.getTechInstrumentID(exp.getInstrument()));
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
    statement.setString(3, person.getAffiliation());
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
    System.out.println(sql);
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
    System.out.println(sql);
    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    statement.setInt(1, coopID);
    statement.setInt(2, projectID);
    statement.execute();
    statement.close();
  }

  //
  // private void insertJunctionTableConnection(TableName t, String firstIDName, String
  // secondIDName,
  // int firstID, int secondID, Connection connection) throws SQLException {
  // logger.info("Adding connection between " + firstIDName + " and " + secondIDName);
  // String sql = "INSERT INTO " + t.toString() + " (?, ?) " + "VALUES(?, ?)";
  // System.out.println(sql);
  // PreparedStatement statement = connection.prepareStatement(sql,
  // Statement.RETURN_GENERATED_KEYS);
  // statement.setString(1, firstIDName);
  // statement.setString(2, secondIDName);
  // statement.setInt(3, firstID);
  // statement.setInt(4, secondID);
  // statement.execute();
  // statement.close();
  // }



  // public int addExperimentToDB(String id) {
  // int exists = isExpInDB(id);
  // if (exists < 0) {
  // String sql = "INSERT INTO experiments (openbis_experiment_identifier) VALUES(?)";
  // Connection conn = login();
  // try (PreparedStatement statement =
  // conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
  // statement.setString(1, id);
  // statement.execute();
  // ResultSet rs = statement.getGeneratedKeys();
  // if (rs.next()) {
  // logout(conn);
  // return rs.getInt(1);
  // }
  // } catch (SQLException e) {
  // logger.error("Was trying to add experiment " + id + " to the person DB");
  // logger.error("SQL operation unsuccessful: " + e.getMessage());
  // }
  // logout(conn);
  // return -1;
  // }
  // logger.info("added experiment do mysql db");
  // return exists;
  // }
  //
  // private int isExpInDB(String id) {
  // logger.info("Looking for experiment " + id + " in the DB");
  // String sql = "SELECT * from experiments WHERE openbis_experiment_identifier = ?";
  // int res = -1;
  // Connection conn = login();
  // try {
  // PreparedStatement statement = conn.prepareStatement(sql);
  // statement.setString(1, id);
  // ResultSet rs = statement.executeQuery();
  // if (rs.next()) {
  // logger.info("experiment found!");
  // res = rs.getInt("id");
  // }
  // } catch (SQLException e) {
  // logger.error("SQL operation unsuccessful: " + e.getMessage());
  // e.printStackTrace();
  // }
  // logout(conn);
  // return res;
  // }
  //
  // public void addPersonToExperiment(int expID, int personID, String role) {
  // if (expID == 0 || personID == 0)
  // return;
  //
  // if (!hasPersonRoleInExperiment(personID, expID, role)) {
  // logger.info("Trying to add person with role " + role + " to an experiment.");
  // String sql =
  // "INSERT INTO experiments_persons (experiment_id, person_id, experiment_role) VALUES(?, ?, ?)";
  // Connection conn = login();
  // try (PreparedStatement statement =
  // conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
  // statement.setInt(1, expID);
  // statement.setInt(2, personID);
  // statement.setString(3, role);
  // statement.execute();
  // logger.info("Successful.");
  // } catch (SQLException e) {
  // logger.error("SQL operation unsuccessful: " + e.getMessage());
  // e.printStackTrace();
  // }
  // logout(conn);
  // }
  // }
  //
  // private boolean hasPersonRoleInExperiment(int personID, int expID, String role) {
  // logger.info("Checking if person already has this role in the experiment.");
  // String sql =
  // "SELECT * from experiments_persons WHERE person_id = ? AND experiment_id = ? and
  // experiment_role = ?";
  // boolean res = false;
  // Connection conn = login();
  // try {
  // PreparedStatement statement = conn.prepareStatement(sql);
  // statement.setInt(1, personID);
  // statement.setInt(2, expID);
  // statement.setString(3, role);
  // ResultSet rs = statement.executeQuery();
  // if (rs.next()) {
  // res = true;
  // logger.info("person already has this role!");
  // }
  // } catch (SQLException e) {
  // logger.error("SQL operation unsuccessful: " + e.getMessage());
  // e.printStackTrace();
  // }
  // logout(conn);
  // return res;
  // }

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
        getVocabularyMapForTable(TableName.technology_instrument),
        getVocabularyMapForTable(TableName.species),
        getVocabularyMapForTable(TableName.technology_type),
        getVocabularyMapForTable(TableName.material),
        getVocabularyMapForTable(TableName.nucleic_acid));
  }

  //
  // public String getInvestigatorForProject(String projectCode) {
  // String id_query = "SELECT pi_id FROM projects WHERE project_code = " + projectCode;
  // String id = "";
  // Connection conn = login();
  // try (PreparedStatement statement = conn.prepareStatement(id_query)) {
  // ResultSet rs = statement.executeQuery();
  // while (rs.next()) {
  // id = Integer.toString(rs.getInt("pi_id"));
  // }
  // statement.close();
  // } catch (SQLException e) {
  // e.printStackTrace();
  // }
  //
  // String sql = "SELECT first_name, last_name FROM project_investigators WHERE pi_id = " + id;
  // String fullName = "";
  // try (PreparedStatement statement = conn.prepareStatement(sql)) {
  // ResultSet rs = statement.executeQuery();
  // while (rs.next()) {
  // String first = rs.getString("first_name");
  // String last = rs.getString("last_name");
  // fullName = first + " " + last;
  // }
  // statement.close();
  // } catch (SQLException e) {
  // e.printStackTrace();
  // }
  // logout(conn);
  // return fullName;
  // }

  // /**
  // * add a new institute to the database. not in use yet since the schema is old
  // *
  // * @param name
  // * @param street
  // * @param zip
  // * @param city
  // * @return
  // */
  // public int addNewInstitute(String name, String street, String zip, String city) {
  // String sql = "insert into institutes (name, street, zip_code, city) " + "VALUES(?, ?, ?, ?)";
  // Connection conn = login();
  // try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
  // {
  // statement.setString(1, name);
  // statement.setString(2, street);
  // statement.setString(3, zip);
  // statement.setString(4, city);
  // statement.execute();
  // ResultSet rs = statement.getGeneratedKeys();
  // if (rs.next()) {
  // return rs.getInt(1);
  // }
  // logger.info("Successful.");
  // } catch (SQLException e) {
  // logger.error("SQL operation unsuccessful: " + e.getMessage());
  // e.printStackTrace();
  // }
  // logout(conn);
  // return -1;
  // }

  // /**
  // * add a person whose institude id is known. not in use yet since the schema is old
  // *
  // * @return
  // */
  // public int addNewPersonWithInstituteID(Person p) {
  // String sql =
  // "insert into project_investigators (zdvID, first_name, last_name, email, phone, institute_id,
  // active) "
  // + "VALUES(?, ?, ?, ?, ?, ?, ?)";
  // Connection conn = login();
  // try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
  // {
  // statement.setString(1, p.getZdvID());
  // statement.setString(2, p.getFirstName());
  // statement.setString(3, p.getLastName());
  // statement.setString(4, p.getEmail());
  // statement.setString(5, p.getPhone());
  // statement.setInt(6, p.getInstituteID());
  // statement.setInt(7, 1);
  // statement.execute();
  // ResultSet rs = statement.getGeneratedKeys();
  // if (rs.next()) {
  // return rs.getInt(1);
  // }
  // logger.info("Successful.");
  // } catch (SQLException e) {
  // logger.error("SQL operation unsuccessful: " + e.getMessage());
  // e.printStackTrace();
  // }
  // logout(conn);
  // return -1;
  // }

  // public int addNewPerson(PersonWithAdress p) {
  // String sql =
  // "insert into project_investigators (zdvID, first_name, last_name, email, street, zip_code,
  // city, phone, institute, active) "
  // + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  // Connection conn = login();
  // try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
  // {
  // statement.setString(1, p.getZdvID());
  // statement.setString(2, p.getFirstName());
  // statement.setString(3, p.getLastName());
  // statement.setString(4, p.getEmail());
  // statement.setString(5, p.getStreet());
  // statement.setString(6, p.getZipCode());
  // statement.setString(7, p.getCity());
  // statement.setString(8, p.getPhone());
  // statement.setString(9, p.getInstitute());
  // statement.setInt(10, 1);
  // statement.execute();
  // ResultSet rs = statement.getGeneratedKeys();
  // if (rs.next()) {
  // return rs.getInt(1);
  // }
  // logger.info("Successful.");
  // } catch (SQLException e) {
  // logger.error("SQL operation unsuccessful: " + e.getMessage());
  // e.printStackTrace();
  // }
  // logout(conn);
  // return -1;
  // }

  // public void printPeople() {
  // String sql = "SELECT * FROM project_investigators";
  // Connection conn = login();
  // try (PreparedStatement statement = conn.prepareStatement(sql)) {
  // ResultSet rs = statement.executeQuery();
  // while (rs.next()) {
  // System.out.println(Integer.toString(rs.getInt(1)) + " " + rs.getString(2) + " "
  // + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " "
  // + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " "
  // + rs.getString(9) + " " + rs.getString(10) + " " + rs.getString(11));
  // }
  // statement.close();
  // } catch (SQLException e) {
  // e.printStackTrace();
  // }
  // }
  //
  // public void printProjects() {
  // String sql = "SELECT pi_id, project_code FROM projects";
  // Connection conn = login();
  // try (PreparedStatement statement = conn.prepareStatement(sql)) {
  // ResultSet rs = statement.executeQuery();
  // while (rs.next()) {
  // int pi_id = rs.getInt("pi_id");
  // String first = rs.getString("project_code");
  // System.out.println(pi_id + first);
  // }
  // statement.close();
  // } catch (SQLException e) {
  // e.printStackTrace();
  // }
  // }

}
