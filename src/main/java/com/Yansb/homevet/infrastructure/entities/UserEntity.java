package com.Yansb.homevet.infrastructure.entities;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import com.Yansb.homevet.infrastructure.entities.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Parameter;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@EqualsAndHashCode(exclude = { "pets", "address", "files", "profilePicture" })
@ToString(exclude = { "pets", "address", "files", "profilePicture" })
public class UserEntity {
        @Id
        private String id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String email;

        @Column(columnDefinition = "phone_number")
        private String phoneNumber;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "profile_picture_id", referencedColumnName = "id")
        @JsonIgnore
        private FileEntity profilePicture;

        @Type(value = ListArrayType.class, parameters = {
                        @Parameter(name = ListArrayType.SQL_ARRAY_TYPE, value = "user_role")
        })
        @Column(columnDefinition = "user_role[]")
        @Enumerated(EnumType.STRING)
        private List<UserRole> roles;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "pets_owners", joinColumns = @JoinColumn(name = "owner_id"), inverseJoinColumns = @JoinColumn(name = "pet_id"))
        @JsonIgnore
        private Set<PetsEntity> pets;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        @JsonIgnore
        private Collection<AddressEntity> address;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        @JsonIgnore
        private Collection<FileEntity> files;

        @CreationTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "created_at", nullable = false, updatable = false)
        private Instant createdAt;

        @UpdateTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "updated_at")
        private Instant updatedAt;

        public void addPet(PetsEntity pet) {
                this.pets.add(pet);
        }
}
