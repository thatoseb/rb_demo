package com.sbk.service.impl;

import com.sbk.service.IncidentTypesService;
import com.sbk.domain.IncidentTypes;
import com.sbk.repository.IncidentTypesRepository;
import com.sbk.service.dto.IncidentTypesDTO;
import com.sbk.service.mapper.IncidentTypesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IncidentTypes}.
 */
@Service
@Transactional
public class IncidentTypesServiceImpl implements IncidentTypesService {

    private final Logger log = LoggerFactory.getLogger(IncidentTypesServiceImpl.class);

    private final IncidentTypesRepository incidentTypesRepository;

    private final IncidentTypesMapper incidentTypesMapper;

    public IncidentTypesServiceImpl(IncidentTypesRepository incidentTypesRepository, IncidentTypesMapper incidentTypesMapper) {
        this.incidentTypesRepository = incidentTypesRepository;
        this.incidentTypesMapper = incidentTypesMapper;
    }

    /**
     * Save a incidentTypes.
     *
     * @param incidentTypesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IncidentTypesDTO save(IncidentTypesDTO incidentTypesDTO) {
        log.debug("Request to save IncidentTypes : {}", incidentTypesDTO);
        IncidentTypes incidentTypes = incidentTypesMapper.toEntity(incidentTypesDTO);
        incidentTypes = incidentTypesRepository.save(incidentTypes);
        return incidentTypesMapper.toDto(incidentTypes);
    }

    /**
     * Get all the incidentTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IncidentTypesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IncidentTypes");
        return incidentTypesRepository.findAll(pageable)
            .map(incidentTypesMapper::toDto);
    }


    /**
     * Get one incidentTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IncidentTypesDTO> findOne(Long id) {
        log.debug("Request to get IncidentTypes : {}", id);
        return incidentTypesRepository.findById(id)
            .map(incidentTypesMapper::toDto);
    }

    /**
     * Delete the incidentTypes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IncidentTypes : {}", id);
        incidentTypesRepository.deleteById(id);
    }
}
