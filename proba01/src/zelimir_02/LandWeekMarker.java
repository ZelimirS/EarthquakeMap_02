package zelimir_02;

import java.util.HashMap;

import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class LandWeekMarker extends LandMarker {
	
	PApplet pApp = new PApplet();
	PImage image = pApp.loadImage("data/ui/land_gray.png");
	HashMap<String, Object> properties;

	public LandWeekMarker(Location loc, HashMap<String, Object> properties) {
		super(loc, properties);
		this.properties = properties;
	}

	@Override
	void drawStrongLandEarthquake(PGraphics pg, float x, float y) {
	}

	@Override
	void drawMediumLandEarthquake(PGraphics pg, float x, float y) {
	}

	@Override
	void drawWeekLandEarthquake(PGraphics pg, float x, float y) {
		pg.image(image, x, y-image.height/2);
	}

	@Override
	public boolean isInside(float checkX, float checkY, float x, float y) {
		x = x - image.width/2;
		y = y - image.height;
		return checkX > x && checkX < image.width + x && checkY > y && checkY < image.height + y;
	}

}
