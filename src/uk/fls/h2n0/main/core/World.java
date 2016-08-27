package uk.fls.h2n0.main.core;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fls.engine.main.art.SplitImage;
import fls.engine.main.util.Camera;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.core.tiles.Tile;

public class World {

	public Tile[] tiles;
	public byte[] data;
	public int w, h;
	
	private int px, py;

	private List<Entity> entitys;

	public World() {
		loadWorld();
	}

	public void setTile(int x, int y, Tile nt) {
		if (!isValid(x, y))
			return;
		this.tiles[x + y * this.w] = nt;
	}

	public void setData(int x, int y, int nv) {
		if (!isValid(x, y))
			return;
		this.data[x + y * this.w] = (byte) nv;
	}

	private boolean isValid(int x, int y) {
		if (x < 0 || y < 0 || x >= this.w || y >= this.h)
			return false;
		else
			return true;
	}

	public Tile getTile(int x, int y) {
		if (!isValid(x, y))return Tile.none;
		return this.tiles[x + y * this.w];
	}

	public void addEntity(Entity e) {
		e.world = this;
		this.entitys.add(e);
	}

	public void update() {
		for (Entity e : entitys) {
			e.update();
		}
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
	}

	public void loadWorld() {
		BufferedImage img = new SplitImage("/level.png").load();
		this.w = img.getWidth();
		this.h = img.getHeight();
		int[] pixels = new int[w * h];
		this.tiles = new Tile[w * h];
		this.data = new byte[w * h];
		this.entitys = new ArrayList<Entity>();
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
			}else if(c == 0xFFFF00){// Player spawn section
				setTile(tx, ty, Tile.floor);
				this.px = tx * 8;
				this.py = ty * 8;
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
}
