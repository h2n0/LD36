package uk.fls.h2n0.main.core.entity;

import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;

public class FireBall extends Entity {
	
	private final float angle;
	private int life;
	
	public FireBall(int x, int y, float ang) {
		this.pos = new Point(x,y);
		this.angle = ang;
		this.life = 120;
		this.frameData = sp.getData(7);
	}

	@Override
	public void render(Renderer r) {
		r.renderSectionWithTint(frameData, this.pos.getIX(), this.pos.getIY(), 8, r.makeRGB(255, 100, 0));
	}

	@Override
	public void update() {
		if(this.life > 0)this.life--;
		if(this.life == 0){
			die();
		}
		
		float nx = (float)Math.cos(angle);
		float ny = (float)Math.sin(angle);
		if(!this.move(nx, ny)){
			die();
		}
		
		
		
		if(this.life % 3 == 0){
			float amt = (float)((270 / 180) * Math.PI);
			int num = 1;
			for(int i = -num; i <= num; i++){
				float ox = (float)Math.cos(-angle + (amt*i)) * 2;
				float oy = (float)Math.sin(-angle + (amt*i)) * 2;
				this.world.addEntity(new Particle(this.pos.x + 4 + ox, this.pos.y + 4 + oy));
			}
		}
	}
	
	private void die(){
		this.alive = false;
	}

}
