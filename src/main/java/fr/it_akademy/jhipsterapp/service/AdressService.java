package fr.it_akademy.jhipsterapp.service;

import fr.it_akademy.jhipsterapp.service.dto.AdressDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.jhipsterapp.domain.Adress}.
 */
public interface AdressService {
    /**
     * Save a adress.
     *
     * @param adressDTO the entity to save.
     * @return the persisted entity.
     */
    AdressDTO save(AdressDTO adressDTO);

    /**
     * Updates a adress.
     *
     * @param adressDTO the entity to update.
     * @return the persisted entity.
     */
    AdressDTO update(AdressDTO adressDTO);

    /**
     * Partially updates a adress.
     *
     * @param adressDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdressDTO> partialUpdate(AdressDTO adressDTO);

    /**
     * Get all the adresses.
     *
     * @return the list of entities.
     */
    List<AdressDTO> findAll();

    /**
     * Get the "id" adress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdressDTO> findOne(Long id);

    /**
     * Delete the "id" adress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
