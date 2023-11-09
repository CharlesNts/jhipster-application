package fr.it_akademy.jhipsterapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.jhipsterapp.IntegrationTest;
import fr.it_akademy.jhipsterapp.domain.Adress;
import fr.it_akademy.jhipsterapp.repository.AdressRepository;
import fr.it_akademy.jhipsterapp.service.dto.AdressDTO;
import fr.it_akademy.jhipsterapp.service.mapper.AdressMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AdressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdressResourceIT {

    private static final String DEFAULT_STREET_NUMB = "AAAAAAAAAA";
    private static final String UPDATED_STREET_NUMB = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STREET_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/adresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdressRepository adressRepository;

    @Autowired
    private AdressMapper adressMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdressMockMvc;

    private Adress adress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adress createEntity(EntityManager em) {
        Adress adress = new Adress().streetNumb(DEFAULT_STREET_NUMB).streetName(DEFAULT_STREET_NAME);
        return adress;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adress createUpdatedEntity(EntityManager em) {
        Adress adress = new Adress().streetNumb(UPDATED_STREET_NUMB).streetName(UPDATED_STREET_NAME);
        return adress;
    }

    @BeforeEach
    public void initTest() {
        adress = createEntity(em);
    }

    @Test
    @Transactional
    void createAdress() throws Exception {
        int databaseSizeBeforeCreate = adressRepository.findAll().size();
        // Create the Adress
        AdressDTO adressDTO = adressMapper.toDto(adress);
        restAdressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adressDTO)))
            .andExpect(status().isCreated());

        // Validate the Adress in the database
        List<Adress> adressList = adressRepository.findAll();
        assertThat(adressList).hasSize(databaseSizeBeforeCreate + 1);
        Adress testAdress = adressList.get(adressList.size() - 1);
        assertThat(testAdress.getStreetNumb()).isEqualTo(DEFAULT_STREET_NUMB);
        assertThat(testAdress.getStreetName()).isEqualTo(DEFAULT_STREET_NAME);
    }

    @Test
    @Transactional
    void createAdressWithExistingId() throws Exception {
        // Create the Adress with an existing ID
        adress.setId(1L);
        AdressDTO adressDTO = adressMapper.toDto(adress);

        int databaseSizeBeforeCreate = adressRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adress in the database
        List<Adress> adressList = adressRepository.findAll();
        assertThat(adressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdresses() throws Exception {
        // Initialize the database
        adressRepository.saveAndFlush(adress);

        // Get all the adressList
        restAdressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adress.getId().intValue())))
            .andExpect(jsonPath("$.[*].streetNumb").value(hasItem(DEFAULT_STREET_NUMB)))
            .andExpect(jsonPath("$.[*].streetName").value(hasItem(DEFAULT_STREET_NAME)));
    }

    @Test
    @Transactional
    void getAdress() throws Exception {
        // Initialize the database
        adressRepository.saveAndFlush(adress);

        // Get the adress
        restAdressMockMvc
            .perform(get(ENTITY_API_URL_ID, adress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adress.getId().intValue()))
            .andExpect(jsonPath("$.streetNumb").value(DEFAULT_STREET_NUMB))
            .andExpect(jsonPath("$.streetName").value(DEFAULT_STREET_NAME));
    }

    @Test
    @Transactional
    void getNonExistingAdress() throws Exception {
        // Get the adress
        restAdressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdress() throws Exception {
        // Initialize the database
        adressRepository.saveAndFlush(adress);

        int databaseSizeBeforeUpdate = adressRepository.findAll().size();

        // Update the adress
        Adress updatedAdress = adressRepository.findById(adress.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAdress are not directly saved in db
        em.detach(updatedAdress);
        updatedAdress.streetNumb(UPDATED_STREET_NUMB).streetName(UPDATED_STREET_NAME);
        AdressDTO adressDTO = adressMapper.toDto(updatedAdress);

        restAdressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adressDTO))
            )
            .andExpect(status().isOk());

        // Validate the Adress in the database
        List<Adress> adressList = adressRepository.findAll();
        assertThat(adressList).hasSize(databaseSizeBeforeUpdate);
        Adress testAdress = adressList.get(adressList.size() - 1);
        assertThat(testAdress.getStreetNumb()).isEqualTo(UPDATED_STREET_NUMB);
        assertThat(testAdress.getStreetName()).isEqualTo(UPDATED_STREET_NAME);
    }

    @Test
    @Transactional
    void putNonExistingAdress() throws Exception {
        int databaseSizeBeforeUpdate = adressRepository.findAll().size();
        adress.setId(longCount.incrementAndGet());

        // Create the Adress
        AdressDTO adressDTO = adressMapper.toDto(adress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adress in the database
        List<Adress> adressList = adressRepository.findAll();
        assertThat(adressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdress() throws Exception {
        int databaseSizeBeforeUpdate = adressRepository.findAll().size();
        adress.setId(longCount.incrementAndGet());

        // Create the Adress
        AdressDTO adressDTO = adressMapper.toDto(adress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adress in the database
        List<Adress> adressList = adressRepository.findAll();
        assertThat(adressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdress() throws Exception {
        int databaseSizeBeforeUpdate = adressRepository.findAll().size();
        adress.setId(longCount.incrementAndGet());

        // Create the Adress
        AdressDTO adressDTO = adressMapper.toDto(adress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adressDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adress in the database
        List<Adress> adressList = adressRepository.findAll();
        assertThat(adressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdressWithPatch() throws Exception {
        // Initialize the database
        adressRepository.saveAndFlush(adress);

        int databaseSizeBeforeUpdate = adressRepository.findAll().size();

        // Update the adress using partial update
        Adress partialUpdatedAdress = new Adress();
        partialUpdatedAdress.setId(adress.getId());

        partialUpdatedAdress.streetNumb(UPDATED_STREET_NUMB);

        restAdressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdress))
            )
            .andExpect(status().isOk());

        // Validate the Adress in the database
        List<Adress> adressList = adressRepository.findAll();
        assertThat(adressList).hasSize(databaseSizeBeforeUpdate);
        Adress testAdress = adressList.get(adressList.size() - 1);
        assertThat(testAdress.getStreetNumb()).isEqualTo(UPDATED_STREET_NUMB);
        assertThat(testAdress.getStreetName()).isEqualTo(DEFAULT_STREET_NAME);
    }

    @Test
    @Transactional
    void fullUpdateAdressWithPatch() throws Exception {
        // Initialize the database
        adressRepository.saveAndFlush(adress);

        int databaseSizeBeforeUpdate = adressRepository.findAll().size();

        // Update the adress using partial update
        Adress partialUpdatedAdress = new Adress();
        partialUpdatedAdress.setId(adress.getId());

        partialUpdatedAdress.streetNumb(UPDATED_STREET_NUMB).streetName(UPDATED_STREET_NAME);

        restAdressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdress))
            )
            .andExpect(status().isOk());

        // Validate the Adress in the database
        List<Adress> adressList = adressRepository.findAll();
        assertThat(adressList).hasSize(databaseSizeBeforeUpdate);
        Adress testAdress = adressList.get(adressList.size() - 1);
        assertThat(testAdress.getStreetNumb()).isEqualTo(UPDATED_STREET_NUMB);
        assertThat(testAdress.getStreetName()).isEqualTo(UPDATED_STREET_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingAdress() throws Exception {
        int databaseSizeBeforeUpdate = adressRepository.findAll().size();
        adress.setId(longCount.incrementAndGet());

        // Create the Adress
        AdressDTO adressDTO = adressMapper.toDto(adress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adressDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adress in the database
        List<Adress> adressList = adressRepository.findAll();
        assertThat(adressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdress() throws Exception {
        int databaseSizeBeforeUpdate = adressRepository.findAll().size();
        adress.setId(longCount.incrementAndGet());

        // Create the Adress
        AdressDTO adressDTO = adressMapper.toDto(adress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adress in the database
        List<Adress> adressList = adressRepository.findAll();
        assertThat(adressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdress() throws Exception {
        int databaseSizeBeforeUpdate = adressRepository.findAll().size();
        adress.setId(longCount.incrementAndGet());

        // Create the Adress
        AdressDTO adressDTO = adressMapper.toDto(adress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdressMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adressDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adress in the database
        List<Adress> adressList = adressRepository.findAll();
        assertThat(adressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdress() throws Exception {
        // Initialize the database
        adressRepository.saveAndFlush(adress);

        int databaseSizeBeforeDelete = adressRepository.findAll().size();

        // Delete the adress
        restAdressMockMvc
            .perform(delete(ENTITY_API_URL_ID, adress.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Adress> adressList = adressRepository.findAll();
        assertThat(adressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
