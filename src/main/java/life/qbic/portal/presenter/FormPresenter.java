package life.qbic.portal.presenter;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Upload;
import life.qbic.portal.model.Experiment;
import life.qbic.portal.model.Person;
import life.qbic.portal.model.Project;
import life.qbic.portal.view.Form.FormLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class FormPresenter implements Upload.Receiver, Upload.SucceededListener{

    private final FormLayout formLayout;
    private final MainPresenter mainPresenter;
    private final ExperimentPresenter experimentPresenter;
    private final PersonFormPresenter personFormPresenter;
    private final ProjectFormPresenter projectFormPresenter;
    private File tempFile;

    public FormPresenter(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter;
        this.formLayout = new FormLayout();


       // this.fieldGroup.setBuffered(true);

        this.experimentPresenter = new ExperimentPresenter(this);
        this.personFormPresenter = new PersonFormPresenter(this);
        this.projectFormPresenter = new ProjectFormPresenter(this);

        addListener();
        addAllFiledsToFieldGroup();
    }

    private void addAllFiledsToFieldGroup(){
        //this.fieldGroup.bindMemberFields(this.formLayout.getUploadAttachment());
    }

    private void addListener(){
        addUploadListener();
        addSaveEntryListener();
        addCancelListener();
    }


    private void addUploadListener(){
        this.formLayout.getUploadAttachment().setReceiver(this);
        this.formLayout.getUploadAttachment().addSucceededListener(this);

    }

    private void addSaveEntryListener(){
        this.formLayout.getSaveEntries().addClickListener(clickEvent -> {
            saveEntry();
            this.mainPresenter.loadProjects();
            this.mainPresenter.displayProjects();
        });
    }

    private void addCancelListener(){
        this.formLayout.getCancel().addClickListener( clickEvent -> {
            this.mainPresenter.loadProjects();
            this.mainPresenter.displayProjects();
        });

    }

    public FormLayout getFormLayout(){
        return this.formLayout;
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        try {
            tempFile = File.createTempFile(filename, "xml");
            tempFile.deleteOnExit();
            return new FileOutputStream(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        try {
            File destinationFile = new File("c:\\" + event.getFilename());
            // TODO read and parse destinationFile
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MainPresenter getMainPresenter() {
        return mainPresenter;
    }

    private void saveEntry(){
        //TODO validate this: use fieldgroup for this

        Person contactPerson = new Person(
                this.formLayout.getContactPersonForm().getFirstNameValue(),
                this.formLayout.getContactPersonForm().getLastNameValue(),
                this.formLayout.getContactPersonForm().getEmailValue(),
                this.formLayout.getContactPersonForm().getCityValue(),
                this.formLayout.getContactPersonForm().getPhoneNumberValue(),
                this.formLayout.getContactPersonForm().getInstitutionValue()
        );

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

        for(Object id : this.formLayout.getExperimentForm().getAllExperiments().getContainerDataSource().getItemIds()){
            Item item = this.formLayout.getExperimentForm().getAllExperiments().getContainerDataSource().getItem(id);

            if( ! ((String)item.getItemProperty("Read Type").getValue()).isEmpty()) {
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
                project.addExperiment(experiment);

            }
        }

        //TODO sanity check if this adds up to overall amount of samples
//        for(Object id : this.formLayout.getExperimentForm().getBatches().getContainerDataSource().getItemIds()){
//            Item item = this.formLayout.getExperimentForm().getBatches().getContainerDataSource().getItem(id);
//
//            if( ! ((String)item.getItemProperty("Number of Samples").getValue()).isEmpty()) {
//                Batch batch = new Batch(Integer.valueOf((String) item.getItemProperty("Number of Samples").getValue()),
//                        (Date) item.getItemProperty("Estimated Delivery Date").getValue());
//                System.out.println(item);
//                System.out.println(batch.getEstimatedDelivery());
//                System.out.println(batch.getNumOfSamples());
//            }
//        }

        for(Object id : this.formLayout.getApplicantForm().getPersons().getContainerDataSource().getItemIds()){
            Item item = this.formLayout.getApplicantForm().getPersons().getContainerDataSource().getItem(id);

            if( ! ((String)item.getItemProperty("Last Name").getValue()).isEmpty()) {
                Person applicant = new Person((String) item.getItemProperty("First Name").getValue(),
                        (String) item.getItemProperty("Last Name").getValue(),
                        "",
                        (String) item.getItemProperty("City").getValue(),
                        "",
                        (String) item.getItemProperty("Institution").getValue());

                project.addApplicant(applicant);

            }
        }

        for(Object id : this.formLayout.getCooperationPartners().getPersons().getContainerDataSource().getItemIds()){
            Item item = this.formLayout.getCooperationPartners().getPersons().getContainerDataSource().getItem(id);

            if( ! ((String)item.getItemProperty("Last Name").getValue()).isEmpty()) {
                Person cooperationPartner = new Person((String) item.getItemProperty("First Name").getValue(),
                        (String) item.getItemProperty("Last Name").getValue(),
                        "",
                        (String) item.getItemProperty("City").getValue(),
                        "",
                        (String) item.getItemProperty("Institution").getValue());

                project.addCooperationPartner(cooperationPartner);

            }
        }

        try {
            this.mainPresenter.getDb().createProjectWithConnections(project);
        }catch(SQLException e ){
            e.printStackTrace();
        }



    }
}
