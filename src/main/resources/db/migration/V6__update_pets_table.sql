ALTER TABLE
  pets DROP CONSTRAINT IF EXISTS pets_owner_id_fkey;

ALTER TABLE
  pets RENAME COLUMN weigth TO weight;

ALTER TABLE
  pets DROP COLUMN IF EXISTS owner_id;

CREATE TABLE IF NOT EXISTS pets_owners(
  pet_id UUID NOT NULL,
  owner_id TEXT NOT NULL,
  CONSTRAINT pk_pets_owners PRIMARY KEY (pet_id, owner_id),
  CONSTRAINT fk_pets FOREIGN KEY (pet_id) REFERENCES pets(id),
  CONSTRAINT fk_owners FOREIGN KEY (owner_id) REFERENCES users(id)
);
