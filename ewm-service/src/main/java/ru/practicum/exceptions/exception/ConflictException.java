package ru.practicum.exceptions.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }

    public String getReason() {
        return "Integrity constraint has been violated.";
    }
}
