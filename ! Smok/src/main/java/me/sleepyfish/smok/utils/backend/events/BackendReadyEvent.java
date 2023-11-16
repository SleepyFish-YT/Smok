package me.sleepyfish.smok.utils.backend.events;

import me.sleepyfish.smok.rats.event.Event;
import me.sleepyfish.smok.utils.backend.Backend;

// Class from SMok Client by SleepyFish
public class BackendReadyEvent extends Event {

    private final Backend backend;

    public BackendReadyEvent(Backend backend) {
        this.backend = backend;
    }

    public Backend getBackend() {
        return this.backend;
    }

}