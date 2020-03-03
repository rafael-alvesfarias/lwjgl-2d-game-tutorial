package com.rednine.helloworld.world;

public class Tile {
	public static Tile[] tiles = new Tile[16];
	
	public static final Tile test_tile = new Tile((byte) 0, "checker");
	public static final Tile grass_32 = new Tile((byte) 1, "grass_32");
	public static final Tile grass_256 = new Tile((byte) 2, "grass_256");
	
	private byte id;
	private String texture;
	
	public Tile(byte id, String texture) {
		this.id = id;
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
