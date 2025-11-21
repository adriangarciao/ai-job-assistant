DO $$
BEGIN
    -- Examples; keep/extend based on your current columns
    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name='resumes' AND column_name='createdat') THEN
        EXECUTE 'ALTER TABLE resumes RENAME COLUMN createdat TO created_at';
    END IF;

    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name='resumes' AND column_name='updatedat') THEN
        EXECUTE 'ALTER TABLE resumes RENAME COLUMN updatedat TO updated_at';
    END IF;

    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name='resumes' AND column_name='userid') THEN
        EXECUTE 'ALTER TABLE resumes RENAME COLUMN userid TO user_id';
    END IF;
END $$;
