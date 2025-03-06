package com.Yansb.homevet.infrastructure.entities;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Parameter;
import java.time.Instant;
import java.util.Collection;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity {
  @Id
  private String id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column(columnDefinition = "phone_number")
  private String phoneNumber;

  @Column()
  private String CRV;

  @Column(columnDefinition = "drivers_license")
  private String driversLicense;

  @Type(
      value = ListArrayType.class,
      parameters = {
          @Parameter(
              name = ListArrayType.SQL_ARRAY_TYPE,
              value = "user_role"
          )
      }
  )
  @Column(columnDefinition = "user_role[]")
  @Enumerated(EnumType.STRING)
  private List<UserRole> roles;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Collection<AddressEntity> address;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at")
  private Instant updatedAt;
}
