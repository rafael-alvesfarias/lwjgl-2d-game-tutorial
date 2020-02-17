package com.rednine.helloworld;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
	private static final int GLFW_KEY_FIRST = GLFW_KEY_SPACE;

	private long window;
	
	private boolean keys[];
	
	public Input(long window) {
		this.window = window;
		this.keys = new boolean[GLFW_KEY_LAST];
	}
	
	public boolean isKeyDown(int key) {
		return glfwGetKey(window, key) == 1;
	}
	
	public boolean isKeyPressed(int key) {
		return isKeyDown(key) && !keys[key];
	}
	
	public boolean isKeyReleased(int key) {
		return !isKeyDown(key) && keys[key];
	}
	
	public boolean isMouseButtonDown(int button) {
		return glfwGetMouseButton(window, button) == 1;
	}
	
	public void update() {
		for (int i = GLFW_KEY_FIRST; i < GLFW_KEY_LAST; i++)
			keys[i] = isKeyDown(i);
	}
}
