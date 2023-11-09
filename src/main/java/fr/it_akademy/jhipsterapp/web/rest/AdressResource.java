package fr.it_akademy.jhipsterapp.web.rest;

import fr.it_akademy.jhipsterapp.repository.AdressRepository;
import fr.it_akademy.jhipsterapp.service.AdressService;
import fr.it_akademy.jhipsterapp.service.dto.AdressDTO;
import fr.it_akademy.jhipsterapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.it_akademy.jhipsterapp.domain.Adress}.
 */
@RestController
@RequestMapping("/api/adresses")
public class AdressResource {

    private final Logger log = LoggerFactory.getLogger(AdressResource.class);

    private static final String ENTITY_NAME = "adress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdressService adressService;

    private final AdressRepository adressRepository;

    public AdressResource(AdressService adressService, AdressRepository adressRepository) {
        this.adressService = adressService;
        this.adressRepository = adressRepository;
    }

    /**
     * {@code POST  /adresses} : Create a new adress.
     *
     * @param adressDTO the adressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adressDTO, or with status {@code 400 (Bad Request)} if the adress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AdressDTO> createAdress(@RequestBody AdressDTO adressDTO) throws URISyntaxException {
        log.debug("REST request to save Adress : {}", adressDTO);
        if (adressDTO.getId() != null) {
            throw new BadRequestAlertException("A new adress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdressDTO result = adressService.save(adressDTO);
        return ResponseEntity
            .created(new URI("/api/adresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adresses/:id} : Updates an existing adress.
     *
     * @param id the id of the adressDTO to save.
     * @param adressDTO the adressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adressDTO,
     * or with status {@code 400 (Bad Request)} if the adressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdressDTO> updateAdress(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdressDTO adressDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Adress : {}, {}", id, adressDTO);
        if (adressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdressDTO result = adressService.update(adressDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /adresses/:id} : Partial updates given fields of an existing adress, field will ignore if it is null
     *
     * @param id the id of the adressDTO to save.
     * @param adressDTO the adressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adressDTO,
     * or with status {@code 400 (Bad Request)} if the adressDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adressDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdressDTO> partialUpdateAdress(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdressDTO adressDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Adress partially : {}, {}", id, adressDTO);
        if (adressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdressDTO> result = adressService.partialUpdate(adressDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adressDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /adresses} : get all the adresses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adresses in body.
     */
    @GetMapping("")
    public List<AdressDTO> getAllAdresses() {
        log.debug("REST request to get all Adresses");
        return adressService.findAll();
    }

    /**
     * {@code GET  /adresses/:id} : get the "id" adress.
     *
     * @param id the id of the adressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdressDTO> getAdress(@PathVariable Long id) {
        log.debug("REST request to get Adress : {}", id);
        Optional<AdressDTO> adressDTO = adressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adressDTO);
    }

    /**
     * {@code DELETE  /adresses/:id} : delete the "id" adress.
     *
     * @param id the id of the adressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdress(@PathVariable Long id) {
        log.debug("REST request to delete Adress : {}", id);
        adressService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
