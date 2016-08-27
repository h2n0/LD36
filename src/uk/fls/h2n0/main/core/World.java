package uk.fls.h2n0.main.core;

import java.awt.image.BufferedImage;

import fls.engine.main.art.SplitImage;

public class World {

	
	public int[] tiles;
	public byte[] data;
	private int w;
	private int h;
	
	public World(){
		loadWorld();
	}
	
	public void setTile(int x, int y, Tile nt){
		if(!isValid(x, y))return;
	}
	
	public void setData(int x, int y, int nv){
		if(!isValid(x, y))return;
		this.data[x + y * this.w] = (byte)nv;
	}
	
	private boolean isValid(int x, int y){
		if(x < 0 || y < 0 || x >= this.w || y >= this.h)return false;
		else return true;
	}
	
	
	public void loadWorld(){
		BufferedImage img = new SplitImage("/level.png").load();
		this.w = img.getWidth();
		this.h = img.getHeight();
		int[] pixels = new int[w * h];
		this.tiles = new int[w * h];
		this.data = new byte[w * h];
		img.getRGB(0, 0, w, h, pixels, 0, h);
		for(int i = 0; i < pixels.length; i++){
			int c = (pixels[i] & 0xFFFFFF);
			if(c == 0)continue;
		
			int tx = i % this.w;
			int ty = i / this.w;
			
			if(c == 0xFFFFFF){
				
			}
		}
	}
}
