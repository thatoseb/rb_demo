package com.sbk.web.rest;

import com.sbk.service.OfficerService;
import com.sbk.web.rest.errors.BadRequestAlertException;
import com.sbk.service.dto.OfficerDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sbk.domain.Officer}.
 */
@RestController
@RequestMapping("/api")
public class OfficerResource {

    private final Logger log = LoggerFactory.getLogger(OfficerResource.class);

    private static final String ENTITY_NAME = "officer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfficerService officerService;

    public OfficerResource(OfficerService officerService) {
        this.officerService = officerService;
    }

    /**
     * {@code POST  /officers} : Create a new officer.
     *
     * @param officerDTO the officerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new officerDTO, or with status {@code 400 (Bad Request)} if the officer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/officers")
    public ResponseEntity<OfficerDTO> createOfficer(@RequestBody OfficerDTO officerDTO) throws URISyntaxException {
        log.debug("REST request to save Officer : {}", officerDTO);
        if (officerDTO.getId() != null) {
            throw new BadRequestAlertException("A new officer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfficerDTO result = officerService.save(officerDTO);
        return ResponseEntity.created(new URI("/api/officers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /officers} : Updates an existing officer.
     *
     * @param officerDTO the officerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated officerDTO,
     * or with status {@code 400 (Bad Request)} if the officerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the officerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/officers")
    public ResponseEntity<OfficerDTO> updateOfficer(@RequestBody OfficerDTO officerDTO) throws URISyntaxException {
        log.debug("REST request to update Officer : {}", officerDTO);
        if (officerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OfficerDTO result = officerService.save(officerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, officerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /officers} : get all the officers.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of officers in body.
     */
    @GetMapping("/officers")
    public ResponseEntity<List<OfficerDTO>> getAllOfficers(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Officers");
        Page<OfficerDTO> page = officerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /officers/:id} : get the "id" officer.
     *
     * @param id the id of the officerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the officerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/officers/{id}")
    public ResponseEntity<OfficerDTO> getOfficer(@PathVariable Long id) {
        log.debug("REST request to get Officer : {}", id);
        Optional<OfficerDTO> officerDTO = officerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(officerDTO);
    }

    /**
     * {@code DELETE  /officers/:id} : delete the "id" officer.
     *
     * @param id the id of the officerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/officers/{id}")
    public ResponseEntity<Void> deleteOfficer(@PathVariable Long id) {
        log.debug("REST request to delete Officer : {}", id);
        officerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
