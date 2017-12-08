package probni.zelimir;

import processing.core.PApplet;
import processing.core.PImage;

public class CircleColor extends PApplet {

	PImage img;

	public void setup() {
		size(500, 500, P2D);

		String str = "http://www.mojohaus.org/jaxb2-maven-plugin/Documentation/v2.3.1/images/plantuml/postProcessing.png";
		String str2 = "D:/courses/java_san_diego/proba01/src/data/test/beach.jpg";
		img = loadImage(str2);
		img.resize(0, height);
		image(img, 0, 0);

		

	}

	public void draw() {
		int[] rgbColor = sunColorSec(second());
		fill(rgbColor[0], rgbColor[1], rgbColor[2]);
		noStroke();
		ellipse(width/4, height/5, width / 4, width / 4);
	}
	
	public int[] sunColorSec(float seconds){
		int[] arr = new int[3];
		float fromThirty = Math.abs(30 - seconds);
		float ratio = fromThirty/30;
		arr[0] = (int) (255*ratio);
		arr[1] = (int)(255*ratio);
		arr[2] = 0;
		
		return arr;
	}

}
