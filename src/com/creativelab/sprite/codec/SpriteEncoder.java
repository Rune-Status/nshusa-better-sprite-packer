package com.creativelab.sprite.codec;

import java.io.DataOutputStream;
import java.io.IOException;

import com.creativelab.sprite.SpriteBase;

public final class SpriteEncoder {
	
	private SpriteEncoder() {
		
	}
	
	public static void encode(DataOutputStream dat, SpriteBase sprite) throws IOException {		
		if (sprite.getId() != -1) {
			dat.writeByte(1);
			dat.writeShort(sprite.getId());
		}

		if (sprite.getName() != null && !sprite.getName().equals("None")) {
			dat.writeByte(2);
			dat.writeUTF(sprite.getName());
		}
		
		if (sprite.getWidth() != 0) {
			dat.writeByte(3);
			dat.writeShort(sprite.getWidth());
		}

		if (sprite.getHeight() != 0) {
			dat.writeByte(4);
			dat.writeShort(sprite.getHeight());
		}

		if (sprite.getDrawOffsetX() != 0) {
			dat.writeByte(5);
			dat.writeShort(sprite.getDrawOffsetX());
		}

		if (sprite.getDrawOffsetY() != 0) {
			dat.writeByte(6);
			dat.writeShort(sprite.getDrawOffsetY());
		}

		if (sprite.getPixels() != null) {
			dat.writeByte(7);
			
			dat.writeInt(sprite.getPixels().length);			
			
			for(int i = 0; i < sprite.getPixels().length; i++) {				
				dat.writeInt(sprite.getPixels()[i]);
			}
			
		}
		
		dat.writeByte(0);
		
	}

}
