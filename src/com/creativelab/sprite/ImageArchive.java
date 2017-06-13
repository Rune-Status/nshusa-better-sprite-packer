package com.creativelab.sprite;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import com.creativelab.sprite.codec.SpriteDecoder;
import com.creativelab.sprite.codec.SpriteEncoder;
import com.creativelab.util.SpritePackerUtils;

public class ImageArchive {
	
	private final Set<SpriteBase> sprites = new LinkedHashSet<>();	
	
	private int hash;
	
	private ImageArchive(int hash) {
		this.hash = hash;
	}
	
	private ImageArchive(String name) {
		this.hash = SpritePackerUtils.nameToHash(name);
	}
	
	public static ImageArchive create(int hash) {
		return new ImageArchive(hash);
	}
	
	public static ImageArchive create(String name) {
		return new ImageArchive(name);
	}	
	
	public boolean add(SpriteBase sprite) {
		return sprites.add(sprite);
	}
	
	public boolean remove(int id) {
		Optional<SpriteBase> result = sprites.stream().filter(it -> it.getId() == id).findFirst();
		
		if (result.isPresent()) {
			return sprites.remove(result.get());
		}
		
		return false;
	}
	
	public boolean contains(int id) {
		return sprites.stream().anyMatch(it -> it.getId() == id);
	}
	
	public Optional<SpriteBase> search(int id) {
		return sprites.stream().filter(it -> it.getId() == id).findFirst();
	}
	
	public byte[] encode() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (DataOutputStream dos = new DataOutputStream(bos)) {
			dos.writeInt(hash);
			dos.writeShort(sprites.size());			
			for(SpriteBase sprite : sprites) {
				SpriteEncoder.encode(dos, sprite);				
			}
		}
		return bos.toByteArray();
	}
	
	public static ImageArchive decode(byte[] data) throws IOException {		
		try(DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data))) {
			int hash = dis.readInt();		
			int sprites = dis.readShort();
			
			ImageArchive imageArchive = ImageArchive.create(hash);
			
			for (int i = 0; i < sprites; i++) {
				imageArchive.add(SpriteDecoder.decode(dis));
			}
			
			return imageArchive;
			
		}
		
	}

	public int getHash() {
		return hash;
	}

	public void setName(String name) {
		this.hash = SpritePackerUtils.nameToHash(name);
	}

	public Set<SpriteBase> getSprites() {
		return sprites;
	}
	
	public int hashCode() {
		return hash;
	}
	
	public boolean equals(Object o) {
		
		if (o == null) {
			return false;
		}
		
		if (o instanceof ImageArchive) {
			ImageArchive other = (ImageArchive) o;
			
			if (this.hashCode() == other.hashCode()) {
				return true;
			}
		}
		
		return false;
		
	}
	
}
