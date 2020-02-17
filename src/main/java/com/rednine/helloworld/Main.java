package com.rednine.helloworld;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Main {
	
	public Main() {
		Window.setCallbacks();
		// init GLFW
		if (!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW");
		}
		
		Window win = new Window();
		win.setSize(1920, 1080);
		win.setFullscreen(true);
		win.createWindow("Game");
		
		
		GL.createCapabilities();
		
		Camera camera = new Camera(win.getWidth(), win.getHeight());
		
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
		while (!win.shouldClose()) {
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
				
				if (win.getInput().isKeyDown(GLFW_KEY_ESCAPE)) {
					glfwSetWindowShouldClose(win.getWindow(), true);
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
				win.swapBuffers();
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
