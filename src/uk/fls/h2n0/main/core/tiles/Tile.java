package uk.fls.h2n0.main.core.tiles;

import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.core.World;

public abstract class Tile {

	

	private boolean blocksMovment = false;
	
	public Tile(){
		
	}
	
	public abstract void render(Renderer r, int dx, int dy, int tx, int ty);
	
	public abstract void update(World w, int tx, int ty);
}
