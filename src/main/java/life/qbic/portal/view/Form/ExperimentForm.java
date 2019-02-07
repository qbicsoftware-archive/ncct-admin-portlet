package life.qbic.portal.view.Form;

import com.vaadin.data.validator.NullValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import life.qbic.portal.view.utils.CustomStyle;


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
        this.allExperiments.addColumn("Genome Size(Gb)", String.class);
        this.allExperiments.addColumn("Nucleic Acid", String.class);
        this.allExperiments.addColumn("Coverage(X)", String.class);
        this.allExperiments.addColumn("Number of Samples", String.class);
        this.allExperiments.addColumn("Cost(EUR)", String.class);

        addGridSettings(allExperiments);

        this.type = new ComboBox("Read Type");
        CustomStyle.addComboboxSettings(type);

        this.species = new ComboBox("Species");
        CustomStyle.addComboboxSettings(species);

        this.material = new ComboBox("Material");
        CustomStyle.addComboboxSettings(material);

        this.instrument = new ComboBox("Instrument");
        CustomStyle.addComboboxSettings(instrument);

        this.library = new ComboBox("Library");
        CustomStyle.addComboboxSettings(library);

        this.genomeSize = new TextField("Genome Size(Gb)");
        CustomStyle.addTextFieldSettings(genomeSize, "[0-9]+\\.?[0-9]*", "Number must be formatted as positive double");

        this.nucleicAcid = new ComboBox("Nucleic Acid");
        CustomStyle.addComboboxSettings(nucleicAcid);

        this.coverage = new TextField("Coverage(X)");
        CustomStyle.addTextFieldSettings(coverage, "[0-9]+",  "Must be positive number");

        this.numberOfSamplesExperiment = new TextField("Number of Samples");
        CustomStyle.addTextFieldSettings(numberOfSamplesExperiment, "[0-9]+",  "Must be positive number");

        this.costs = new TextField("Cost(EUR)");
        CustomStyle.addTextFieldSettings(costs, "[0-9]+(\\.[0-9][0-9]?)?", "Number must be formatted as 123.45!" );

        this.allExperiments.getColumn("Read Type").setEditorField(type);
        this.allExperiments.getColumn("Species").setEditorField(species);
        this.allExperiments.getColumn("Material").setEditorField(material);
        this.allExperiments.getColumn("Instrument").setEditorField(instrument);
        this.allExperiments.getColumn("Library").setEditorField(library);
        this.allExperiments.getColumn("Genome Size(Gb)").setEditorField(genomeSize);
        this.allExperiments.getColumn("Nucleic Acid").setEditorField(nucleicAcid);
        this.allExperiments.getColumn("Coverage(X)").setEditorField(coverage);
        this.allExperiments.getColumn("Number of Samples").setEditorField(numberOfSamplesExperiment);
        this.allExperiments.getColumn("Cost(EUR)").setEditorField(costs);

        addEmptyExperimentRow();


        this.batches = new Grid();
        this.batches.addColumn("Estimated Delivery Date", String.class);
        this.batches.addColumn("Number of Samples", String.class);
        addGridSettings(batches);

        this.numberOfSamplesBatches = new TextField();
        CustomStyle.addTextFieldSettings(numberOfSamplesBatches, "[0-9]+",  "Must be positive number");

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


    public void addEmptyBatchRow() {//TODO whenever a batch is successfully added to grid, then add new line
        this.batches.addRow("", "");
    }

    public void addEmptyExperimentRow() {//TODO whenever a experiment is successfully added to grid, then add new line
        this.allExperiments.addRow("", "", "", "", "", "", "", "", "", "");
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
