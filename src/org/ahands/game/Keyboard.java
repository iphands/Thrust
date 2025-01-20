package org.ahands.game;

import org.lwjgl.glfw.GLFW;


public class Keyboard {
    private final long window;

    public Keyboard(long window) {
        this.window = window;
    }

    boolean isKeyDown(int key) {
        int state = GLFW.glfwGetKey(this.window, key);
        return state == GLFW.GLFW_PRESS;
    }
}
