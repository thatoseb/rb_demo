package com.sbk.web.rest;

import com.sbk.service.IncidentTypesService;
import com.sbk.web.rest.errors.BadRequestAlertException;
import com.sbk.service.dto.IncidentTypesDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sbk.domain.IncidentTypes}.
 */
@RestController
@RequestMapping("/api")
public class IncidentTypesResource {

    private final Logger log = LoggerFactory.getLogger(IncidentTypesResource.class);

    private static final String ENTITY_NAME = "incidentTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncidentTypesService incidentTypesService;

    public IncidentTypesResource(IncidentTypesService incidentTypesService) {
        this.incidentTypesService = incidentTypesService;
    }

    /**
     * {@code POST  /incident-types} : Create a new incidentTypes.
     *
     * @param incidentTypesDTO the incidentTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incidentTypesDTO, or with status {@code 400 (Bad Request)} if the incidentTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incident-types")
    public ResponseEntity<IncidentTypesDTO> createIncidentTypes(@Valid @RequestBody IncidentTypesDTO incidentTypesDTO) throws URISyntaxException {
        log.debug("REST request to save IncidentTypes : {}", incidentTypesDTO);
        if (incidentTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new incidentTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncidentTypesDTO result = incidentTypesService.save(incidentTypesDTO);
        return ResponseEntity.created(new URI("/api/incident-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /incident-types} : Updates an existing incidentTypes.
     *
     * @param incidentTypesDTO the incidentTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incidentTypesDTO,
     * or with status {@code 400 (Bad Request)} if the incidentTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incidentTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/incident-types")
    public ResponseEntity<IncidentTypesDTO> updateIncidentTypes(@Valid @RequestBody IncidentTypesDTO incidentTypesDTO) throws URISyntaxException {
        log.debug("REST request to update IncidentTypes : {}", incidentTypesDTO);
        if (incidentTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IncidentTypesDTO result = incidentTypesService.save(incidentTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, incidentTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /incident-types} : get all the incidentTypes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incidentTypes in body.
     */
    @GetMapping("/incident-types")
    public ResponseEntity<List<IncidentTypesDTO>> getAllIncidentTypes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of IncidentTypes");
        Page<IncidentTypesDTO> page = incidentTypesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /incident-types/:id} : get the "id" incidentTypes.
     *
     * @param id the id of the incidentTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incidentTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incident-types/{id}")
    public ResponseEntity<IncidentTypesDTO> getIncidentTypes(@PathVariable Long id) {
        log.debug("REST request to get IncidentTypes : {}", id);
        Optional<IncidentTypesDTO> incidentTypesDTO = incidentTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incidentTypesDTO);
    }

    /**
     * {@code DELETE  /incident-types/:id} : delete the "id" incidentTypes.
     *
     * @param id the id of the incidentTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/incident-types/{id}")
    public ResponseEntity<Void> deleteIncidentTypes(@PathVariable Long id) {
        log.debug("REST request to delete IncidentTypes : {}", id);
        incidentTypesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
