package probni.zelimir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class EarthquakeCityMap extends PApplet {
	
	private UnfoldingMap map;
	
	public void setup(){
		
		size(950, 600, OPENGL);
//		map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
		map = new UnfoldingMap(this, 200, 50, 700, 500, new Microsoft.RoadProvider());
		map.zoomToLevel(3);
		MapUtils.createDefaultEventDispatcher(this, map);
		
//		List<Feature> features = new ArrayList<>();
		List<Marker> markers = new ArrayList<>();
		Map<Location, Feature> locFeaMap = new HashMap<>();
		
		Location loc1 = new Location(-38.29f, -73.05f);
		Feature chileEq = new PointFeature(loc1);
		chileEq.addProperty("title", "Valdivia, Chile");
		chileEq.addProperty("magnitude", "9.5");
		chileEq.addProperty("date", "22. may 1960.");
		chileEq.addProperty("year", "1960.");
		
		Location loc2 = new Location(61.02f, -147.65f);
		Feature alaskaEq = new PointFeature(loc2);
		alaskaEq.addProperty("title", "Alaska");
		alaskaEq.addProperty("magnitude", "9.2");
		alaskaEq.addProperty("date", "23. march 1964.");
		alaskaEq.addProperty("year", "1964.");
		
		Location loc3 = new Location(3.3f, 95.78f);
		Feature sumatraEq = new PointFeature(loc3);
		sumatraEq.addProperty("title", "Sumatra");
		sumatraEq.addProperty("magnitude", "9.1");
		sumatraEq.addProperty("date", "26. december 2004.");
		sumatraEq.addProperty("year", "2004.");
		
		Location loc4 = new Location(38.322f, 142.369f);
		Feature japanEq = new PointFeature(loc4);
		japanEq.addProperty("title", "Japan");
		japanEq.addProperty("magnitude", "9.0");
		japanEq.addProperty("date", "11. march 2011.");
		japanEq.addProperty("year", "2011.");
		
		Location loc5 = new Location(52.76f, 160.06f);
		Feature kamchatkaEq = new PointFeature(loc5);
		kamchatkaEq.addProperty("title", "Kamchatka");
		kamchatkaEq.addProperty("magnitude", "9.0");
		kamchatkaEq.addProperty("date", "4. november 1952.");
		kamchatkaEq.addProperty("year", "1952.");
		
		locFeaMap.put(loc1, chileEq);
		locFeaMap.put(loc2, alaskaEq);
		locFeaMap.put(loc3, sumatraEq);
		locFeaMap.put(loc4, japanEq);
		locFeaMap.put(loc5, kamchatkaEq);
		
		for(Location loc : locFeaMap.keySet()){
			Marker marker = new SimplePointMarker(loc, (locFeaMap.get(loc)).getProperties());
			map.addMarker(marker);
//			

			
		}
	
	}
	
	public void draw(){
		map.draw();
	}

}
