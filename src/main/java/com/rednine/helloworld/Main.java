package com.rednine.helloworld;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Main {
	
	public Main() {
		// init GLFW
		if (!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW");
		}
		
		// creating window
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		long window = glfwCreateWindow(640, 480, "My LWJGL Program", 0, 0);
		if (window == 0) {
			throw new IllegalStateException("Failed to create window");
		}
		
		//moves window to the center
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (videoMode.width() - 640) / 2, (videoMode.height() - 480) / 2);
		glfwShowWindow(window);
		
		glfwMakeContextCurrent(window);
		
		GL.createCapabilities();
		
		Camera camera = new Camera(640, 480);
		
		glEnable(GL_TEXTURE_2D);
		
		float[] vertices = new float[] {
			-0.5f, 0.5f, 0,   //TOP LEFT      0
			0.5f, 0.5f, 0,    //TOP RIGHT     1
			0.5f, -0.5f, 0,   //BOTTOM RIGHT  2
			-0.5f, -0.5f, 0,  //BOTTOM LEFT   3
		};
		
		float[] texture = new float[] {
				0,0,
				1,0,
				1,1,
				0,1,
		};
		
		int[] indices = new int[] {
				0,1,2,
				2,3,0
		};
		
		Model model = new Model(vertices, texture, indices);
		Shader shader = new Shader("shader");
		Texture tex = new Texture("resources/tree.png");
		
		Matrix4f scale = new Matrix4f()
				.translate(new Vector3f(100, 0, 0))
				.scale(64);
		Matrix4f target = new Matrix4f();
		camera.setPosition(new Vector3f(-100, 0, 0));
		
		double frame_cap = 1.0/60.0;
		
		double frame_time = 0;
		int frames = 0;
		
		double time = Timer.getTime();
		double unprocessed = 0;
		
		
		//wait for window events
		while (!glfwWindowShouldClose(window)) {
			boolean can_render = false;
			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed += passed;
			frame_time += passed;
			
			time = time_2;
			
			//process all unprocessed frames to mantain consistent game loop even in slow machines
			while (unprocessed >= frame_cap) {
				unprocessed-=frame_cap;
				can_render = true;
				target = scale;
				
				if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE) {
					glfwSetWindowShouldClose(window, true);
				}
				
				glfwPollEvents();
				if (frame_time >= 1.0) {
					frame_time = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
			}
			
			if (can_render) {
				//clears all the pixel colors to black you can use glClearColor to change the
				// clear color
				glClear(GL_COLOR_BUFFER_BIT);
				
				shader.bind();
				shader.setUniform("sampler", 0);
				shader.setUniform("projection", camera.getProjection().mul(target));
				model.render();
				tex.bind(0);
				
				// there are two contexts in opengl
				// one of them are used to draw the graphics while the other
				// is being shown in the window
				// so we need to swap then in order to show the changes
				glfwSwapBuffers(window);
				frames++;
			}
		}
		
		//after window closes
		glfwTerminate();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
