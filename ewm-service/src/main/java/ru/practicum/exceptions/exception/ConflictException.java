package ru.practicum.exceptions.exception;

public class ConflictException extends RuntimeException {
    public ConflictException (final String message){
        super(message);
    }
    public String getReason(){
        return "conflict";
    }
}
