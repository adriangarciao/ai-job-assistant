-- Add a separate column name to avoid keyword issues
ALTER TABLE users ADD COLUMN IF NOT EXISTS user_role VARCHAR(20);

-- Backfill existing rows to USER so we can enforce NOT NULL
UPDATE users SET user_role = 'USER' WHERE user_role IS NULL;

-- Lock it down
ALTER TABLE users ALTER COLUMN user_role SET NOT NULL;
