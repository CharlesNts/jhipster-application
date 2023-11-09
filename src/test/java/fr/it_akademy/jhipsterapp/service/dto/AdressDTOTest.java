package fr.it_akademy.jhipsterapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.jhipsterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdressDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdressDTO.class);
        AdressDTO adressDTO1 = new AdressDTO();
        adressDTO1.setId(1L);
        AdressDTO adressDTO2 = new AdressDTO();
        assertThat(adressDTO1).isNotEqualTo(adressDTO2);
        adressDTO2.setId(adressDTO1.getId());
        assertThat(adressDTO1).isEqualTo(adressDTO2);
        adressDTO2.setId(2L);
        assertThat(adressDTO1).isNotEqualTo(adressDTO2);
        adressDTO1.setId(null);
        assertThat(adressDTO1).isNotEqualTo(adressDTO2);
    }
}
