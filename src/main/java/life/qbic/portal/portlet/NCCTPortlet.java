package life.qbic.portal.portlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Layout;

import com.vaadin.ui.VerticalLayout;
import life.qbic.portal.presenter.ProjectsPresenter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Entry point for portlet ncct-admin-portlet. This class derives from {@link QBiCPortletUI}, which is found in the {@code portal-utils-lib} library.
 * 
 * @see <a href=https://github.com/qbicsoftware/portal-utils-lib>portal-utils-lib</a>
 */
@Theme("mytheme")
@SuppressWarnings("serial")
@Widgetset("life.qbic.portal.portlet.AppWidgetSet")
public class NCCTPortlet extends QBiCPortletUI {

    private static final Logger LOG = LogManager.getLogger(NCCTPortlet.class);

    VerticalLayout layout = new VerticalLayout();

    @Override
    protected Layout getPortletContent(final VaadinRequest request) {
        LOG.info("Generating content for {}", NCCTPortlet.class);
        
        // TODO: generate content for your portlet
        //       this method returns any non-null layout to avoid a NullPointerException later on

        ProjectsPresenter mainPresenter = new ProjectsPresenter();
        return mainPresenter.getCanvas();
    }


}
