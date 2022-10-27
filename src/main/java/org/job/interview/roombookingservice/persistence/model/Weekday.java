package org.job.interview.roombookingservice.persistence.model;

import lombok.Getter;

@Getter
public enum Weekday {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY;


    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}