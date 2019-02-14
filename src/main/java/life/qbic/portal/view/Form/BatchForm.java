package life.qbic.portal.view.Form;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import life.qbic.portal.view.utils.CustomStyle;

/**
 * @author fhanssen
 */


public class BatchForm extends Grid {

    private final TextField numberOfSamplesBatches;

    public BatchForm() {

        this.addColumn("Estimated Delivery Date", String.class);
        this.addColumn("Number of Samples", String.class);
        CustomStyle.addGridSettings(this);

        this.numberOfSamplesBatches = new TextField();
        CustomStyle.addTextFieldSettings(numberOfSamplesBatches, "[0-9]+", "Must be positive number");

        DateField dateField = new DateField();
        dateField.setResolution(Resolution.DAY);
        dateField.setValidationVisible(true);
        dateField.addValidator(new NullValidator("Select date", false));

        this.getColumn("Estimated Delivery Date").setEditorField(dateField);
        this.getColumn("Number of Samples").setEditorField(numberOfSamplesBatches);

        addEmptyBatchRow();


        //TODO: very irregular and should  be in presenter. Couldn't get it to work there though and due to deadline ahead, it will remain here for now
        this.getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {

            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                addEmptyBatchRow();
            }
        });

    }


    public void addEmptyBatchRow() {//TODO whenever a batch is successfully added to grid, then add new line
        this.addRow("", "");
    }

    public TextField getNumberOfSamplesBatches() {
        return numberOfSamplesBatches;
    }


}
