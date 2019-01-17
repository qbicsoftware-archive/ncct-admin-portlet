package life.qbic.portal.view.Form;


import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

public class ProjectForm extends VerticalLayout {

    private final TextField dfgID;
    private final TextField projectTitle;
    private final TextField totalCost;
    private final TextArea projectDescription;

    private final ComboBox topicalAssignment;
    //TODO private final ComboBox classification; //S1 or S2 -> this may nee ot be removed
    private final TextField[] keywords;
    private final TextArea sequencingAim;



    public ProjectForm(){

        this.projectTitle = new TextField();
        this.projectTitle.setSizeFull();
        addTextField("Project Title:", this.projectTitle);

        this.dfgID = new TextField();
        addTextField("DFG ID:", dfgID);

        Label topicalLabel = new Label("<b><u>Topical assignment (DFG classification):</u></b>", ContentMode.HTML);
        this.topicalAssignment = new ComboBox();
        this.topicalAssignment.setSizeFull();
        this.topicalAssignment.addStyleName("corners");
        this.topicalAssignment.setFilteringMode(FilteringMode.CONTAINS);


        Label descriptionLabel = new Label("<b><u>Short project description:</u></b>", ContentMode.HTML);
        this.projectDescription = new TextArea( );
        this.projectDescription.addStyleName("corners");
        this.projectDescription.setSizeFull();
        this.projectDescription.setWordwrap(true);
        this.projectDescription.setRows(5);
        this.projectDescription.setMaxLength(2000);

        HorizontalLayout keyWordLayout = new HorizontalLayout();
        keyWordLayout.setSizeFull();
        Label keywordTitle = new Label("<b><u>Key words (max. 5):</u></b>", ContentMode.HTML);
        this.keywords = new TextField[5];
        for(int i = 0; i < 5; i++){
            keywords[i] = new TextField("#" + (i+1));
            keywords[i].setSizeFull();
            keywords[i].addStyleName("corners");
            keywords[i].setHeight(40, Unit.PIXELS);

            keyWordLayout.addComponent(keywords[i]);
        }

        Label sequencingLabel = new Label("<b><u>Aim of the planned sequencing analysis:</u></b>", ContentMode.HTML);
        this.sequencingAim = new TextArea();
        this.sequencingAim.addStyleName("corners");
        this.sequencingAim.setSizeFull();
        this.sequencingAim.setWordwrap(true);
        this.sequencingAim.setRows(5);
        this.sequencingAim.setMaxLength(1000);

        this.addComponents(topicalLabel, topicalAssignment,  descriptionLabel,
                projectDescription,
                keywordTitle,
                keyWordLayout, sequencingLabel,
                sequencingAim);

        this.totalCost = new TextField();
        addTextField("Total amount for sequencing costs (in EUR; costs should include all services of the NGS-CC,e.g." +
                " library preparation AND sequencing):", totalCost);

        this.setSpacing(true);

    }

    private void addTextField(String title, TextField textField){
        Label label = new Label("<b><u>" + title +"</u></b>", ContentMode.HTML);
        textField.addStyleName("corners");
        textField.setHeight(40, Unit.PIXELS);
        this.addComponents(label, textField);
    }

    public TextField getDfgID() {
        return dfgID;
    }

    public TextField getProjectTitle() {
        return projectTitle;
    }

    public TextField getTotalCost() {
        return totalCost;
    }

    public TextArea getProjectDescription() {
        return projectDescription;
    }

    public ComboBox getTopicalAssignment() {
        return topicalAssignment;
    }

    public TextField[] getKeywords() {
        return keywords;
    }

    public TextArea getSequencingAim() {
        return sequencingAim;
    }
}
