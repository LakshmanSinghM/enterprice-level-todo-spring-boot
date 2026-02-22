-- liquibase formatted sql
-- =====================================================
-- Seed default permissions (PostgreSQL optimized)
-- =====================================================
-- changeset lakshman:seed-permissions-postgres

INSERT INTO permissions (name, description) VALUES
('todo:read', 'Read todo items'),
('todo:create', 'Create new todo items'),
('todo:update', 'Update existing todo items'),
('todo:delete', 'Delete todo items'),
('category:read', 'Read category data'),
('category:create', 'Create category'),
('category:update', 'Update category'),
('category:delete', 'Delete category')
ON CONFLICT (name) DO NOTHING;

-- rollback DELETE FROM permissions WHERE name IN (
--   'todo:read',
--   'todo:create',
--   'todo:update',
--   'todo:delete',
--   'category:read',
--   'category:create',
--   'category:update',
--   'category:delete'
-- );