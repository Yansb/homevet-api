ALTER TABLE
  files
ADD
  COLUMN IF NOT EXISTS uploaded_at TIMESTAMP DEFAULT NULL;

ALTER TABLE
  files
ADD
  COLUMN IF NOT EXISTS user_id TEXT NOT NULL;

ALTER TABLE
  files
ADD
  CONSTRAINT fk_files_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE
  users
ADD
  COLUMN IF NOT EXISTS profile_picture_id TEXT;

ALTER TABLE
  users
ADD
  CONSTRAINT fk_users_profile_picture FOREIGN KEY (profile_picture_id) REFERENCES files(id) ON DELETE
SET
  NULL;
