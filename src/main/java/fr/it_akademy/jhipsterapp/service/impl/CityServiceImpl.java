package fr.it_akademy.jhipsterapp.service.impl;

import fr.it_akademy.jhipsterapp.domain.City;
import fr.it_akademy.jhipsterapp.repository.CityRepository;
import fr.it_akademy.jhipsterapp.service.CityService;
import fr.it_akademy.jhipsterapp.service.dto.CityDTO;
import fr.it_akademy.jhipsterapp.service.mapper.CityMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.jhipsterapp.domain.City}.
 */
@Service
@Transactional
public class CityServiceImpl implements CityService {

    private final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

    private final CityRepository cityRepository;

    private final CityMapper cityMapper;

    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public CityDTO save(CityDTO cityDTO) {
        log.debug("Request to save City : {}", cityDTO);
        City city = cityMapper.toEntity(cityDTO);
        city = cityRepository.save(city);
        return cityMapper.toDto(city);
    }

    @Override
    public CityDTO update(CityDTO cityDTO) {
        log.debug("Request to update City : {}", cityDTO);
        City city = cityMapper.toEntity(cityDTO);
        city = cityRepository.save(city);
        return cityMapper.toDto(city);
    }

    @Override
    public Optional<CityDTO> partialUpdate(CityDTO cityDTO) {
        log.debug("Request to partially update City : {}", cityDTO);

        return cityRepository
            .findById(cityDTO.getId())
            .map(existingCity -> {
                cityMapper.partialUpdate(existingCity, cityDTO);

                return existingCity;
            })
            .map(cityRepository::save)
            .map(cityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CityDTO> findAll() {
        log.debug("Request to get all Cities");
        return cityRepository.findAll().stream().map(cityMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the cities where Agent is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CityDTO> findAllWhereAgentIsNull() {
        log.debug("Request to get all cities where Agent is null");
        return StreamSupport
            .stream(cityRepository.findAll().spliterator(), false)
            .filter(city -> city.getAgent() == null)
            .map(cityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CityDTO> findOne(Long id) {
        log.debug("Request to get City : {}", id);
        return cityRepository.findById(id).map(cityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete City : {}", id);
        cityRepository.deleteById(id);
    }
}
