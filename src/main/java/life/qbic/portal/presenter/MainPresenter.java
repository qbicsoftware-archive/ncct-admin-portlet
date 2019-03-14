package life.qbic.portal.presenter;

import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;
import life.qbic.portal.model.db.elements.Project;
import life.qbic.portal.model.db.operations.DBConfig;
import life.qbic.portal.model.db.operations.DBDataInsert;
import life.qbic.portal.model.db.operations.DBDataRetrieval;
import life.qbic.portal.model.db.operations.DBDataUpdate;
import life.qbic.portal.utils.ConfigurationManager;
import life.qbic.portal.utils.ConfigurationManagerFactory;
import life.qbic.portal.utils.LiferayIndependentConfigurationManager;
import life.qbic.portal.utils.PortalUtils;
import life.qbic.portal.view.Overview.ProjectsLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fhanssen
 */


public class MainPresenter {

    // TODO in case a project is pressed directly add edit function, but only for admins?

    private final VerticalLayout canvas;
    private final ProjectsLayout projectsLayout;

    private final DBDataRetrieval dbDataRetrieval;
    private final DBDataInsert dbDataInsert;
    private final DBConfig dbConfig;
    private final DBDataUpdate dbDataUpdate;

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

        this.dbConfig = new DBConfig(config.getMysqlHost(), config.getMysqlPort(), config.getNCCTMysqlDB(), config.getMysqlUser(), config.getMysqlPass());
        this.dbDataRetrieval = new DBDataRetrieval(this.dbConfig);
        this.dbDataInsert = new DBDataInsert(this.dbConfig);
        this.dbDataUpdate = new DBDataUpdate(this.dbConfig);

        initDB();
        addListeners();

        loadProjects();
        this.canvas.addComponent(this.projectsLayout);
    }

    private void initDB() {
        this.dbDataRetrieval.initVocabularies();
    }

    private void addListeners() {
        addButtonListener();
        addProjectsListener();
    }

    private void addProjectsListener() {

        this.projectsLayout.getProjects().setSelectionMode(SelectionMode.NONE);

        this.projectsLayout.getProjects().addItemClickListener((ItemClickListener) event -> {
            if (event.isDoubleClick()) {

                canvas.removeAllComponents();
                FormPresenter formPresenter = new FormPresenter(this, true);
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
        dbDataRetrieval.getProjects().forEach(project -> {

            Object id = this.projectsLayout.getProjects().addRow(
                    project.getDfgID(), project.getTitle(), project.getContactPerson().getFirstName()
                            .concat(" ").concat(project.getContactPerson().getLastName()),
                    project.getDescription());
            idToProject.put(id, project);
        });
    }

    public VerticalLayout getCanvas() {
        return canvas;
    }

    public DBDataInsert getDbDataInsert() {
        return dbDataInsert;
    }

    public DBDataUpdate getDbDataUpdate() {
        return dbDataUpdate;
    }

    public DBDataRetrieval getDbDataRetrieval() {
        return dbDataRetrieval;
    }
}
