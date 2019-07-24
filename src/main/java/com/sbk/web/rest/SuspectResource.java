package com.sbk.web.rest;

import com.sbk.service.SuspectService;
import com.sbk.web.rest.errors.BadRequestAlertException;
import com.sbk.service.dto.SuspectDTO;

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
 * REST controller for managing {@link com.sbk.domain.Suspect}.
 */
@RestController
@RequestMapping("/api")
public class SuspectResource {

    private final Logger log = LoggerFactory.getLogger(SuspectResource.class);

    private static final String ENTITY_NAME = "suspect";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SuspectService suspectService;

    public SuspectResource(SuspectService suspectService) {
        this.suspectService = suspectService;
    }

    /**
     * {@code POST  /suspects} : Create a new suspect.
     *
     * @param suspectDTO the suspectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new suspectDTO, or with status {@code 400 (Bad Request)} if the suspect has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/suspects")
    public ResponseEntity<SuspectDTO> createSuspect(@RequestBody SuspectDTO suspectDTO) throws URISyntaxException {
        log.debug("REST request to save Suspect : {}", suspectDTO);
        if (suspectDTO.getId() != null) {
            throw new BadRequestAlertException("A new suspect cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SuspectDTO result = suspectService.save(suspectDTO);
        return ResponseEntity.created(new URI("/api/suspects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /suspects} : Updates an existing suspect.
     *
     * @param suspectDTO the suspectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated suspectDTO,
     * or with status {@code 400 (Bad Request)} if the suspectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the suspectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/suspects")
    public ResponseEntity<SuspectDTO> updateSuspect(@RequestBody SuspectDTO suspectDTO) throws URISyntaxException {
        log.debug("REST request to update Suspect : {}", suspectDTO);
        if (suspectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SuspectDTO result = suspectService.save(suspectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, suspectDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /suspects} : get all the suspects.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of suspects in body.
     */
    @GetMapping("/suspects")
    public ResponseEntity<List<SuspectDTO>> getAllSuspects(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Suspects");
        Page<SuspectDTO> page = suspectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /suspects/:id} : get the "id" suspect.
     *
     * @param id the id of the suspectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the suspectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/suspects/{id}")
    public ResponseEntity<SuspectDTO> getSuspect(@PathVariable Long id) {
        log.debug("REST request to get Suspect : {}", id);
        Optional<SuspectDTO> suspectDTO = suspectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(suspectDTO);
    }

    /**
     * {@code DELETE  /suspects/:id} : delete the "id" suspect.
     *
     * @param id the id of the suspectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/suspects/{id}")
    public ResponseEntity<Void> deleteSuspect(@PathVariable Long id) {
        log.debug("REST request to delete Suspect : {}", id);
        suspectService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
