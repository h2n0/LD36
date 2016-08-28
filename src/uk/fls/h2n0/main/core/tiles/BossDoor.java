package uk.fls.h2n0.main.core.tiles;

import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.core.World;
import uk.fls.h2n0.main.util.Animation;

public class BossDoor extends Tile {

	private boolean walkedOn;
	private boolean bossBeaten;
	private Animation anim;
	
	public BossDoor(){
		this.anim = new Animation(this.sp, Animation.I_STYLE, 7, 6, 5, 4);
		this.walkedOn = false;
		this.bossBeaten = false;
	}
	
	@Override
	public void render(World w, Renderer r, int dx, int dy, int tx, int ty) {
		r.renderSection(this.anim.getFrame(), dx, dy, 8);
	}

	@Override
	public void update(World w, int tx, int ty) {
		if(anim.getFrameNumber() < 3 && this.walkedOn){
			int val = w.getData(tx, ty);
			if(val > 60){
				anim.nextFrame();
				w.setData(tx, ty, 0);
			}else{
				w.setData(tx, ty, val + 1);
			}
		}
		this.blocksMovment = !this.bossBeaten && this.walkedOn;
	}
	
	public void activate(){
		this.walkedOn = true;
	}

}
