package life.qbic.portal.presenter;

import com.vaadin.ui.VerticalLayout;

import life.qbic.portal.model.Batch;
import life.qbic.portal.model.DBConfig;
import life.qbic.portal.model.DBManager;
import life.qbic.portal.model.Experiment;
import life.qbic.portal.model.Person;
import life.qbic.portal.model.Project;
import life.qbic.portal.model.Vocabulary;
import life.qbic.portal.utils.ConfigurationManager;
import life.qbic.portal.utils.ConfigurationManagerFactory;
import life.qbic.portal.utils.LiferayIndependentConfigurationManager;
import life.qbic.portal.utils.PortalUtils;
import life.qbic.portal.view.Overview.ProjectsLayout;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class MainPresenter {

  // TODO in case a project is pressed directly add edit function, but only for admins?

  private final VerticalLayout canvas;
  private final ProjectsLayout projectsLayout;

  private final DBManager db;

  public MainPresenter() {
    this.projectsLayout = new ProjectsLayout();
    this.canvas = new VerticalLayout();

    this.canvas.addComponent(projectsLayout);

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
    addListener();
  }

  private void initDB() {
    db.initVocabularies();
  }

  private void addListener() {
    addButtonListener();
  }

  private void addButtonListener() {
    this.projectsLayout.getAddNewProjectButton().addClickListener(clickEvent -> {
      this.canvas.removeAllComponents();
      FormPresenter formPresenter = new FormPresenter(this);
      this.canvas.addComponent(formPresenter.getFormLayout());
    });
  }

  public void displayProjects() {
    // TODO get existing projects and add them to the form
    this.canvas.removeAllComponents();
    this.canvas.addComponent(projectsLayout);
  }

  public VerticalLayout getCanvas() {
    return canvas;
  }

  public DBManager getDb() {
    return db;
  }
}
