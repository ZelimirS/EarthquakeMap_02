package zelimir_02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.zelimir.parser.ZelimirParser;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class ZelimirsEarthquakeMap extends PApplet {
	
	private static final long serialVersionUID = 1L;
	
	UnfoldingMap map;
	private String rssQuakeUrl = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_day.atom";
	private String cityFile = "data/ui/city-data.json";
	private String countryFile = "data/ui/countries.geo.json";
	
	List<CommonMarker> earthquakeMarkers = new ArrayList<>();
	List<CommonMarker> cityMarkers = new ArrayList<>();
	List<Marker> countryMarkers = new ArrayList<>();
	
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;

	
	public void setup(){
		
		size(1200, 600, OPENGL);

		map = new UnfoldingMap(this, 200, 0, 1000, 600, new  Microsoft.RoadProvider());
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		
		List<Feature> earthquakeFeatures = ZelimirParser.loadFeaturesFromRSS(this, rssQuakeUrl);
		List<Feature> cityFeatures = GeoJSONReader.loadData(this, cityFile);
		List<Feature> countryFeatures = GeoJSONReader.loadData(this, countryFile);
		
//		earthquakeMarkers = MapUtils.createSimpleMarkers(earthquakeFeatures);
//		cityMarkers = MapUtils.createSimpleMarkers(cityFeatures);
		countryMarkers = MapUtils.createSimpleMarkers(countryFeatures);
		
		
		for(Feature f : cityFeatures){
			CommonMarker m = new CityMarker(((PointFeature)f).getLocation(), f.getProperties());
			cityMarkers.add(m);
			map.addMarker(m);
		}
		for(Feature f : earthquakeFeatures){
			CommonMarker m = new EarthquakeMarker(((PointFeature)f).getLocation(), f.getProperties(), isOnLand(f), (float)(f.getProperty("magnitude")));
			earthquakeMarkers.add(m);
			map.addMarker(m);
		}
		
		//////////////////////////////// print *************************************
		printQuakesAndCities(earthquakeMarkers);
		printQuakesAndCities(cityMarkers);
		Collections.sort(cityMarkers, new CityMarker());
		System.out.println("****************************************************************");
		for(CommonMarker cmn : cityMarkers){
			System.out.println(cmn.getProperties().toString());
		}
				
	}
	
	public void draw(){
		map.draw();
	}
	
	private boolean isOnLand(Feature f){
		if(isInCountry(f, countryMarkers)==true) return true;
		return false;
	}
	private boolean isInCountry(Feature f, List<Marker> countryMarkers){
		for(Marker country : countryMarkers){
			if(country.getClass() == MultiMarker.class){
				for(Marker m : ((MultiMarker) country).getMarkers()){
					if(((AbstractShapeMarker)m).isInsideByLocation(((PointFeature) f).getLocation())){
						return true;
					}
				}
			}
			else{
				if(((AbstractShapeMarker)country).isInsideByLocation(((PointFeature) f).getLocation())){
					return true;
				}	
			}
		}
		return false;
	}
	
	public void mouseMoved(){
		if(lastSelected != null){
			lastSelected.setSelected(false);
			lastSelected = null;
		}
		selectIfHover(earthquakeMarkers);
		selectIfHover(cityMarkers);
	}
	public void selectIfHover(List<CommonMarker> markersList){
		if(lastSelected != null){
			return;
		}
		for(CommonMarker mrk : markersList){
			if(((CommonMarker)mrk).isInside(map, mouseX, mouseY)){
				mrk.setSelected(true);
				lastSelected = mrk;		
				return;
			}
		}
	}
	
	@Override
	public void mouseClicked(){
		if(lastClicked != null){
			unhideMarkers();
			lastClicked = null;
		}
		else if(lastClicked == null){
			checkEarthquakesClick();
			if(lastClicked == null){
				checkCitiesClick();
			}
		}		
	}

	public void checkEarthquakesClick(){
		if(lastClicked != null){
			return;
		}
		for(CommonMarker cm : earthquakeMarkers){
			if(cm.isVisible() && cm.isInside(map, mouseX, mouseY)){
				lastClicked = cm;
				for(CommonMarker cmn : earthquakeMarkers){
					if(cmn != lastClicked){
						cmn.setVisible(false);
					}
				}
				for(CommonMarker cmn : cityMarkers){
					if(cmn.getDistanceTo(lastClicked.getLocation()) > ((EarthquakeMarker) lastClicked).threatCircle()){
						cmn.setVisible(false);
					}
				}
				
				return;
			}
		}		
	}

	public void checkCitiesClick(){
		if(lastClicked != null) return;
		for(CommonMarker cm : cityMarkers){
			if(cm.isVisible() && cm.isInside(map, mouseX, mouseY)){
				lastClicked = cm;
				for(CommonMarker cmr : cityMarkers){
					if(cmr != lastClicked){
						cmr.setVisible(false);;
					}
				}
				for(CommonMarker cmm : earthquakeMarkers){
					if(cmm.getDistanceTo(lastClicked.getLocation()) > ((EarthquakeMarker) cmm).threatCircle()){
						cmm.setVisible(false);;
					}
				}
				return;
			}
		}
	}
	
	private void unhideMarkers(){
		for(CommonMarker cm : earthquakeMarkers){
			cm.setVisible(true);
		}
		for(CommonMarker cm : cityMarkers){
			cm.setVisible(true);
		}
	}
	
	public void printQuakesAndCities(List<CommonMarker> lst){
		Collections.sort(lst);
		for(CommonMarker cmn : lst){
			System.out.println(cmn.getProperties().toString());
		}
	}

}
