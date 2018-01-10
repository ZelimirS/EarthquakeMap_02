package com.zelimir.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PApplet;
import processing.data.XML;

public class ZelimirParser {

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static List<Feature> loadFeaturesFromRSS(PApplet p, String fileName) {
		List<Feature> features = new ArrayList<>();

		XML xml = p.loadXML(fileName);
		XML[] entries = xml.getChildren("entry");
		PointFeature locFeature;

		for (int i = 0; i < entries.length; i++) {

			Location location = locationFromEntry(entries[i]);

			if (location != null) {
				locFeature = new PointFeature(location);
				features.add(locFeature);
			} else {
				continue;
			}

			String title = getStringValue(entries[i], "title");
			if (title != null) {
				locFeature.putProperty("title", title);
				locFeature.putProperty("magnitude", Float.parseFloat(title.substring(2, 5)));
			}

			float depthValue = getFloatValue(entries[i], "georss:elev");
			int interVal = (int) depthValue/100;
			depthValue = (float) interVal/10;
			locFeature.putProperty("depth", Math.abs(depthValue));
			
			XML[] categoriesXML = entries[i].getChildren("category");
			for(int j = 0; j < categoriesXML.length; j++){
				String label = categoriesXML[j].getString("label");
				if(label.equals("Age")){
					String ageStr = categoriesXML[j].getString("term");
					locFeature.putProperty("age", ageStr);
				}
			}

		}

		return features;
	}

	private static Location locationFromEntry(XML entry) {
		Location location = null;
		XML xmlPoint = entry.getChild("georss:point");
		if (xmlPoint != null && xmlPoint.getContent() != null) {
			String strPoint = xmlPoint.getContent("georss:point");
			String[] latLon = strPoint.split(" ");
			float lat = Float.valueOf(latLon[0]);
			float lon = Float.valueOf(latLon[1]);
			location = new Location(lat, lon);
		}
		return location;
	}

	private static String getStringValue(XML entry, String tagName) {
		String strTitle = null;
		XML xmlTitle = entry.getChild(tagName);
		if (xmlTitle != null && xmlTitle.getContent() != null) {
			strTitle = xmlTitle.getContent();
		}
		return strTitle;
	}

	private static float getFloatValue(XML entry, String tagName) {
		return Float.parseFloat(getStringValue(entry, tagName));
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static Map<String, Float> loadStringAndFloatFromCSV(PApplet p, String fileName) {

		Map<String, Float> lifeExpectancy = new HashMap<>();
		String[] rows = p.loadStrings(fileName);
		for (String row : rows) {
			String[] columns = row.split(",");
			float value;
			if (columns[5].equals("..")) {
				value = 0.0f;
			} else {
				value = Float.parseFloat(columns[5]);
			}
			lifeExpectancy.put(columns[4], value);
		}
		return lifeExpectancy;
	}

}
