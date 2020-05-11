package com.sbk.service.mapper;

import com.sbk.domain.*;
import com.sbk.service.dto.IncidentTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IncidentTypes} and its DTO {@link IncidentTypesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IncidentTypesMapper extends EntityMapper<IncidentTypesDTO, IncidentTypes> {



    default IncidentTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        IncidentTypes incidentTypes = new IncidentTypes();
        incidentTypes.setId(id);
        return incidentTypes;
    }
}
