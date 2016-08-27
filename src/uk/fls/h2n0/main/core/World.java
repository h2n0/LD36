package uk.fls.h2n0.main.core;

import java.awt.image.BufferedImage;

import fls.engine.main.art.SplitImage;
import uk.fls.h2n0.main.core.tiles.Tile;

public class World {

	
	public Tile[] tiles;
	public byte[] data;
	public int w, h;
	
	public World(){
		loadWorld();
	}
	
	public void setTile(int x, int y, Tile nt){
		if(!isValid(x, y))return;
		this.tiles[x + y * this.w] = nt;
	}
	
	public void setData(int x, int y, int nv){
		if(!isValid(x, y))return;
		this.data[x + y * this.w] = (byte)nv;
	}
	
	private boolean isValid(int x, int y){
		if(x < 0 || y < 0 || x >= this.w || y >= this.h)return false;
		else return true;
	}
	
	public Tile getTile(int x, int y){
		if(!isValid(x,y))return Tile.none;
		return this.tiles[x + y * this.w];
	}
	
	
	public void loadWorld(){
		BufferedImage img = new SplitImage("/level.png").load();
		this.w = img.getWidth();
		this.h = img.getHeight();
		int[] pixels = new int[w * h];
		this.tiles = new Tile[w * h];
		this.data = new byte[w * h];
		img.getRGB(0, 0, w, h, pixels, 0, h);
		for(int i = 0; i < pixels.length; i++){
			int c = (pixels[i] & 0xFFFFFF);
			if(c == 0)continue;
		
			int tx = i % this.w;
			int ty = i / this.w;
			
			if(c == 0xFFFFFF){
				setTile(tx, ty, Tile.floor);
			}else{
				setTile(tx, ty, Tile.none);
			}
		}
	}
}
