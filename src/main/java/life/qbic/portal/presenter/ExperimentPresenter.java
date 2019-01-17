package life.qbic.portal.presenter;

import com.vaadin.data.fieldgroup.FieldGroup;

public class ExperimentPresenter {

    private final FormPresenter formPresenter;

    public ExperimentPresenter(FormPresenter formPresenter){
        this.formPresenter = formPresenter;
        addListener();
    }

    private void addListener(){
        formPresenter.getFormLayout().getExperimentForm().getAddExperiment().addClickListener(clickEvent -> {

            try {
                formPresenter.getFormLayout().getExperimentForm().addDataToGrid(
                        (String) formPresenter.getFormLayout().getExperimentForm().getType().getValue(),
                        (String) formPresenter.getFormLayout().getExperimentForm().getSpecies().getValue(),
                        (String) formPresenter.getFormLayout().getExperimentForm().getMaterial().getValue(),
                        (String) formPresenter.getFormLayout().getExperimentForm().getInstrument().getValue(),
                        (String) formPresenter.getFormLayout().getExperimentForm().getLibrary().getValue(),
                        Double.valueOf(formPresenter.getFormLayout().getExperimentForm().getGenomeSize().getValue()),
                        (String) formPresenter.getFormLayout().getExperimentForm().getNucleic_acid().getValue(),
                        Integer.valueOf(formPresenter.getFormLayout().getExperimentForm().getCoverage().getValue()),
                        Integer.valueOf(formPresenter.getFormLayout().getExperimentForm().getNumberOfSamples().getValue()),
                        Double.valueOf(formPresenter.getFormLayout().getExperimentForm().getCosts().getValue()));
            }catch(IllegalArgumentException e){
                //TODO tell form layout to add notification
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
