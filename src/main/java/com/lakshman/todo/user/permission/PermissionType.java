package com.lakshman.todo.user.permission;

public enum PermissionType {

    // User purpose
    TODO_READ("todo:read"),
    TODO_CREATE("todo:create"),
    TODO_UPDATE("todo:update"),
    TODO_DELETE("todo:delete"),

    // admin purpose
    CATEGORY_READ("category:read"),
    CATEGORY_CREATE("category:create"),
    CATEGORY_UPDATE("category:update"),
    CATEGORY_DELETE("category:delete");

    private final String permission;

    PermissionType(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
