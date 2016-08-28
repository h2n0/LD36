package uk.fls.h2n0.main.core;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fls.engine.main.art.SplitImage;
import fls.engine.main.util.Camera;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.core.entity.Decoration;
import uk.fls.h2n0.main.core.entity.Entity;
import uk.fls.h2n0.main.core.entity.Gem;
import uk.fls.h2n0.main.core.entity.Player;
import uk.fls.h2n0.main.core.tiles.BossDoor;
import uk.fls.h2n0.main.core.tiles.DoorTile;
import uk.fls.h2n0.main.core.tiles.Tile;
import uk.fls.h2n0.main.util.Font;

public class World {

	public Tile[] tiles;
	public byte[] data;
	public int w, h;
	
	private int px, py;
	private int popupTime = 0;
	private int popupYOff = 0;
	private String popupMesg;
	private List<Entity> entitys;
	private List<Entity> entitysToAdd;
	private List<Entity> entitysToRemove;

	public World() {
		loadWorld();
	}

	public void setTile(int x, int y, Tile nt) {
		if (!isValid(x, y))
			return;
		this.tiles[x + y * this.w] = nt;
	}
	
	public Tile getTile(int x, int y) {
		if (!isValid(x, y))return Tile.none;
		return this.tiles[x + y * this.w];
	}

	public void setData(int x, int y, int nv) {
		if (!isValid(x, y))
			return;
		this.data[x + y * this.w] = (byte) nv;
	}
	
	public int getData(int x,int y){
		if(!isValid(x, y))return -1;
		return this.data[x + y * this.w];
	}

	private boolean isValid(int x, int y) {
		if (x < 0 || y < 0 || x >= this.w || y >= this.h)
			return false;
		else
			return true;
	}

	public void addEntity(Entity e) {
		e.world = this;
		this.entitysToAdd.add(e);
	}

	public void update(Camera cam) {
		Iterator<Entity> it = this.entitys.iterator();
		while(it.hasNext()){
			Entity e = it.next();
			e.update();
			if(!e.isAlive())this.entitysToRemove.add(e);
		}

		
		int mx = cam.pos.getIX() / 8;
		int max = cam.w;

		int[] d = new int[8 * 8];
		int my = cam.pos.getIY() / 8;
		int may = cam.w;
		for (int x = mx - 1; x <= mx + max; x++) {
			for (int y = my - 1; y <= my + may; y++) {
				Tile t = getTile(x, y);
				if (t != null)t.update(this, x, y);
			}
		}
		
		if(this.popupTime < 0 && this.popupYOff > 0){
			popupYOff --;
		}else{
			this.popupTime--;
		}
		
		processEntitys();
	}

	public void render(Renderer r, Camera cam) {
		r.fill(0);
		r.setOffset(-cam.pos.getIX(), -cam.pos.getIY());
		int mx = cam.pos.getIX() / 8;
		int max = cam.w;

		
		int[] d = new int[8 * 8];
		Arrays.fill(d, 255 << 16);
		int my = cam.pos.getIY() / 8;
		int may = cam.w;
		for (int x = mx - 1; x <= mx + max; x++) {
			for (int y = my - 1; y <= my + may; y++) {
				Tile t = getTile(x, y);
				if (t != null)t.render(this, r, x * 8, y * 8, x, y);
			}
		}

		for (Entity e : entitys) {
			e.render(r);
		}
		
		r.setOffset(0, 0);
		
		if(this.popupYOff > 0){
			Font.drawString(r, this.popupMesg, (160-(this.popupMesg.length()*8))/2, 144 - this.popupYOff);
		}
	}

	public void loadWorld() {
		BufferedImage img = new SplitImage("/level.png").load();
		this.w = img.getWidth();
		this.h = img.getHeight();
		int[] pixels = new int[w * h];
		this.tiles = new Tile[w * h];
		this.data = new byte[w * h];
		this.entitys = new ArrayList<Entity>();
		this.entitysToAdd = new ArrayList<Entity>();
		this.entitysToRemove = new ArrayList<Entity>();
		img.getRGB(0, 0, w, h, pixels, 0, h);
		for (int i = 0; i < pixels.length; i++) {
			int c = (pixels[i] & 0xFFFFFF);

			int tx = i % this.w;
			int ty = i / this.w;
			
			setTile(tx, ty, Tile.none);

			if (c == 0xFFFFFF) {// Basic floor tile
				setTile(tx, ty, Tile.floor);
			}else if(c == 0x0000FF){
				setTile(tx, ty, Tile.floor);
				addEntity(new Gem(tx * 8, ty * 8));
			}else if(c == 0xAAAA00){// Breakable object
				setTile(tx, ty, Tile.floor);
				addEntity(new Decoration(tx * 8, ty * 8));
			}else if(c == 0xFFFF00){// Player spawn section
				setTile(tx, ty, Tile.floor);
				this.px = tx * 8;
				this.py = ty * 8;
			}else if(c >= 0x00FF00 && c < 0x00FFFF){
				int doorNumber = c & 0x0000FF;
				setTile(tx, ty, Tile.door);
				setData(tx,ty, doorNumber);
			}else if(c == 0xFF9900){
				setTile(tx,ty, Tile.wall);
			}else if(c >= 0xCC00CC && c <= 0xCC00CE){//Mini boss door
				setTile(tx, ty, Tile.bossDoor);
				int value = c & 0x0000FF;
				value -= 203;
				setData(tx, ty, value);
				//System.out.println(value);
			}else if(c == 0xCC0000){//Mini boss spawn point
				setTile(tx, ty, Tile.floor);
			}else if(c >= 0x0100CC){// Mini boss door trigger
				setTile(tx, ty, Tile.bossDoorTrigger);
				int value = c >> 16;
				setData(tx, ty, value);
			}
		}

		// Gonna go over the tiles and change some of them
		for (int i = 0; i < this.tiles.length; i++) {
			int tx = i % this.w;
			int ty = i / this.w;

			Tile above = this.getTile(tx, ty - 1);
			Tile below = this.getTile(tx, ty + 1);
			Tile left = this.getTile(tx, ty - 1);
			Tile right = this.getTile(tx, ty - 1);
			Tile current = this.getTile(tx, ty);

			if (current == Tile.floor) {
				if ((above == Tile.none || above == null) && below == Tile.floor) {
					setTile(tx, ty, Tile.wall);
				}
			}
		}
	}
	
	private void processEntitys(){
		for(Entity e : this.entitysToRemove){
			this.entitys.remove(e);
		}
		
		for(Entity e : this.entitysToAdd){
			this.entitys.add(e);
		}
		
		this.entitysToAdd.clear();
		this.entitysToRemove.clear();
	}
	
	public List<Entity> getEntitysAround(Entity checker){
		return this.getEntitysAround(checker, 10);
	}
	
	public List<Entity> getEntitysAround(Entity checker, int r){
		List<Entity> res = new ArrayList<Entity>();
		for(Entity e : entitys){
			if(e == checker)continue;
			if(e.getPos().dist(checker.getPos()) < r * r){
				res.add(e);
			}
		}
		return res;
	}
	
	public Player getPlayer(){
		return new Player(this.px, this.py);
	}
	
	public void showPopup(String msg, int time){
		this.popupTime = time;
		this.popupMesg = msg;
		this.popupYOff = 24; 
	}
	
	public void updateGemCount(){
		for(int i = 0; i < this.tiles.length; i++){
			Tile t = this.tiles[i];
			if(t instanceof DoorTile){
				int tx = i % this.w;
				int ty = i / this.w;
				int val = getData(tx,ty);
				setData(tx, ty, val-1);
			}
		}
	}
	
	
	public void triggerBossDoors(int num){
		for(int i = 0; i < this.tiles.length; i++){
			int tx = i % this.w;
			int ty = i / this.w;
			Tile t = this.tiles[i];
			if(t instanceof DoorTile){
				if(this.getData(tx, ty) == num){
					((BossDoor)t).activate();
				}
			}
		}
	}
}
