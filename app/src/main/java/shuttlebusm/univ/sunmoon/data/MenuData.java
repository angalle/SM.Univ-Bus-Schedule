package shuttlebusm.univ.sunmoon.data;

import android.graphics.drawable.Drawable;
import android.view.Menu;

public class MenuData {
	
	public static boolean sunNotSun ;
	public static boolean satNotSat ;
	
	public boolean isSunNotSun() {
		return sunNotSun;
	}

	public void setSunNotSun(boolean sunNotSun) {
		this.sunNotSun = sunNotSun;
	}

	public boolean isSatNotSat() {
		return satNotSat;
	}

	public void setSatNotSat(boolean satNotSat) {
		this.satNotSat = satNotSat;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getDatOftheWeek() {
		return datOftheWeek;
	}

	public void setDatOftheWeek(String datOftheWeek) {
		this.datOftheWeek = datOftheWeek;
	}

	public int getSplashDataIndex() {
		return splashDataIndex;
	}

	public void setSplashDataIndex(int splashDataIndex) {
		this.splashDataIndex = splashDataIndex;
	}

	public Drawable getImg() {
		return img;
	}

	public void setImg(Drawable img) {
		this.img = img;
	}

	Menu menu;
	String datOftheWeek = "";
	int splashDataIndex ;
	Drawable img ;
	
	public MenuData(Menu menu, String datOftheWeek, int splashDataIndex, Drawable img) {
		this.menu = menu;
		this.datOftheWeek = datOftheWeek;
		this.splashDataIndex = splashDataIndex;
		this.img = img;
	}
	
	public MenuData() {

	}

}
