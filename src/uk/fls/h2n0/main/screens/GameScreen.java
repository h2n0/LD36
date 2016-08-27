package uk.fls.h2n0.main.screens;

import java.awt.Graphics;

import fls.engine.main.screen.Screen;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.core.World;

public class GameScreen extends Screen {

	
	private Renderer r;
	private World w;
	
	public void postInit(){
		this.r = new Renderer(this.game.getImage());
		this.w = new World();
	}
	
	@Override
	public void render(Graphics g) {
		
	}

	@Override
	public void update() {

	}

}
