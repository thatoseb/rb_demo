package com.sbk.web.rest;

import com.sbk.RobocopApp;
import com.sbk.domain.IncidentTypes;
import com.sbk.repository.IncidentTypesRepository;
import com.sbk.service.IncidentTypesService;
import com.sbk.service.dto.IncidentTypesDTO;
import com.sbk.service.mapper.IncidentTypesMapper;
import com.sbk.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sbk.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link IncidentTypesResource} REST controller.
 */
@SpringBootTest(classes = RobocopApp.class)
public class IncidentTypesResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private IncidentTypesRepository incidentTypesRepository;

    @Autowired
    private IncidentTypesMapper incidentTypesMapper;

    @Autowired
    private IncidentTypesService incidentTypesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restIncidentTypesMockMvc;

    private IncidentTypes incidentTypes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncidentTypesResource incidentTypesResource = new IncidentTypesResource(incidentTypesService);
        this.restIncidentTypesMockMvc = MockMvcBuilders.standaloneSetup(incidentTypesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncidentTypes createEntity(EntityManager em) {
        IncidentTypes incidentTypes = new IncidentTypes()
            .type(DEFAULT_TYPE);
        return incidentTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncidentTypes createUpdatedEntity(EntityManager em) {
        IncidentTypes incidentTypes = new IncidentTypes()
            .type(UPDATED_TYPE);
        return incidentTypes;
    }

    @BeforeEach
    public void initTest() {
        incidentTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncidentTypes() throws Exception {
        int databaseSizeBeforeCreate = incidentTypesRepository.findAll().size();

        // Create the IncidentTypes
        IncidentTypesDTO incidentTypesDTO = incidentTypesMapper.toDto(incidentTypes);
        restIncidentTypesMockMvc.perform(post("/api/incident-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the IncidentTypes in the database
        List<IncidentTypes> incidentTypesList = incidentTypesRepository.findAll();
        assertThat(incidentTypesList).hasSize(databaseSizeBeforeCreate + 1);
        IncidentTypes testIncidentTypes = incidentTypesList.get(incidentTypesList.size() - 1);
        assertThat(testIncidentTypes.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createIncidentTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incidentTypesRepository.findAll().size();

        // Create the IncidentTypes with an existing ID
        incidentTypes.setId(1L);
        IncidentTypesDTO incidentTypesDTO = incidentTypesMapper.toDto(incidentTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidentTypesMockMvc.perform(post("/api/incident-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncidentTypes in the database
        List<IncidentTypes> incidentTypesList = incidentTypesRepository.findAll();
        assertThat(incidentTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentTypesRepository.findAll().size();
        // set the field null
        incidentTypes.setType(null);

        // Create the IncidentTypes, which fails.
        IncidentTypesDTO incidentTypesDTO = incidentTypesMapper.toDto(incidentTypes);

        restIncidentTypesMockMvc.perform(post("/api/incident-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentTypesDTO)))
            .andExpect(status().isBadRequest());

        List<IncidentTypes> incidentTypesList = incidentTypesRepository.findAll();
        assertThat(incidentTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIncidentTypes() throws Exception {
        // Initialize the database
        incidentTypesRepository.saveAndFlush(incidentTypes);

        // Get all the incidentTypesList
        restIncidentTypesMockMvc.perform(get("/api/incident-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incidentTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getIncidentTypes() throws Exception {
        // Initialize the database
        incidentTypesRepository.saveAndFlush(incidentTypes);

        // Get the incidentTypes
        restIncidentTypesMockMvc.perform(get("/api/incident-types/{id}", incidentTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incidentTypes.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIncidentTypes() throws Exception {
        // Get the incidentTypes
        restIncidentTypesMockMvc.perform(get("/api/incident-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncidentTypes() throws Exception {
        // Initialize the database
        incidentTypesRepository.saveAndFlush(incidentTypes);

        int databaseSizeBeforeUpdate = incidentTypesRepository.findAll().size();

        // Update the incidentTypes
        IncidentTypes updatedIncidentTypes = incidentTypesRepository.findById(incidentTypes.getId()).get();
        // Disconnect from session so that the updates on updatedIncidentTypes are not directly saved in db
        em.detach(updatedIncidentTypes);
        updatedIncidentTypes
            .type(UPDATED_TYPE);
        IncidentTypesDTO incidentTypesDTO = incidentTypesMapper.toDto(updatedIncidentTypes);

        restIncidentTypesMockMvc.perform(put("/api/incident-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentTypesDTO)))
            .andExpect(status().isOk());

        // Validate the IncidentTypes in the database
        List<IncidentTypes> incidentTypesList = incidentTypesRepository.findAll();
        assertThat(incidentTypesList).hasSize(databaseSizeBeforeUpdate);
        IncidentTypes testIncidentTypes = incidentTypesList.get(incidentTypesList.size() - 1);
        assertThat(testIncidentTypes.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingIncidentTypes() throws Exception {
        int databaseSizeBeforeUpdate = incidentTypesRepository.findAll().size();

        // Create the IncidentTypes
        IncidentTypesDTO incidentTypesDTO = incidentTypesMapper.toDto(incidentTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncidentTypesMockMvc.perform(put("/api/incident-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidentTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IncidentTypes in the database
        List<IncidentTypes> incidentTypesList = incidentTypesRepository.findAll();
        assertThat(incidentTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncidentTypes() throws Exception {
        // Initialize the database
        incidentTypesRepository.saveAndFlush(incidentTypes);

        int databaseSizeBeforeDelete = incidentTypesRepository.findAll().size();

        // Delete the incidentTypes
        restIncidentTypesMockMvc.perform(delete("/api/incident-types/{id}", incidentTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IncidentTypes> incidentTypesList = incidentTypesRepository.findAll();
        assertThat(incidentTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidentTypes.class);
        IncidentTypes incidentTypes1 = new IncidentTypes();
        incidentTypes1.setId(1L);
        IncidentTypes incidentTypes2 = new IncidentTypes();
        incidentTypes2.setId(incidentTypes1.getId());
        assertThat(incidentTypes1).isEqualTo(incidentTypes2);
        incidentTypes2.setId(2L);
        assertThat(incidentTypes1).isNotEqualTo(incidentTypes2);
        incidentTypes1.setId(null);
        assertThat(incidentTypes1).isNotEqualTo(incidentTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidentTypesDTO.class);
        IncidentTypesDTO incidentTypesDTO1 = new IncidentTypesDTO();
        incidentTypesDTO1.setId(1L);
        IncidentTypesDTO incidentTypesDTO2 = new IncidentTypesDTO();
        assertThat(incidentTypesDTO1).isNotEqualTo(incidentTypesDTO2);
        incidentTypesDTO2.setId(incidentTypesDTO1.getId());
        assertThat(incidentTypesDTO1).isEqualTo(incidentTypesDTO2);
        incidentTypesDTO2.setId(2L);
        assertThat(incidentTypesDTO1).isNotEqualTo(incidentTypesDTO2);
        incidentTypesDTO1.setId(null);
        assertThat(incidentTypesDTO1).isNotEqualTo(incidentTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(incidentTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(incidentTypesMapper.fromId(null)).isNull();
    }
}
