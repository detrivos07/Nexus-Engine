package com.detrivos.game.level.feature;

import com.detrivos.game.level.LevelGenerator;
import com.detrivos.game.level.tile.Tile;

public class BorderFeature extends Feature {
	
	public BorderFeature(LevelGenerator lg, Tile primary) {
		super(lg, primary);
	}

	public BorderFeature(LevelGenerator lg, Tile primary, Tile[] secondary) {
		super(lg, primary, secondary);
	}

	@Override
	public void addToFeatureMap(Feature[] map, int w, int h) {
		Feature[] temp = new Feature[map.length];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if (y == 0 || y == (h - 1)) {
					temp[x + y * w] = this;
					continue;
				}
				if ((y != 0 || y != (h - 1)) && (x == 0 || x == (w - 1))) {
					temp[x + y * w] = this;
					continue;
				}
			}
		}
		
		for (int t = 0; t < 2; t++) {
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int r = 4;
					int surround = 0;
					for (int i = 0; i < 9; i++) {
						if (i % 2 == 0) continue;
						
						int xi = (i % 3) - 1;
						int yi = (i / 3) - 1;
						
						if (getAt(temp, x + xi, y + yi, w) instanceof BorderFeature) {
							if (r > 1) r--;;
						} else surround++;
					}
					if (surround < 4) if (random.nextInt(r + (surround == 3 ? 1 : 0)) == 0) temp[x + y * w] = this;
					else if (surround == 0) temp[x + y * w] = this;
				}
			}
		}
		
		for (int t = 0; t < 2; t++) {
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int r = 4;
					int surround = 0;
					for (int i = 0; i < 9; i++) {
						if (i % 2 == 0) continue;
						
						int xi = (i % 3) - 1;
						int yi = (i / 3) - 1;
						
						if (getAt(temp, y + yi, x + xi, w) instanceof BorderFeature) {
							if (r > 1) r--;;
						} else surround++;
					}
					if (surround < 4) if (random.nextInt(r + (surround == 3 ? 1 : 0)) == 0) temp[y + x * w] = this;
					else if (surround == 0) temp[y + x * w] = this;
				}
			}
		}
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int surround = 0;
				for (int i = 0; i < 9; i++) {
					if (i % 2 == 0) continue;
					
					int xi = (i % 3) - 1;
					int yi = (i / 3) - 1;
					
					if (!(getAt(temp, y + yi, x + xi, w) instanceof BorderFeature)) surround++;
				}
				if (surround == 0) temp[y + x * w] = this;
			}
		}
		
		for (int i = 0; i < temp.length; i++) map[i] = temp[i];
	}
}
