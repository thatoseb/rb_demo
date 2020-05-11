package com.sbk.repository;

import com.sbk.domain.IncidentTypes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IncidentTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidentTypesRepository extends JpaRepository<IncidentTypes, Long> {

}
