package probni.zelimir;

import processing.core.PApplet;
import processing.core.PImage;

public class Unisti_01 extends PApplet {
	
	String pictUrl = "http://www.standard.rs/images/2017-2/visegradmehmedpasa01.jpg";
	PImage img;
	
	public void setup(){
		size(260,200);
		img = loadImage(pictUrl, "jpg");
	}
	
	
	public void draw(){
		int[] colorArr = colorSeconds();
		
		img.resize(0, height);
		image(img,0,0);
		fill(colorArr[0], colorArr[1], 0);
		ellipse(width/4, height/5, 50, 50);
		noStroke();
		
	}

	public int[] colorSeconds(){
		int[] arr = new int[3];
		float secondsFrom30 = Math.abs(second() - 30);
		float ratio = secondsFrom30 / 30;
		arr[0] = (int) (255*ratio);
		arr[1] = (int) (255*ratio);
		arr[2] = 0;
		return arr;
	}
	
}


