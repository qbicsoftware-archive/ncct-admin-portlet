package life.qbic.portal.presenter;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import life.qbic.portal.model.db.Vocabulary;

/**
 * @author fhanssen
 */


public class ProjectFormPresenter {

    private final FormPresenter formPresenter;

    public ProjectFormPresenter(FormPresenter formPresenter) {
        this.formPresenter = formPresenter;

        fillComboBoxes();
    }


    private void fillComboBoxes() {
        this.formPresenter.getFormLayout().getProjectForm().getTopicalAssignment().addItems(Vocabulary.getTopicalAssignmentNames());
        this.formPresenter.getFormLayout().getProjectForm().getClassification().addItems(Vocabulary.getClassificationValues());

    }
}
