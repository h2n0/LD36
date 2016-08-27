package uk.fls.h2n0.main.screens;

import java.awt.Graphics;

import fls.engine.main.screen.Screen;
import fls.engine.main.util.Camera;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.core.Player;
import uk.fls.h2n0.main.core.World;

public class GameScreen extends Screen {

	
	private Renderer r;
	private World w;
	private Camera cam;
	private Player p;
	
	public void postInit(){
		this.r = new Renderer(this.game.getImage());
		this.w = new World();
		this.cam = new Camera(160/8, 144/8);
		this.p = new Player(46 * 8, 96 * 8);
		this.w.addEntity(this.p);
		this.cam.ww = this.w.w * 8;
		this.cam.wh = this.w.h * 8;
	}
	
	@Override
	public void update() {
		//this.cam.pos.setPos(xOff, yOff);
		
		this.w.update();
		
		int xOff = 0;
		int yOff = 0;
		if(this.input.isKeyHeld(this.input.w))yOff--;
		else if(this.input.isKeyHeld(this.input.s))yOff++;
		
		if(this.input.isKeyHeld(this.input.a))xOff--;
		else if(this.input.isKeyHeld(this.input.d))xOff++;
		
		
		this.p.move(xOff, yOff);
		
		this.cam.center(this.p.getPos().getIX(), this.p.getPos().getIY(), -68);
	}
	
	@Override
	public void render(Graphics g) {
		this.w.render(r, cam);
	}
}
