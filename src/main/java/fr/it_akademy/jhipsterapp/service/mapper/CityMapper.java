package fr.it_akademy.jhipsterapp.service.mapper;

import fr.it_akademy.jhipsterapp.domain.City;
import fr.it_akademy.jhipsterapp.service.dto.CityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<CityDTO, City> {}
