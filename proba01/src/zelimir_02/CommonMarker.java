package zelimir_02;

import java.util.Comparator;
import java.util.HashMap;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

abstract class CommonMarker extends AbstractMarker implements Comparable<CommonMarker>, Comparator<CommonMarker>{
	
	private boolean visible = true;
	
	public CommonMarker(){}
	public CommonMarker(Location loc, HashMap<java.lang.String,java.lang.Object> properties){
		super(loc, properties);
	}

	@Override
	public void draw(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.imageMode(PConstants.CENTER);
		if(visible) drawMarker(pg, x, y);
		pg.popStyle();
	}

	public abstract void drawMarker(PGraphics pg, float x, float y);
	public abstract void showTitle(PGraphics pg, float x, float y);

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	

}
