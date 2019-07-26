package com.sbk.repository;

import com.sbk.domain.Incident;
import com.sbk.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Incident entity.
 */
@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

    @Query("select incident from Incident incident where incident.user.login = ?#{principal.username}")
    List<Incident> findByUserIsCurrentUser();

    @Query(value = "select distinct incident from Incident incident left join fetch incident.officers left join fetch incident.suspects",
        countQuery = "select count(distinct incident) from Incident incident")
    Page<Incident> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct incident from Incident incident  left join fetch incident.officers left join fetch incident.suspects where incident.user.id = :userId",
        countQuery = "select count(distinct incident) from Incident incident")
    Page<Incident> findAllWithEagerRelationshipsByUser(Pageable pageable, @Param("userId") Long userId);

    @Query("select distinct incident from Incident incident left join fetch incident.officers left join fetch incident.suspects")
    List<Incident> findAllWithEagerRelationships();

    @Query("select incident from Incident incident left join fetch incident.officers left join fetch incident.suspects where incident.id =:id")
    Optional<Incident> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select incident from Incident incident where incident.user.id = :userId")
    Page<Incident> findAllByUser(Pageable pageable, @Param("userId") Long userId);

}
