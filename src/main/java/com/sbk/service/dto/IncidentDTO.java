package com.sbk.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.sbk.domain.enumeration.IncidentStatus;

/**
 * A DTO for the {@link com.sbk.domain.Incident} entity.
 */
public class IncidentDTO implements Serializable {

    private Long id;

    private ZonedDateTime startDate;

    private IncidentStatus incidentStatus;

    private String location;

    private String description;


    private Set<OfficerDTO> officers = new HashSet<>();

    private Set<SuspectDTO> suspects = new HashSet<>();

    private Long userId;

    private String userLogin;

    private Long incidentTypesId;

    private String incidentTypesType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public IncidentStatus getIncidentStatus() {
        return incidentStatus;
    }

    public void setIncidentStatus(IncidentStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<OfficerDTO> getOfficers() {
        return officers;
    }

    public void setOfficers(Set<OfficerDTO> officers) {
        this.officers = officers;
    }

    public Set<SuspectDTO> getSuspects() {
        return suspects;
    }

    public void setSuspects(Set<SuspectDTO> suspects) {
        this.suspects = suspects;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getIncidentTypesId() {
        return incidentTypesId;
    }

    public void setIncidentTypesId(Long incidentTypesId) {
        this.incidentTypesId = incidentTypesId;
    }

    public String getIncidentTypesType() {
        return incidentTypesType;
    }

    public void setIncidentTypesType(String incidentTypesType) {
        this.incidentTypesType = incidentTypesType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncidentDTO incidentDTO = (IncidentDTO) o;
        if (incidentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncidentDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", incidentStatus='" + getIncidentStatus() + "'" +
            ", location='" + getLocation() + "'" +
            ", description='" + getDescription() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            ", incidentTypes=" + getIncidentTypesId() +
            ", incidentTypes='" + getIncidentTypesType() + "'" +
            "}";
    }
}
