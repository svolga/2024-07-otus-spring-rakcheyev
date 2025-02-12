package ru.otus.hw.security;

public enum RoleKey {
    ADMIN("ADMIN"),
    TEACHER("TEACHER"),
    STUDENT("STUDENT");

    public final String label;

    private RoleKey(String label) {
        this.label = label;
    }
}
