package life.qbic.portal.view;

import com.vaadin.ui.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.IntStream;


public class FormLayout extends VerticalLayout{

    private final ContactPersonForm contactPersonForm;

    private final ApplicantForm applicantForm;

    private final Upload uploadAttachment;

    private final Button saveEntries;

    public FormLayout() {

        this.contactPersonForm = new ContactPersonForm();
        this.applicantForm = new ApplicantForm();
        addApplicantForm();
        this.uploadAttachment = new Upload();
        this.saveEntries = new Button("Save");

        this.addComponents(contactPersonForm,
                            applicantForm,
                            uploadAttachment,
                            saveEntries);
        this.setComponentAlignment(uploadAttachment, Alignment.BOTTOM_RIGHT);
        this.setComponentAlignment(saveEntries, Alignment.BOTTOM_RIGHT);

        this.setSpacing(true);
    }

    public void addApplicantForm() {
        this.applicantForm.addRow();
    }


    public Button getSaveEntries() {
        return saveEntries;
    }

    public Upload getUploadAttachment() {
        return uploadAttachment;
    }

}
