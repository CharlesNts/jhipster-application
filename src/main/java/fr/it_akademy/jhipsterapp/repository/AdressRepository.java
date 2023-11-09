package fr.it_akademy.jhipsterapp.repository;

import fr.it_akademy.jhipsterapp.domain.Adress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Adress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdressRepository extends JpaRepository<Adress, Long> {}
