package uk.fls.h2n0.main.core.tiles;

import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.core.World;

public class WallTile extends Tile {

	
	int[] d = null;
	
	public WallTile() {
		this.blocksMovment = true;
	}
	@Override
	public void render(World w, Renderer r, int dx, int dy, int tx, int ty) {
		if(d == null){
			d = this.sp.getData(2);
		}
		
		r.renderSection(d, dx, dy, 8);
	}

	@Override
	public void update(World w, int tx, int ty) {

	}

}
