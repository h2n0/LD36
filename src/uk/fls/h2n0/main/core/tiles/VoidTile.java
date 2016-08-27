package uk.fls.h2n0.main.core.tiles;

import java.util.Arrays;

import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.core.World;

public class VoidTile extends Tile {

	private int[] data = null;
	
	@Override
	public void render(World w, Renderer r, int dx, int dy, int tx, int ty) {
		if(this.data == null){
			this.data = new int[8 * 8];
			Arrays.fill(this.data, 0);
		}
		r.renderSection(data, dx, dy, 8);
	}

	@Override
	public void update(World w, int tx, int ty) {

	}

}
