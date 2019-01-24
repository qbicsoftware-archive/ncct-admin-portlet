package life.qbic.portal.presenter;


import com.vaadin.data.fieldgroup.FieldGroup;

public class PersonFormPresenter {

    private final FormPresenter formPresenter;

    public PersonFormPresenter(FormPresenter formPresenter){
        this.formPresenter = formPresenter;
        addListener();
    }

    private void addListener(){
        this.formPresenter.getFormLayout().getApplicantForm().getPersons().getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {

            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                formPresenter.getFormLayout().getApplicantForm().addRow();
            }
        });

        this.formPresenter.getFormLayout().getCooperationPartners().getPersons().getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {

            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                formPresenter.getFormLayout().getCooperationPartners().addRow();
            }
        });
    }



}
