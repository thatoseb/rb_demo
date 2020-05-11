package com.sbk.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sbk.domain.IncidentTypes} entity.
 */
public class IncidentTypesDTO implements Serializable {

    private Long id;

    @NotNull
    private String type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncidentTypesDTO incidentTypesDTO = (IncidentTypesDTO) o;
        if (incidentTypesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidentTypesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncidentTypesDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
