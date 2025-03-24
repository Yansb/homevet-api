ALTER TABLE
  address ALTER complement DROP NOT NULL;

ALTER TABLE
  doctors
ALTER COLUMN
  service_radius TYPE INTEGER USING service_radius :: INTEGER;
