package de.amit.battlequest.controller.test;

import org.springframework.data.jpa.repository.JpaRepository;

import de.amit.battlequest.model.Test;

public interface TestRepository extends JpaRepository<Test, Long> {

}
