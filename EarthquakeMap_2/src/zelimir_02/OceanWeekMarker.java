package zelimir_02;

import java.util.HashMap;

import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class OceanWeekMarker extends OceanMarker {
	
	PApplet pApp = new PApplet();
	PImage image = pApp.loadImage("data/ui/ocean_gray.png");

	public OceanWeekMarker(Location loc, HashMap<String, Object> properties) {
		super(loc, properties);
	}

	@Override
	void drawStrongOceanEarthquake(PGraphics pg, float x, float y) {
	}

	@Override
	void drawMediumOceanEarthquake(PGraphics pg, float x, float y) {
	}

	@Override
	void drawWeekOceanEarthquake(PGraphics pg, float x, float y) {
		pg.image(image, x, y-image.height/2);		
	}

	@Override
	public boolean isInside(float checkX, float checkY, float x, float y) {
		x = x - image.width/2;
		y = y - image.height;
		return checkX > x && checkX < image.width + x && checkY > y && checkY < image.height + y;
	}

}
