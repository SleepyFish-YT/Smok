package me.sleepyfish.smok.utils.entities.role;

// Class from SMok Client by SleepyFish
public class RoleUser {

    private final String name;
    private final RoleManager.Role role;

    public RoleUser(String name, RoleManager.Role role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public RoleManager.Role getRole() {
        return role;
    }

}