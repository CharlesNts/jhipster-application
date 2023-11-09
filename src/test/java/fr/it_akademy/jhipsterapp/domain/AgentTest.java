package fr.it_akademy.jhipsterapp.domain;

import static fr.it_akademy.jhipsterapp.domain.AdressTestSamples.*;
import static fr.it_akademy.jhipsterapp.domain.AgentTestSamples.*;
import static fr.it_akademy.jhipsterapp.domain.CityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.jhipsterapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AgentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agent.class);
        Agent agent1 = getAgentSample1();
        Agent agent2 = new Agent();
        assertThat(agent1).isNotEqualTo(agent2);

        agent2.setId(agent1.getId());
        assertThat(agent1).isEqualTo(agent2);

        agent2 = getAgentSample2();
        assertThat(agent1).isNotEqualTo(agent2);
    }

    @Test
    void cityTest() throws Exception {
        Agent agent = getAgentRandomSampleGenerator();
        City cityBack = getCityRandomSampleGenerator();

        agent.setCity(cityBack);
        assertThat(agent.getCity()).isEqualTo(cityBack);

        agent.city(null);
        assertThat(agent.getCity()).isNull();
    }

    @Test
    void adressTest() throws Exception {
        Agent agent = getAgentRandomSampleGenerator();
        Adress adressBack = getAdressRandomSampleGenerator();

        agent.addAdress(adressBack);
        assertThat(agent.getAdresses()).containsOnly(adressBack);
        assertThat(adressBack.getAgent()).isEqualTo(agent);

        agent.removeAdress(adressBack);
        assertThat(agent.getAdresses()).doesNotContain(adressBack);
        assertThat(adressBack.getAgent()).isNull();

        agent.adresses(new HashSet<>(Set.of(adressBack)));
        assertThat(agent.getAdresses()).containsOnly(adressBack);
        assertThat(adressBack.getAgent()).isEqualTo(agent);

        agent.setAdresses(new HashSet<>());
        assertThat(agent.getAdresses()).doesNotContain(adressBack);
        assertThat(adressBack.getAgent()).isNull();
    }
}
