package com.sbk.service;

import com.sbk.service.dto.OfficerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sbk.domain.Officer}.
 */
public interface OfficerService {

    /**
     * Save a officer.
     *
     * @param officerDTO the entity to save.
     * @return the persisted entity.
     */
    OfficerDTO save(OfficerDTO officerDTO);

    /**
     * Get all the officers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OfficerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" officer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OfficerDTO> findOne(Long id);

    /**
     * Delete the "id" officer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
