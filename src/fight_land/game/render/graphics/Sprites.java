package fight_land.game.render.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprites {
	
	private ArrayList<BufferedImage> textures;
	private int actual_sprites;
	
	private Sprites(ArrayList<BufferedImage> textures) {
		this.textures = textures;
		this.actual_sprites = 0;
	}
	
	public Sprites(BufferedImage img, int width, int height) {
		this.textures = new ArrayList<BufferedImage>();
		int x = 0;
		int y = 0;
		while(img.getWidth()/width > x && img.getHeight()/height > y) {
			this.textures.add(img.getSubimage((width*x), (height*y), width, height));
			x++;
			if(img.getWidth()/width <= x) {
				x = 0;
				y++;
			}
		}
		this.actual_sprites = 0;
	}
	
	public void resetSprite() {
		this.actual_sprites = 0;
	}
	
	public BufferedImage getNextSprite() {
		if(this.actual_sprites >= this.textures.size()) {
			this.actual_sprites = 0;
		}
		return this.textures.get(this.actual_sprites++);
	}
	
	public BufferedImage getActualSprite() {
		if(this.actual_sprites < this.textures.size()) {
			return this.textures.get(this.actual_sprites);
		}else {
			return this.textures.get(0);
		}
	}
	
	public Sprites getSpritesFlip(){
		ArrayList<BufferedImage> flipedImages = new ArrayList<BufferedImage>();
		BufferedImage flipedBuffered;
		for (int i = 0; i < this.textures.size(); i++) {
			flipedBuffered = new BufferedImage(this.textures.get(i).getWidth(), this.textures.get(i).getHeight(), BufferedImage.TYPE_INT_ARGB);
			flipedBuffered.createGraphics().drawImage(this.textures.get(i), this.textures.get(i).getWidth(), 0, -this.textures.get(i).getWidth(), this.textures.get(0).getHeight(), null);
			flipedImages.add(flipedBuffered);
		}
		return new Sprites(flipedImages);
	}
	
	public Sprites clone() {
		ArrayList<BufferedImage> clonedImage = new ArrayList<BufferedImage>();
		for(int i = 0; i < this.textures.size();i++) {
			clonedImage.add(this.textures.get(i));
		}
		System.out.println("ArrayListCloned");
		return new Sprites(clonedImage);
	}
	
	

}
