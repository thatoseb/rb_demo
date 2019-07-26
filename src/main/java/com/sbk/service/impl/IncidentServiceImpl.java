package com.sbk.service.impl;

import com.sbk.service.IncidentService;
import com.sbk.domain.Incident;
import com.sbk.repository.IncidentRepository;
import com.sbk.service.dto.IncidentDTO;
import com.sbk.service.mapper.IncidentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Incident}.
 */
@Service
@Transactional
public class IncidentServiceImpl implements IncidentService {

    private final Logger log = LoggerFactory.getLogger(IncidentServiceImpl.class);

    private final IncidentRepository incidentRepository;

    private final IncidentMapper incidentMapper;

    public IncidentServiceImpl(IncidentRepository incidentRepository, IncidentMapper incidentMapper) {
        this.incidentRepository = incidentRepository;
        this.incidentMapper = incidentMapper;
    }

    /**
     * Save a incident.
     *
     * @param incidentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IncidentDTO save(IncidentDTO incidentDTO) {
        log.debug("Request to save Incident : {}", incidentDTO);
        Incident incident = incidentMapper.toEntity(incidentDTO);
        incident = incidentRepository.save(incident);
        return incidentMapper.toDto(incident);
    }

    /**
     * Get all the incidents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IncidentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Incidents");
        return incidentRepository.findAll(pageable)
            .map(incidentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IncidentDTO> findAllByUser(Pageable pageable, Long userId) {
        log.debug("Request to get all Incidents");
        return incidentRepository.findAllByUser(pageable, userId)
            .map(incidentMapper::toDto);
    }

    /**
     * Get all the incidents with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<IncidentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return incidentRepository.findAllWithEagerRelationships(pageable).map(incidentMapper::toDto);
    }


    public Page<IncidentDTO> findAllWithEagerRelationshipsByUser(Pageable pageable, Long userId) {
        return incidentRepository.findAllWithEagerRelationshipsByUser(pageable, userId).map(incidentMapper::toDto);
    }


    /**
     * Get one incident by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IncidentDTO> findOne(Long id) {
        log.debug("Request to get Incident : {}", id);
        return incidentRepository.findOneWithEagerRelationships(id)
            .map(incidentMapper::toDto);
    }

    /**
     * Delete the incident by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Incident : {}", id);
        incidentRepository.deleteById(id);
    }
}
