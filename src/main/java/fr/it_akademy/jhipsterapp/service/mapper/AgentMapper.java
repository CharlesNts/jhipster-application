package fr.it_akademy.jhipsterapp.service.mapper;

import fr.it_akademy.jhipsterapp.domain.Agent;
import fr.it_akademy.jhipsterapp.domain.City;
import fr.it_akademy.jhipsterapp.service.dto.AgentDTO;
import fr.it_akademy.jhipsterapp.service.dto.CityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Agent} and its DTO {@link AgentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AgentMapper extends EntityMapper<AgentDTO, Agent> {
    @Mapping(target = "city", source = "city", qualifiedByName = "cityId")
    AgentDTO toDto(Agent s);

    @Named("cityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CityDTO toDtoCityId(City city);
}
