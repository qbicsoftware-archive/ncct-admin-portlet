package life.qbic.portal.view.Form;

import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import life.qbic.portal.model.utils.MyException;
import life.qbic.portal.view.utils.CustomStyle;

import java.util.stream.IntStream;

/**
 * @author fhanssen
 */


public class ProjectForm extends VerticalLayout {

    private final TextField projectTitle;
    private final TextField qbicID;
    private final TextField dfgID;
    private final ComboBox topicalAssignment;
    private final TextArea projectDescription;
    private final ComboBox classification;
    private final HorizontalLayout keyWordLayout;
    private final TextField[] keywords;
    private final TextArea sequencingAim;
    private final TextField totalCost;


    public ProjectForm() {

        this.setSpacing(true);

        //Init and set project title;
        this.projectTitle = new TextField();
        this.dfgID = new TextField();
        this.qbicID = new TextField();
        this.topicalAssignment = new ComboBox();
        this.projectDescription = new TextArea();
        this.classification = new ComboBox();
        this.keyWordLayout = new HorizontalLayout();
        this.keywords = new TextField[5];
        this.sequencingAim = new TextArea();
        this.totalCost = new TextField();

        addTextField("Project Title:", this.projectTitle);
        addTextField("DFG ID:", dfgID);
        addTextField("QBiC ID:", qbicID);

        IntStream.range(0, 5).forEach(i -> {
            keywords[i] = new TextField("#" + (i + 1));
            keyWordLayout.addComponent(keywords[i]);
        });

        this.addComponents(new Label("<b><u>Topical assignment (DFG classification):</u></b>", ContentMode.HTML), topicalAssignment,
                new Label("<b><u>Short project description:</u></b>", ContentMode.HTML), projectDescription,
                new Label("<b><u>Classification</u></b>", ContentMode.HTML), classification,
                new Label("<b><u>Key words (max. 5):</u></b>", ContentMode.HTML), keyWordLayout,
                new Label("<b><u>Aim of the planned sequencing analysis:</u></b>", ContentMode.HTML), sequencingAim);

        addTextField("Total amount for sequencing costs (in EUR; costs should include all services of the NGS-CC,e.g." +
                " library preparation AND sequencing):", totalCost);


        addStyleElements();
        addValidations();

    }

    private void addTextField(String title, TextField textField) {
        Label label = new Label("<b><u>" + title + "</u></b>", ContentMode.HTML);
        CustomStyle.styleTextField(textField);
        this.addComponents(label, textField);

    }

    private void addStyleElements() {

        this.projectTitle.setSizeFull();

        this.topicalAssignment.setFilteringMode(FilteringMode.CONTAINS);


        CustomStyle.styleTextArea(projectDescription);
        this.projectDescription.setRows(5);

        this.classification.setFilteringMode(FilteringMode.CONTAINS);

        IntStream.range(0, 5).forEach(i -> {
            keywords[i].setSizeFull();
            CustomStyle.styleTextField(keywords[i]);
        });
        keyWordLayout.setSizeFull();

        CustomStyle.styleTextArea(sequencingAim);
        this.sequencingAim.setRows(5);



    }

    private void addValidations() {

        this.projectTitle.setRequired(true);
        this.projectTitle.setMaxLength(100);

        this.dfgID.setRequired(true);

        this.qbicID.setRequired(true);

        this.topicalAssignment.setRequired(true);
        this.topicalAssignment.addValidator(new StringLengthValidator("Select value", 1, Integer.MAX_VALUE, false));

        this.projectDescription.setRequired(true);
        this.projectDescription.setMaxLength(2000);

        this.classification.setRequired(true);
        this.classification.addValidator(new StringLengthValidator("Select value", 1, Integer.MAX_VALUE, false));

        IntStream.range(0, 5).forEach(i -> {
            keywords[i].setMaxLength(19);
        });

        this.sequencingAim.setRequired(true);
        this.sequencingAim.setMaxLength(1000);
        this.sequencingAim.setInvalidAllowed(false);

        this.totalCost.setRequired(true);
        this.totalCost.setValidationVisible(true);
        this.totalCost.setImmediate(true);
        this.totalCost.addValidator(new RegexpValidator("[0-9]+(\\.[0-9][0-9]?)?", true, "Number must be formatted as 123.45!"));
    }

    public String getQbicIDValue() throws MyException {

        if (qbicID.getValue().isEmpty()) {
            throw new MyException("No QBiC ID set.");

        }
        return qbicID.getValue();
    }

    public String getDfgIDValue() throws MyException{
        if (dfgID.getValue().isEmpty()) {
            throw new MyException("No DFG ID set.");

        }
        return dfgID.getValue();
    }

    public String getProjectTitleValue() throws MyException{
        if (projectTitle.getValue().isEmpty()) {
            throw new MyException("No project title set.");

        }
        return projectTitle.getValue();
    }

    public String getTotalCostValue() throws MyException{
        if (totalCost.getValue().isEmpty()) {
            throw new MyException("No total costs set.");

        }
        return totalCost.getValue();
    }

    public String getProjectDescriptionValue() throws MyException{
        if (projectDescription.getValue().isEmpty()) {
            throw new MyException("No project description set.");

        }
        return projectDescription.getValue();
    }

    public String getTopicalAssignmentValue() throws MyException{
        if (topicalAssignment.getValue().equals("")) {
            throw new MyException("No topical assignment set.");

        }
        return topicalAssignment.getValue().toString();
    }

    public String getKeywordsValue() {
        StringBuilder values = new StringBuilder();

        for (int i = 0; i < keywords.length; i++) {
            values.append(keywords[i].getValue());

            if (i < keywords.length - 1) {
                values.append(",");
            }
        }
        return values.toString();
    }

    public String getSequencingAimValue() throws MyException{
        if (sequencingAim.getValue().isEmpty()) {
            throw new MyException("No sequencing aim set.");

        }
        return sequencingAim.getValue();
    }

    public String getClassificationValue() throws MyException {

        try {
            return classification.getValue().toString();

        } catch (NullPointerException e) {
            throw new MyException("No classification value specified.", e);
        }
    }

    public ComboBox getTopicalAssignment() {
        return topicalAssignment;
    }

    public ComboBox getClassification() {
        return classification;
    }
}
