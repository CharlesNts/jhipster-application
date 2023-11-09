package fr.it_akademy.jhipsterapp.repository;

import fr.it_akademy.jhipsterapp.domain.Agent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Agent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {}
