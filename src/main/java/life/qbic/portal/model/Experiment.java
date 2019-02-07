package life.qbic.portal.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Experiment {

  private int id;
  private int numOfSamples;
  private String coverage;
  private BigDecimal costs;
  private String genomeSize;
  private String material;
  private String species;
  private String technologyType;
  private String instrument;
  private String nucleicAcid;
  private String library;
  private List<Batch> batches;
  // private String project;

  public Experiment(int id, int numOfSamples, String coverage, BigDecimal costs, String genomeSize,
      String material, String species, String technologyType, String instrument, String nucleicAcid,
      String library) {
    super();
    this.numOfSamples = numOfSamples;
    this.coverage = coverage;
    this.costs = costs;
    this.genomeSize = genomeSize;
    this.material = material;
    this.species = species;
    this.technologyType = technologyType;
    this.instrument = instrument;
    this.nucleicAcid = nucleicAcid;
    this.library = library;
    this.batches = new ArrayList<>();
    this.id = id;
  }

  public Experiment(int numOfSamples, String coverage,
                    BigDecimal costs, String genomeSize,
      String material, String species,
                    String technologyType, String instrument, String nucleicAcid,
      String library) {
    this(-1, numOfSamples, coverage, costs, genomeSize, material, species, technologyType,
        instrument, nucleicAcid, library);
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

  public String getInstrument() {
    return instrument;
  }

  public void setInstrument(String instrument) {
    this.instrument = instrument;
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


}
