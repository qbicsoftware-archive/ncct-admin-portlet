package life.qbic.portal.model.db;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @author afriedrich
 */

public class Project {

    private int id;
    private String qbicID;
    private String dfgID;
    private String title;
    private BigDecimal totalCost;
    private String description;
    private File declarationOfIntent;
    private String classification;
    private String keywords;
    private String sequencingAim;
    private Person contactPerson;
    private String topicalAssignment;
    private List<Person> cooperationPartners;
    private List<Person> applicants;
    private List<Experiment> experiments;

    public Project(int id, String qbicID, String dfgID, String title, BigDecimal totalCost,
                   String description, File declarationOfIntent, String classification, String keywords,
                   String sequencingAim, Person contactPerson, String topicalAssignment) {
        super();
        this.id = id;
        this.qbicID = qbicID;
        this.dfgID = dfgID;
        this.title = title;
        this.totalCost = totalCost;
        this.description = description;
        this.declarationOfIntent = declarationOfIntent;
        this.classification = classification;
        this.keywords = keywords;
        this.sequencingAim = sequencingAim;
        this.contactPerson = contactPerson;
        this.topicalAssignment = topicalAssignment;
        this.cooperationPartners = new ArrayList<>();
        this.applicants = new ArrayList<>();
        this.experiments = new ArrayList<>();
    }

    public Project(String qbicID, String dfgID, String title, String totalCost,
                   String description, File declarationOfIntent, String classification, String keywords,
                   String sequencingAim, Person contactPerson, String topicalAssignment) {
        this(-1, qbicID, dfgID, title, new BigDecimal(totalCost), description, declarationOfIntent, classification, keywords, sequencingAim, contactPerson, topicalAssignment);
    }

    public Project(String dfgID, String title, String totalCost,
                   String description, File declarationOfIntent, String classification, String keywords,
                   String sequencingAim, Person contactPerson, String topicalAssignment) {
        this(-1, "", dfgID, title, new BigDecimal(totalCost), description, declarationOfIntent, classification, keywords, sequencingAim, contactPerson, topicalAssignment);
    }

    public void addExperiment(Experiment experiment) {
        experiments.add(experiment);
    }

    public List<Experiment> getExperiments() {
        return experiments;
    }

    public void setExperiments(List<Experiment> experiments) {
        this.experiments = experiments;
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

    public File getDeclarationOfIntent() {
        return declarationOfIntent;
    }

    public void setDeclarationOfIntent(File declarationOfIntent) {
        this.declarationOfIntent = declarationOfIntent;
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

    public void setApplicants(List<Person> applicants) {
        this.applicants = applicants;
    }

    public void setCooperationPartners(List<Person> partners) {
        this.cooperationPartners = partners;
    }


}
