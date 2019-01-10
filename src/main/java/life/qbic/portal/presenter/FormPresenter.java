package life.qbic.portal.presenter;

import com.vaadin.ui.Upload;
import life.qbic.portal.view.Form.FormLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FormPresenter implements Upload.Receiver, Upload.SucceededListener{

    private final FormLayout formLayout;
    private final ProjectsPresenter projectsPresenter;
    private File tempFile;

    public FormPresenter(ProjectsPresenter projectsPresenter){
        this.projectsPresenter = projectsPresenter;
        this.formLayout = new FormLayout();

        addListener();
    }

    private void addListener(){
        addUploadListener();
        addSaveEntryListener();
    }


    private void addUploadListener(){
        this.formLayout.getProjectForm().getUploadAttachment().setReceiver(this);
        this.formLayout.getProjectForm().getUploadAttachment().addSucceededListener(this);

    }

    private void addSaveEntryListener(){
        this.formLayout.getSaveEntries().addClickListener(clickEvent -> {
            //TODO save in DB,
            this.projectsPresenter.displayProjects();
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




}
