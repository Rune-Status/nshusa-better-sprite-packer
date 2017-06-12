package com.creativelab.sprite.codec;

import java.io.DataInputStream;
import java.io.IOException;

import com.creativelab.sprite.SpriteBase;

public final class SpriteDecoder {
	
	private SpriteDecoder() {
		
	}
    
    public static SpriteBase decode(DataInputStream dat) throws IOException {	
    	SpriteBase sprite = new SpriteBase();
          while (true) {
                
                byte opcode = dat.readByte();
                
                if (opcode == 0) {
                      return sprite;
                } else if (opcode == 1) {
                	sprite.setId(dat.readShort());
                } else if (opcode == 2) {
                	sprite.setName(dat.readUTF());
                } else if (opcode == 3) {
                	sprite.setWidth(dat.readShort());
                } else if (opcode == 4) {
                	sprite.setHeight(dat.readShort());                	
                } else if (opcode == 5) {
                	sprite.setDrawOffsetX(dat.readShort());
                } else if (opcode == 6) {
                	sprite.setDrawOffsetY(dat.readShort());
                } else if (opcode == 7) {
                	
                      int indexLength = dat.readInt();
                      
                      int[] pixels = new int[indexLength];                      
                      
                      for (int i = 0; i < pixels.length; i++) {
                    	  
                    	  int color = dat.readInt();
                    	  
                    	  pixels[i] = color;
                      }
                      
                      sprite.setPixels(pixels);
                }
          }
    }

}
