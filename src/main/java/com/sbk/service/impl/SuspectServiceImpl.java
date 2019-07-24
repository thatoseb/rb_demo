package com.sbk.service.impl;

import com.sbk.service.SuspectService;
import com.sbk.domain.Suspect;
import com.sbk.repository.SuspectRepository;
import com.sbk.service.dto.SuspectDTO;
import com.sbk.service.mapper.SuspectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Suspect}.
 */
@Service
@Transactional
public class SuspectServiceImpl implements SuspectService {

    private final Logger log = LoggerFactory.getLogger(SuspectServiceImpl.class);

    private final SuspectRepository suspectRepository;

    private final SuspectMapper suspectMapper;

    public SuspectServiceImpl(SuspectRepository suspectRepository, SuspectMapper suspectMapper) {
        this.suspectRepository = suspectRepository;
        this.suspectMapper = suspectMapper;
    }

    /**
     * Save a suspect.
     *
     * @param suspectDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SuspectDTO save(SuspectDTO suspectDTO) {
        log.debug("Request to save Suspect : {}", suspectDTO);
        Suspect suspect = suspectMapper.toEntity(suspectDTO);
        suspect = suspectRepository.save(suspect);
        return suspectMapper.toDto(suspect);
    }

    /**
     * Get all the suspects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SuspectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Suspects");
        return suspectRepository.findAll(pageable)
            .map(suspectMapper::toDto);
    }


    /**
     * Get one suspect by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SuspectDTO> findOne(Long id) {
        log.debug("Request to get Suspect : {}", id);
        return suspectRepository.findById(id)
            .map(suspectMapper::toDto);
    }

    /**
     * Delete the suspect by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Suspect : {}", id);
        suspectRepository.deleteById(id);
    }
}
