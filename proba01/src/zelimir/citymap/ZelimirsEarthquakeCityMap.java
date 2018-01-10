package zelimir.citymap;

import java.util.ArrayList;
import java.util.List;

import com.zelimir.parser.ZelimirParser;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.AbstractMarker;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import probni.zelimir.CityMarker;
import processing.core.PApplet;
import processing.core.PFont;

public class ZelimirsEarthquakeCityMap extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	UnfoldingMap map;
	// String rssQuakeUrl =
	// "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	String rssQuakeUrl = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_day.atom";
	String rssQuakeUrlOffline = "data/data/2.5_week.atom";

	List<ImageMarker> earthquakeMarkersImage = new ArrayList<>();////////////////////////////////////////////////
	List<Marker> earthquakeMarkers = new ArrayList<>();
	private List<Marker> cityMarkers = new ArrayList<>();
	private List<Marker> countryMarkers = new ArrayList<>();
	ImageMarker imgMarker = null;
	private String cityFile = "data/ui/city-data.json";
	private String countryFile = "data/ui/countries.geo.json";

	public void setup() {

		List<Feature> earthquakeFeatures = ZelimirParser.loadFeaturesFromRSS(this, rssQuakeUrl);
		List<Feature> cityFeatures = GeoJSONReader.loadData(this, cityFile);
		List<Feature> countryFeatures = GeoJSONReader.loadData(this, countryFile);
		// List<Feature> earthquakeFeatures =
		// ZelimirParser.loadFeaturesFromRSS(this, rssQuakeUrlOffline);

		size(1200, 600, OPENGL);

		 map = new UnfoldingMap(this, 200, 50, 900, 500, new  Microsoft.RoadProvider());
//		map = new UnfoldingMap(this, 200, 50, 900, 500, new EsriProvider.DeLorme());
//		 map = new UnfoldingMap(this, 200, 50, 900, 500, new Google.GoogleMapProvider());

		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);

		earthquakeMarkers = MapUtils.createSimpleMarkers(earthquakeFeatures);
		cityMarkers = MapUtils.createSimpleMarkers(cityFeatures);
		countryMarkers = MapUtils.createSimpleMarkers(countryFeatures);
		// //////////////////
		
		for(Marker m : cityMarkers){
			CityMarker imgM = new CityMarker(m.getLocation(), loadImage("data/data/cityMarker.png"));
			imgM.setProperties(m.getProperties());
			map.addMarker(imgM);
		}

		for (Marker mrk : earthquakeMarkers) {
			// mrk.setStrokeWeight(0);
			float magnitude = (float) (mrk.getProperty("magnitude"));
			String age = (String) mrk.getProperty("age");
			if (magnitude <= 4.0) {
				imgMarker = new ImageMarker(mrk.getLocation(), loadImage("data/ui/marker_gray.png"));
				imgMarker.setProperties(mrk.getProperties());
				earthquakeMarkersImage.add(imgMarker);
			} else if (magnitude > 4.0 && magnitude <= 4.9) {
				imgMarker = new ImageMarker(mrk.getLocation(), loadImage("data/ui/marker_yellow.png"));
				imgMarker.setProperties(mrk.getProperties());
				 earthquakeMarkersImage.add(imgMarker);
			} else if (magnitude > 4.9) {
				imgMarker = new ImageMarker(mrk.getLocation(), loadImage("data/ui/marker_red.png"));
				imgMarker.setProperties(mrk.getProperties());
				 earthquakeMarkersImage.add(imgMarker);
			}
			map.addMarker(imgMarker);
		}
		
		//*****************************************************************************************************************************
		EarthquakeMarker eqm;
		//*****************************************************************************************************************************
		for (Marker mk : earthquakeMarkers) {
			System.out.println("### " + mk.getProperties());
		}
	}

	public void draw() {
		map.draw();
		addLegend();
		addText(earthquakeMarkersImage);

	}

	private void addText(List<ImageMarker> mrk) {
		for (Marker m : mrk) {
			float mapX = ((AbstractMarker) m).getScreenPosition(map).x;
			float mapY = ((AbstractMarker) m).getScreenPosition(map).y;
			String strMag = m.getProperty("magnitude").toString();
			if(m.getStringProperty("age").equals("Past Hour") && map.isHit(mapX, mapY)){
				if((mapY > 60 && mapY < map.getHeight()+50 - 10) && (mapX > 205 && mapX < map.getWidth()+200 - 10)){
					fill(255,0,0);
					ellipse(mapX+2.0f, mapY, 14, 14);
				}
			}
			if(m.isInside(map, mouseX+11, mouseY+43) && map.isHit(mapX, mapY)){
				if(mapX < map.getWidth()+200 - 20){
					fill(90);
					text(strMag, mapX, mapY);
				}
			}
		}
	}
	
	private boolean isLand(Marker earthquakeMrk){
		for(Marker mrk : countryMarkers){
			return isInCountry(mrk, earthquakeMrk);
		}
		return false;
	}
	private boolean isInCountry(Marker countryM, Marker earthquakeM){
		if(countryM.getClass() == MultiMarker.class){
			for(Marker m : ((MultiMarker) countryM).getMarkers()){
				if(((AbstractShapeMarker)m).isInsideByLocation(earthquakeM.getLocation())){
					earthquakeM.setProperty("name", countryM.getProperty("name"));
					return true;
				}
			}
		}
		else if(((AbstractShapeMarker)countryM).isInsideByLocation(earthquakeM.getLocation())){
			earthquakeM.setProperty("name", countryM.getProperty("name"));
			return true;
		}
		return false;
	}

	private void addLegend() {
		fill(50, 50, 50);
		strokeWeight(0.0f);
		rect(10, 50, 180, 300);

		PFont f01 = createFont("Arial Bold", 16);

		String str = "Earthquake key";
		textFont(f01);
		fill(255, 255, 255);
		text(str, 30, 60, 180, 80);

//		fill(255, 0, 0);
//		ellipse(40, 130, 16, 16);
		image(loadImage("data/ui/marker_red.png"), 40-11, 100);

		String stri = "5 + magnitude";
		textFont(f01);
		textSize(14);

		fill(255, 255, 255);
		text(stri, 58, 122, 180, 80);

//		fill(255, 247, 0);
//		ellipse(40, 180, 10, 10);
		image(loadImage("data/ui/marker_yellow.png"), 40-11, 130+20);

		String strin = "(4, 4.9] magnitude";
		textFont(f01);
		textSize(14);
		fill(255, 255, 255);
		text(strin, 58, 172, 180, 80);

//		fill(0, 191, 255);
//		ellipse(40, 230, 5, 5);
		image(loadImage("data/ui/marker_gray.png"), 40-11, 130+70);

		String string = "4 - magnitude";
		textFont(f01);
		textSize(14);
		fill(255, 255, 255);
		text(string, 58, 222, 180, 80);
		
		fill(255, 0, 0);
		ellipse(42, 270, 15, 15);
		String sss = "Past Hour";
		textFont(f01);
		textSize(14);
		fill(255, 255, 255);
		text(sss, 58, 265, 180, 80);
	}

}
