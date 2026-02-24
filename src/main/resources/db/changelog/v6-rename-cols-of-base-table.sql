-- liquibase formatted sql

-- changeset lakshman:audit-permissions-1

-- Add new columns as nullable first
ALTER TABLE permissions ADD COLUMN created_at TIMESTAMP(6);
ALTER TABLE permissions ADD COLUMN created_by BIGINT;
ALTER TABLE permissions ADD COLUMN is_active BOOLEAN DEFAULT TRUE;
ALTER TABLE permissions ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;
ALTER TABLE permissions ADD COLUMN updated_at TIMESTAMP(6);
ALTER TABLE permissions ADD COLUMN updated_by BIGINT;

-- Backfill from old columns if they exist
UPDATE permissions SET created_at = "createdAt" WHERE created_at IS NULL;
UPDATE permissions SET created_by = "createdBy" WHERE created_by IS NULL;
UPDATE permissions SET is_active = "isActive" WHERE is_active IS NULL;
UPDATE permissions SET is_deleted = "isDeleted" WHERE is_deleted IS NULL;
UPDATE permissions SET updated_at = "updatedAt" WHERE updated_at IS NULL;
UPDATE permissions SET updated_by = "updatedBy" WHERE updated_by IS NULL;

-- Ensure no NULL values remain
UPDATE permissions SET created_at = NOW() WHERE created_at IS NULL;
UPDATE permissions SET is_active = TRUE WHERE is_active IS NULL;
UPDATE permissions SET is_deleted = FALSE WHERE is_deleted IS NULL;

-- Add NOT NULL constraints
ALTER TABLE permissions ALTER COLUMN created_at SET NOT NULL;
ALTER TABLE permissions ALTER COLUMN is_active SET NOT NULL;
ALTER TABLE permissions ALTER COLUMN is_deleted SET NOT NULL;

-- Drop old camelCase columns
ALTER TABLE permissions DROP COLUMN IF EXISTS "createdAt";
ALTER TABLE permissions DROP COLUMN IF EXISTS "createdBy";
ALTER TABLE permissions DROP COLUMN IF EXISTS "isActive";
ALTER TABLE permissions DROP COLUMN IF EXISTS "isDeleted";
ALTER TABLE permissions DROP COLUMN IF EXISTS "updatedAt";
ALTER TABLE permissions DROP COLUMN IF EXISTS "updatedBy";



-- changeset lakshman:audit-roles-1
ALTER TABLE roles ADD COLUMN created_at TIMESTAMP(6);
ALTER TABLE roles ADD COLUMN created_by BIGINT;
ALTER TABLE roles ADD COLUMN is_active BOOLEAN DEFAULT TRUE;
ALTER TABLE roles ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;
ALTER TABLE roles ADD COLUMN updated_at TIMESTAMP(6);
ALTER TABLE roles ADD COLUMN updated_by BIGINT;

UPDATE roles SET created_at = "createdAt" WHERE created_at IS NULL;
UPDATE roles SET created_by = "createdBy" WHERE created_by IS NULL;
UPDATE roles SET is_active = "isActive" WHERE is_active IS NULL;
UPDATE roles SET is_deleted = "isDeleted" WHERE is_deleted IS NULL;
UPDATE roles SET updated_at = "updatedAt" WHERE updated_at IS NULL;
UPDATE roles SET updated_by = "updatedBy" WHERE updated_by IS NULL;

UPDATE roles SET created_at = NOW() WHERE created_at IS NULL;
UPDATE roles SET is_active = TRUE WHERE is_active IS NULL;
UPDATE roles SET is_deleted = FALSE WHERE is_deleted IS NULL;

ALTER TABLE roles ALTER COLUMN created_at SET NOT NULL;
ALTER TABLE roles ALTER COLUMN is_active SET NOT NULL;
ALTER TABLE roles ALTER COLUMN is_deleted SET NOT NULL;

ALTER TABLE roles DROP COLUMN IF EXISTS "createdAt";
ALTER TABLE roles DROP COLUMN IF EXISTS "createdBy";
ALTER TABLE roles DROP COLUMN IF EXISTS "isActive";
ALTER TABLE roles DROP COLUMN IF EXISTS "isDeleted";
ALTER TABLE roles DROP COLUMN IF EXISTS "updatedAt";
ALTER TABLE roles DROP COLUMN IF EXISTS "updatedBy";


-- changeset lakshman:audit-users-1
ALTER TABLE users ADD COLUMN created_at TIMESTAMP(6);
ALTER TABLE users ADD COLUMN created_by BIGINT;
ALTER TABLE users ADD COLUMN is_active BOOLEAN DEFAULT TRUE;
ALTER TABLE users ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE;
ALTER TABLE users ADD COLUMN updated_at TIMESTAMP(6);
ALTER TABLE users ADD COLUMN updated_by BIGINT;

UPDATE users SET created_at = "createdAt" WHERE created_at IS NULL;
UPDATE users SET created_by = "createdBy" WHERE created_by IS NULL;
UPDATE users SET is_active = "isActive" WHERE is_active IS NULL;
UPDATE users SET is_deleted = "isDeleted" WHERE is_deleted IS NULL;
UPDATE users SET updated_at = "updatedAt" WHERE updated_at IS NULL;
UPDATE users SET updated_by = "updatedBy" WHERE updated_by IS NULL;

UPDATE users SET created_at = NOW() WHERE created_at IS NULL;
UPDATE users SET is_active = TRUE WHERE is_active IS NULL;
UPDATE users SET is_deleted = FALSE WHERE is_deleted IS NULL;

ALTER TABLE users ALTER COLUMN created_at SET NOT NULL;
ALTER TABLE users ALTER COLUMN is_active SET NOT NULL;
ALTER TABLE users ALTER COLUMN is_deleted SET NOT NULL;

ALTER TABLE users DROP COLUMN IF EXISTS "createdAt";
ALTER TABLE users DROP COLUMN IF EXISTS "createdBy";
ALTER TABLE users DROP COLUMN IF EXISTS "isActive";
ALTER TABLE users DROP COLUMN IF EXISTS "isDeleted";
ALTER TABLE users DROP COLUMN IF EXISTS "updatedAt";
ALTER TABLE users DROP COLUMN IF EXISTS "updatedBy";