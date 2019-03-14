package life.qbic.portal.presenter;

import life.qbic.portal.model.db.elements.Vocabulary;

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

    public void setInformation(String title, String dfgID, String qbicID, String topicalAssignment, String description,
                               String classification, String keywords, String sequencingAim, String totalCost){


        this.formPresenter.getFormLayout().getProjectForm().setProjectTitle(title);
        this.formPresenter.getFormLayout().getProjectForm().setDfgID(dfgID);
        this.formPresenter.getFormLayout().getProjectForm().setQbicID(qbicID);
        this.formPresenter.getFormLayout().getProjectForm().setTopicalAssignment(topicalAssignment);
        this.formPresenter.getFormLayout().getProjectForm().setProjectDescription(description);
        this.formPresenter.getFormLayout().getProjectForm().setClassification(classification);
        this.formPresenter.getFormLayout().getProjectForm().setKeywords(keywords);
        this.formPresenter.getFormLayout().getProjectForm().setSequencingAim(sequencingAim);
        this.formPresenter.getFormLayout().getProjectForm().setTotalCost(totalCost);

    }
}
