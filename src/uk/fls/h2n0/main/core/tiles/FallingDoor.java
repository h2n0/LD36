package uk.fls.h2n0.main.core.tiles;

import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.core.World;
import uk.fls.h2n0.main.util.Animation;

public class FallingDoor extends Tile {
	
	private Animation anim;
	
	public FallingDoor(){
		this.blocksMovment = true;
		this.anim = new Animation(this.sp, Animation.I_STYLE, 4, 5, 6, 7);
	}
	
	@Override
	public void render(World w, Renderer r, int dx, int dy, int tx, int ty) {
		r.renderSection(this.anim.getFrame(), dx, dy, 8);
	}

	@Override
	public void update(World w, int tx, int ty) {
		int val = w.getData(tx, ty);
		if(val > 60){
			if(anim.getFrameNumber() == 3)w.setTile(tx, ty, Tile.floor);
			anim.nextFrame();
			w.setData(tx, ty, 0);
		}else{
			w.setData(tx, ty, val + 1);
		}
	}

}
