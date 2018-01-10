package probni.zelimir;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public abstract class OceanMarker extends AbstractMarker {

	public PImage image;
	
	public OceanMarker(){}
	public OceanMarker(Marker mrk){
		super(mrk.getLocation());
	}

	@Override
	public void draw(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.imageMode(PConstants.CORNER);
		drawMarker(pg, x, y);
		pg.popStyle();
	}

	public abstract void drawMarker(PGraphics pg, float x, float y);
	
	@Override
	protected boolean isInside(float checkX, float checkY, float x, float y) {
		return checkX > x && checkX < image.width + x && checkY > y && checkY < image.height + y;
	}

}
