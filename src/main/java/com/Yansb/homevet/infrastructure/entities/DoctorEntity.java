package com.Yansb.homevet.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "doctors")
public class DoctorEntity {
  @Id
  private UUID id;

  @Column()
  private String license_number;

  @Column()
  private Integer service_radius;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "attending_address_id", referencedColumnName = "id")
  private AddressEntity attendingAddress;

  @JsonIgnore
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private UserEntity user;

  @ManyToMany
  @JoinTable(name = "doctor_specialties", joinColumns = @JoinColumn(name = "doctor_id"), inverseJoinColumns = @JoinColumn(name = "speciality_id"))
  private List<SpecialtyEntity> specialties;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at")
  private Instant createdAt;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at")
  private Instant updatedAt;

  public void addSpecialty(SpecialtyEntity specialtyEntity) {
    this.specialties.add(specialtyEntity);
  }
}
