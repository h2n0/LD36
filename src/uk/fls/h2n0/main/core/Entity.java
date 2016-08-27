package uk.fls.h2n0.main.core;

import fls.engine.main.io.FileIO;
import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import fls.engine.main.util.rendertools.SpriteParser;

public abstract class Entity {

	
	protected Point pos;
	protected int[] frameData;
	protected float speed = 1f;
	public World world;
	protected SpriteParser sp = new SpriteParser(FileIO.instance.readInternalFile("/entitys/data1.art"));
	
	public abstract void render(Renderer r);
	
	public abstract void update();
	
	public void move(int dx, int dy){
		float nx = this.pos.x - dx;
		float ny = this.pos.y - dy;
		
		float rad = (float)Math.atan2(this.pos.y - ny, this.pos.x - nx);

		float npx = (float) (this.pos.x + (Math.cos(rad) * speed));
		float npy = (float) (this.pos.y + (Math.sin(rad) * speed));
		this.pos.setPos(npx, npy);
	}
	
	public Point getPos(){
		return this.pos;
	}
	
	private void angMove(int deg){
		float rad = (float)((deg / 180) * Math.PI);
		
	}
}
