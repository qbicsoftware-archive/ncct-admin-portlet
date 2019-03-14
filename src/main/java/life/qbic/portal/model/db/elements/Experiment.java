package life.qbic.portal.model.db.elements;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author afriedrich
 */

public class Experiment {

    private int id;
    private int numOfSamples;
    private String coverage;
    private BigDecimal costs;
    private String genomeSize;
    private String material;
    private String species;
    private String technologyType;
    private String application;
    private String nucleicAcid;
    private String library;
    private List<Batch> batches;

    public Experiment(int id, int numOfSamples, String coverage, BigDecimal costs, String genomeSize,
                      String material, String species, String technologyType, String application, String nucleicAcid,
                      String library) {
        super();
        this.numOfSamples = numOfSamples;
        this.coverage = coverage;
        this.costs = costs;
        this.genomeSize = genomeSize;
        this.material = material;
        this.species = species;
        this.technologyType = technologyType;
        this.application = application;
        this.nucleicAcid = nucleicAcid;
        this.library = library;
        this.batches = new ArrayList<>();
        this.id = id;
    }

    public Experiment(int numOfSamples, String coverage, String costs, String genomeSize,
                      String material, String species, String technologyType, String application, String nucleicAcid,
                      String library) {
        this(-1, numOfSamples, coverage, new BigDecimal(costs), genomeSize, material, species, technologyType,
                application, nucleicAcid, library);
    }

    public int getID() {
        return id;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void addBatch(Batch b) {
        batches.add(b);
    }

    public int getNumOfSamples() {
        return numOfSamples;
    }

    public void setNumOfSamples(int numOfSamples) {
        this.numOfSamples = numOfSamples;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public BigDecimal getCosts() {
        return costs;
    }

    public void setCosts(BigDecimal costs) {
        this.costs = costs;
    }

    public String getGenomeSize() {
        return genomeSize;
    }

    public void setGenomeSize(String genomeSize) {
        this.genomeSize = genomeSize;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getTechnologyType() {
        return technologyType;
    }

    public void setTechnologyType(String technologyType) {
        this.technologyType = technologyType;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getNucleicAcid() {
        return nucleicAcid;
    }

    public void setNucleicAcid(String nucleicAcid) {
        this.nucleicAcid = nucleicAcid;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experiment that = (Experiment) o;
        return id == that.id &&
                numOfSamples == that.numOfSamples &&
                Objects.equals(coverage, that.coverage) &&
                Objects.equals(costs, that.costs) &&
                Objects.equals(genomeSize, that.genomeSize) &&
                Objects.equals(material, that.material) &&
                Objects.equals(species, that.species) &&
                Objects.equals(technologyType, that.technologyType) &&
                Objects.equals(application, that.application) &&
                Objects.equals(nucleicAcid, that.nucleicAcid) &&
                Objects.equals(library, that.library) &&
                Objects.equals(batches, that.batches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numOfSamples, coverage, costs, genomeSize, material, species, technologyType, application, nucleicAcid, library, batches);
    }
}
