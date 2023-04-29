package ru.practicum.exceptions.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String message) {
        super(message);
    }

    public String getReason() {
        return "The required object was not found.";
    }
}
