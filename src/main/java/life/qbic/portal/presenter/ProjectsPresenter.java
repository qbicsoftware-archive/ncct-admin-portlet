package life.qbic.portal.presenter;

import com.vaadin.ui.VerticalLayout;
import life.qbic.portal.view.ProjectsLayout;

public class ProjectsPresenter {

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
        this.canvas.removeAllComponents();
        this.canvas.addComponent(projectsLayout);
    }

    public VerticalLayout getCanvas() {
        return canvas;
    }
}
