package life.qbic.portal.presenter;

import com.vaadin.ui.VerticalLayout;
import life.qbic.portal.view.Overview.ProjectsLayout;

public class ProjectsPresenter {

    //TODO in case a project is pressed directly add edit function, but only for admins?

    private final ProjectsLayout projectsLayout;
    private final VerticalLayout canvas;

    public ProjectsPresenter(){
        this.projectsLayout = new ProjectsLayout();
        this.canvas = new VerticalLayout();

        this.canvas.addComponent(projectsLayout);
        addListener();
    }

    private void addListener(){
        addButtonListener();
    }

    private void addButtonListener(){
        this.projectsLayout.getAddNewProjectButton().addClickListener(clickEvent -> {
            this.canvas.removeAllComponents();
            FormPresenter formPresenter = new FormPresenter(this);
            this.canvas.addComponent(formPresenter.getFormLayout());
        });
    }

    public void displayProjects(){
        //TODO get existing projects and add them to the form
        this.canvas.removeAllComponents();
        this.canvas.addComponent(projectsLayout);
    }

    public VerticalLayout getCanvas() {
        return canvas;
    }
}
