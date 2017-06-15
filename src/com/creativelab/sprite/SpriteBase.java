package com.creativelab.sprite;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Objects;

import com.creativelab.util.BufferedImageUtils;

public class SpriteBase {

	private int id;

	private int[] pixels;

	private String name = "None";

	private int drawOffsetX;

	private int drawOffsetY;

	private int width;

	private int height;

	public SpriteBase() {

	}
	
	public static SpriteBase convert(BufferedImage source) {		
			BufferedImage quantized = BufferedImageUtils.convert(source, BufferedImage.TYPE_INT_ARGB);			
			
			final int[] pixels = ((DataBufferInt) quantized.getRaster().getDataBuffer()).getData();
			
			SpriteBase sprite = new SpriteBase();
			sprite.setWidth(quantized.getWidth());
			sprite.setHeight(quantized.getHeight());
			sprite.setPixels(pixels);
			
			return sprite;
	}
	
	public BufferedImage toBufferedImage() {
		
		BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		
		final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		System.arraycopy(this.pixels, 0, pixels, 0, this.pixels.length);
		
		return image;
	}

	public SpriteBase(int id) {		
		this.id = id;
	}

	public int[] getPixels() {
		return this.pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDrawOffsetX() {
		return this.drawOffsetX;
	}

	public void setDrawOffsetX(int drawOffsetX) {
		this.drawOffsetX = drawOffsetX;
	}

	public int getDrawOffsetY() {
		return this.drawOffsetY;
	}

	public void setDrawOffsetY(int drawOffsetY) {
		this.drawOffsetY = drawOffsetY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int hashCode() {
		return Objects.hash(id);
	}
	
	public boolean equals(Object o) {
		
		if (o == null) {
			return false;
		}
		
		if (o instanceof SpriteBase) {
			SpriteBase other = (SpriteBase) o;
			
			if (this.hashCode() == other.hashCode()) {
				return true;
			}
		}
		
		return false;
	}

}
