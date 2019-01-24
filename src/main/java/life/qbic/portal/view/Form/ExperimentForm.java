package life.qbic.portal.view.Form;

import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.util.Date;

public class ExperimentForm extends VerticalLayout {

    private final Grid allExperiments;

    private final TextField numberOfSamplesExperiment;
    private final TextField coverage;
    private final TextField costs;
    private final TextField genomeSize;
    private final ComboBox material;
    private final ComboBox instrument;
    private final ComboBox species;
    private final ComboBox type;
    private final ComboBox library;
    private final ComboBox nucleicAcid;

    private final Grid batches;
    private final TextField numberOfSamplesBatches;

    public ExperimentForm() {

        this.allExperiments = new Grid();
        this.allExperiments.addColumn("Read Type", String.class);
        this.allExperiments.addColumn("Species", String.class);
        this.allExperiments.addColumn("Material", String.class);
        this.allExperiments.addColumn("Instrument", String.class);
        this.allExperiments.addColumn("Library", String.class);
        this.allExperiments.addColumn("Genome size(Gb)", String.class);
        this.allExperiments.addColumn("Nucleic Acid", String.class);
        this.allExperiments.addColumn("Coverage (X)", String.class);
        this.allExperiments.addColumn("Number of Samples", String.class);
        this.allExperiments.addColumn("Cost(EUR)", String.class);

        addGridSettings(allExperiments);

        this.type = new ComboBox("Read type");
        addComboboxSettings(type);

        this.species = new ComboBox("Specie");
        addComboboxSettings(species);

        this.material = new ComboBox("Material");
        addComboboxSettings(material);

        this.instrument = new ComboBox("Instrument");
        addComboboxSettings(instrument);

        this.library = new ComboBox("Library");
        addComboboxSettings(library);

        this.genomeSize = new TextField("Genome Size(Gb)");
        addTextFieldSettings(genomeSize, "[0-9]+\\.?[0-9]*", "Number must be formatted as positive double");

        this.nucleicAcid = new ComboBox("Nucleic Acid");
        addComboboxSettings(nucleicAcid);

        this.coverage = new TextField("Coverage(X)");
        addTextFieldSettings(coverage, "[0-9]+",  "Must be positive number");

        this.numberOfSamplesExperiment = new TextField("Number of Samples");
        addTextFieldSettings(numberOfSamplesExperiment, "[0-9]+",  "Must be positive number");

        this.costs = new TextField("Cost(EUR)");
        addTextFieldSettings(costs, "[0-9]+(\\,[0-9][0-9]?)?", "Number must be formatted as 123,45!" );

        this.allExperiments.getColumn("Read Type").setEditorField(type);
        this.allExperiments.getColumn("Species").setEditorField(species);
        this.allExperiments.getColumn("Material").setEditorField(material);
        this.allExperiments.getColumn("Instrument").setEditorField(instrument);
        this.allExperiments.getColumn("Library").setEditorField(library);
        this.allExperiments.getColumn("Genome size(Gb)").setEditorField(genomeSize);
        this.allExperiments.getColumn("Nucleic Acid").setEditorField(nucleicAcid);
        this.allExperiments.getColumn("Coverage (X)").setEditorField(coverage);
        this.allExperiments.getColumn("Number of Samples").setEditorField(numberOfSamplesExperiment);
        this.allExperiments.getColumn("Cost(EUR)").setEditorField(costs);

        addEmptyExperimentRow();


        this.batches = new Grid();
        this.batches.addColumn("Estimated Delivery Date", String.class);
        this.batches.addColumn("Number of Samples", String.class);
        addGridSettings(batches);

        this.numberOfSamplesBatches = new TextField();
        addTextFieldSettings(numberOfSamplesBatches, "[0-9]+",  "Must be positive number");

        DateField dateField = new DateField();
        dateField.setResolution(Resolution.DAY);
        dateField.setValidationVisible(true);
        dateField.addValidator(new NullValidator("Select date", false));
        this.batches.getColumn("Estimated Delivery Date").setEditorField(dateField);
        this.batches.getColumn("Number of Samples").setEditorField(numberOfSamplesBatches);

        addEmptyBatchRow();

        this.addComponents(new Label("<b><u> Current Experiments: </u></b>", ContentMode.HTML), allExperiments,
                            new Label("<b><u>Batches:</u></b>", ContentMode.HTML), batches);

        this.setSpacing(true);


    }

    private void addGridSettings(Grid grid) {
        grid.setSizeFull();
        grid.setHeightMode(HeightMode.ROW);
        grid.setHeightByRows(5);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setEditorEnabled(true);
    }

    private void addTextFieldSettings(TextField textField, String validationRegex, String errorMessage) {
        textField.setRequired(true);
        textField.setValidationVisible(true);
        textField.setImmediate(true);
        textField.addValidator(new RegexpValidator(validationRegex, true, errorMessage));
    }

    private void addComboboxSettings(ComboBox comboBox) {
        comboBox.setFilteringMode(FilteringMode.CONTAINS);
        comboBox.setImmediate(true);
        comboBox.setValidationVisible(true);
        comboBox.addValidator(new StringLengthValidator("Select value",1, Integer.MAX_VALUE, false));

    }

    public void addEmptyBatchRow() {//TODO whenever a batch is successfully added to grid, then add new line
        this.batches.addRow("", "");
    }

    public void addEmptyExperimentRow() {//TODO whenever a experiment is successfully added to grid, then add new line
        this.allExperiments.addRow("", "", "", "", "", "", "", "", "", "");
    }

    private void clearEntryFields() {
        this.type.clear();
        this.species.clear();
        this.material.clear();
        this.instrument.clear();
        this.library.clear();
        this.genomeSize.clear();
        this.nucleicAcid.clear();
        this.coverage.clear();
        this.numberOfSamplesExperiment.clear();
        this.costs.clear();
    }

    public TextField getNumberOfSamplesExperiment() {
        return numberOfSamplesExperiment;
    }

    public TextField getCoverage() {
        return coverage;
    }

    public TextField getCosts() {
        return costs;
    }

    public TextField getGenomeSize() {
        return genomeSize;
    }

    public ComboBox getMaterial() {
        return material;
    }

    public ComboBox getInstrument() {
        return instrument;
    }

    public ComboBox getSpecies() {
        return species;
    }

    public ComboBox getType() {
        return type;
    }

    public ComboBox getLibrary() {
        return library;
    }

    public ComboBox getNucleicAcid() {
        return nucleicAcid;
    }

    public Grid getBatches() {
        return batches;
    }

    public Grid getAllExperiments() {
        return allExperiments;
    }

    public TextField getNumberOfSamplesBatches() {
        return numberOfSamplesBatches;
    }
}
