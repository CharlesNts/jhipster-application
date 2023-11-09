package fr.it_akademy.jhipsterapp.domain;

import static fr.it_akademy.jhipsterapp.domain.AdressTestSamples.*;
import static fr.it_akademy.jhipsterapp.domain.AgentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.jhipsterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adress.class);
        Adress adress1 = getAdressSample1();
        Adress adress2 = new Adress();
        assertThat(adress1).isNotEqualTo(adress2);

        adress2.setId(adress1.getId());
        assertThat(adress1).isEqualTo(adress2);

        adress2 = getAdressSample2();
        assertThat(adress1).isNotEqualTo(adress2);
    }

    @Test
    void agentTest() throws Exception {
        Adress adress = getAdressRandomSampleGenerator();
        Agent agentBack = getAgentRandomSampleGenerator();

        adress.setAgent(agentBack);
        assertThat(adress.getAgent()).isEqualTo(agentBack);

        adress.agent(null);
        assertThat(adress.getAgent()).isNull();
    }
}
