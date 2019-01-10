package life.qbic.portal.view.Form;

import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;

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

    public ExperimentForm(){

        this.allExperiments = new Grid();
        this.allExperiments.addColumn("Type", String.class);
        this.allExperiments.addColumn("Species", String.class);
        this.allExperiments.addColumn("Material", String.class);
        this.allExperiments.addColumn("Instrument", String.class);
        this.allExperiments.addColumn("Library", String.class);
        this.allExperiments.addColumn("Genome size", String.class);
        this.allExperiments.addColumn("Nucleic Acid", String.class);
        this.allExperiments.addColumn("Coverage", String.class);
        this.allExperiments.addColumn("Number of Samples", String.class);
        this.allExperiments.addColumn("Cost", String.class);

        this.allExperiments.setSizeFull();
        this.allExperiments.setHeightMode( HeightMode.ROW );
        this.allExperiments.setHeightByRows(5);
        this.allExperiments.setSelectionMode(Grid.SelectionMode.MULTI);


        this.numberOfSamples = new TextField("Number of Samples");
        this.numberOfSamples.addStyleName("corners");

        this.coverage = new TextField("Coverage");
        this.coverage.addStyleName("corners");

        this.costs = new TextField("Cost");
        this.costs.addStyleName("corners");

        this.genomeSize = new TextField("Genome Size");
        this.genomeSize.addStyleName("corners");

        this.material = new ComboBox("Material");
        this.material.addStyleName("corners");

        this.instrument = new ComboBox("Instrument");
        this.instrument.addStyleName("corners");

        this.species = new ComboBox("Specie");
        this.species.addStyleName("corners");

        this.type = new ComboBox("Read type");
        this.type.addStyleName("corners");

        this.library = new ComboBox("Library");
        this.library.addStyleName("corners");

        this.nucleic_acid = new ComboBox("Nucleic Acid");
        this.nucleic_acid.addStyleName("corners");

        this.batches = new Grid("Batches");
        this.batches.addColumn("Estimated Delivery Date", String.class);
        this.batches.addColumn("Number of Samples", String.class);

        this.batches.setEditorEnabled(true);
        this.batches.setSizeFull();
        this.batches.setHeightMode( HeightMode.ROW );
        this.batches.setHeightByRows(5);
        this.batches.setSelectionMode(Grid.SelectionMode.MULTI);

        IntStream.range(0,5).forEach(i -> addRow());
        IntStream.range(0,5).forEach(i -> addRow2());

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.addComponents(
                type,
                species,
                material,
                instrument,
                library,
                genomeSize,
                nucleic_acid,
                coverage,
                numberOfSamples,
                costs);
        this.addComponents(allExperiments, layout, batches);
        this.setSpacing(true);
    }

    public void addRow(){//TODO whenever a batch is successfully added to grid, then add new line
        this.batches.addRow("", "");
    }

    public void addRow2(){//TODO whenever a batch is successfully added to grid, then add new line
        this.allExperiments.addRow("", "", "", "","", "", "", "","","");
    }

}
