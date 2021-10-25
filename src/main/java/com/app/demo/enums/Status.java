package com.app.demo.enums;

public enum Status {
    REGISTERED, CONFIRMED, MARKED, PROCESSED, FINISHED;

    public static Status[] getConfirmedStatuses() {
        return new Status[]{CONFIRMED, MARKED};
    }

    public static int[] getConfirmedStatusesId() {
        return new int[]{CONFIRMED.ordinal(), MARKED.ordinal()};
    }
}
