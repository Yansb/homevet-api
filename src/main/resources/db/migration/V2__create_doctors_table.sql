CREATE EXTENSION IF NOT EXISTS postgis;

ALTER TABLE users
    DROP COLUMN CRV,
    DROP COLUMN drivers_license;

CREATE TABLE files (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    link TEXT NOT NULL,
    file_type TEXT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE doctors (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id TEXT NOT NULL,
    license_number TEXT NOT NULL,
    service_radius DECIMAL NOT NULL,
    attending_address_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,


    CONSTRAINT fk_address FOREIGN KEY (attending_address_id) REFERENCES address(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX doctor_user_id_idx ON doctors(user_id);

CREATE TABLE specialities(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX specialities_name_idx ON specialities(name);

CREATE TABLE doctor_specialities(
    doctor_id UUID NOT NULL,
    speciality_id UUID NOT NULL,

    CONSTRAINT pk_doctor_specialities PRIMARY KEY (doctor_id, speciality_id),
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_speciality FOREIGN KEY (speciality_id) REFERENCES specialities(id)
);

CREATE TABLE certifications(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    doctor_id UUID NOT NULL,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    file_id TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_certifications_files FOREIGN KEY (file_id) REFERENCES files(id),
    CONSTRAINT fk_doctor_certifications FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

ALTER TABLE address ADD COLUMN location geography(point, 4326);
CREATE INDEX idx_address_location ON address USING GIST (location);
