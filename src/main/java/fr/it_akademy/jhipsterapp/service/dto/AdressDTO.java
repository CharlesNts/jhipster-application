package fr.it_akademy.jhipsterapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.jhipsterapp.domain.Adress} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdressDTO implements Serializable {

    private Long id;

    private String streetNumb;

    private String streetName;

    private AgentDTO agent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetNumb() {
        return streetNumb;
    }

    public void setStreetNumb(String streetNumb) {
        this.streetNumb = streetNumb;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public AgentDTO getAgent() {
        return agent;
    }

    public void setAgent(AgentDTO agent) {
        this.agent = agent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdressDTO)) {
            return false;
        }

        AdressDTO adressDTO = (AdressDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adressDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdressDTO{" +
            "id=" + getId() +
            ", streetNumb='" + getStreetNumb() + "'" +
            ", streetName='" + getStreetName() + "'" +
            ", agent=" + getAgent() +
            "}";
    }
}
