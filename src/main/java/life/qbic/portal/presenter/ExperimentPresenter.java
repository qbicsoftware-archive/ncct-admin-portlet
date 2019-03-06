package life.qbic.portal.presenter;

import com.vaadin.data.fieldgroup.FieldGroup;
import life.qbic.portal.model.db.Experiment;
import life.qbic.portal.model.db.Vocabulary;
import life.qbic.portal.view.Form.BatchForm;

import java.util.List;


/**
 * @author fhanssen
 */

public class ExperimentPresenter {

    private final FormPresenter formPresenter;

    public ExperimentPresenter(FormPresenter formPresenter) {
        this.formPresenter = formPresenter;
        addListener();
        fillComboBoxes();
    }

    private void addListener() {
        formPresenter.getFormLayout().getExperimentForm().getAllExperiments().getEditorFieldGroup().addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {

            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                formPresenter.getFormLayout().getExperimentForm().addEmptyExperimentRow();
            }
        });

    }

    private void fillComboBoxes() {

        this.formPresenter.getFormLayout().getExperimentForm().getType().addItems(Vocabulary.getReadTypeValues());
        this.formPresenter.getFormLayout().getExperimentForm().getSpecies().addItems(Vocabulary.getSpeciesValues());
        this.formPresenter.getFormLayout().getExperimentForm().getMaterial().addItems(Vocabulary.getMaterialValues());
        this.formPresenter.getFormLayout().getExperimentForm().getInstrument().addItems(Vocabulary.getInstrumentValues());
        this.formPresenter.getFormLayout().getExperimentForm().getLibrary().addItems(Vocabulary.getLibraryValues());
        this.formPresenter.getFormLayout().getExperimentForm().getNucleicAcid().addItems(Vocabulary.getNucleicAcidValues());
    }

    public void setInformation(List<Experiment> experiments){

        experiments.forEach(experiment -> {
            this.formPresenter.getFormLayout().getExperimentForm().addExperimentRow(experiment.getTechnologyType(),
                    experiment.getSpecies(), experiment.getMaterial(), experiment.getApplication(), experiment.getLibrary(),
                    experiment.getGenomeSize(), experiment.getNucleicAcid(), experiment.getCoverage(), String.valueOf(experiment.getNumOfSamples()),
                    String.valueOf(experiment.getCosts()));

            experiment.getBatches().forEach(batch -> {
                this.formPresenter.getFormLayout().getExperimentForm().addBatch(batch.getEstimatedDelivery(),
                        String.valueOf(batch.getNumOfSamples()));
            });
            ((BatchForm)this.formPresenter.getFormLayout().getExperimentForm().getBatches().getTab(this.formPresenter.getFormLayout().getExperimentForm().getCounter()).getComponent()).addEmptyBatchRow();

            this.formPresenter.getFormLayout().getExperimentForm().incrementCounter();
        });
        this.formPresenter.getFormLayout().getExperimentForm().addEmptyExperimentRow();


    }


}
