-- Make optional profile fields nullable so register can omit them
ALTER TABLE users ALTER COLUMN age DROP NOT NULL;
ALTER TABLE users ALTER COLUMN gradyear DROP NOT NULL;
ALTER TABLE users ALTER COLUMN college DROP NOT NULL;
ALTER TABLE users ALTER COLUMN major DROP NOT NULL;

-- If any of these columns don't exist in your schema, it's safe to guard them:
-- DO $$ BEGIN
--   IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name='users' AND column_name='age') THEN
--     EXECUTE 'ALTER TABLE users ALTER COLUMN age DROP NOT NULL';
--   END IF;
-- END $$;
