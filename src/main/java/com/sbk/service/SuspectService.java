package com.sbk.service;

import com.sbk.service.dto.SuspectDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sbk.domain.Suspect}.
 */
public interface SuspectService {

    /**
     * Save a suspect.
     *
     * @param suspectDTO the entity to save.
     * @return the persisted entity.
     */
    SuspectDTO save(SuspectDTO suspectDTO);

    /**
     * Get all the suspects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SuspectDTO> findAll(Pageable pageable);


    /**
     * Get the "id" suspect.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SuspectDTO> findOne(Long id);

    /**
     * Delete the "id" suspect.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
