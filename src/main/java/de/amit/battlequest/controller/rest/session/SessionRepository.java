package de.amit.battlequest.controller.rest.session;

import org.springframework.data.jpa.repository.JpaRepository;

import de.amit.battlequest.model.Session;

public interface SessionRepository extends JpaRepository<Session, String> {
    public Session findByCode(String code);
}