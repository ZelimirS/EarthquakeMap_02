package probni.zelimir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class LifeExpectancyMap extends PApplet {
	
	UnfoldingMap map; 

	public void setup(){
		
		Map<String, Float> lifeExpectancy = new HashMap<>();
		List<Feature> countriesFeatures = new ArrayList<>();
		List<Marker> countryMarkers = new ArrayList<>();
		
		size(1200, 800, OPENGL);
		map = new UnfoldingMap(this, 50, 50, 1000, 700, new Microsoft.AerialProvider());
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		lifeExpectancy = loadLifeExpectancyFromCSV("data/data/LifeExpectancyWorldBank.csv");
		
		countriesFeatures = GeoJSONReader.loadData(this, "data/data/countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countriesFeatures);
		
		map.addMarkers(countryMarkers);
		shadeMap(countryMarkers, lifeExpectancy);
		
	}
	
	public void draw(){
		map.draw();
	}
	
	private void shadeMap(List<Marker> markers, Map<String, Float> expectancies){
		String countryId;
		for(Marker mrk : markers){
			countryId = mrk.getId();
			if(expectancies.containsKey(countryId)){
				int col;
				float age = expectancies.get(countryId);
				if(age != 0.0){
					col = (int) map(age, 40, 90, 10, 255);
				}
				else{
					col = 255;
				}
				mrk.setColor(color(255-col, 100, col));
			}
		}
		
	}
	
	private Map<String, Float> loadLifeExpectancyFromCSV(String fileName){
		Map<String, Float> lifeExpectancy = new HashMap<>();
		String[] rows = loadStrings(fileName);
		for(String row : rows){
			String[] columns = row.split(",");
			float value;
			if(columns[5].equals("..")){
				value = 0.0f;
			}
			else{
				value = Float.parseFloat(columns[5]);
			}
			lifeExpectancy.put(columns[4], value);
		}
		return lifeExpectancy;
	}
	
}
