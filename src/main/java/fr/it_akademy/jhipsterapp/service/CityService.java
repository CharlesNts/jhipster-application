package fr.it_akademy.jhipsterapp.service;

import fr.it_akademy.jhipsterapp.service.dto.CityDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.jhipsterapp.domain.City}.
 */
public interface CityService {
    /**
     * Save a city.
     *
     * @param cityDTO the entity to save.
     * @return the persisted entity.
     */
    CityDTO save(CityDTO cityDTO);

    /**
     * Updates a city.
     *
     * @param cityDTO the entity to update.
     * @return the persisted entity.
     */
    CityDTO update(CityDTO cityDTO);

    /**
     * Partially updates a city.
     *
     * @param cityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CityDTO> partialUpdate(CityDTO cityDTO);

    /**
     * Get all the cities.
     *
     * @return the list of entities.
     */
    List<CityDTO> findAll();

    /**
     * Get all the CityDTO where Agent is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CityDTO> findAllWhereAgentIsNull();

    /**
     * Get the "id" city.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CityDTO> findOne(Long id);

    /**
     * Delete the "id" city.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
