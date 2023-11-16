package me.sleepyfish.smok.utils.backend.events;

import me.sleepyfish.smok.rats.event.Event;

// Class from SMok Client by SleepyFish
public class BackendMessageReceivedEvent extends Event {

    private final String message;

    public BackendMessageReceivedEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}