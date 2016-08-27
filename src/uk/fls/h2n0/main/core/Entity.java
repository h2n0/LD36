package uk.fls.h2n0.main.core;

import fls.engine.main.io.FileIO;
import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import fls.engine.main.util.rendertools.SpriteParser;

public abstract class Entity {

	
	protected Point pos;
	protected int[] frameData;
	protected SpriteParser sp = new SpriteParser(FileIO.instance.readInternalFile("/entitys/data1.art"));
	
	public abstract void render(Renderer r);
	
	public abstract void update();
}