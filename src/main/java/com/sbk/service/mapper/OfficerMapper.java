package com.sbk.service.mapper;

import com.sbk.domain.*;
import com.sbk.service.dto.OfficerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Officer} and its DTO {@link OfficerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OfficerMapper extends EntityMapper<OfficerDTO, Officer> {


    @Mapping(target = "assignedIncidents", ignore = true)
    @Mapping(target = "removeAssignedIncidents", ignore = true)
    Officer toEntity(OfficerDTO officerDTO);

    default Officer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Officer officer = new Officer();
        officer.setId(id);
        return officer;
    }
}
