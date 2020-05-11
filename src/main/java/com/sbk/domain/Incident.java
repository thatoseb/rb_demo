package com.sbk.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.sbk.domain.enumeration.IncidentStatus;

/**
 * A Incident.
 */
@Entity
@Table(name = "incident")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "incident_status")
    private IncidentStatus incidentStatus;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "incident_officer",
               joinColumns = @JoinColumn(name = "incident_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "officer_id", referencedColumnName = "id"))
    private Set<Officer> officers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "incident_suspect",
               joinColumns = @JoinColumn(name = "incident_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "suspect_id", referencedColumnName = "id"))
    private Set<Suspect> suspects = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("incidents")
    private User user;

    @OneToOne(optional = false)    @NotNull

    @MapsId
    @JoinColumn(name = "id")
    private IncidentTypes incidentTypes;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public Incident startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public IncidentStatus getIncidentStatus() {
        return incidentStatus;
    }

    public Incident incidentStatus(IncidentStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
        return this;
    }

    public void setIncidentStatus(IncidentStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    public String getLocation() {
        return location;
    }

    public Incident location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public Incident description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Officer> getOfficers() {
        return officers;
    }

    public Incident officers(Set<Officer> officers) {
        this.officers = officers;
        return this;
    }

    public Incident addOfficer(Officer officer) {
        this.officers.add(officer);
        officer.getAssignedIncidents().add(this);
        return this;
    }

    public Incident removeOfficer(Officer officer) {
        this.officers.remove(officer);
        officer.getAssignedIncidents().remove(this);
        return this;
    }

    public void setOfficers(Set<Officer> officers) {
        this.officers = officers;
    }

    public Set<Suspect> getSuspects() {
        return suspects;
    }

    public Incident suspects(Set<Suspect> suspects) {
        this.suspects = suspects;
        return this;
    }

    public Incident addSuspect(Suspect suspect) {
        this.suspects.add(suspect);
        suspect.getLinkedIncidents().add(this);
        return this;
    }

    public Incident removeSuspect(Suspect suspect) {
        this.suspects.remove(suspect);
        suspect.getLinkedIncidents().remove(this);
        return this;
    }

    public void setSuspects(Set<Suspect> suspects) {
        this.suspects = suspects;
    }

    public User getUser() {
        return user;
    }

    public Incident user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public IncidentTypes getIncidentTypes() {
        return incidentTypes;
    }

    public Incident incidentTypes(IncidentTypes incidentTypes) {
        this.incidentTypes = incidentTypes;
        return this;
    }

    public void setIncidentTypes(IncidentTypes incidentTypes) {
        this.incidentTypes = incidentTypes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Incident)) {
            return false;
        }
        return id != null && id.equals(((Incident) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Incident{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", incidentStatus='" + getIncidentStatus() + "'" +
            ", location='" + getLocation() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
