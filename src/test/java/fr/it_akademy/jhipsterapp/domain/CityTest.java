package fr.it_akademy.jhipsterapp.domain;

import static fr.it_akademy.jhipsterapp.domain.AgentTestSamples.*;
import static fr.it_akademy.jhipsterapp.domain.CityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.jhipsterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(City.class);
        City city1 = getCitySample1();
        City city2 = new City();
        assertThat(city1).isNotEqualTo(city2);

        city2.setId(city1.getId());
        assertThat(city1).isEqualTo(city2);

        city2 = getCitySample2();
        assertThat(city1).isNotEqualTo(city2);
    }

    @Test
    void agentTest() throws Exception {
        City city = getCityRandomSampleGenerator();
        Agent agentBack = getAgentRandomSampleGenerator();

        city.setAgent(agentBack);
        assertThat(city.getAgent()).isEqualTo(agentBack);
        assertThat(agentBack.getCity()).isEqualTo(city);

        city.agent(null);
        assertThat(city.getAgent()).isNull();
        assertThat(agentBack.getCity()).isNull();
    }
}
