package life.qbic.portal.view.Form;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

/**
 * @author fhanssen
 */


public class FormLayout extends VerticalLayout {

    private final TabSheet tabSheet;
    private final ContactPersonForm contactPersonForm;
    private final PersonForm applicantForm;
    private final PersonForm cooperationPartners;
    private final VerticalLayout personLayout;
    private final ProjectForm projectForm;
    private final ExperimentForm experimentForm;
    //private final Label addDoI;
    //private final Upload uploadAttachment;
    private final Button saveEntries;
    private final Button cancel;
    private final Label asterixExplained;
    private final boolean isEdit;
    private final Button deleteApplicant;
    private final Button deleteCooperationPartners;

    public FormLayout(boolean isEdit) {

        this.isEdit = isEdit;

        this.contactPersonForm = new ContactPersonForm();

        this.applicantForm = new PersonForm("Applicants:");
        this.cooperationPartners = new PersonForm("Cooperation Partners:");


        this.projectForm = new ProjectForm();
        projectForm.setMargin(true);

        this.experimentForm = new ExperimentForm();
        experimentForm.setMargin(true);

        if(isEdit){
            this.saveEntries = new Button("Update Project");

        }else {
            this.saveEntries = new Button("Save Project");
            this.applicantForm.addRow();
            this.cooperationPartners.addRow();
            this.experimentForm.addEmptyExperimentRow();
        }
        this.saveEntries.addStyleName("corners");

        this.cancel = new Button("Cancel");
        this.cancel.addStyleName("corners");


//        this.uploadAttachment = new Upload();
//        this.uploadAttachment.setImmediate(true);
//        this.uploadAttachment.addStyleName("corners");
//        this.addDoI = new Label("Declaration of Intent (optional)", ContentMode.HTML);
//        this.addDoI.setIcon(FontAwesome.UPLOAD);

//        HorizontalLayout uploadLayout = new HorizontalLayout(addDoI, uploadAttachment);
//        uploadLayout.setSpacing(true);
//        uploadLayout.setComponentAlignment(uploadAttachment, Alignment.BOTTOM_RIGHT);

        this.deleteApplicant = new Button("Delete");
        this.deleteCooperationPartners = new Button("Delete");

        personLayout = new VerticalLayout(contactPersonForm, applicantForm, deleteApplicant, cooperationPartners, deleteCooperationPartners);
        personLayout.setSpacing(true);
        personLayout.setMargin(true);
        deleteApplicant.setVisible(false);
        deleteCooperationPartners.setVisible(false);

        this.tabSheet = new TabSheet();
        this.tabSheet.addStyleName("framed");
        this.tabSheet.addTab(personLayout, "Participants");
        this.tabSheet.addTab(projectForm, "Project Info");
        this.tabSheet.addTab(experimentForm, "Experiment");
        this.tabSheet.setSizeFull();

        this.asterixExplained = new Label(" <font color='red'>*</font> Required fields", ContentMode.HTML);

        HorizontalLayout buttons = new HorizontalLayout(cancel, saveEntries);
        buttons.setSpacing(true);

        this.addComponents(tabSheet,
                //uploadLayout,
                buttons,
                asterixExplained);

        //this.setComponentAlignment(uploadLayout, Alignment.BOTTOM_RIGHT);
        this.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
        this.setSpacing(true);
        this.setMargin(true);

    }


    public Button getSaveEntries() {
        return saveEntries;
    }

    public Button getCancel() {
        return cancel;
    }

    //public Upload getUploadAttachment() {
//        return uploadAttachment;
//    }

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

    public ProjectForm getProjectForm() {
        return projectForm;
    }

//    public Label getAddDoI() {
//        return addDoI;
//    }

    public void addDeleteApplicantButton(){
        deleteApplicant.setVisible(true);
    }

    public void addDeleteCoopPartnerButton(){
        deleteCooperationPartners.setVisible(true);
    }

    public void removeDeleteApplicantButton(){
        deleteApplicant.setVisible(false);
    }

    public void removeDeleteCoopPartnerButton(){
        deleteCooperationPartners.setVisible(false);
    }

    public Button getDeleteApplicant() {
        return deleteApplicant;
    }

    public Button getDeleteCooperationPartners() {
        return deleteCooperationPartners;
    }
}
