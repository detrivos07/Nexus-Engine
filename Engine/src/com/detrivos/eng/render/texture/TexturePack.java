package com.detrivos.eng.render.texture;

import com.detrivos.eng.render.*;

public class TexturePack {
	
	private Animation[] playerAnims;
	
	private TextureAtlas main;
	
	public TexturePack setPlayerAnims(Animation[] pAnims) {
		playerAnims = pAnims;
		return this;
	}
	
	public TexturePack setMainAtlas(TextureAtlas main) {
		this.main = main;
		return this;
	}
	
	public Animation[] getPlayerAnims() {
		return playerAnims;
	}
	
	public TextureAtlas getMainAtlas() {
		return main;
	}
}
