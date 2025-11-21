-- Rename legacy columns to snake_case if they exist (idempotent)
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name='users' AND column_name='gradyear'
    ) THEN
        EXECUTE 'ALTER TABLE users RENAME COLUMN gradyear TO grad_year';
    END IF;

    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name='users' AND column_name='passwordHash'
    ) THEN
        -- Drop mistaken quoted camel column if it exists (unlikely now)
        EXECUTE 'ALTER TABLE users DROP COLUMN "passwordHash"';
    END IF;

    -- If someone created a quoted "role" column earlier, drop it
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name='users' AND column_name='role'
    ) THEN
        -- Prefer dedicated user_role column (you already added in V3)
        -- If you instead want to keep "role", you could rename it to user_role here.
        NULL;
    END IF;
END $$;

-- Ensure required columns exist & are NOT NULL
ALTER TABLE users
    ALTER COLUMN password_hash SET NOT NULL,
    ALTER COLUMN user_role SET NOT NULL;

-- Optional: enforce email uniqueness at DB level
CREATE UNIQUE INDEX IF NOT EXISTS users_email_unq ON users (email);
