package com.rednine.helloworld.io;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {
	
	private long window;
	
	private int width, height;
	
	private boolean fullscreen;
	
	private Input input;
	
	public static void setCallbacks() {
		glfwSetErrorCallback(new GLFWErrorCallback() {
			@Override
			public void invoke(int error, long description) {
				throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
			}
		});
		
		// try the input callback
	}
	
	public Window() {
		setSize(640, 480);
		setFullscreen(false);
	}
	
	public void createWindow(String title) {
		window = glfwCreateWindow(
				width,
				height,
				title,
				fullscreen ? glfwGetPrimaryMonitor() : 0,
				0);
		
		if (window == 0) {
			throw new IllegalStateException("Failed to create window!");
		}
		
		if (!fullscreen) {
			//moves window to the center
			GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window,
					(videoMode.width() - width) / 2,
					(videoMode.height() - height) / 2);
			
			glfwShowWindow(window);
		}
		
		glfwMakeContextCurrent(window);
		
		input = new Input(window);
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

	public int getHeight() {
		return height;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	
	public void update() {
		input.update();
		glfwPollEvents();
	}

	
	public boolean isFullscreen() {
		return fullscreen;
	}
	
	public long getWindow() {
		return window;
	}
	
	public Input getInput() {
		return input;
	}

}
