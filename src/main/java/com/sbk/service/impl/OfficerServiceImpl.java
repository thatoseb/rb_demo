package com.sbk.service.impl;

import com.sbk.service.OfficerService;
import com.sbk.domain.Officer;
import com.sbk.repository.OfficerRepository;
import com.sbk.service.dto.OfficerDTO;
import com.sbk.service.mapper.OfficerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Officer}.
 */
@Service
@Transactional
public class OfficerServiceImpl implements OfficerService {

    private final Logger log = LoggerFactory.getLogger(OfficerServiceImpl.class);

    private final OfficerRepository officerRepository;

    private final OfficerMapper officerMapper;

    public OfficerServiceImpl(OfficerRepository officerRepository, OfficerMapper officerMapper) {
        this.officerRepository = officerRepository;
        this.officerMapper = officerMapper;
    }

    /**
     * Save a officer.
     *
     * @param officerDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OfficerDTO save(OfficerDTO officerDTO) {
        log.debug("Request to save Officer : {}", officerDTO);
        Officer officer = officerMapper.toEntity(officerDTO);
        officer = officerRepository.save(officer);
        return officerMapper.toDto(officer);
    }

    /**
     * Get all the officers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OfficerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Officers");
        return officerRepository.findAll(pageable)
            .map(officerMapper::toDto);
    }


    /**
     * Get one officer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OfficerDTO> findOne(Long id) {
        log.debug("Request to get Officer : {}", id);
        return officerRepository.findById(id)
            .map(officerMapper::toDto);
    }

    /**
     * Delete the officer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Officer : {}", id);
        officerRepository.deleteById(id);
    }
}
