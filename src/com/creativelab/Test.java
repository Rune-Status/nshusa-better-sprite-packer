package com.creativelab;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.creativelab.sprite.SpriteCache;
import com.creativelab.util.SpritePackerUtils;

public class Test {

	public static void main(String[] args) throws IOException {		
		test2();
	}
	
	// create sprite cache from a directory
	public static void test1() throws IOException {
		final String path = "C:\\Users\\Chad\\Downloads\\media";		
		
		File dir = new File(path);
		
		long start = System.currentTimeMillis();
		
		SpriteCache cache = SpriteCache.load(java.awt.Color.BLACK, dir);
		
		try(FileOutputStream fos = new FileOutputStream(new File("packed.dat"))) {
			fos.write(cache.encode());
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println(end - start + " ms");
		
	}
	
	// decode a packed sprite cache
	public static void test2() throws IOException {
		
		long start = System.currentTimeMillis();
		
		SpriteCache cache = SpriteCache.decode(Files.readAllBytes(Paths.get("./packed.dat")));

		long end = System.currentTimeMillis();
		
		System.out.println(end - start + " ms");
		
		int hash = SpritePackerUtils.nameToHash("mod_icons");
		
		System.out.println(cache.contains(hash));
		
		cache.remove(hash);
		
		System.out.println(cache.contains(hash));
	}


}
