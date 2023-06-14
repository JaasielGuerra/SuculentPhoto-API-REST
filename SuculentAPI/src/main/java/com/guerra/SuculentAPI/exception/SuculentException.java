package com.guerra.SuculentAPI.exception;

import java.util.ArrayList;
import java.util.List;

public class SuculentException extends Exception{

    private List<SuculentException> exceptionsList;

    public SuculentException(String message, List<SuculentException> exceptionsList) {
        super(message);
        this.exceptionsList = exceptionsList;
    }

    public SuculentException(String message, Throwable cause) {
        super(message, cause);
        this.exceptionsList = new ArrayList<>();
    }

    public SuculentException(String message) {
        super(message);
        this.exceptionsList = new ArrayList<>();
    }

    public SuculentException() {
        super("Error en la lógica de negocio");
        this.exceptionsList = new ArrayList<>();
    }

    public List<SuculentException> getExceptionsList() {
        return exceptionsList;
    }

    public void addException(SuculentException exception) {
        if (exceptionsList == null) {
            exceptionsList = new ArrayList<>();
        }
        exceptionsList.add(exception);
    }

    public boolean hasExceptions() {
        return exceptionsList != null && !exceptionsList.isEmpty();
    }

    public SuculentException build() {

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(this.getMessage());

        for (SuculentException exceptionItem : this.exceptionsList) {
            messageBuilder
                    .append("\n\t")
                    .append(exceptionItem.getMessage());

            if (exceptionItem.getCause() != null) {
                messageBuilder
                        .append(" -> ")
                        .append(exceptionItem.getCause());
            }
        }

        return new SuculentException(messageBuilder.toString(), this.exceptionsList);
    }



}
