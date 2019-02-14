package life.qbic.portal.presenter;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import life.qbic.portal.model.Vocabulary;

/**
 * @author fhanssen
 */


public class ProjectFormPresenter {

    private final FormPresenter formPresenter;

    public ProjectFormPresenter(FormPresenter formPresenter) {
        this.formPresenter = formPresenter;

        addListener();
        fillComboBoxes();
    }

    private void addListener() {
        this.formPresenter.getFormLayout().getProjectForm().getClassification().addValueChangeListener(valueChangeEvent -> {
            if(valueChangeEvent.getProperty().getValue().equals("Other")){
                Notification notification = new Notification("Warning",
                        "Warning",
                        Notification.Type.ERROR_MESSAGE,
                        true);
                notification.setDelayMsec(-1); //infinity
                notification.show(Page.getCurrent());
            }
        });
    }

    private void fillComboBoxes() {
        this.formPresenter.getFormLayout().getProjectForm().getTopicalAssignment().addItems(Vocabulary.getTopicalAssignmentNames());
        this.formPresenter.getFormLayout().getProjectForm().getClassification().addItems(Vocabulary.getClassificationValues());
        //TODO remove this after DB is updated
        this.formPresenter.getFormLayout().getProjectForm().getClassification().addItems("Other");
    }
}
