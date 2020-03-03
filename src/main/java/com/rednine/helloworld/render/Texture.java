package com.rednine.helloworld.render;

import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;

public class Texture {
	
	private int id;
	
	private int width;
	
	private int height;
	
	public Texture(String fileName) {
		int[] width = new int[1];
		int[] height = new int[1];
		int[] nrChannels = new int[1];
		
		ByteBuffer pixels = stbi_load("textures/" + fileName, width, height, nrChannels, 0);
		this.width = width[0];
		this.height = height[0];
		
		System.out.println(this.width);
		System.out.println(this.height);

		pixels.flip();
		
		id = glGenTextures();
		
		glBindTexture(GL_TEXTURE_2D, id);
		
		//GL_NEAREST OR GL_LINEAR
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
	}
	
	public void bind(int sampler) {
		// opengl has about 32 different samples we can use
		if (sampler >= 0 && sampler <= 31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, id);
		}
	}
}
