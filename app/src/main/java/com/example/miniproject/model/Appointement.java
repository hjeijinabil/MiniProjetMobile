package com.example.miniproject.model;

import com.google.firebase.Timestamp;

public class Appointement {

    public String name;
    public Timestamp scheduledDate;
    public Timestamp timestamp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Timestamp scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
