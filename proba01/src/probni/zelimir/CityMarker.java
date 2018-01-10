package probni.zelimir;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractMarker;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class CityMarker extends AbstractMarker {
	
	private PImage image;
	
	public CityMarker(Location loc, PImage image){
		super(loc);
		this.image = image;
	}

	@Override
	public void draw(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.imageMode(PConstants.CENTER);
		pg.image(image, x, y-14);
		pg.popStyle();
	}

	@Override
	protected boolean isInside(float checkX, float checkY, float x, float y) {
		return checkX > x && checkX < image.width + x && checkY > y && checkY < image.height + y;
	}
	
	public String getCity(CityMarker cMarker){
		return cMarker.getStringProperty("name");
	}
	
	public String getPopulation(CityMarker cMarker){
		return cMarker.getStringProperty("population");
	}
	
	public String getCountry(CityMarker cMarker){
		return cMarker.getStringProperty("country");
	}

}
