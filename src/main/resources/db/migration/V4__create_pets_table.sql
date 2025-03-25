
ALTER TABLE specialities RENAME TO specialties;
ALTER INDEX specialities_name_idx RENAME TO specialties_name_idx;
ALTER TABLE doctor_specialities RENAME TO doctor_specialties;
ALTER TABLE doctor_specialties RENAME CONSTRAINT fk_speciality TO fk_specialty;

CREATE TYPE pet_gender as ENUM ('MALE', 'FEMALE', 'UNKNOWN');

CREATE TABLE pets (
  id UUID PRIMARY KEY,
  owner_id TEXT REFERENCES users(id),
  name TEXT NOT NULL,
  species TEXT NOT NULL,
  breed TEXT,
  gender pet_gender NOT NULL,
  birth_date DATE,
  weigth DECIMAL,
  notes TEXT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_pets_owner_id ON pets(owner_id);
