package uk.fls.h2n0.main.core.entity;

import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;

public class Gem extends Entity{

	
	private float time;
	protected int color = -1;
	protected int shadowWidth = 4;
	
	public Gem(int x, int y){
		this.pos = new Point(x,y);
		this.frameData = sp.getData(3);
		
		this.color = genColor();
	}
	
	private int makeRGB(int r, int g, int b){
		return (r << 16) | (g << 8) | b;
	}

	@Override
	public void render(Renderer r) {
		int sw = this.shadowWidth;
		float yo = (float)Math.sin(time) * 2f;
		sw -= yo;
		r.shadeElipse(this.pos.getIX() + 2, this.pos.getIY() + 6, sw / 2, sw);
		
		float yPos = (int)(this.pos.getIY() + yo - 3);
		
		for(int i = 0; i < this.frameData.length; i++){
			int cx = i % 8;
			int cy = i / 8;
			int c = this.frameData[i];
			if(c == -1)continue;
			c = c & this.color;
			r.setPixel(this.pos.getIX() + cx, (int)(yPos) + cy, c);
		}
	}

	@Override
	public void update() {
		time += 0.08f;
	}	
	
	protected int genColor(){
		int[] colors = new int[]{makeRGB(255,0,0), makeRGB(0,255,0), makeRGB(0,0,255), makeRGB(255,255,0), makeRGB(255,0,255), makeRGB(0,255,255), makeRGB(255,255,255)};
		return colors[(int)(colors.length * Math.random())];
	}
}
