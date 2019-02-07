package life.qbic.portal.presenter;

import life.qbic.portal.model.Vocabulary;

public class ProjectFormPresenter {

    private final FormPresenter formPresenter;

    public ProjectFormPresenter(FormPresenter formPresenter) {
        this.formPresenter = formPresenter;

        addListener();
        fillComboBoxes();
    }

    private void addListener(){

    }

    private void fillComboBoxes(){
        this.formPresenter.getFormLayout().getProjectForm().getTopicalAssignment().addItems(Vocabulary.getTopicalAssignmentNames());
        this.formPresenter.getFormLayout().getProjectForm().getClassification().addItems(Vocabulary.getClassificationValues());

    }
}
