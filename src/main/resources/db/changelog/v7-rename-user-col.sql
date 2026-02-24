-- liquibase formatted sql

-- changeset lakshman:1771963866988-1
ALTER TABLE users ADD first_name VARCHAR(255);

-- changeset lakshman:1771963866988-2
ALTER TABLE users ADD last_name VARCHAR(255);

-- changeset lakshman:1771963866988-3
ALTER TABLE users ADD provider_type VARCHAR(255);

-- changeset lakshman:1771963866988-4
ALTER TABLE users DROP COLUMN "firstName";

-- changeset lakshman:1771963866988-5
ALTER TABLE users DROP COLUMN "lastName";

-- changeset lakshman:1771963866988-6
ALTER TABLE users DROP COLUMN "providerType";

-- changeset lakshman:1771963866988-7
ALTER TABLE users DROP COLUMN "randomName";

