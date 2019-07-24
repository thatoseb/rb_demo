package com.sbk.web.rest;

import com.sbk.RobocopApp;
import com.sbk.domain.Suspect;
import com.sbk.repository.SuspectRepository;
import com.sbk.service.SuspectService;
import com.sbk.service.dto.SuspectDTO;
import com.sbk.service.mapper.SuspectMapper;
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
 * Integration tests for the {@Link SuspectResource} REST controller.
 */
@SpringBootTest(classes = RobocopApp.class)
public class SuspectResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_KNOWN_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_LAST_KNOWN_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private SuspectRepository suspectRepository;

    @Autowired
    private SuspectMapper suspectMapper;

    @Autowired
    private SuspectService suspectService;

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

    private MockMvc restSuspectMockMvc;

    private Suspect suspect;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SuspectResource suspectResource = new SuspectResource(suspectService);
        this.restSuspectMockMvc = MockMvcBuilders.standaloneSetup(suspectResource)
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
    public static Suspect createEntity(EntityManager em) {
        Suspect suspect = new Suspect()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .lastKnownAddress(DEFAULT_LAST_KNOWN_ADDRESS);
        return suspect;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suspect createUpdatedEntity(EntityManager em) {
        Suspect suspect = new Suspect()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .lastKnownAddress(UPDATED_LAST_KNOWN_ADDRESS);
        return suspect;
    }

    @BeforeEach
    public void initTest() {
        suspect = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuspect() throws Exception {
        int databaseSizeBeforeCreate = suspectRepository.findAll().size();

        // Create the Suspect
        SuspectDTO suspectDTO = suspectMapper.toDto(suspect);
        restSuspectMockMvc.perform(post("/api/suspects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suspectDTO)))
            .andExpect(status().isCreated());

        // Validate the Suspect in the database
        List<Suspect> suspectList = suspectRepository.findAll();
        assertThat(suspectList).hasSize(databaseSizeBeforeCreate + 1);
        Suspect testSuspect = suspectList.get(suspectList.size() - 1);
        assertThat(testSuspect.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testSuspect.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testSuspect.getLastKnownAddress()).isEqualTo(DEFAULT_LAST_KNOWN_ADDRESS);
    }

    @Test
    @Transactional
    public void createSuspectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suspectRepository.findAll().size();

        // Create the Suspect with an existing ID
        suspect.setId(1L);
        SuspectDTO suspectDTO = suspectMapper.toDto(suspect);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuspectMockMvc.perform(post("/api/suspects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suspectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Suspect in the database
        List<Suspect> suspectList = suspectRepository.findAll();
        assertThat(suspectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSuspects() throws Exception {
        // Initialize the database
        suspectRepository.saveAndFlush(suspect);

        // Get all the suspectList
        restSuspectMockMvc.perform(get("/api/suspects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suspect.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastKnownAddress").value(hasItem(DEFAULT_LAST_KNOWN_ADDRESS.toString())));
    }
    
    @Test
    @Transactional
    public void getSuspect() throws Exception {
        // Initialize the database
        suspectRepository.saveAndFlush(suspect);

        // Get the suspect
        restSuspectMockMvc.perform(get("/api/suspects/{id}", suspect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(suspect.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.lastKnownAddress").value(DEFAULT_LAST_KNOWN_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSuspect() throws Exception {
        // Get the suspect
        restSuspectMockMvc.perform(get("/api/suspects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuspect() throws Exception {
        // Initialize the database
        suspectRepository.saveAndFlush(suspect);

        int databaseSizeBeforeUpdate = suspectRepository.findAll().size();

        // Update the suspect
        Suspect updatedSuspect = suspectRepository.findById(suspect.getId()).get();
        // Disconnect from session so that the updates on updatedSuspect are not directly saved in db
        em.detach(updatedSuspect);
        updatedSuspect
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .lastKnownAddress(UPDATED_LAST_KNOWN_ADDRESS);
        SuspectDTO suspectDTO = suspectMapper.toDto(updatedSuspect);

        restSuspectMockMvc.perform(put("/api/suspects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suspectDTO)))
            .andExpect(status().isOk());

        // Validate the Suspect in the database
        List<Suspect> suspectList = suspectRepository.findAll();
        assertThat(suspectList).hasSize(databaseSizeBeforeUpdate);
        Suspect testSuspect = suspectList.get(suspectList.size() - 1);
        assertThat(testSuspect.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSuspect.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSuspect.getLastKnownAddress()).isEqualTo(UPDATED_LAST_KNOWN_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingSuspect() throws Exception {
        int databaseSizeBeforeUpdate = suspectRepository.findAll().size();

        // Create the Suspect
        SuspectDTO suspectDTO = suspectMapper.toDto(suspect);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuspectMockMvc.perform(put("/api/suspects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suspectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Suspect in the database
        List<Suspect> suspectList = suspectRepository.findAll();
        assertThat(suspectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSuspect() throws Exception {
        // Initialize the database
        suspectRepository.saveAndFlush(suspect);

        int databaseSizeBeforeDelete = suspectRepository.findAll().size();

        // Delete the suspect
        restSuspectMockMvc.perform(delete("/api/suspects/{id}", suspect.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Suspect> suspectList = suspectRepository.findAll();
        assertThat(suspectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Suspect.class);
        Suspect suspect1 = new Suspect();
        suspect1.setId(1L);
        Suspect suspect2 = new Suspect();
        suspect2.setId(suspect1.getId());
        assertThat(suspect1).isEqualTo(suspect2);
        suspect2.setId(2L);
        assertThat(suspect1).isNotEqualTo(suspect2);
        suspect1.setId(null);
        assertThat(suspect1).isNotEqualTo(suspect2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SuspectDTO.class);
        SuspectDTO suspectDTO1 = new SuspectDTO();
        suspectDTO1.setId(1L);
        SuspectDTO suspectDTO2 = new SuspectDTO();
        assertThat(suspectDTO1).isNotEqualTo(suspectDTO2);
        suspectDTO2.setId(suspectDTO1.getId());
        assertThat(suspectDTO1).isEqualTo(suspectDTO2);
        suspectDTO2.setId(2L);
        assertThat(suspectDTO1).isNotEqualTo(suspectDTO2);
        suspectDTO1.setId(null);
        assertThat(suspectDTO1).isNotEqualTo(suspectDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(suspectMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(suspectMapper.fromId(null)).isNull();
    }
}
