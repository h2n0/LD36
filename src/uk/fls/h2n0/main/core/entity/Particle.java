package uk.fls.h2n0.main.core.entity;

import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;

public class Particle extends Entity {

	protected float vx, vy;
	protected int lifeTime;
	protected int color;
	
	public Particle(float x, float y) {
		this.pos = new Point(x,y);
		this.lifeTime = 10 + (int)(Math.random() * 20);
		this.color = genColor();
	}
	
	@Override
	public void render(Renderer r) {
		r.setPixel(this.pos.getIX(), this.pos.getIY(), this.color);
	}

	@Override
	public void update() {
		if(this.lifeTime == 0)this.alive = false;
		this.lifeTime--;
	}
	
	private int genColor(){
		int[] colors = new int[]{makeRGB(255,123,0), makeRGB(255,255,255), makeRGB(123,123,123)};
		return colors[(int)(colors.length * Math.random())];
	}
	
	private int makeRGB(int r, int g, int b){
		return (r << 16) | (g << 8) | b;
	}
	

}
