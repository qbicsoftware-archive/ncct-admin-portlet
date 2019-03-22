package life.qbic.portal.view.Form;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import life.qbic.portal.view.utils.CustomStyle;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * @author fhanssen
 */


public class BatchForm extends VerticalLayout {

    private final TextField numberOfSamplesBatches;
    private final Grid batchForm;
    private final Button deleteBatchesButton;

    public BatchForm() {


        this.batchForm = new Grid();
        this.batchForm.addColumn("Estimated Delivery Date", Date.class);
        this.batchForm.addColumn("Number of Samples", String.class);
        CustomStyle.addGridSettings(this.batchForm);

        this.numberOfSamplesBatches = new TextField();
        CustomStyle.addTextFieldSettings(numberOfSamplesBatches, "[0-9]+", "Must be positive number");

        DateField dateField = new DateField();
        dateField.setResolution(Resolution.DAY);
        dateField.setDateFormat("yyyy/MM/dd");
        dateField.setValidationVisible(true);
        dateField.addValidator(new NullValidator("Select date", false));

        this.batchForm.getColumn("Estimated Delivery Date").setEditorField(dateField);
        this.batchForm.getColumn("Number of Samples").setEditorField(numberOfSamplesBatches);

        this.deleteBatchesButton = new Button("Delete");
        this.deleteBatchesButton.setVisible(false);
        this.deleteBatchesButton.addStyleName("corners");
        this.addComponents(batchForm, deleteBatchesButton);

        this.setSpacing(true);
        this.setMargin(true);


        //TODO: very irregular and should  be in presenter. Couldn't get it to work there though and due to deadline ahead, it will remain here for now
        this.batchForm.getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {

            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                Object id = batchForm.getContainerDataSource().lastItemId();
                if (! batchForm.getContainerDataSource().getItem(id).getItemProperty("Number of Samples").getValue().equals("")) {
                    addEmptyBatchRow();
                }
            }
        });

        this.batchForm.addSelectionListener(selectionEvent -> {
            if(selectionEvent.getSelected().size() > 0) {
                deleteBatchesButton.setVisible(true);
            }else{
                deleteBatchesButton.setVisible(false);
            }
        });

        this.deleteBatchesButton.addClickListener(clickEvent -> {
            this.batchForm.getSelectedRows().forEach( row -> {
                this.batchForm.getContainerDataSource().removeItem(row);
            });
            Object id = batchForm.getContainerDataSource().lastItemId();
            if(id == null || ! batchForm.getContainerDataSource().getItem(id).getItemProperty("Estimated Delivery Date").getValue().equals("")){
                addEmptyBatchRow();
            }
            this.batchForm.getSelectionModel().reset();
            this.batchForm.refreshAllRows();
        });

    }


    public void addEmptyBatchRow() {// whenever a batch is successfully added to grid, then add new line

        this.batchForm.addRow(null, "");
    }

    public void addDataRow(Date date, String numSamples) {// whenever a batch is successfully added to grid, then add new line
        DateUtils.truncate(date, java.util.Calendar.DAY_OF_MONTH);
        this.batchForm.addRow(date, numSamples);
    }

    public TextField getNumberOfSamplesBatches() {
        return numberOfSamplesBatches;
    }

    public Grid getBatchForm() {
        return batchForm;
    }
}
