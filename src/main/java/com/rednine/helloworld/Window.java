package com.rednine.helloworld;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWVidMode;

public class Window {
	
	private long window;
	
	private int width, height;
	
	public Window() {
		setSize(640, 480);
	}
	
	public void createWindow(String title) {
		window = glfwCreateWindow(width, height, title, glfwGetPrimaryMonitor(), 0);
		
		if (window == 0) {
			throw new IllegalStateException("Failed to create window!");
		}
		
		//moves window to the center
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window,
				(videoMode.width() - width) / 2,
				(videoMode.height() - height) / 2);
		
		glfwShowWindow(window);
		
		glfwMakeContextCurrent(window);
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public void swapBuffers() {
		glfwSwapBuffers(window);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

}
