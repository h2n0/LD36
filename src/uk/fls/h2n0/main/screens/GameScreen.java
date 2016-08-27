package uk.fls.h2n0.main.screens;

import java.awt.Graphics;

import fls.engine.main.screen.Screen;
import fls.engine.main.util.Camera;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.core.World;
import uk.fls.h2n0.main.core.tiles.Tile;

public class GameScreen extends Screen {

	
	private Renderer r;
	private World w;
	private Camera cam;
	
	private int xOff = 0;
	private int yOff = 0;
	
	public void postInit(){
		this.r = new Renderer(this.game.getImage());
		this.w = new World();
		this.cam = new Camera(160, 144);
		this.xOff = 46 * 8;
		this.yOff = 96 * 8;
	}
	
	@Override
	public void update() {
		this.cam.pos.setPos(xOff, yOff);
		
		if(this.input.isKeyHeld(this.input.w))this.yOff--;
		else if(this.input.isKeyHeld(this.input.s))this.yOff ++;
		
		if(this.input.isKeyHeld(this.input.a))this.xOff --;
		else if(this.input.isKeyHeld(this.input.d))this.xOff ++;
		
		if(this.yOff < 0)this.yOff = 0;
		if(this.xOff < 0)this.xOff = 0;
		if(this.yOff > this.w.h * 8 - this.cam.h/8)this.yOff = this.w.h * 8 - this.cam.h/8;
		if(this.xOff > this.w.w * 8 - this.cam.w/8)this.xOff = this.w.w * 8 - this.cam.w/8;
	}
	
	@Override
	public void render(Graphics g) {
		this.r.fill(0);
		this.r.setOffset(-this.cam.pos.getIX(), -this.cam.pos.getIY());
		int mx = this.cam.pos.getIX()/8;
		int max = this.cam.w / 8;
		
		int my = this.cam.pos.getIY()/8;
		int may = this.cam.w / 8;
		for(int x = mx-1; x <= mx + max; x++){
			for(int y = my-1; y <= my + may; y++){
				Tile t = this.w.getTile(x, y);
				if(t != null)t.render(this.w, r, x * 8, y * 8, x, y);
				else Tile.none.render(this.w, r, x * 8, y * 8, x, y);
			}
		}
	}
}
