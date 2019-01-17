package life.qbic.portal.view.Form;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.util.stream.IntStream;

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

    public FormLayout() {

        this.tabSheet = new TabSheet();
        this.tabSheet.addStyleName("framed");
        this.contactPersonForm = new ContactPersonForm();
        this.applicantForm = new PersonForm("Applicants:");
        IntStream.range(0,1).forEach(i -> addApplicantForm());
        this.cooperationPartners = new PersonForm("Cooperation Partners:");
        IntStream.range(0,1).forEach(i -> addCooperationPartner());
        this.projectForm = new ProjectForm();

        this.experimentForm = new ExperimentForm();
        this.saveEntries = new Button("Save Project");
        this.saveEntries.addStyleName("corners");
        this.uploadAttachment = new Upload();
        this.addDoI = new Label("<b><u>Upload Declaration of Intent:</u></b>", ContentMode.HTML);

        VerticalLayout verticalLayout = new VerticalLayout(contactPersonForm,applicantForm, cooperationPartners);
        verticalLayout.setSpacing(true);
        this.tabSheet.addTab(verticalLayout, "Participants");
        this.tabSheet.addTab(projectForm, "Project Info");
        this.tabSheet.addTab(experimentForm, "Experiment");

        this.tabSheet.setSizeFull();
        projectForm.setMargin(true);
        experimentForm.setMargin(true);
        verticalLayout.setMargin(true);
        this.addComponents(tabSheet,
                            addDoI,
                            uploadAttachment,
                            saveEntries);


        this.setComponentAlignment(saveEntries, Alignment.BOTTOM_RIGHT);

        this.setSpacing(true);
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
