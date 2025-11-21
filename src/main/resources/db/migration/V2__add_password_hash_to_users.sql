-- If someone once created a quoted camelCase column, drop it first (safe no-op otherwise)
ALTER TABLE users DROP COLUMN IF EXISTS "passwordHash";

-- Add the correct column
ALTER TABLE users ADD COLUMN IF NOT EXISTS password_hash VARCHAR(100);

-- (Optional) backfill existing rows with a placeholder bcrypt (so we can set NOT NULL)
-- This hash corresponds to password: "changeme123" (example)
UPDATE users
SET password_hash = '$2a$10$6N0mCGqv1x5mS5aQ3s3SMej2f4q9n8N1wM4YfQ2x2bE7uD7pQwFvC'
WHERE password_hash IS NULL;

-- Enforce NOT NULL going forward
ALTER TABLE users ALTER COLUMN password_hash SET NOT NULL;

-- (Optional, if you don’t already have it)
CREATE UNIQUE INDEX IF NOT EXISTS users_email_unq ON users (email);
