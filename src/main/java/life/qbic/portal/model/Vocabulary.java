package life.qbic.portal.model;

import java.util.Map;

public class Vocabulary {

  private static Map<String, String> topicalAssignmentToID;
  private static Map<String, Integer> libraryToID;
  private static  Map<String, Integer> technologyInstrumentToID;
  private static  Map<String, Integer> speciesNameToID;
  private static  Map<String, Integer> technologyTypeToID;
  private static  Map<String, Integer> materialToID;
  private static  Map<String, Integer> nucleicAcidToID;

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

}
