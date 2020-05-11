package com.sbk.service;

import com.sbk.service.dto.IncidentTypesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sbk.domain.IncidentTypes}.
 */
public interface IncidentTypesService {

    /**
     * Save a incidentTypes.
     *
     * @param incidentTypesDTO the entity to save.
     * @return the persisted entity.
     */
    IncidentTypesDTO save(IncidentTypesDTO incidentTypesDTO);

    /**
     * Get all the incidentTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IncidentTypesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" incidentTypes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IncidentTypesDTO> findOne(Long id);

    /**
     * Delete the "id" incidentTypes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
