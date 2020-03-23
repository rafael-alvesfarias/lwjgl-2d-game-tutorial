package com.rednine.helloworld.world;

public class Tile {
	public static Tile[] tiles = new Tile[16];
	public static byte tile_counter = 0;
	
	public static final Tile test_tile = new Tile("checker");
	public static final Tile grass_32 = new Tile("grass_32");
	public static final Tile grass_256 = new Tile("grass_256");
	
	private byte id;
	private String texture;
	
	public Tile(String texture) {
		this.id = tile_counter;
		tile_counter++;
		this.texture = texture;
		if (tiles[id] != null)
			throw new IllegalStateException("Tiles at:[" + id + "] is already being used");
		tiles[id] = this;
	}
	
	
	public byte getId() {
		return id;
	}
	
	public String getTexture() {
		return texture;
	}
	

}
