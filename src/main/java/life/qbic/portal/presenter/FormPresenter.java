package life.qbic.portal.presenter;

import com.vaadin.data.Item;
import com.vaadin.server.Page;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import life.qbic.portal.model.db.elements.Batch;
import life.qbic.portal.model.db.elements.Experiment;
import life.qbic.portal.model.db.elements.Person;
import life.qbic.portal.model.db.elements.Project;
import life.qbic.portal.view.Form.FormLayout;

import java.io.File;
import java.util.Date;

/**
 * @author fhanssen
 */


public class FormPresenter {

    private final FormLayout formLayout;
    private final MainPresenter mainPresenter;
    private final ExperimentPresenter experimentPresenter;
    private final PersonFormPresenter personFormPresenter;
    private final ProjectFormPresenter projectFormPresenter;
    private File tempFile;
    private final boolean isEdit;
    private Project oldProject;

    public FormPresenter(MainPresenter mainPresenter, boolean isEdit) {
        this.mainPresenter = mainPresenter;
        this.isEdit = isEdit;

        this.formLayout = new FormLayout(isEdit);

        this.experimentPresenter = new ExperimentPresenter(this);
        this.personFormPresenter = new PersonFormPresenter(this);
        this.projectFormPresenter = new ProjectFormPresenter(this);


        addListener();
    }

    private void addListener() {
    //    addUploadListener();
        addSaveEntryListener();
        addCancelListener();
    }


//    private void addUploadListener() {
//        this.formLayout.getUploadAttachment().setReceiver(this);
//        this.formLayout.getUploadAttachment().addSucceededListener(this);
//
//    }

    private void addSaveEntryListener() {
        this.formLayout.getSaveEntries().addClickListener(clickEvent -> {
            try {
                if (isEdit) {
                    this.mainPresenter.getDbDataUpdate().updateProjectWithConnection(oldProject, setData());

                } else {
                    this.mainPresenter.getDbDataInsert().createProjectWithConnections(setData());
                }
                this.mainPresenter.loadProjects();
                this.mainPresenter.displayProjects();
            } catch (Exception e) {
                Notification notification = new Notification("Couldn't store entry",
                        e.getMessage(),
                        Notification.Type.ERROR_MESSAGE,
                        true);
                notification.setDelayMsec(-1); //infinity
                notification.show(Page.getCurrent());
                e.printStackTrace();
            }
        });
    }

    private void addCancelListener() {
        this.formLayout.getCancel().addClickListener(clickEvent -> {
            this.mainPresenter.loadProjects();
            this.mainPresenter.displayProjects();
        });

    }

    public FormLayout getFormLayout() {
        return this.formLayout;
    }

//    @Override
//    public OutputStream receiveUpload(String filename, String mimeType) {
//        try {
//            tempFile = File.createTempFile(filename, "pdf");
//            tempFile.deleteOnExit();
//            return new FileOutputStream(tempFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

//    @Override
//    public void uploadSucceeded(Upload.SucceededEvent event) {
//        this.getFormLayout().getAddDoI().setCaption(tempFile.getName());
//    }

    private Project setData() throws Exception {

        Person contactPerson = mainPresenter.getDbDataRetrieval().getPerson(this.formLayout.getContactPersonForm().getFirstNameValue(),
                this.formLayout.getContactPersonForm().getLastNameValue(),
                this.formLayout.getContactPersonForm().getInstitutionValue(),
                this.formLayout.getContactPersonForm().getCityValue(),
                this.formLayout.getContactPersonForm().getEmailValue(),
                this.formLayout.getContactPersonForm().getPhoneNumberValue());


        Project project = new Project(this.formLayout.getProjectForm().getQbicIDValue(),
                this.formLayout.getProjectForm().getDfgIDValue(),
                this.formLayout.getProjectForm().getProjectTitleValue(),
                this.formLayout.getProjectForm().getTotalCostValue(),
                this.formLayout.getProjectForm().getProjectDescriptionValue(),
                tempFile,
                this.formLayout.getProjectForm().getClassificationValue(),
                this.formLayout.getProjectForm().getKeywordsValue(),
                this.formLayout.getProjectForm().getSequencingAimValue(),
                contactPerson,
                this.formLayout.getProjectForm().getTopicalAssignmentValue()
        );

        if (isEdit) {
            project.setId(oldProject.getId());
        }

        for (Object id : this.formLayout.getApplicantForm().getPersons().getContainerDataSource().getItemIds()) {

            Item item = this.formLayout.getApplicantForm().getPersons().getContainerDataSource().getItem(id);

            if (!((String) item.getItemProperty("Last Name").getValue()).isEmpty()) {
                Person applicant = mainPresenter.getDbDataRetrieval().getPerson((String) item.getItemProperty("First Name").getValue(),
                        (String) item.getItemProperty("Last Name").getValue(),
                        (String) item.getItemProperty("Institution").getValue(),
                (String) item.getItemProperty("City").getValue());
                project.addApplicant(applicant);
            }
        }

        for (Object id : this.formLayout.getCooperationPartners().getPersons().getContainerDataSource().getItemIds()) {
            Item item = this.formLayout.getCooperationPartners().getPersons().getContainerDataSource().getItem(id);

            if (!((String) item.getItemProperty("Last Name").getValue()).isEmpty()) {
                Person cooperationPartner = new Person((String) item.getItemProperty("First Name").getValue(),
                        (String) item.getItemProperty("Last Name").getValue(),
                        "",
                        (String) item.getItemProperty("City").getValue(),
                        "",
                        (String) item.getItemProperty("Institution").getValue());

                project.addCooperationPartner(cooperationPartner);

            }
        }


        for (Object id : this.formLayout.getExperimentForm().getAllExperiments().getContainerDataSource().getItemIds()) {
            Item item = this.formLayout.getExperimentForm().getAllExperiments().getContainerDataSource().getItem(id);

            if (!((String) item.getItemProperty("Read Type").getValue()).isEmpty()) {
                Experiment experiment = new Experiment(Integer.valueOf((String) item.getItemProperty("Number of Samples").getValue()),
                        (String) item.getItemProperty("Coverage(X)").getValue(),
                        (String) item.getItemProperty("Cost(EUR)").getValue(),
                        (String) item.getItemProperty("Genome Size(Gb)").getValue(),
                        (String) item.getItemProperty("Material").getValue(),
                        (String) item.getItemProperty("Species").getValue(),
                        (String) item.getItemProperty("Read Type").getValue(),
                        (String) item.getItemProperty("Instrument").getValue(),
                        (String) item.getItemProperty("Nucleic Acid").getValue(),
                        (String) item.getItemProperty("Library").getValue());

                for (Object batchId : ((Grid) this.formLayout.getExperimentForm().getBatches().getTab(((int) id) - 1).getComponent()).getContainerDataSource().getItemIds()) {

                    Item batchItem = ((Grid) this.formLayout.getExperimentForm().getBatches().getTab(((int) id) - 1).getComponent()).getContainerDataSource().getItem(batchId);

                    if (!((String) batchItem.getItemProperty("Number of Samples").getValue()).isEmpty()) {
                        Batch batch = new Batch(Integer.valueOf((String) batchItem.getItemProperty("Number of Samples").getValue()),
                                (Date) batchItem.getItemProperty("Estimated Delivery Date").getValue());

                        experiment.addBatch(batch);
                    }
                }

                project.addExperiment(experiment);

            }
        }


        return project;

    }


    public void setInformation(Project project) {

        personFormPresenter.setInformation(project.getContactPerson(), project.getApplicants(), project.getCooperationPartners());
        experimentPresenter.setInformation(project.getExperiments());
        projectFormPresenter.setInformation(project.getTitle(), project.getDfgID(), project.getQbicID(), project.getTopicalAssignment(), project.getDescription(),
                project.getClassification(), project.getKeywords(), project.getSequencingAim(), project.getTotalCostToString());
        oldProject = project;
    }

    public MainPresenter getMainPresenter(){
        return this.mainPresenter;
    }
}
