package fr.it_akademy.jhipsterapp.service.impl;

import fr.it_akademy.jhipsterapp.domain.Adress;
import fr.it_akademy.jhipsterapp.repository.AdressRepository;
import fr.it_akademy.jhipsterapp.service.AdressService;
import fr.it_akademy.jhipsterapp.service.dto.AdressDTO;
import fr.it_akademy.jhipsterapp.service.mapper.AdressMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.jhipsterapp.domain.Adress}.
 */
@Service
@Transactional
public class AdressServiceImpl implements AdressService {

    private final Logger log = LoggerFactory.getLogger(AdressServiceImpl.class);

    private final AdressRepository adressRepository;

    private final AdressMapper adressMapper;

    public AdressServiceImpl(AdressRepository adressRepository, AdressMapper adressMapper) {
        this.adressRepository = adressRepository;
        this.adressMapper = adressMapper;
    }

    @Override
    public AdressDTO save(AdressDTO adressDTO) {
        log.debug("Request to save Adress : {}", adressDTO);
        Adress adress = adressMapper.toEntity(adressDTO);
        adress = adressRepository.save(adress);
        return adressMapper.toDto(adress);
    }

    @Override
    public AdressDTO update(AdressDTO adressDTO) {
        log.debug("Request to update Adress : {}", adressDTO);
        Adress adress = adressMapper.toEntity(adressDTO);
        adress = adressRepository.save(adress);
        return adressMapper.toDto(adress);
    }

    @Override
    public Optional<AdressDTO> partialUpdate(AdressDTO adressDTO) {
        log.debug("Request to partially update Adress : {}", adressDTO);

        return adressRepository
            .findById(adressDTO.getId())
            .map(existingAdress -> {
                adressMapper.partialUpdate(existingAdress, adressDTO);

                return existingAdress;
            })
            .map(adressRepository::save)
            .map(adressMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdressDTO> findAll() {
        log.debug("Request to get all Adresses");
        return adressRepository.findAll().stream().map(adressMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdressDTO> findOne(Long id) {
        log.debug("Request to get Adress : {}", id);
        return adressRepository.findById(id).map(adressMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Adress : {}", id);
        adressRepository.deleteById(id);
    }
}
