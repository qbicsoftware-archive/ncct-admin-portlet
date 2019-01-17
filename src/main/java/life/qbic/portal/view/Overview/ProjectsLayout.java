package life.qbic.portal.view.Overview;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class ProjectsLayout extends VerticalLayout {

    private final Projects projects;
    private final Button addNewProjectButton;

    public ProjectsLayout(){
        this.projects = new Projects();
        this.addNewProjectButton = new Button("Add New Project");
        this.addNewProjectButton.addStyleName("corners");

        this.addComponents(projects, addNewProjectButton);

        setSpacing(true);
        this.setComponentAlignment(addNewProjectButton, Alignment.BOTTOM_RIGHT);
    }

    //TODo load projects from DB
    public Button getAddNewProjectButton() {
        return addNewProjectButton;
    }
}
