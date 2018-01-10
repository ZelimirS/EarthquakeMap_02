package zelimir_02;

import java.util.HashMap;

import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class CityMarker extends CommonMarker{
	
	PApplet pa = new PApplet();
	PImage image = pa.loadImage("data/data/cityMarker.png");
	HashMap<String, Object> properties;
	
	public CityMarker(){}
	public CityMarker(Location loc, HashMap<java.lang.String,java.lang.Object> properties){
		super(loc, properties);
		this.properties = properties;
	}

	@Override
	protected boolean isInside(float checkX, float checkY, float x, float y) {
		x = x - image.width/2;
		y = y - image.height;
		return checkX > x && checkX < image.width + x && checkY > y && checkY < image.height + y;
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.image(this.image, x, y-image.height/2);
		if(selected) showTitle(pg, x, y);
	}
	
	@Override
	public void showTitle(PGraphics pg, float x, float y){
		String nameAndPopulation = getCity() + ",  " + getPopulation();
		pg.noStroke();
		pg.fill(70,130,180);
		pg.rect(x, y, nameAndPopulation.length()*9, 18);
		pg.textSize(16);
		pg.fill(255,255,255);
		pg.text(nameAndPopulation, x, y+15);
	}
	
	public String getCity(){
		return getStringProperty("name");
	}
	
	public String getPopulation(){
		return getStringProperty("population");
	}
	public float getFloatPopulation(){
		return Float.parseFloat(getProperty("population").toString());
	}
	
	public String getCountry(){
		return getStringProperty("country");
	}

	@Override
	public int compareTo(CommonMarker o) {
		return this.getCity().compareTo(((CityMarker)o).getCity());
	}

	@Override
	public int compare(CommonMarker arg0, CommonMarker arg1) {
		if(((CityMarker)arg0).getFloatPopulation() > ((CityMarker)arg1).getFloatPopulation()) return 1;
		else if(((CityMarker)arg0).getFloatPopulation() < ((CityMarker)arg1).getFloatPopulation()) return -1;
		else return 0;
	}
	
}
