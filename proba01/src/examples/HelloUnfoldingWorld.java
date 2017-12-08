package examples;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

/**
 * Hello Unfolding World.
 * 
 * Download the distribution with examples for many more examples and features.
 * 
 * Create a new Eclipse project. Open properties of the src dir to find its path. Copy files there. Refresh project.
   For the jar files, copy them anywhere into eclipse dir, right click each, and "add to build path".
 */
public class HelloUnfoldingWorld extends PApplet {

	UnfoldingMap map;

	public void setup() {
		size(800, 600, OPENGL);

//		map = new UnfoldingMap(this);
		map = new UnfoldingMap(this, new Microsoft.AerialProvider());

		map.zoomAndPanTo(10, new Location(32.881, -117.238));

		MapUtils.createDefaultEventDispatcher(this, map);
	}

	public void draw() {
		background(0);
		map.draw();
	}

}
