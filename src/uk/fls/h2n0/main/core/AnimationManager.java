package uk.fls.h2n0.main.core;

import java.util.HashMap;

import uk.fls.h2n0.main.util.Animation;

public class AnimationManager {

	
	private HashMap<String, Animation> anims;
	private String currentName;
	
	public AnimationManager(){
		this.anims = new HashMap<String, Animation>();
	}
	
	public void addAnimation(String name, Animation an){
		this.anims.put(name, an);
	}
	
	public Animation getAnimation(String name){
		this.currentName = name;
		return this.anims.get(name);
	}
	
	public String getCurrentName(){
		return this.currentName;
	}
}
