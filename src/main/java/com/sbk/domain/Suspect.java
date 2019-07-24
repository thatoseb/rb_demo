package com.sbk.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Suspect.
 */
@Entity
@Table(name = "suspect")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Suspect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "last_known_address")
    private String lastKnownAddress;

    @ManyToMany(mappedBy = "suspects")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Incident> linkedIncidents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Suspect firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Suspect lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastKnownAddress() {
        return lastKnownAddress;
    }

    public Suspect lastKnownAddress(String lastKnownAddress) {
        this.lastKnownAddress = lastKnownAddress;
        return this;
    }

    public void setLastKnownAddress(String lastKnownAddress) {
        this.lastKnownAddress = lastKnownAddress;
    }

    public Set<Incident> getLinkedIncidents() {
        return linkedIncidents;
    }

    public Suspect linkedIncidents(Set<Incident> incidents) {
        this.linkedIncidents = incidents;
        return this;
    }

    public Suspect addLinkedIncidents(Incident incident) {
        this.linkedIncidents.add(incident);
        incident.getSuspects().add(this);
        return this;
    }

    public Suspect removeLinkedIncidents(Incident incident) {
        this.linkedIncidents.remove(incident);
        incident.getSuspects().remove(this);
        return this;
    }

    public void setLinkedIncidents(Set<Incident> incidents) {
        this.linkedIncidents = incidents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Suspect)) {
            return false;
        }
        return id != null && id.equals(((Suspect) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Suspect{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", lastKnownAddress='" + getLastKnownAddress() + "'" +
            "}";
    }
}
