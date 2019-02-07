package life.qbic.portal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Vocabulary {

  private static Map<String, String> topicalAssignmentToID;
  private static Map<String, Integer> libraryToID;
  private static Map<String, Integer> technologyInstrumentToID;
  private static Map<String, Integer> speciesNameToID;
  private static Map<String, Integer> technologyTypeToID;
  private static Map<String, Integer> materialToID;
  private static Map<String, Integer> nucleicAcidToID;

  private static Map<Integer, String> idToLibrary;
  private static Map<Integer, String> idToTechnologyInstrument;
  private static Map<Integer, String> idToSpeciesName;
  private static Map<Integer, String> idToTechnologyType;
  private static Map<Integer, String> idToMaterial;
  private static Map<Integer, String> idToNucleicAcid;

  public Vocabulary(Map<String, String> topicalAssignmentToID, Map<String, Integer> libraryToID,
      Map<String, Integer> technologyInstrumentToID, Map<String, Integer> speciesNameToID,
      Map<String, Integer> technologyTypeToID, Map<String, Integer> materialToID,
      Map<String, Integer> nucleicAcidToID) {
    super();
    Vocabulary.topicalAssignmentToID = topicalAssignmentToID;
    Vocabulary.libraryToID = libraryToID;
    Vocabulary.technologyInstrumentToID = technologyInstrumentToID;
    Vocabulary.speciesNameToID = speciesNameToID;
    Vocabulary.technologyTypeToID = technologyTypeToID;
    Vocabulary.materialToID = materialToID;
    Vocabulary.nucleicAcidToID = nucleicAcidToID;
    
    Vocabulary.idToLibrary = 
        libraryToID.entrySet()
        .stream()
        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    Vocabulary.idToTechnologyInstrument = 
        technologyInstrumentToID.entrySet()
        .stream()
        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    Vocabulary.idToSpeciesName = 
        speciesNameToID.entrySet()
        .stream()
        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    Vocabulary.idToTechnologyType = 
        technologyTypeToID.entrySet()
        .stream()
        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    Vocabulary.idToMaterial = 
        materialToID.entrySet()
        .stream()
        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    Vocabulary.idToNucleicAcid = 
        nucleicAcidToID.entrySet()
        .stream()
        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
  }

  public static String getTopicalAssignmentID(String name) {
    return topicalAssignmentToID.get(name);
  }

  public static int getLibraryID(String name) {
    return libraryToID.get(name);
  }

  public static int getTechInstrumentID(String name) {
    return technologyInstrumentToID.get(name);
  }

  public static int getSpeciesID(String name) {
    return speciesNameToID.get(name);
  }

  public static int getTechnologyTypeID(String name) {
    return technologyTypeToID.get(name);
  }

  public static int getNucleicAcidID(String name) {
    return nucleicAcidToID.get(name);
  }

  public static int getMaterialID(String name) {
    return materialToID.get(name);
  }
  

//  public static String getTopicalAssignment(int id) {
//    return topicalAssignmentToID.get(name);
//  }

  public static String getLibraryName(int id) {
    return idToLibrary.get(id);
  }

  public static String getTechInstrumentName(int id ) {
    return idToTechnologyInstrument.get(id);
  }

  public static String getSpeciesName(int id) {
    return idToSpeciesName.get(id);
  }

  public static String getTechnologyTypeName(int id) {
    return idToTechnologyType.get(id);
  }

  public static String getNucleicAcidName(int id) {
    return idToNucleicAcid.get(id);
  }

  public static String getMaterialName(int id) {
    return idToMaterial.get(id);
  }


  public static List<String> getReadTypeValues() {
    return new ArrayList<>(idToTechnologyType.values());
  }

  public static List<String> getSpeciesValues() {
    return new ArrayList<>(idToSpeciesName.values());
  }

  public static List<String> getMaterialValues() {
    return new ArrayList<>(idToMaterial.values());
  }
  
  public static List<String> getTechnologyValues() {
    return new ArrayList<>(idToTechnologyType.values());
  }

  public static List<String> getInstrumentValues() {
    return new ArrayList<>(idToTechnologyInstrument.values());
  }

  public static List<String> getLibraryValues() {
    return new ArrayList<>(idToLibrary.values());
  }

  public static List<String> getNucleicAcidValues() {
    return new ArrayList<>(idToNucleicAcid.values());
  }

  public static List<String> getTopicalAssignmentNames(){
    return new ArrayList<>(topicalAssignmentToID.keySet());
  }

}
