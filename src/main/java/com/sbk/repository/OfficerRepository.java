package com.sbk.repository;

import com.sbk.domain.Officer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Officer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfficerRepository extends JpaRepository<Officer, Long> {

}
