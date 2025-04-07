package com.Yansb.homevet.infrastructure.entities;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import com.Yansb.homevet.infrastructure.entities.enums.PetGender;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import jakarta.persistence.CascadeType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pets")
@EqualsAndHashCode(exclude = "owners")
@ToString(exclude = "owners")
public class PetsEntity {
  @Id
  private UUID id;

  @Column()
  private String name;

  @Column()
  private String species;

  @Column()
  private String breed;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "birth_date")
  private Instant birthDate;

  @Column(columnDefinition = "pet_gender")
  @JdbcType(PostgreSQLEnumJdbcType.class)
  private PetGender gender;

  @ManyToMany(mappedBy = "pets", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JsonIgnore
  private Set<UserEntity> owners;

  @Column()
  private String notes;

  @Column()
  private Float weight;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at")
  private Instant updatedAt;

}
