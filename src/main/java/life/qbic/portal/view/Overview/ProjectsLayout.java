package life.qbic.portal.view.Overview;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

public class ProjectsLayout extends HorizontalLayout {

    private final Projects projects;
    private final Button addNewProjectButton;

    public ProjectsLayout(){
        this.projects = new Projects();
        this.addNewProjectButton = new Button("Add");

        this.addComponents(projects, addNewProjectButton);
    }

    //TODo load projects from DB
    public Button getAddNewProjectButton() {
        return addNewProjectButton;
    }
}
