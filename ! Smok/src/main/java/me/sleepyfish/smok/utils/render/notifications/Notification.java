package me.sleepyfish.smok.utils.render.notifications;

import java.awt.Color;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.impl.visual.GuiModule;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.utils.font.FontUtils;
import me.sleepyfish.smok.utils.render.RenderUtils;
import me.sleepyfish.smok.utils.render.RoundedUtils;
import me.sleepyfish.smok.utils.render.animation.normal.Animation;
import me.sleepyfish.smok.utils.render.animation.normal.Direction;
import me.sleepyfish.smok.utils.render.animation.normal.impl.DecelerateAnimation;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import me.sleepyfish.smok.utils.render.color.NewColorUtils;
import net.minecraft.client.gui.ScaledResolution;

// Class from SMok Client by SleepyFish
public class Notification {

    private final Timer timer = new Timer();

    private Animation animation;
    private Animation slideAnimation;

    private final Notification.Icon icon;
    private final String message;
    private final String title;

    private boolean loaded = false;
    private final double width;
    private final long delay;

    public Notification(String title, String msg, Notification.Icon icon, long delay) {
        this.title = title;
        this.message = msg;
        this.icon = icon;
        this.delay = delay;
        this.width = Math.max(FontUtils.r20.getStringWidth(msg), FontUtils.r20.getStringWidth(title) + 10.0) + 15.0;
    }

    public void show() {
        this.animation = new DecelerateAnimation(250, this.width + 5.0);
        this.slideAnimation = new DecelerateAnimation((int) this.delay, this.width + 6.0);
        this.timer.reset();
    }

    public boolean isShown() {
        if (Smok.inst.mc.thePlayer != null) {
            return !this.animation.isDone(Direction.BACKWARDS);
        } else {
            return false;
        }
    }

    public void render() {
        ScaledResolution sr = new ScaledResolution(Smok.inst.mc);
        if (Smok.inst.eveManager != null && !this.loaded) {
            this.loaded = true;
            Smok.inst.eveManager.register(this);
        }

        if (this.timer.delay(this.delay)) {
            this.animation.setDirection(Direction.BACKWARDS);
            this.slideAnimation.setDirection(Direction.BACKWARDS);
        }

        String icon = "?";
        if (this.icon == Notification.Icon.Bell) {
            icon = "F";
        }

        if (this.icon == Notification.Icon.Info) {
            icon = "b";
        }

        if (this.icon == Notification.Icon.Warning) {
            icon = "A";
        }

        if (this.icon == Notification.Icon.Check) {
            icon = "I";
        }

        if (this.icon == Notification.Icon.Refresh) {
            icon = "y";
        }

        if (this.icon == Notification.Icon.No) {
            icon = "s";
        }

        int heightOff = sr.getScaledHeight() - 40;
        if (ClientUtils.inClickGui()) {
            heightOff -= 30;
        }

        if (ClientUtils.isSmokTheme()) {
            if (GuiModule.renderShadows.isEnabled()) {
                RenderUtils.drawShadow((float) (sr.getScaledWidth() - this.animation.getValue()), (float) heightOff, (float) (this.width + 13.0), 30.0F, 2.0F);
            }

            RoundedUtils.drawGradientRoundLR((float) (sr.getScaledWidth() - this.animation.getValue()), (float) heightOff, (float) (this.width + 13.0), 30.0F, 2.0F, NewColorUtils.backgroundColor, NewColorUtils.backgroundColor.darker());
            RoundedUtils.drawGradientRoundLR((float) (sr.getScaledWidth() - this.animation.getValue()), (float) (heightOff + 28), (float) this.slideAnimation.getValue(), 2.0F, 1.0F, NewColorUtils.clientColorDefIndex, ColorUtils.getClientColor(9696));

            FontUtils.i20.drawString(icon, sr.getScaledWidth() - this.animation.getValue() + 5.0, (heightOff + 7), NewColorUtils.fontColorGradient);
            FontUtils.r20.drawString(this.title, sr.getScaledWidth() - this.animation.getValue() + 18.0, (heightOff + 6), NewColorUtils.fontColorGradient);
            FontUtils.r20.drawString(this.message, sr.getScaledWidth() - this.animation.getValue() + 6.0, (heightOff + 16), NewColorUtils.fontColorGradient);
        } else {
            if (GuiModule.renderShadows.isEnabled()) {
                RenderUtils.drawShadow((float) (sr.getScaledWidth() - this.animation.getValue()), (float) (heightOff - 10), (float) (this.width + 10.0), 40.0F, 2.0F);
            }

            RoundedUtils.drawRound((float) (sr.getScaledWidth() - this.animation.getValue()), (float) (heightOff - 10), (float) (this.width + 10.0), 40.0F, 2.0F, new Color(-1156509423));
            RoundedUtils.drawRound((float) (sr.getScaledWidth() - this.animation.getValue()), (float) (heightOff + 28), (float) this.slideAnimation.getValue(), 2.0F, 1.0F, Color.white);

            FontUtils.i20.drawString("b", sr.getScaledWidth() - this.animation.getValue() + 7.0, heightOff - 3, NewColorUtils.fontColorGradient);
            FontUtils.r20.drawString(this.title, sr.getScaledWidth() - this.animation.getValue() + 23.0, heightOff - 4.5F, NewColorUtils.fontColorGradient);
            FontUtils.r20.drawString(this.message, sr.getScaledWidth() - this.animation.getValue() + 6.0, heightOff + 14, NewColorUtils.fontColorGradient);
        }
    }

    public enum Icon {
        Info, Bell, Warning,
        Check, Refresh, No;
    }

}