package zelimir_02;

import java.util.HashMap;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

abstract class LandMarker extends AbstractMarker{
		
	public LandMarker(Location loc, HashMap<String, Object> properties) {
		super(loc, properties);
	}

	public void draw(PGraphics pg, float x, float y){
		pg.pushStyle();
		pg.imageMode(PConstants.CENTER);
		drawStrongLandEarthquake(pg, x, y);
		drawMediumLandEarthquake(pg, x, y);
		drawWeekLandEarthquake(pg, x, y);
		pg.popStyle();
	}
	
	abstract void drawStrongLandEarthquake(PGraphics pg, float x, float y);
	abstract void drawMediumLandEarthquake(PGraphics pg, float x, float y);
	abstract void drawWeekLandEarthquake(PGraphics pg, float x, float y);
	
	public abstract boolean isInside(float x, float y, float z, float m);

	public void showTitle(PGraphics pg, float x, float y) {
		String strMag = properties.get("magnitude").toString();
		pg.noStroke();
		pg.fill(255,127,80);
		pg.rect(x, y, strMag.length()*9, 18);
		pg.textSize(16);
		pg.fill(0,0,0);
		pg.text(strMag, x, y+15);				
	}

}
