DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name='applications' AND column_name='jobtitle'
    ) THEN
        EXECUTE 'ALTER TABLE applications RENAME COLUMN jobtitle TO job_title';
    END IF;

    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name='applications' AND column_name='applieddate'
    ) THEN
        EXECUTE 'ALTER TABLE applications RENAME COLUMN applieddate TO applied_date';
    END IF;

    -- If someone created a quoted "status" or other oddities, handle similarly:
    -- (status is already a good snake_case name, so usually nothing to do)
END $$;

-- Ensure the FK is correct/snake_case (adjust if your column is different)
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name='applications' AND column_name='user_id'
    ) THEN
        -- If you had a legacy "userId" column:
        IF EXISTS (
            SELECT 1 FROM information_schema.columns
            WHERE table_name='applications' AND column_name='userid'
        ) THEN
            EXECUTE 'ALTER TABLE applications RENAME COLUMN userid TO user_id';
        END IF;
    END IF;
END $$;
