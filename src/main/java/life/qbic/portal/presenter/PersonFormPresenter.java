package life.qbic.portal.presenter;


import com.vaadin.data.fieldgroup.FieldGroup;
import life.qbic.portal.model.db.Person;
import life.qbic.portal.view.Form.ContactPersonForm;

import java.util.List;

/**
 * @author fhanssen
 */


public class PersonFormPresenter {

    private final FormPresenter formPresenter;

    public PersonFormPresenter(FormPresenter formPresenter) {
        this.formPresenter = formPresenter;
        addListener();
    }

    private void addListener() {
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

    public void setInformation(Person contactPerson, List<Person> applicants, List<Person> cooperationPartners){

        this.formPresenter.getFormLayout().getContactPersonForm().setLastName(contactPerson.getLastName());
        this.formPresenter.getFormLayout().getContactPersonForm().setFirstName(contactPerson.getFirstName());
        this.formPresenter.getFormLayout().getContactPersonForm().setInstitution(contactPerson.getInstitution());
        this.formPresenter.getFormLayout().getContactPersonForm().setCity(contactPerson.getCity());
        this.formPresenter.getFormLayout().getContactPersonForm().setEmail(contactPerson.getEmail());
        this.formPresenter.getFormLayout().getContactPersonForm().setPhoneNumber(contactPerson.getPhone());


        applicants.forEach(applicant -> {
            this.formPresenter.getFormLayout().getApplicantForm().addRow(applicant.getLastName(), applicant.getFirstName(), applicant.getInstitution(), applicant.getCity());
        });
        this.formPresenter.getFormLayout().getApplicantForm().addRow();

        cooperationPartners.forEach(cooperationPartner -> {
            this.formPresenter.getFormLayout().getCooperationPartners().addRow(cooperationPartner.getLastName(), cooperationPartner.getFirstName(), cooperationPartner.getInstitution(), cooperationPartner.getCity());
        });
        this.formPresenter.getFormLayout().getCooperationPartners().addRow();


    }
}
