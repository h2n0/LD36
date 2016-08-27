package uk.fls.h2n0.main.screens;

import java.awt.Graphics;

import fls.engine.main.screen.Screen;
import fls.engine.main.util.Renderer;

public class GameScreen extends Screen {

	
	private Renderer r;
	
	public void postInit(){
		this.r = new Renderer(this.game.getImage());
	}
	
	@Override
	public void render(Graphics g) {
		int c = (int)System.currentTimeMillis() % this.r.makeRGB(255, 255, 255);
		this.r.fill(c);
	}

	@Override
	public void update() {

	}

}
