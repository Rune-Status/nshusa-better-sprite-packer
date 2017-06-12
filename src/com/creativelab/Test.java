package com.creativelab;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.creativelab.sprite.SpriteCache;

public class Test {

	public static void main(String[] args) throws IOException {		

	}
	
	// create sprite cache from a directory
	public static void test1() throws IOException {
		final String path = "C:\\Users\\Chad\\Downloads\\media";		
		
		File dir = new File(path);
		
		SpriteCache cache = SpriteCache.load(dir);
		
		try(FileOutputStream fos = new FileOutputStream(new File("packed.dat"))) {
			fos.write(cache.encode());
		}
		
	}
	
	// decode a packed sprite cache
	public static void test2() throws IOException {
		SpriteCache cache = SpriteCache.decode(Files.readAllBytes(Paths.get("./packed.dat")));
		
		cache.getImageArchives().forEach(it -> System.out.println(it.getHash() + " " + it.getSprites().size()));
	}


}
