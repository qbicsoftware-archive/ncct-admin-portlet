-- MySQL Script generated by MySQL Workbench
-- Thu 10 Jan 2019 12:40:10 PM CET
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
  `experiment_id1` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_batch_experiment2_idx` (`experiment_id1` ASC) VISIBLE,
  CONSTRAINT `fk_batch_experiment2`
    FOREIGN KEY (`experiment_id1`)
    REFERENCES `ncct_db`.`experiment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


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
  `material_id` INT NOT NULL,
  `species_id` INT NOT NULL,
  `technology_type_id` INT NOT NULL,
  `technology_instrument_id` INT NOT NULL,
  `nucleic_acid_id` INT NOT NULL,
  `library_id` INT NOT NULL,
  `project_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_experiment_material1_idx` (`material_id` ASC) VISIBLE,
  INDEX `fk_experiment_species1_idx` (`species_id` ASC) VISIBLE,
  INDEX `fk_experiment_technology_type1_idx` (`technology_type_id` ASC) VISIBLE,
  INDEX `fk_experiment_technology_instrument1_idx` (`technology_instrument_id` ASC) VISIBLE,
  INDEX `fk_experiment_nucleic_acid1_idx` (`nucleic_acid_id` ASC) VISIBLE,
  INDEX `fk_experiment_library1_idx` (`library_id` ASC) VISIBLE,
  INDEX `fk_experiment_project1_idx` (`project_id` ASC) VISIBLE,
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
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);


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
  `id` INT NOT NULL AUTO_INCREMENT,
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
  `id` INT NOT NULL AUTO_INCREMENT,
  `qbic_id` VARCHAR(10) NOT NULL,
  `dfg_id` VARCHAR(50) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `total_cost` DECIMAL NOT NULL,
  `description` VARCHAR(2000) NOT NULL,
  `classification` VARCHAR(45) NOT NULL,
  `declaration_of_intent` VARCHAR(100) NOT NULL,
  `keywords` VARCHAR(100) NOT NULL,
  `sequencing_aim` VARCHAR(1000) NOT NULL,
  `contact_person_id` INT NOT NULL,
  `topical_assignments_number` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_project_contact_person1_idx` (`contact_person_id` ASC) VISIBLE,
  UNIQUE INDEX `dfg_id_UNIQUE` (`dfg_id` ASC) VISIBLE,
  UNIQUE INDEX `qbic_id_UNIQUE` (`qbic_id` ASC) VISIBLE,
  INDEX `fk_project_topical_assignments1_idx` (`topical_assignments_number` ASC) VISIBLE,
  CONSTRAINT `fk_project_contact_person1`
    FOREIGN KEY (`contact_person_id`)
    REFERENCES `ncct_db`.`person` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_project_topical_assignments1`
    FOREIGN KEY (`topical_assignments_number`)
    REFERENCES `ncct_db`.`topical_assignment` (`number`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET = armscii8;


-- -----------------------------------------------------
-- Table `ncct_db`.`project_has_applicants`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ncct_db`.`project_has_applicants` ;

CREATE TABLE IF NOT EXISTS `ncct_db`.`project_has_applicants` (
  `project_id` INT NOT NULL,
  `applicant_id` INT NOT NULL,
  INDEX `fk_applicants_has_project_project1_idx` (`project_id` ASC) VISIBLE,
  INDEX `fk_applicants_has_project_contact_person1_idx` (`applicant_id` ASC) VISIBLE,
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
  `project_id` INT NOT NULL,
  `cooperation_partner_id` INT NOT NULL,
  INDEX `fk_project_has_cooperation_partners_project1_idx` (`project_id` ASC) VISIBLE,
  INDEX `fk_project_has_cooperation_partners_contact_person1_idx` (`cooperation_partner_id` ASC) VISIBLE,
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
  `number` VARCHAR(6) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`number`));


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

