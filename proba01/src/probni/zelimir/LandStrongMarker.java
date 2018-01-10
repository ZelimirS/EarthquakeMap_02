package probni.zelimir;

import de.fhpotsdam.unfolding.marker.Marker;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class LandStrongMarker extends LandMarker {
	
	PApplet pa = new PApplet();
	PImage image = pa.loadImage("data/ui/marker_red.png");
	
	public LandStrongMarker(Marker mrk){
		super(mrk);
		super.image = this.image;
	}

	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.image(image, x-11, y-37);
	}
	
	protected boolean isInside(float checkX, float checkY, float x, float y) {
		return checkX > x && checkX < image.width + x && checkY > y && checkY < image.height + y;
	}

}
