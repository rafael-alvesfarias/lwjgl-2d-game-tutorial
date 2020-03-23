package com.rednine.helloworld.world;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.rednine.helloworld.render.Camera;
import com.rednine.helloworld.render.Shader;

public class World {

	private byte[] tiles;
	private int width;
	private int height;
	
	private Matrix4f world;

	public World() {
		width = 16;
		height = 16;

		tiles = new byte[width * height];
		
		world = new Matrix4f().setTranslation(new Vector3f(0));
		world.scale(16);
	}

	public void render(TileRenderer render, Shader shader, Camera cam) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// j + i * width is the formula to get the tile from the tiles array that represents a world matrix
				// -i is used to drawn the world in top-down direction
				render.renderTile(tiles[j + i * width], j, -i, shader, world, cam);
			}
		}
	}

}