package life.qbic.portal.presenter;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.SelectionMode;
import life.qbic.portal.model.db.DBConfig;
import life.qbic.portal.model.db.DBManager;
import life.qbic.portal.model.db.Project;
import life.qbic.portal.utils.ConfigurationManager;
import life.qbic.portal.utils.ConfigurationManagerFactory;
import life.qbic.portal.utils.LiferayIndependentConfigurationManager;
import life.qbic.portal.utils.PortalUtils;
import life.qbic.portal.view.Overview.InformationForm;
import life.qbic.portal.view.Overview.ProjectsLayout;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fhanssen
 */


public class MainPresenter {

    // TODO in case a project is pressed directly add edit function, but only for admins?

    private final VerticalLayout canvas;
    private final ProjectsLayout projectsLayout;

    private final DBManager db;

    private final Map<Object, Project> idToProject = new HashMap<>();

    public MainPresenter() {
        this.projectsLayout = new ProjectsLayout();
        this.canvas = new VerticalLayout();


        ConfigurationManager config;

        if (PortalUtils.isLiferayPortlet()) {
            config = ConfigurationManagerFactory.getInstance();
        } else {
            LiferayIndependentConfigurationManager.Instance.init("local.properties");
            config = LiferayIndependentConfigurationManager.Instance;
        }

        db = new DBManager(new DBConfig(config.getMysqlHost(), config.getMysqlPort(),
                config.getNCCTMysqlDB(), config.getMysqlUser(), config.getMysqlPass()));

        initDB();
        addListeners();

        loadProjects();
        this.canvas.addComponent(projectsLayout);
    }

    private void initDB() {
        db.initVocabularies();
    }

    private void addListeners() {
        addButtonListener();
        addProjectsListener();
    }

    private void addProjectsListener() {
        this.projectsLayout.getProjects().setSelectionMode(SelectionMode.NONE);// TODO if we need
        // selection we can use
        // doubleClickListener below, instead
        this.projectsLayout.getProjects().addItemClickListener((ItemClickListener) event -> {
            if (event.isDoubleClick()) {
//                    InformationForm informationForm = new InformationForm(idToProject.get(event.getItemId()));
//
//                    if(idToProject.get(event.getItemId()).getDeclarationOfIntent().exists()) {
//                        addDownloadOption(informationForm.getDownload(), idToProject.get(event.getItemId()).getDeclarationOfIntent());
//                    }
//                    // Center it in the browser window
//                    informationForm.center();
//                    // Open it in the UI
//                    UI.getCurrent().addWindow(informationForm);
                canvas.removeAllComponents();
                FormPresenter formPresenter = new FormPresenter(this, true);
                System.out.println("Id to project: " + idToProject.get(event.getItemId()).getApplicants().size());
                formPresenter.setInformation(idToProject.get(event.getItemId()));
                canvas.addComponent(formPresenter.getFormLayout());

            }
        });
    }

    private void addButtonListener() {
        this.projectsLayout.getAddNewProjectButton().addClickListener(clickEvent -> {
            this.canvas.removeAllComponents();
            FormPresenter formPresenter = new FormPresenter(this, false);
            this.canvas.addComponent(formPresenter.getFormLayout());
        });
    }

    public void displayProjects() {
        this.canvas.removeAllComponents();
        this.canvas.addComponent(projectsLayout);
    }

    void loadProjects() {
        this.projectsLayout.getProjects().getContainerDataSource().removeAllItems();
        db.getProjects().forEach(project -> {
            Object id = this.projectsLayout.getProjects().addRow(
                    project.getDfgID(), project.getTitle(), project.getContactPerson().getFirstName()
                            .concat(" ").concat(project.getContactPerson().getLastName()),
                    project.getDescription());
            System.out.println(project.getApplicants().size());
            idToProject.put(id, project);
        });
    }

    private void addDownloadOption(Button button, File tempFile) {

        FileDownloader fileDownload = new FileDownloader(new FileResource(tempFile));
        fileDownload.extend(button);
    }

    public VerticalLayout getCanvas() {
        return canvas;
    }

    public DBManager getDb() {
        return db;
    }
}
