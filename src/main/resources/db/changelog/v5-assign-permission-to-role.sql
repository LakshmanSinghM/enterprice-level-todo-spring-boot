-- liquibase formatted sql
 
-- Assign default permissions to USER role 
-- changeset lakshman:assign-user-role-permissions-2

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.name IN (
    'todo:read',
    'todo:create',
    'todo:update',
    'todo:delete'
)
WHERE r.name = 'USER'
ON CONFLICT DO NOTHING;

-- rollback DELETE FROM role_permissions
-- WHERE role_id = (SELECT id FROM roles WHERE name = 'USER');



-- Assign all permissions to ADMIN role
-- changeset lakshman:assign-admin-role-permissions-3

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
CROSS JOIN permissions p
WHERE r.name = 'ADMIN'
ON CONFLICT DO NOTHING;

-- rollback DELETE FROM role_permissions
-- WHERE role_id = (SELECT id FROM roles WHERE name = 'ADMIN');


-- changeset lakshman:1771794155495-1
ALTER TABLE permissions
ADD IF NOT EXISTS "createdAt" TIMESTAMP(6) WITHOUT TIME ZONE
NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- rollback ALTER TABLE permissions DROP COLUMN "createdAt";


-- changeset lakshman:1771794155495-2
ALTER TABLE permissions
ADD IF NOT EXISTS "createdBy" BIGINT;

-- rollback ALTER TABLE permissions DROP COLUMN "createdBy";


-- changeset lakshman:1771794155495-3
ALTER TABLE permissions
ADD IF NOT EXISTS "isActive" BOOLEAN
NOT NULL DEFAULT TRUE;

-- rollback ALTER TABLE permissions DROP COLUMN "isActive";


-- changeset lakshman:1771794155495-4
ALTER TABLE permissions
ADD IF NOT EXISTS "isDeleted" BOOLEAN
NOT NULL DEFAULT FALSE;

-- rollback ALTER TABLE permissions DROP COLUMN "isDeleted";


-- changeset lakshman:1771794155495-5
ALTER TABLE permissions
ADD  IF NOT EXISTS "updatedAt" TIMESTAMP(6) WITHOUT TIME ZONE;

-- rollback ALTER TABLE permissions DROP COLUMN "updatedAt";


-- changeset lakshman:1771794155495-6
ALTER TABLE permissions
ADD IF NOT EXISTS "updatedBy" BIGINT;

-- rollback ALTER TABLE permissions DROP COLUMN "updatedBy";