package zelimir_02;

import java.util.HashMap;

import de.fhpotsdam.unfolding.geo.Location;
import zelimir_02.LandMarker;
import processing.core.PGraphics;

class EarthquakeMarker extends CommonMarker {
	
	private boolean isLand;
	private float magnitude;
	private Location loc;
	private HashMap<String, Object> properties;
	
	private LandMarker lm = null;
	private OceanMarker om = null;

	public EarthquakeMarker(Location loc, HashMap<String, Object> properties, boolean isLand, float magnitude){
		super(loc, properties);
		this.isLand = isLand;
		this.magnitude = magnitude;
		this.loc = loc;
		this.properties = properties;
	}

	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		if(isLand == true){
			if(magnitude <= 4.0f){
				lm = new LandWeekMarker(loc, properties);
				lm.drawWeekLandEarthquake(pg, x, y);
			}
			else if( magnitude > 4.0f && magnitude <= 4.9f){
				lm = new LandMediumMarker(loc, properties);
				lm.drawMediumLandEarthquake(pg, x, y);
			}
			else if(magnitude > 4.9f){
				lm = new LandStrongMarker(loc, properties);
				lm.drawStrongLandEarthquake(pg, x, y);
			}
		}
		if(isLand == false){
			if(magnitude <= 4.0f){
				om = new OceanWeekMarker(loc, properties);
				om.drawWeekOceanEarthquake(pg, x, y);
			}
			else if( magnitude > 4.0f && magnitude <= 4.9f){
				om = new OceanMediumMarker(loc, properties);
				om.drawMediumOceanEarthquake(pg, x, y);			}
			else if(magnitude > 4.9f){
				om = new OceanStrongMarker(loc, properties);
				om.drawStrongOceanEarthquake(pg, x, y);
			}
		}
		if(selected){
			showTitle(pg, x, y);
		}
		
		showPastHour(pg, x, y);
	}

	public boolean isLand() {
		return isLand;
	}

	public float getMagnitude() {
		return magnitude;
	}

	public Location getLoc() {
		return loc;
	}

	public LandMarker getLm() {
		return lm;
	}
	public OceanMarker getOm(){
		return om;
	}

	@Override
	protected boolean isInside(float arg0, float arg1, float arg2, float arg3) {
		if(lm != null){ return lm.isInside(arg0, arg1,  arg2, arg3); }
		else if(om != null){ return om.isInside(arg0, arg1,  arg2, arg3); }
		return false;
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		if(lm != null){
			lm.showTitle(pg, x, y);
		}
		else if(om != null){
			om.showTitle(pg, x, y);	
		}
	}
	
	public double threatCircle() {	
		double miles = 20.0f * Math.pow(1.8, 2*getMagnitude()-5);
		double km = (miles * 1.6f);
		return km;
	}
	
	public void showPastHour(PGraphics pg, float x, float y){
		String quakeAge = properties.get("age").toString();
		if(quakeAge.equals("Past Hour")){
			pg.noFill();
			pg.strokeWeight(1);
			pg.rect(x-8, y-26, 16, 26);
		}	
	}

	@Override
	public int compareTo(CommonMarker arg0) {
		if(this.getMagnitude() < ((EarthquakeMarker) arg0).getMagnitude()) return 1;
		else if(this.getMagnitude() > ((EarthquakeMarker) arg0).getMagnitude()) return -1;
		else return 0;
	}

	@Override
	public int compare(CommonMarker o1, CommonMarker o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
