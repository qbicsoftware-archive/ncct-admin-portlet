package life.qbic.portal.view.Form;

import com.vaadin.data.validator.NullValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import life.qbic.portal.view.utils.CustomStyle;

import java.util.Date;

public class BatchForm extends Grid {

    private final TextField numberOfSamplesBatches;

    public BatchForm(){

        this.addColumn("Estimated Delivery Date", String.class);
        this.addColumn("Number of Samples", String.class);
        CustomStyle.addGridSettings(this);

        this.numberOfSamplesBatches = new TextField();
        CustomStyle.addTextFieldSettings(numberOfSamplesBatches, "[0-9]+",  "Must be positive number");

        DateField dateField = new DateField();
        dateField.setResolution(Resolution.DAY);
        dateField.setValidationVisible(true);
        dateField.addValidator(new NullValidator("Select date", false));

        this.getColumn("Estimated Delivery Date").setEditorField(dateField);
        this.getColumn("Number of Samples").setEditorField(numberOfSamplesBatches);

        addEmptyBatchRow();


    }


    public void addEmptyBatchRow() {//TODO whenever a batch is successfully added to grid, then add new line
        this.addRow("", "");
    }

    public TextField getNumberOfSamplesBatches() {
        return numberOfSamplesBatches;
    }



}
