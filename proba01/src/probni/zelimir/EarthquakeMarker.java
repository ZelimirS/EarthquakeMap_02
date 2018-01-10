package probni.zelimir;

import java.util.List;

import de.fhpotsdam.unfolding.marker.AbstractMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import processing.core.PConstants;
import processing.core.PGraphics;

public class EarthquakeMarker{
	
	public LandMarker landM;
	public OceanMarker oceanM;
	
	public boolean isOnLand;
	
	public EarthquakeMarker(Marker earthquakeM, boolean isOnLand){
		this.isOnLand = isOnLand;
		float mag = (float) earthquakeM.getProperty("magnitude");
		if(this.isOnLand == false){
			if(mag > 4.9f) oceanM = new OceanStrongMarker(earthquakeM);
			else if(mag > 4.0f && mag <= 4.9f) oceanM = new OceanMediumMarker(earthquakeM);
			else if(mag <= 4.0f) oceanM = new OceanWeekMarker(earthquakeM);
		}
		else{
			oceanM = new OceanNothingMarker();
		}
		if(this.isOnLand == true){
			if(mag > 4.9f) landM = new LandStrongMarker(earthquakeM);
			else if(mag > 4.0f && mag <= 4.9f) landM = new LandMediumMarker(earthquakeM);
			else if(mag <= 4.0f) landM = new LandWeekMarker(earthquakeM);
		}
		else{
			landM = new LandNothingMarker();
		}
	}

}
