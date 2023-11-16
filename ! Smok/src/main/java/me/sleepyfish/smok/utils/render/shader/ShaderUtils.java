package me.sleepyfish.smok.utils.render.shader;

import java.io.InputStream;

import me.sleepyfish.smok.utils.misc.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

// Class from SMok Client by SleepyFish
public class ShaderUtils {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private final int programID;

    public ShaderUtils(String fragShaderLoc, String shaderLoc) {
        int program = GL20.glCreateProgram();

        try {
            int fragmentShaderID = this.createShader(mc.getResourceManager().getResource(new ResourceLocation(fragShaderLoc)).getInputStream(), 35632);
            GL20.glAttachShader(program, fragmentShaderID);
            int vertexShaderID = this.createShader(mc.getResourceManager().getResource(new ResourceLocation(shaderLoc)).getInputStream(), 35633);
            GL20.glAttachShader(program, vertexShaderID);
        } catch (Exception ignored) {
        }

        GL20.glLinkProgram(program);
        int status = GL20.glGetProgrami(program, 35714);
        if (status == 0) {
            throw new IllegalStateException("Shader failed to link!");
        } else {
            this.programID = program;
        }
    }

    public ShaderUtils(String fragmentShaderLoc) {
        this(fragmentShaderLoc, ClientUtils.path + "/shaders/vertex.vsh");
    }

    public void init() {
        GL20.glUseProgram(this.programID);
    }

    public void unload() {
        GL20.glUseProgram(0);
    }

    public void setUniformf(String name, float... args) {
        int loc = GL20.glGetUniformLocation(this.programID, name);
        switch (args.length) {
            case 1:
                GL20.glUniform1f(loc, args[0]);
                break;
            case 2:
                GL20.glUniform2f(loc, args[0], args[1]);
                break;
            case 3:
                GL20.glUniform3f(loc, args[0], args[1], args[2]);
                break;
            case 4:
                GL20.glUniform4f(loc, args[0], args[1], args[2], args[3]);
        }
    }

    public void setUniformi(String name, int... args) {
        int loc = GL20.glGetUniformLocation(programID, name);
        if (args.length > 1) GL20.glUniform2i(loc, args[0], args[1]);
        else GL20.glUniform1i(loc, args[0]);
    }

    public static void drawQuads(float x, float y, float width, float height) {
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0F, 0.0F);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(0.0F, 1.0F);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(1.0F, 1.0F);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(1.0F, 0.0F);
        GL11.glVertex2f(x + width, y);
        GL11.glEnd();
    }

    private int createShader(InputStream inputStream, int type) {
        int shader = GL20.glCreateShader(type);
        GL20.glShaderSource(shader, ClientUtils.readInputStream(inputStream));
        GL20.glCompileShader(shader);
        if (GL20.glGetShaderi(shader, 35713) == 0) {
            System.out.println(GL20.glGetShaderInfoLog(shader, 4096));
            throw new IllegalStateException(String.format("Shader failed to compile!", type));
        } else {
            return shader;
        }
    }

}