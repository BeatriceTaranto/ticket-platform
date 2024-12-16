package com.ticketplatform.model;

public enum Status {
    TODO,
    IN_PROGRESS,
    COMPLETED;
    
    public String getFormattedName() {
        switch (this) {
            case TODO:
                return "To Do";
            case IN_PROGRESS:
                return "In Progress";
            case COMPLETED:
                return "Completed";
            default:
                throw new IllegalArgumentException("Unknown status: " + this);
        }
    }
}


