package probni.zelimir;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractMarker;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class ImageMarker extends AbstractMarker {
	
	PImage img;
	
	public ImageMarker(Location loc, PImage img){
		super(loc);
		this.img = img;
	}

	@Override
	public void draw(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.imageMode(PConstants.CORNER);
		pg.image(img, x - 11, y - 37);
		pg.popStyle();
	}

	@Override
	protected boolean isInside(float checkX, float checkY, float x, float y) {
		return checkX > x && checkX < img.width + x && checkY > y && checkY < img.height + y;
	}
	
	public String getMagnitude(ImageMarker iMarker){
		return iMarker.getStringProperty("magnitude");
	}
	
	public String getName(ImageMarker iMarker){
		return iMarker.getStringProperty("name");
	}
	
	public String getDepth(ImageMarker iMarker){
		return iMarker.getStringProperty("depth");
	}
	
	public String getAge(ImageMarker iMarker){
		return iMarker.getStringProperty("age");
	}

}
