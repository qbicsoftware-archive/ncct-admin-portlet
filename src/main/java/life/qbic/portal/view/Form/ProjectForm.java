package life.qbic.portal.view.Form;


import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;

public class ProjectForm extends VerticalLayout {

    private final TextField dfgID;
    private final TextField projectTitle;
    private final TextField totalCost;
    private final TextArea projectDescription;

    private final ComboBox topicalAssignment;
    //TODO private final ComboBox classification; //S1 or S2 -> this may nee ot be removed
    private final Label keywordTitle;
    private final HorizontalLayout keyWordLayout;
    private final TextField[] keywords;

    private final TextArea sequencingAim;

    private final Upload uploadAttachment;


    public ProjectForm(){

        this.projectTitle = new TextField("Project Title");
        this.projectTitle.addStyleName("corners");
        this.projectTitle.setSizeFull();

        this.dfgID = new TextField("DFG ID");
        this.dfgID.addStyleName("corners");

        this.topicalAssignment = new ComboBox("Topical assignment (DFG classification)");
        this.topicalAssignment.setSizeFull();
        this.topicalAssignment.addStyleName("corners");
        this.topicalAssignment.setFilteringMode(FilteringMode.CONTAINS);


        this.projectDescription = new TextArea("Short project description (max. 15 lines)" );
        this.projectDescription.addStyleName("corners");
        this.projectDescription.setSizeFull();
        this.projectDescription.setWordwrap(true);

        this.keyWordLayout = new HorizontalLayout();
        this.keyWordLayout.setSizeFull();
        this.keywordTitle = new Label("Key words (max. 5)");
        this.keywords = new TextField[5];
        for(int i = 0; i < 5; i++){
            keywords[i] = new TextField("#" + (i+1));
            keywords[i].setSizeFull();
            keywords[i].addStyleName("corners");
            keyWordLayout.addComponent(keywords[i]);
        }

        this.sequencingAim = new TextArea("Aim of the planned sequencing analysis (max. 5 lines)");
        this.sequencingAim.addStyleName("corners");
        this.sequencingAim.setSizeFull();
        this.sequencingAim.setWordwrap(true);

        this.totalCost = new TextField("Total amount for sequencing costs (in EUR; costs should include all services of the NGS-CC,e.g. library preparation AND sequencing)");
        this.totalCost.addStyleName("corners");

        this.uploadAttachment = new Upload();

        this.addComponents(projectTitle,
                dfgID,
                topicalAssignment,
                projectDescription,
                keywordTitle,
                keyWordLayout,
                sequencingAim,
                totalCost,
                uploadAttachment
                );

        this.setSpacing(true);

    }

    public Upload getUploadAttachment() {
        return uploadAttachment;
    }

}
