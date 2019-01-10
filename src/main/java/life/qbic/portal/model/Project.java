package life.qbic.portal.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Project {

  private int id;
  private String qbicID;
  private String dfgID;
  private String title;
  private BigDecimal totalCost;
  private String description;
  private String declarationOfInterest;
  private String classification;
  private String keywords;
  private String sequencingAim;
  private Person contactPerson;
  private String topicalAssignment;
  private List<Person> cooperationPartners;
  private List<Person> applicants;
  private List<Experiment> experiments;

  public Project(int id, String qbicID, String dfgID, String title, BigDecimal totalCost,
      String description, String declarationOfInterest, String classification, String keywords,
      String sequencingAim, Person contactPerson, String topicalAssignment) {
    super();
    this.id = id;
    this.qbicID = qbicID;
    this.dfgID = dfgID;
    this.title = title;
    this.totalCost = totalCost;
    this.description = description;
    this.declarationOfInterest = declarationOfInterest;
    this.classification = classification;
    this.keywords = keywords;
    this.sequencingAim = sequencingAim;
    this.contactPerson = contactPerson;
    this.topicalAssignment = topicalAssignment;
    this.cooperationPartners = new ArrayList<>();
    this.applicants = new ArrayList<>();
    this.experiments = new ArrayList<>();
  }
  
  public void addExperiment(Experiment experiment) {
    experiments.add(experiment);
  }
  
  public List<Experiment> getExperiments() {
    return experiments;
  }

  public void addCooperationPartner(Person partner) {
    cooperationPartners.add(partner);
  }

  public void addApplicant(Person applicant) {
    applicants.add(applicant);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getQbicID() {
    return qbicID;
  }

  public void setQbicID(String qbicID) {
    this.qbicID = qbicID;
  }

  public String getDfgID() {
    return dfgID;
  }

  public void setDfgID(String dfgID) {
    this.dfgID = dfgID;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public BigDecimal getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(BigDecimal totalCost) {
    this.totalCost = totalCost;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDeclarationOfInterest() {
    return declarationOfInterest;
  }

  public void setDeclarationOfInterest(String declarationOfInterest) {
    this.declarationOfInterest = declarationOfInterest;
  }

  public String getClassification() {
    return classification;
  }

  public void setClassification(String classification) {
    this.classification = classification;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getSequencingAim() {
    return sequencingAim;
  }

  public void setSequencingAim(String sequencingAim) {
    this.sequencingAim = sequencingAim;
  }

  public Person getContactPerson() {
    return contactPerson;
  }

  public void setContactPerson(Person contactPerson) {
    this.contactPerson = contactPerson;
  }

  public String getTopicalAssignment() {
    return topicalAssignment;
  }

  public void setTopicalAssignment(String topicalAssignment) {
    this.topicalAssignment = topicalAssignment;
  }

  public List<Person> getCooperationPartners() {
    return cooperationPartners;
  }

  public List<Person> getApplicants() {
    return applicants;
  }

  
}
