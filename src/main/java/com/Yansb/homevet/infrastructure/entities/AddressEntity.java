package com.Yansb.homevet.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "address")
public class AddressEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false, columnDefinition = "zip_code")
    private String zipCode;

    @Column(nullable = false, columnDefinition = "firebase_id")
    private String state;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false,columnDefinition = "address_name")
    private String addressName;

    @Column(nullable = false)
    private String complement;

    @ManyToOne
    private UserEntity user;

    @Column()
    private Instant mainAddressAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Instant updatedAt;
}
