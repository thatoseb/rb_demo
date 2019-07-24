package com.sbk.web.rest;

import com.sbk.RobocopApp;
import com.sbk.domain.Officer;
import com.sbk.repository.OfficerRepository;
import com.sbk.service.OfficerService;
import com.sbk.service.dto.OfficerDTO;
import com.sbk.service.mapper.OfficerMapper;
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
 * Integration tests for the {@Link OfficerResource} REST controller.
 */
@SpringBootTest(classes = RobocopApp.class)
public class OfficerResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATION_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    @Autowired
    private OfficerRepository officerRepository;

    @Autowired
    private OfficerMapper officerMapper;

    @Autowired
    private OfficerService officerService;

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

    private MockMvc restOfficerMockMvc;

    private Officer officer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfficerResource officerResource = new OfficerResource(officerService);
        this.restOfficerMockMvc = MockMvcBuilders.standaloneSetup(officerResource)
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
    public static Officer createEntity(EntityManager em) {
        Officer officer = new Officer()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .identificationNumber(DEFAULT_IDENTIFICATION_NUMBER)
            .designation(DEFAULT_DESIGNATION);
        return officer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Officer createUpdatedEntity(EntityManager em) {
        Officer officer = new Officer()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .identificationNumber(UPDATED_IDENTIFICATION_NUMBER)
            .designation(UPDATED_DESIGNATION);
        return officer;
    }

    @BeforeEach
    public void initTest() {
        officer = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfficer() throws Exception {
        int databaseSizeBeforeCreate = officerRepository.findAll().size();

        // Create the Officer
        OfficerDTO officerDTO = officerMapper.toDto(officer);
        restOfficerMockMvc.perform(post("/api/officers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officerDTO)))
            .andExpect(status().isCreated());

        // Validate the Officer in the database
        List<Officer> officerList = officerRepository.findAll();
        assertThat(officerList).hasSize(databaseSizeBeforeCreate + 1);
        Officer testOfficer = officerList.get(officerList.size() - 1);
        assertThat(testOfficer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testOfficer.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testOfficer.getIdentificationNumber()).isEqualTo(DEFAULT_IDENTIFICATION_NUMBER);
        assertThat(testOfficer.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    public void createOfficerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = officerRepository.findAll().size();

        // Create the Officer with an existing ID
        officer.setId(1L);
        OfficerDTO officerDTO = officerMapper.toDto(officer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfficerMockMvc.perform(post("/api/officers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Officer in the database
        List<Officer> officerList = officerRepository.findAll();
        assertThat(officerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOfficers() throws Exception {
        // Initialize the database
        officerRepository.saveAndFlush(officer);

        // Get all the officerList
        restOfficerMockMvc.perform(get("/api/officers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(officer.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].identificationNumber").value(hasItem(DEFAULT_IDENTIFICATION_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())));
    }
    
    @Test
    @Transactional
    public void getOfficer() throws Exception {
        // Initialize the database
        officerRepository.saveAndFlush(officer);

        // Get the officer
        restOfficerMockMvc.perform(get("/api/officers/{id}", officer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(officer.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.identificationNumber").value(DEFAULT_IDENTIFICATION_NUMBER.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOfficer() throws Exception {
        // Get the officer
        restOfficerMockMvc.perform(get("/api/officers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfficer() throws Exception {
        // Initialize the database
        officerRepository.saveAndFlush(officer);

        int databaseSizeBeforeUpdate = officerRepository.findAll().size();

        // Update the officer
        Officer updatedOfficer = officerRepository.findById(officer.getId()).get();
        // Disconnect from session so that the updates on updatedOfficer are not directly saved in db
        em.detach(updatedOfficer);
        updatedOfficer
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .identificationNumber(UPDATED_IDENTIFICATION_NUMBER)
            .designation(UPDATED_DESIGNATION);
        OfficerDTO officerDTO = officerMapper.toDto(updatedOfficer);

        restOfficerMockMvc.perform(put("/api/officers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officerDTO)))
            .andExpect(status().isOk());

        // Validate the Officer in the database
        List<Officer> officerList = officerRepository.findAll();
        assertThat(officerList).hasSize(databaseSizeBeforeUpdate);
        Officer testOfficer = officerList.get(officerList.size() - 1);
        assertThat(testOfficer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testOfficer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testOfficer.getIdentificationNumber()).isEqualTo(UPDATED_IDENTIFICATION_NUMBER);
        assertThat(testOfficer.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void updateNonExistingOfficer() throws Exception {
        int databaseSizeBeforeUpdate = officerRepository.findAll().size();

        // Create the Officer
        OfficerDTO officerDTO = officerMapper.toDto(officer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfficerMockMvc.perform(put("/api/officers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Officer in the database
        List<Officer> officerList = officerRepository.findAll();
        assertThat(officerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOfficer() throws Exception {
        // Initialize the database
        officerRepository.saveAndFlush(officer);

        int databaseSizeBeforeDelete = officerRepository.findAll().size();

        // Delete the officer
        restOfficerMockMvc.perform(delete("/api/officers/{id}", officer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Officer> officerList = officerRepository.findAll();
        assertThat(officerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Officer.class);
        Officer officer1 = new Officer();
        officer1.setId(1L);
        Officer officer2 = new Officer();
        officer2.setId(officer1.getId());
        assertThat(officer1).isEqualTo(officer2);
        officer2.setId(2L);
        assertThat(officer1).isNotEqualTo(officer2);
        officer1.setId(null);
        assertThat(officer1).isNotEqualTo(officer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfficerDTO.class);
        OfficerDTO officerDTO1 = new OfficerDTO();
        officerDTO1.setId(1L);
        OfficerDTO officerDTO2 = new OfficerDTO();
        assertThat(officerDTO1).isNotEqualTo(officerDTO2);
        officerDTO2.setId(officerDTO1.getId());
        assertThat(officerDTO1).isEqualTo(officerDTO2);
        officerDTO2.setId(2L);
        assertThat(officerDTO1).isNotEqualTo(officerDTO2);
        officerDTO1.setId(null);
        assertThat(officerDTO1).isNotEqualTo(officerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(officerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(officerMapper.fromId(null)).isNull();
    }
}
