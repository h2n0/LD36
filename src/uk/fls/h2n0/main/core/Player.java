package uk.fls.h2n0.main.core;

import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;

public class Player extends Entity {

	
	private int steps;
	private int frame;
	private int[] anim;
	
	public Player(int x, int y) {
		this.frameData = this.sp.getData(0);
		this.pos = new Point(x,y);
		this.anim = new int[]{0,1,2,1};
	}
	
	@Override
	public void render(Renderer r) {
		r.renderSection(frameData, this.pos.getIX(), this.pos.getIY(), 8);
	}

	@Override
	public void update() {
		this.speed = 0.75f;
	}
	
	public void move(int x, int y){
		if(x == 0 && y == 0)return;
		super.move(x,y);
		this.steps++;
		if(steps % 15 == 0){
			frameData = sp.getData(this.anim[this.frame]);
			this.frame = (this.frame+1) % this.anim.length;
		}
	}
	
}
