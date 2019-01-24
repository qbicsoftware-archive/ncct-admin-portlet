package life.qbic.portal.presenter;

import com.vaadin.data.fieldgroup.FieldGroup;

public class ExperimentPresenter {

    private final FormPresenter formPresenter;

    public ExperimentPresenter(FormPresenter formPresenter){
        this.formPresenter = formPresenter;
        addListener();
    }

    private void addListener(){
        formPresenter.getFormLayout().getExperimentForm().getAllExperiments().getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {

            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                formPresenter.getFormLayout().getExperimentForm().addEmptyExperimentRow();
            }
        });

        formPresenter.getFormLayout().getExperimentForm().getBatches().getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {

            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                formPresenter.getFormLayout().getExperimentForm().addEmptyBatchRow();
            }
        });

    }


}
