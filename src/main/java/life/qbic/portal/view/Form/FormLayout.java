package life.qbic.portal.view.Form;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

public class FormLayout extends VerticalLayout {

    private final TabSheet tabSheet;
    private final ContactPersonForm contactPersonForm;
    private final PersonForm applicantForm;
    private final PersonForm cooperationPartners;
    private final ProjectForm projectForm;
    private final ExperimentForm experimentForm;
    private final Label addDoI;
    private final Upload uploadAttachment;
    private final Button saveEntries;
    private final Label asterixExplained;

    public FormLayout() {

        this.contactPersonForm = new ContactPersonForm();

        this.applicantForm = new PersonForm("Applicants:");
        addApplicantForm();

        this.cooperationPartners = new PersonForm("Cooperation Partners:");
        addCooperationPartner();

        this.projectForm = new ProjectForm();
        projectForm.setMargin(true);

        this.experimentForm = new ExperimentForm();
        experimentForm.setMargin(true);

        this.saveEntries = new Button("Save Project");
        this.saveEntries.addStyleName("corners");

        this.uploadAttachment = new Upload();
        this.addDoI = new Label("<b><u>Upload Declaration of Intent:</u></b>", ContentMode.HTML);

        VerticalLayout verticalLayout = new VerticalLayout(contactPersonForm,applicantForm, cooperationPartners);
        verticalLayout.setSpacing(true);
        verticalLayout.setMargin(true);

        this.tabSheet = new TabSheet();
        this.tabSheet.addStyleName("framed");
        this.tabSheet.addTab(verticalLayout, "Participants");
        this.tabSheet.addTab(projectForm, "Project Info");
        this.tabSheet.addTab(experimentForm, "Experiment");
        this.tabSheet.setSizeFull();

        this.asterixExplained = new Label(" <font color='red'>*</font> Required fields", ContentMode.HTML);
        this.addComponents(tabSheet,
                            addDoI,
                            uploadAttachment,
                            saveEntries,
                            asterixExplained);


        this.setComponentAlignment(saveEntries, Alignment.BOTTOM_RIGHT);
        this.setSpacing(true);
        //TODO add explanation for red asterix, somehow it needs to be checked whther all fields are required when pressing save!


    }

    private void addApplicantForm() {
        this.applicantForm.addRow();
    }

    private void addCooperationPartner() {
        this.cooperationPartners.addRow();
    }

    public Button getSaveEntries() {
        return saveEntries;
    }

    public Upload getUploadAttachment() {
        return uploadAttachment;
    }

    public ExperimentForm getExperimentForm() {
        return experimentForm;
    }

    public ContactPersonForm getContactPersonForm() {
        return contactPersonForm;
    }

    public PersonForm getApplicantForm() {
        return applicantForm;
    }

    public PersonForm getCooperationPartners() {
        return cooperationPartners;
    }
}