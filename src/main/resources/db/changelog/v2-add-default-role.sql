-- liquibase formatted sql

-- changeset lakshman:insert-default-roles-safe

INSERT INTO roles ("createdAt","isActive","isDeleted",name,description)
VALUES (NOW(),TRUE,FALSE,'ADMIN','Administrator with full access')
ON CONFLICT (name) DO NOTHING;

INSERT INTO roles ("createdAt","isActive","isDeleted",name,description)
VALUES (NOW(),TRUE,FALSE,'USER','Default user role')
ON CONFLICT (name) DO NOTHING;

INSERT INTO roles ("createdAt","isActive","isDeleted",name,description)
VALUES (NOW(),TRUE,FALSE,'MANAGER','Manager level access')
ON CONFLICT (name) DO NOTHING;

-- rollback DELETE FROM roles WHERE name IN ('ADMIN','USER','MANAGER');