package com.creativelab.util;

public final class SpritePackerUtils {
	
	private SpritePackerUtils() {
		
	}

	public static int nameToHash(String name) {
		int hash = 0;
		name = name.toUpperCase();
		for (int i = 0; i < name.length(); i++) {
			hash = (hash * 61 + name.charAt(i)) - 32;
		}
		return hash;
	}
}
