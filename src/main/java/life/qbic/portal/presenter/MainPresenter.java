package life.qbic.portal.presenter;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.SelectionMode;
import life.qbic.portal.model.DBConfig;
import life.qbic.portal.model.DBManager;
import life.qbic.portal.model.Project;
import life.qbic.portal.utils.ConfigurationManager;
import life.qbic.portal.utils.ConfigurationManagerFactory;
import life.qbic.portal.utils.LiferayIndependentConfigurationManager;
import life.qbic.portal.utils.PortalUtils;
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
        this.projectsLayout.getProjects().addItemClickListener(new ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                Project p = idToProject.get(event.getItemId());

                Window subWindow = new Window("Project " + p.getDfgID());
                subWindow.setWidth("600");
                subWindow.setHeight("350");
                VerticalLayout subContent = new VerticalLayout();
                subContent.setSpacing(true);
                subContent.setMargin(true);
                subWindow.setContent(subContent);

                Grid infoGrid = new Grid();
                infoGrid.addColumn("Information", String.class);
                infoGrid.addColumn("Value", String.class);

                infoGrid.setSelectionMode(SelectionMode.NONE);
                infoGrid.setEditorEnabled(false);
                infoGrid.setHeightMode(HeightMode.ROW);
                infoGrid.setHeightByRows(5);
                infoGrid.setSizeFull();

                infoGrid.addRow("Classification", p.getClassification());
                infoGrid.addRow("Keywords", p.getKeywords());
                infoGrid.addRow("QBiC ID", p.getQbicID());
                infoGrid.addRow("Sequencing Aim", p.getSequencingAim());
                infoGrid.addRow("Topical Assigment", p.getTopicalAssignment());

                subContent.addComponent(infoGrid);
                Label l = new Label("Download Declaration of Intent");
                subContent.addComponent(l);
                subContent.addComponent(createDeclarationDownloadButton(p.getDeclarationOfIntent()));

                // Center it in the browser window
                subWindow.center();
                // Open it in the UI
                UI.getCurrent().addWindow(subWindow);
            }
        });
    }

    private void addButtonListener() {
        this.projectsLayout.getAddNewProjectButton().addClickListener(clickEvent -> {
            this.canvas.removeAllComponents();
            FormPresenter formPresenter = new FormPresenter(this);
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
            idToProject.put(id, project);
        });
    }

    private Button createDeclarationDownloadButton(File tempFile) {
        Button download = new Button();
        download.setIcon(FontAwesome.DOWNLOAD);
        FileDownloader fileDownload = new FileDownloader(new FileResource(tempFile));
        fileDownload.extend(download);
        return download;
    }

    public VerticalLayout getCanvas() {
        return canvas;
    }

    public DBManager getDb() {
        return db;
    }
}
