package com.creativelab.sprite;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import com.creativelab.util.CompressionUtils;
import com.creativelab.util.SpritePackerUtils;

public final class SpriteCache {
	
	private final Set<ImageArchive> imageArchives = new LinkedHashSet<>();
	
	private static final int SIGNATURE_LENGTH = 3;
	
	private SpriteCache() {
		
	}
	
	public static SpriteCache create() {
		return new SpriteCache();
	}
	
	public byte[] encode() throws IOException {		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		try(DataOutputStream dos = new DataOutputStream(bos)) {
			dos.writeShort(imageArchives.size());			
			for (ImageArchive imageArchive : imageArchives) {
				
				byte[] uncompressed = imageArchive.encode();				
				
				byte[] compressed = CompressionUtils.gzip(uncompressed);
				
				dos.writeInt(uncompressed.length);
				dos.writeInt(compressed.length);
				
				for(byte b : compressed) {
					dos.writeByte(b);					
				}
			}
		}
		
		byte[] encoded = bos.toByteArray();		
		
		ByteBuffer buffer = ByteBuffer.allocate(encoded.length + SIGNATURE_LENGTH);
		
		buffer.put((byte)'b');
		buffer.put((byte)'s');
		buffer.put((byte)'p');
		buffer.put(encoded);
		
		buffer.flip();
		
		return buffer.array();
	}
	
	public static SpriteCache decode(byte[] data) throws IOException {
		try(DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data))) {			
			byte[] signature = new byte[SIGNATURE_LENGTH];
			
			dis.read(signature);
			
			if (signature[0] != 'b' && signature[1] != 's' && signature[2] != 'p') {
				throw new IOException("Invalid file signature!");
			}
			
			final SpriteCache cache = SpriteCache.create();
			
			short imageArchives = dis.readShort();			
			
			for (int i = 0; i < imageArchives; i++) {
				int uncompressedLength = dis.readInt();
				
				int compressedLength = dis.readInt();
				
				byte[] uncompressed = new byte[uncompressedLength];
				
				byte[] compressed = new byte[compressedLength];
				
				dis.readFully(compressed);
				
				CompressionUtils.degzip(compressed, uncompressed);
				
				cache.add(ImageArchive.decode(uncompressed));
			}
			
			return cache;
		}
	}
	
	public static SpriteCache load(File directory) throws IOException {		
		
		if (!directory.isDirectory()) {
			throw new IOException("This file is not a directory.");
		}
		
		File[] files = directory.listFiles();
		
		final SpriteCache cache = SpriteCache.create();
		
		final ImageArchive defaultArchive = ImageArchive.create("default");		
		
		cache.add(defaultArchive);
		
		for (File file : files) {
			if (file.isDirectory()) {
				ImageArchive archive = ImageArchive.create(file.getName());
				
				File[] images = file.listFiles();

				for (File fimage : images) {
					try {
						
						BufferedImage img = ImageIO.read(fimage);
						
						SpriteBase sprite = SpriteBase.convert(img);
						
						sprite.setId(Integer.parseInt(fimage.getName().substring(0, fimage.getName().indexOf("."))));
						
						archive.add(sprite);
						
					} catch (Exception ex) {
						continue;
					}					
				}
				
				cache.add(archive);
				
			} else {
				try {
					BufferedImage image = ImageIO.read(file);
					
					SpriteBase sprite = SpriteBase.convert(image);
					
					sprite.setId(Integer.parseInt(file.getName().substring(0, file.getName().indexOf("."))));
					
					defaultArchive.add(sprite);
				} catch (Exception ex) {
					ex.printStackTrace();
					continue;
				}
			}

		}
		
		return cache;
				
	}
	
	public boolean create(int hash) {
		return imageArchives.add(ImageArchive.create(hash));		
	}
	
	public boolean create(String name) {
		return imageArchives.add(ImageArchive.create(SpritePackerUtils.nameToHash(name)));		
	}
	
	public boolean add(ImageArchive imageArchive) {
		return imageArchives.add(imageArchive);
	}
	
	public boolean remove(ImageArchive imageArchive) {
		return imageArchives.remove(imageArchive);
	}
	
	public boolean contains(String name) {
		return imageArchives.stream().anyMatch(it -> SpritePackerUtils.nameToHash(name) == it.getHash());
	}
	
	public boolean contains(int hash) {
		return imageArchives.stream().anyMatch(it -> hash == it.getHash());
	}

	public Set<ImageArchive> getImageArchives() {
		return imageArchives;
	}

}
