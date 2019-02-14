package life.qbic.portal.view.Overview;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * @author fhanssen
 */


public class ProjectsLayout extends VerticalLayout {

    private final Projects projects;
    private final Button addNewProjectButton;
    private final Label doubleClickExplanation;

    public ProjectsLayout() {
        this.projects = new Projects();
        this.addNewProjectButton = new Button("Add New Project");
        this.addNewProjectButton.addStyleName("corners");

        this.doubleClickExplanation = new Label("Double-click on project to display information.");

        this.addComponents(projects, addNewProjectButton, doubleClickExplanation);

        setSpacing(true);
        this.setComponentAlignment(addNewProjectButton, Alignment.BOTTOM_RIGHT);
    }

    public Button getAddNewProjectButton() {
        return addNewProjectButton;
    }

    public Projects getProjects() {
        return projects;
    }
}
