-- MySQL Script generated by MySQL Workbench
-- Thu 07 Feb 2019 01:24:59 PM CET
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ncct_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ncct_db` ;

-- -----------------------------------------------------
-- Schema ncct_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ncct_db` DEFAULT CHARACTER SET utf8 ;
USE `ncct_db` ;

-- -----------------------------------------------------
-- Table `ncct_db`.`batch`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ncct_db`.`batch` ;

CREATE TABLE IF NOT EXISTS `ncct_db`.`batch` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `estimated_delivery_date` DATE NOT NULL,
  `number_samples` INT NOT NULL,
  `experiment_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_batch_experiment2_idx` (`experiment_id` ASC) ,
  CONSTRAINT `fk_batch_experiment2`
    FOREIGN KEY (`experiment_id`)
    REFERENCES `ncct_db`.`experiment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `ncct_db`.`classification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ncct_db`.`classification` ;

CREATE TABLE IF NOT EXISTS `ncct_db`.`classification` (
  `id` INT ZEROFILL NOT NULL AUTO_INCREMENT,
  `classification` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `classification_UNIQUE` (`classification` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ncct_db`.`experiment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ncct_db`.`experiment` ;

CREATE TABLE IF NOT EXISTS `ncct_db`.`experiment` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number_of_samples` INT NOT NULL,
  `coverage` VARCHAR(45) NOT NULL,
  `costs` DECIMAL NOT NULL,
  `genome_size` VARCHAR(45) NOT NULL,
  `material_id` INT UNSIGNED NOT NULL,
  `species_id` INT UNSIGNED NOT NULL,
  `technology_type_id` INT UNSIGNED NOT NULL,
  `technology_instrument_id` INT UNSIGNED NOT NULL,
  `nucleic_acid_id` INT UNSIGNED NOT NULL,
  `library_id` INT UNSIGNED NOT NULL,
  `project_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_experiment_material1_idx` (`material_id` ASC) ,
  INDEX `fk_experiment_species1_idx` (`species_id` ASC) ,
  INDEX `fk_experiment_technology_type1_idx` (`technology_type_id` ASC) ,
  INDEX `fk_experiment_technology_instrument1_idx` (`technology_instrument_id` ASC) ,
  INDEX `fk_experiment_nucleic_acid1_idx` (`nucleic_acid_id` ASC) ,
  INDEX `fk_experiment_library1_idx` (`library_id` ASC) ,
  INDEX `fk_experiment_project1_idx` (`project_id` ASC) ,
  CONSTRAINT `fk_experiment_material1`
    FOREIGN KEY (`material_id`)
    REFERENCES `ncct_db`.`material` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_experiment_species1`
    FOREIGN KEY (`species_id`)
    REFERENCES `ncct_db`.`species` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_experiment_technology_type1`
    FOREIGN KEY (`technology_type_id`)
    REFERENCES `ncct_db`.`technology_type` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_experiment_technology_instrument1`
    FOREIGN KEY (`technology_instrument_id`)
    REFERENCES `ncct_db`.`technology_instrument` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_experiment_nucleic_acid1`
    FOREIGN KEY (`nucleic_acid_id`)
    REFERENCES `ncct_db`.`nucleic_acid` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_experiment_library1`
    FOREIGN KEY (`library_id`)
    REFERENCES `ncct_db`.`library` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_experiment_project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `ncct_db`.`project` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `ncct_db`.`library`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ncct_db`.`library` ;

CREATE TABLE IF NOT EXISTS `ncct_db`.`library` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `ncct_db`.`material`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ncct_db`.`material` ;

CREATE TABLE IF NOT EXISTS `ncct_db`.`material` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) );


-- -----------------------------------------------------
-- Table `ncct_db`.`nucleic_acid`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ncct_db`.`nucleic_acid` ;

CREATE TABLE IF NOT EXISTS `ncct_db`.`nucleic_acid` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `ncct_db`.`person`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ncct_db`.`person` ;

CREATE TABLE IF NOT EXISTS `ncct_db`.`person` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `lastname` VARCHAR(100) NOT NULL,
  `firstname` VARCHAR(100) NOT NULL,
  `institution` VARCHAR(100) NOT NULL,
  `city` VARCHAR(50) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `phonenumber` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `ncct_db`.`project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ncct_db`.`project` ;

CREATE TABLE IF NOT EXISTS `ncct_db`.`project` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `qbic_id` VARCHAR(10) NOT NULL,
  `dfg_id` VARCHAR(50) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `total_cost` DECIMAL NOT NULL,
  `description` VARCHAR(2000) NOT NULL,
  `classification` VARCHAR(45) NOT NULL,
  `declaration_of_intent` BLOB NOT NULL,
  `keywords` VARCHAR(100) NOT NULL,
  `sequencing_aim` VARCHAR(1000) NOT NULL,
  `contact_person_id` INT UNSIGNED NOT NULL,
  `topical_assignment_id` INT UNSIGNED NOT NULL,
  `classification_id` INT ZEROFILL NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_project_contact_person1_idx` (`contact_person_id` ASC) ,
  UNIQUE INDEX `dfg_id_UNIQUE` (`dfg_id` ASC) ,
  UNIQUE INDEX `qbic_id_UNIQUE` (`qbic_id` ASC) ,
  INDEX `fk_project_topical_assignments1_idx` (`topical_assignment_id` ASC) ,
  INDEX `fk_project_Classification1_idx` (`classification_id` ASC) ,
  CONSTRAINT `fk_project_contact_person1`
    FOREIGN KEY (`contact_person_id`)
    REFERENCES `ncct_db`.`person` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_project_topical_assignments1`
    FOREIGN KEY (`topical_assignment_id`)
    REFERENCES `ncct_db`.`topical_assignment` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_project_Classification1`
    FOREIGN KEY (`classification_id`)
    REFERENCES `ncct_db`.`Classification` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET = armscii8;


-- -----------------------------------------------------
-- Table `ncct_db`.`project_has_applicants`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ncct_db`.`project_has_applicants` ;

CREATE TABLE IF NOT EXISTS `ncct_db`.`project_has_applicants` (
  `project_id` INT UNSIGNED NOT NULL,
  `applicant_id` INT UNSIGNED NOT NULL,
  INDEX `fk_applicants_has_project_project1_idx` (`project_id` ASC) ,
  INDEX `fk_applicants_has_project_contact_person1_idx` (`applicant_id` ASC) ,
  CONSTRAINT `fk_applicants_has_project_project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `ncct_db`.`project` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_applicants_has_project_contact_person1`
    FOREIGN KEY (`applicant_id`)
    REFERENCES `ncct_db`.`person` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `ncct_db`.`project_has_cooperation_partners`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ncct_db`.`project_has_cooperation_partners` ;

CREATE TABLE IF NOT EXISTS `ncct_db`.`project_has_cooperation_partners` (
  `project_id` INT UNSIGNED NOT NULL,
  `cooperation_partner_id` INT UNSIGNED NOT NULL,
  INDEX `fk_project_has_cooperation_partners_project1_idx` (`project_id` ASC) ,
  INDEX `fk_project_has_cooperation_partners_contact_person1_idx` (`cooperation_partner_id` ASC) ,
  CONSTRAINT `fk_project_has_cooperation_partners_project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `ncct_db`.`project` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_project_has_cooperation_partners_contact_person1`
    FOREIGN KEY (`cooperation_partner_id`)
    REFERENCES `ncct_db`.`person` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `ncct_db`.`species`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ncct_db`.`species` ;

CREATE TABLE IF NOT EXISTS `ncct_db`.`species` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `ncct_db`.`technology_instrument`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ncct_db`.`technology_instrument` ;

CREATE TABLE IF NOT EXISTS `ncct_db`.`technology_instrument` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `ncct_db`.`technology_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ncct_db`.`technology_type` ;

CREATE TABLE IF NOT EXISTS `ncct_db`.`technology_type` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `ncct_db`.`topical_assignment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ncct_db`.`topical_assignment` ;

CREATE TABLE IF NOT EXISTS `ncct_db`.`topical_assignment` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(6) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) );


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `ncct_db`.`Classification`
-- -----------------------------------------------------
START TRANSACTION;
USE `ncct_db`;
INSERT INTO `ncct_db`.`Classification` (`id`, `classification`) VALUES (1, 'S1');
INSERT INTO `ncct_db`.`Classification` (`id`, `classification`) VALUES (2, 'S2');

COMMIT;


-- -----------------------------------------------------
-- Data for table `ncct_db`.`library`
-- -----------------------------------------------------
START TRANSACTION;
USE `ncct_db`;
INSERT INTO `ncct_db`.`library` (`id`, `name`) VALUES (1, 'Illumina SBS');
INSERT INTO `ncct_db`.`library` (`id`, `name`) VALUES (2, 'PacBio SMRT');
INSERT INTO `ncct_db`.`library` (`id`, `name`) VALUES (3, 'Oxford Nanopore Technology');

COMMIT;


-- -----------------------------------------------------
-- Data for table `ncct_db`.`material`
-- -----------------------------------------------------
START TRANSACTION;
USE `ncct_db`;
INSERT INTO `ncct_db`.`material` (`id`, `name`) VALUES (1, 'Isolated nuclein');
INSERT INTO `ncct_db`.`material` (`id`, `name`) VALUES (2, 'Tissue');
INSERT INTO `ncct_db`.`material` (`id`, `name`) VALUES (3, 'Other');

COMMIT;


-- -----------------------------------------------------
-- Data for table `ncct_db`.`nucleic_acid`
-- -----------------------------------------------------
START TRANSACTION;
USE `ncct_db`;
INSERT INTO `ncct_db`.`nucleic_acid` (`id`, `name`) VALUES (1, 'DNA');
INSERT INTO `ncct_db`.`nucleic_acid` (`id`, `name`) VALUES (2, 'RNA');

COMMIT;


-- -----------------------------------------------------
-- Data for table `ncct_db`.`species`
-- -----------------------------------------------------
START TRANSACTION;
USE `ncct_db`;
INSERT INTO `ncct_db`.`species` (`id`, `name`) VALUES (1, 'Other');

COMMIT;


-- -----------------------------------------------------
-- Data for table `ncct_db`.`technology_instrument`
-- -----------------------------------------------------
START TRANSACTION;
USE `ncct_db`;
INSERT INTO `ncct_db`.`technology_instrument` (`id`, `name`) VALUES (1, 'Other');

COMMIT;


-- -----------------------------------------------------
-- Data for table `ncct_db`.`technology_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `ncct_db`;
INSERT INTO `ncct_db`.`technology_type` (`id`, `name`) VALUES (1, 'Short read');
INSERT INTO `ncct_db`.`technology_type` (`id`, `name`) VALUES (2, 'Long read');

COMMIT;


-- -----------------------------------------------------
-- Data for table `ncct_db`.`topical_assignment`
-- -----------------------------------------------------
START TRANSACTION;
USE `ncct_db`;
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '201-01', 'Biochemistry');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '201-02', 'Biophysics');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '201-03', 'Cell Biology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '201-04', 'Structural Biology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '201-05', 'General Genetics');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '201-06', 'Developmental Biology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '201-07', 'Bioinformatics and Theoretical Biology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '201-08', 'Anatomy');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '202-01', 'Evolution and Systematics of Plants and Fungi');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '202-02', 'Plant Ecology and Ecosystem Analysis');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '202-03', 'Inter-Organismic Interactions and Chemical Ecology of Plant Systems');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '202-04', 'Plant Physiology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '202-05', 'Plant Biochemistry and Biophysics');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '202-06', 'Plant Cell and Developmental Biology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '202-07', 'Plant Genetics');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '203-01', 'Special Zoology and Morphology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '203-02', 'Evolution, Anthropology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '203-03', 'Animal Ecology, Biodiversity and Ecosystem Research');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '203-04', 'Sensory and Behavioural Biology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '203-05', 'Biochemistry and Animal Physiology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '203-06', 'Evolutionary Cell and Developmental Biology (Zoology)');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '204-01', 'Metabolism, Biochemistry and Genetics of Microorganisms');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '204-02', 'Microbial Ecology and Applied Microbiology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '204-03', 'Medical Microbiology, Parasitology, Medical Mycology and Hygiene, Molecular Infection Biology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '204-04', 'Virology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '204-05', 'Immunology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-01', 'Epidemiology, Medical Biometry, Medical Informatics');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-02', 'Public Health, Health Services Research, Social Medicine');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-03', 'Human Genetics');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-04', 'Physiology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-05', 'Nutritional Sciences');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-06', 'Pathology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-07', 'Clinical Chemistry and Pathobiochemistry');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-08', 'Pharmacy');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-09', 'Pharmacology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-10', 'Toxicology, Occupational Medicine and Forensic Medicine');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-11', 'Anaesthesiology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-12', 'Cardiology, Angiology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-13', 'Pneumology, Clinical Infectiology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-14', 'Hematology, Oncology, Transfusion Medicine');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-15', 'Gastroenterology, Metabolism');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-16', 'Nephrology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-17', 'Endocrinology, Diabetology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-18', 'Rheumatology, Clinical Immunology, Allergology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-19', 'Dermatology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-20', 'Pediatric and Adolescent Medicine');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-21', 'Gynaecology and Obstetrics');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-22', 'Reproductive Medicine/Biology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-23', 'Urology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-24', 'Biogerontology and Geriatric Medicine');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-25', 'General and Visceral Surgery');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-26', 'Cardiothoracic and Vascular Surgery');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-27', 'Traumatology and Orthopaedics');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-28', 'Dentistry, Oral Surgery');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-29', 'Otolaryngology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-30', 'Radiology and Nuclear Medicine');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-31', 'Radiation Oncology and Radiobiology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '205-32', 'Biomedical Technology and Medical Physics');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '206-01', 'Molecular Neuroscience and Neurogenetics');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '206-02', 'Cellular Neuroscience');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '206-03', 'Developmental Neurobiology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '206-04', 'Systemic Neuroscience, Computational Neuroscience, Behaviour');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '206-05', 'Organismic Neurobiology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '206-06', 'Cognitive Neuroscience');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '206-07', 'Molecular and Cellular Neurology, Neuropathology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '206-08', 'Clinical Neurosciences I - Neurology, Neurosurgery, Neuroradiology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '206-09', 'Biological and Molecular Psychiatry');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '206-10', 'Clinical Neurosciences II - Psychiatry, Psychotherapy, Child and Adolescent Psychiatry');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '206-11', 'Clinical Neurosciences III - Ophthalmology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '207-01', 'Soil Sciences');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '207-02', 'Plant Cultivation and Agricultural Technology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '207-03', 'Plant Nutrition');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '207-04', 'Ecology of Agricultural Landscapes');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '207-05', 'Plant Breeding');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '207-06', 'Phytomedicine');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '207-07', 'Agricultural Economics and Sociology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '207-08', 'Forestry');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '207-09', 'Animal Husbandry, Breeding and Hygiene');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '207-10', 'Animal Nutrition and Nutrition Physiology');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '207-11', 'Basic Veterinary Medical Science');
INSERT INTO `ncct_db`.`topical_assignment` (`id`, `number`, `name`) VALUES (DEFAULT, '207-12', 'Basic Research on Pathogenesis, Diagnostics and Therapy and Clinical Veterinary Medicine');

COMMIT;


