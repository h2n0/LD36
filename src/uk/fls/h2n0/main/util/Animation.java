package uk.fls.h2n0.main.util;

import fls.engine.main.util.rendertools.SpriteParser;

public class Animation {

	public static int I_STYLE = 1;
	public static int XY_STYLE = 2;
	
	private int[][] data;
	private int currentFrame;
	private SpriteParser sp;
	public Animation(SpriteParser sp, int s, int... frames){
		if(frames.length % s != 0)throw new RuntimeException("Invalid number of frames");
		
		this.sp = sp;
		this.data = new int[frames.length / s][];
		
		if(s == I_STYLE){
			loadFramesI(frames);
		}else if(s == XY_STYLE){
			loadFramesXY(frames);
		}else{
			throw new RuntimeException("Invalid frame seperation value");
		}
	}
	
	private void loadFramesXY(int[] cords){
		int frame = 0;
		for(int i = 0; i < cords.length; i += 2){
			int x = cords[i];
			int y = cords[i + 1];
			this.data[frame] = this.sp.getData(x, y); 
			frame++;
		}
	}
	
	private void loadFramesI(int[] cords){
		for(int i = 0; i < cords.length; i++){
			this.data[i] = this.sp.getData(cords[i]);
		}
	}
	
	public int[] getFrame(){
		return this.data[this.currentFrame];
	}
	
	public void nextFrame(){
		this.currentFrame = (this.currentFrame + 1) % this.data.length;
	}
}
