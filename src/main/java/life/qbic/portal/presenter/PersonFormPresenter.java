package life.qbic.portal.presenter;


import com.vaadin.data.fieldgroup.FieldGroup;
import life.qbic.portal.model.db.elements.Person;

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
                Object id = formPresenter.getFormLayout().getApplicantForm().getPersons().getContainerDataSource().lastItemId();
                if (! formPresenter.getFormLayout().getApplicantForm().getPersons().getContainerDataSource().getItem(id).getItemProperty("Last Name").getValue().equals("")) {
                    formPresenter.getFormLayout().getApplicantForm().addRow();
                }
            }
        });

        this.formPresenter.getFormLayout().getCooperationPartners().getPersons().getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {

            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                Object id = formPresenter.getFormLayout().getCooperationPartners().getPersons().getContainerDataSource().lastItemId();
                if (! formPresenter.getFormLayout().getCooperationPartners().getPersons().getContainerDataSource().getItem(id).getItemProperty("Last Name").getValue().equals("")) {
                    formPresenter.getFormLayout().getCooperationPartners().addRow();
                }
            }
        });

        this.formPresenter.getFormLayout().getApplicantForm().getPersons().addSelectionListener(selectionEvent -> {
            if(selectionEvent.getSelected().size() > 0) {
                this.formPresenter.getFormLayout().addDeleteApplicantButton();
            }else{
                this.formPresenter.getFormLayout().removeDeleteApplicantButton();
            }
        });

        this.formPresenter.getFormLayout().getCooperationPartners().getPersons().addSelectionListener(selectionEvent -> {
            if(selectionEvent.getSelected().size() > 0) {
                this.formPresenter.getFormLayout().addDeleteCoopPartnerButton();
            }else{
                this.formPresenter.getFormLayout().removeDeleteCoopPartnerButton();

            }
        });

        this.formPresenter.getFormLayout().getDeleteApplicant().addClickListener(clickEvent -> {
            this.formPresenter.getFormLayout().getApplicantForm().getPersons().getSelectedRows().forEach( row -> {
                this.formPresenter.getFormLayout().getApplicantForm().getPersons().getContainerDataSource().removeItem(row);
            });

            Object id = formPresenter.getFormLayout().getApplicantForm().getPersons().getContainerDataSource().lastItemId();
            if(id == null || ! this.formPresenter.getFormLayout().getApplicantForm().getPersons().getContainerDataSource().getItem(id).getItemProperty("Last Name").getValue().equals("")){
                this.formPresenter.getFormLayout().getApplicantForm().addRow();
            }


            this.formPresenter.getFormLayout().getApplicantForm().getPersons().getSelectionModel().reset();
            this.formPresenter.getFormLayout().getApplicantForm().getPersons().refreshAllRows();
        });

        this.formPresenter.getFormLayout().getDeleteCooperationPartners().addClickListener(clickEvent -> {
            this.formPresenter.getFormLayout().getCooperationPartners().getPersons().getSelectedRows().forEach( row -> {
                this.formPresenter.getFormLayout().getCooperationPartners().getPersons().getContainerDataSource().removeItem(row);
            });

            Object id = formPresenter.getFormLayout().getCooperationPartners().getPersons().getContainerDataSource().lastItemId();
            if(id == null || ! this.formPresenter.getFormLayout().getCooperationPartners().getPersons().getContainerDataSource().getItem(id).getItemProperty("Last Name").getValue().equals("")){
                this.formPresenter.getFormLayout().getCooperationPartners().addRow();
            }

            this.formPresenter.getFormLayout().getCooperationPartners().getPersons().getSelectionModel().reset();
            this.formPresenter.getFormLayout().getCooperationPartners().getPersons().refreshAllRows();
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
