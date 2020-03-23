package com.rednine.helloworld.world;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.rednine.helloworld.io.Window;
import com.rednine.helloworld.render.Camera;
import com.rednine.helloworld.render.Shader;

public class World {

	private byte[] tiles;
	private int width;
	private int height;
	private int scale;
	
	private Matrix4f world;

	public World() {
		width = 64;
		height = 64;
		scale = 16;

		tiles = new byte[width * height];
		
		world = new Matrix4f().setTranslation(new Vector3f(0));
		world.scale(scale);
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
	
	/**
	 * Corrects the camera position to the max size of the world.
	 * 
	 * This method only works with the current camera projection,  
	 * current model vertex configuration with the origin in the center of the model
	 * and if you translate the world using the camera projection matrix.
	 * 
	 * @param camera
	 * @param window
	 */
	public void correctCamera(Camera camera, Window window) {
		Vector3f pos = camera.getPosition();
		
		int w = -width * scale * 2;
		int h = height * scale * 2;
		
		// the camera position is inverted to reflect his projection to 
		// all the objects in the scene.
		// so we need to test the values with that in mind
		
		//left
		if (pos.x > -(window.getWidth()/2) + scale)
			pos.x = -(window.getWidth()/2) + scale;
		//right
		if (pos.x < w + (window.getWidth()/2) + scale)
			pos.x = w + (window.getWidth()/2) + scale;
		//top
		if (pos.y < (window.getHeight()/2) - scale)
			pos.y = (window.getHeight()/2) - scale;
		//bottom
		if (pos.y > h - (window.getHeight()/2) - scale)
			pos.y = h - (window.getHeight()/2) - scale;
	}

	public void setTile(Tile tile, int x, int y) {
		tiles[x + y * width] = tile.getId();
	}
}
