package com.sbk.service.mapper;

import com.sbk.domain.*;
import com.sbk.service.dto.IncidentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Incident} and its DTO {@link IncidentDTO}.
 */
@Mapper(componentModel = "spring", uses = {OfficerMapper.class, SuspectMapper.class, UserMapper.class})
public interface IncidentMapper extends EntityMapper<IncidentDTO, Incident> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    IncidentDTO toDto(Incident incident);

    @Mapping(target = "removeOfficer", ignore = true)
    @Mapping(target = "removeSuspect", ignore = true)
    @Mapping(source = "userId", target = "user")
    Incident toEntity(IncidentDTO incidentDTO);

    default Incident fromId(Long id) {
        if (id == null) {
            return null;
        }
        Incident incident = new Incident();
        incident.setId(id);
        return incident;
    }
}
