package com.creativelab;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.creativelab.sprite.SpriteCache;

public class Test {

	public static void main(String[] args) throws IOException {		
		test2();
	}
	
	// create sprite cache from a directory
	public static void test1() throws IOException {
		final String path = "C:\\Users\\Chad\\Downloads\\#143 sprites";		
		
		File dir = new File(path);
		
		long start = System.currentTimeMillis();
		
		SpriteCache cache = SpriteCache.load(dir);
		
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
		
		cache.getImageArchives().stream().filter(it -> it.getSprites().size() > 1).forEach(it -> System.out.println(it.getHash() + " " + it.getSprites().size()));
		
		long end = System.currentTimeMillis();
		
		System.out.println(end - start + " ms");
	}


}
