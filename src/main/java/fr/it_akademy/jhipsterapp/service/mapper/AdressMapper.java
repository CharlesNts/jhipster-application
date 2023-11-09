package fr.it_akademy.jhipsterapp.service.mapper;

import fr.it_akademy.jhipsterapp.domain.Adress;
import fr.it_akademy.jhipsterapp.domain.Agent;
import fr.it_akademy.jhipsterapp.service.dto.AdressDTO;
import fr.it_akademy.jhipsterapp.service.dto.AgentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Adress} and its DTO {@link AdressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdressMapper extends EntityMapper<AdressDTO, Adress> {
    @Mapping(target = "agent", source = "agent", qualifiedByName = "agentId")
    AdressDTO toDto(Adress s);

    @Named("agentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AgentDTO toDtoAgentId(Agent agent);
}
