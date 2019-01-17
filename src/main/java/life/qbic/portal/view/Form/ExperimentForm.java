package life.qbic.portal.view.Form;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.time.LocalDate;
import java.util.stream.IntStream;

public class ExperimentForm extends VerticalLayout {

    private final TextField numberOfSamples;
    private final TextField coverage;
    private final TextField costs;
    private final TextField genomeSize;
    private final ComboBox material;
    private final ComboBox instrument;
    private final ComboBox species;
    private final ComboBox type;
    private final ComboBox library;
    private final ComboBox nucleic_acid;

    private final Grid batches;

    private final Grid allExperiments;
    private final Button addExperiment;

    public ExperimentForm(){

        Label expLabel = new Label("<b><u> Current Experiments: </u></b>", ContentMode.HTML);
        this.allExperiments = new Grid();
        this.allExperiments.addColumn("Read Type", String.class);
        this.allExperiments.addColumn("Species", String.class);
        this.allExperiments.addColumn("Material", String.class);
        this.allExperiments.addColumn("Instrument", String.class);
        this.allExperiments.addColumn("Library", String.class);
        this.allExperiments.addColumn("Genome size(Gb)", Double.class);
        this.allExperiments.addColumn("Nucleic Acid", String.class);
        this.allExperiments.addColumn("Coverage (X)", Integer.class);
        this.allExperiments.addColumn("Number of Samples", Integer.class);
        this.allExperiments.addColumn("Cost(EUR)", Double.class);

        addGridSettings(allExperiments);

        this.addExperiment = new Button("Add New Experiment");
        this.addExperiment.addStyleName("corners");

        this.numberOfSamples = new TextField("Number of Samples");
        this.numberOfSamples.setValue("0");
        addTextFieldSettings(numberOfSamples);

        this.coverage = new TextField("Coverage(X)");
        this.coverage.setValue("0");
        addTextFieldSettings(coverage);

        this.costs = new TextField("Cost(EUR)");
        this.costs.setValue("0.00");
        addTextFieldSettings(costs);

        this.genomeSize = new TextField("Genome Size(Gb)");
        this.genomeSize.setValue("0.0");
        addTextFieldSettings(genomeSize);

        this.material = new ComboBox("Material");
        addComboboxSettings(material);

        this.instrument = new ComboBox("Instrument");
        addComboboxSettings(instrument);

        this.species = new ComboBox("Specie");
        addComboboxSettings(species);

        this.type = new ComboBox("Read type");
        addComboboxSettings(type);

        this.library = new ComboBox("Library");
        addComboboxSettings(library);

        this.nucleic_acid = new ComboBox("Nucleic Acid");
        addComboboxSettings(nucleic_acid);

        Label batchLabel = new Label("<b><u>Batches:</u></b>", ContentMode.HTML);
        this.batches = new Grid();
        this.batches.addColumn("Estimated Delivery Date", String.class);
        this.batches.addColumn("Number of Samples", Integer.class);

        this.batches.setEditorEnabled(true);
        this.batches.setSizeFull();
        this.batches.setHeightMode( HeightMode.ROW );
        this.batches.setHeightByRows(5);
        this.batches.setSelectionMode(Grid.SelectionMode.MULTI);

        IntStream.range(0,1).forEach(i -> addEmptyBatchRow());

        HorizontalLayout entryFieldsLayout = new HorizontalLayout();
        entryFieldsLayout.setSizeFull();
        entryFieldsLayout.addComponents(type, species, material, instrument,
                             library, genomeSize, nucleic_acid, coverage,
                             numberOfSamples, costs);

        this.addComponents(expLabel, allExperiments, entryFieldsLayout, addExperiment, batchLabel, batches);
        this.setComponentAlignment(addExperiment, Alignment.BOTTOM_RIGHT);
        this.setSpacing(true);
    }

    private void addTextFieldSettings(TextField textField){
        textField.addStyleName("corners");
        textField.setSizeFull();
        textField.setHeight(40, UNITS_PIXELS);
    }

    private void addGridSettings(Grid grid){
        grid.setSizeFull();
        grid.setHeightMode( HeightMode.ROW );
        grid.setHeightByRows(5);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
    }

    private void addComboboxSettings(ComboBox comboBox){
        comboBox.addStyleName("corners");
        comboBox.setInvalidAllowed(false);
        comboBox.setNullSelectionAllowed(false);
        comboBox.setFilteringMode(FilteringMode.CONTAINS);
        comboBox.setSizeFull();

        //TODO remove this when real data is available
        String[] planets = new String[] {
                "Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune" };
        for (int i = 0; i < planets.length; i++)
            for (int j = 0; j < planets.length; j++)
                comboBox.addItem(planets[j] + " to " + planets[i]);
    }

    public void addEmptyBatchRow(){//TODO whenever a batch is successfully added to grid, then add new line
        this.batches.addRow(null, null);
    }

    public void addDataToGrid(String type, String species, String material, String instrument,
                              String library, double genomeSize, String nucleic_acid, int coverage,
                              int numberOfSamples, double costs ) throws IllegalArgumentException{

        if(type != null && species!= null && material != null && instrument != null && library != null &&
             nucleic_acid != null) {

            this.allExperiments.addRow(type, species, material, instrument,
                                    library, genomeSize, nucleic_acid, coverage,
                                    numberOfSamples, costs);
            clearEntryFields();
        }else{
            throw new IllegalArgumentException("All fields must be specified");
        }
    }

    private void clearEntryFields(){
        this.type.clear();
        this.species.clear();
        this.material.clear();
        this.instrument.clear();
        this.library.clear();
        this.genomeSize.clear();
        this.nucleic_acid.clear();
        this.coverage.clear();
        this.numberOfSamples.clear();
        this.costs.clear();
    }

    public TextField getNumberOfSamples() {
        return numberOfSamples;
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

    public ComboBox getNucleic_acid() {
        return nucleic_acid;
    }

    public Grid getBatches() {
        return batches;
    }

    public Grid getAllExperiments() {
        return allExperiments;
    }

    public Button getAddExperiment() {
        return addExperiment;
    }
}
