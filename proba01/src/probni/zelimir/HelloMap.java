package probni.zelimir;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class HelloMap extends PApplet{
	
	private UnfoldingMap map1;
	
	public void setup(){
		
		size(800,600,P2D);
		Location loc = new Location(44.778714f, 17.203077f);
		map1 = new UnfoldingMap(this, 50, 50, 700, 500, new Microsoft.AerialProvider());
		map1.zoomAndPanTo(14, loc);
		MapUtils.createDefaultEventDispatcher(this, map1);
	}
	public void draw(){
		map1.draw();
	}
}