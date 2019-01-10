package life.qbic.portal.view.Form;

import com.vaadin.ui.*;

import java.util.stream.IntStream;

public class FormLayout extends TabSheet {

    private final ContactPersonForm contactPersonForm;
    private final ApplicantForm applicantForm;
    private final ProjectForm projectForm;
    private final ExperimentForm experimentForm;

    private final Button saveEntries;

    public FormLayout() {


        this.contactPersonForm = new ContactPersonForm();
        this.applicantForm = new ApplicantForm();
        IntStream.range(0,5).forEach(i -> addApplicantForm());

        this.projectForm = new ProjectForm();

        this.experimentForm = new ExperimentForm();
        this.saveEntries = new Button("Save");

        this.addTab(new VerticalLayout(contactPersonForm,applicantForm), "Participants");
        this.addTab(projectForm, "Project Info");
        this.addTab(experimentForm, "Experiment");
        //this.addComponents(

        //                    saveEntries);
        //this.setComponentAlignment(uploadAttachment, Alignment.BOTTOM_RIGHT);
        //this.setComponentAlignment(saveEntries, Alignment.BOTTOM_RIGHT);

        //this.setSpacing(true);
    }

    public void addApplicantForm() {
        this.applicantForm.addRow();
    }


    public Button getSaveEntries() {
        return saveEntries;
    }

    public ProjectForm getProjectForm() {
        return projectForm;
    }
}
