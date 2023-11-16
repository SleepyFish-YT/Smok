package me.sleepyfish.smok.utils.entities.role;

import java.util.ArrayList;

// Class from SMok Client by SleepyFish
// this will be replaced with servers soon
public class RoleManager {

    private final ArrayList<RoleUser> playersWithRole;

    public RoleManager() {
        this.playersWithRole = new ArrayList<>();

        // Owner / Main Dev
        this.addUser(new RoleUser("nickthesliktrick", Role.Developer));
        this.addUser(new RoleUser("android_skull", Role.Developer));

        // Fake Devs
        this.addUser(new RoleUser("just_a_joel", Role.Developer));
        this.addUser(new RoleUser("devofdeath", Role.Developer));
        this.addUser(new RoleUser("2juicy4you", Role.Developer));

        // YouTubers
        this.addUser(new RoleUser("toasterislegit", Role.YouTuber));
        this.addUser(new RoleUser("smellon69420", Role.YouTuber));
        this.addUser(new RoleUser("77caadet", Role.YouTuber));
        this.addUser(new RoleUser("cydefm", Role.YouTuber));
        this.addUser(new RoleUser("smok29", Role.YouTuber));

        // Randoms
        this.addUser(new RoleUser("darktunnelfreak", Role.User));
        this.addUser(new RoleUser("legacystarfall", Role.User));
        this.addUser(new RoleUser("imnotsonic_yt", Role.User));
        this.addUser(new RoleUser("itsbenjamin", Role.User));
        this.addUser(new RoleUser("_snowclouds", Role.User));
        this.addUser(new RoleUser("tanqyryalt", Role.User));
        this.addUser(new RoleUser("xpectorque", Role.User));
        this.addUser(new RoleUser("gqmonster", Role.User));
        this.addUser(new RoleUser("igorekowo", Role.User));
        this.addUser(new RoleUser("notcaestu", Role.User));
        this.addUser(new RoleUser("lexissedd", Role.User));
        this.addUser(new RoleUser("t_rex9981", Role.User));
        this.addUser(new RoleUser("slimpy_p", Role.User));
        this.addUser(new RoleUser("verifmoi", Role.User));
        this.addUser(new RoleUser("pclippex", Role.User));
        this.addUser(new RoleUser("mrbeast ", Role.User));
        this.addUser(new RoleUser("_s_n_w_", Role.User));
        this.addUser(new RoleUser("td_bear", Role.User));
        this.addUser(new RoleUser("comfey_", Role.User));
        this.addUser(new RoleUser("xpector", Role.User));
        this.addUser(new RoleUser("faites", Role.User));
        this.addUser(new RoleUser("saitan", Role.User));
        this.addUser(new RoleUser("nuwfu", Role.User));
        this.addUser(new RoleUser("aya_f", Role.User));
        this.addUser(new RoleUser("zinl", Role.User));
        this.addUser(new RoleUser("3tr0", Role.User));
        this.addUser(new RoleUser("vxlzk", Role.User));
    }

    public void unInject() {
        this.getPlayersWithRole().clear();
    }

    public ArrayList<RoleUser> getPlayersWithRole() {
        return playersWithRole;
    }

    public void addUser(RoleUser user) {
        this.getPlayersWithRole().add(user);
    }

    public enum Role {
        User, Developer, YouTuber;
    }

}