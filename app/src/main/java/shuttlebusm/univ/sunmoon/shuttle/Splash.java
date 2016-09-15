package shuttlebusm.univ.sunmoon.shuttle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.ArrayList;

import shuttlebusm.univ.sunmoon.R;
import shuttlebusm.univ.sunmoon.connect.Connection;
import shuttlebusm.univ.sunmoon.data.Shuttle;
import shuttlebusm.univ.sunmoon.data.SplashData;
import shuttlebusm.univ.sunmoon.data.SplashData2;
import shuttlebusm.univ.sunmoon.gcm.RegistrationIntentService;

public class Splash extends Activity {
	//
	static String information = "";
	static ArrayList<Shuttle>[] arrbusList = new ArrayList[SplashData.busUrl.length];
	String ad = "ca-app-pub-8944137857067935/7777639600";
	private String SENDER_ID="gcmpro-1181";
	private GoogleCloudMessaging gcm;
	boolean test =true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		internetSetting();



		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
		anim.reset();
		LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
		l.clearAnimation();
		l.startAnimation(anim);

		anim = AnimationUtils.loadAnimation(this, R.anim.translate);
		anim.reset();
		ImageView iv = (ImageView) findViewById(R.id.splash);
		iv.clearAnimation();
		iv.startAnimation(anim);

		Thread th = new Thread(){
			@Override
			public void run() {
				try {
					Intent next = new Intent(Splash.this, MainActivity.class);
					startActivity(next);
					finish();
				} catch (Exception e) {
					e.printStackTrace();
				}
				super.run();
			}
		};
		th.start();
		DataSettingT();
	}

	private void DataSettingT()  {
		int i=0;
		for (String url2 : SplashData2.busUrl) {
			arrbusList[i] = new ArrayList<Shuttle>();
			Connection getBusArray = new Connection(url2);
			if (i != SplashData2.busUrl.length - 1) {
				getBusArray.HttpConnect();
				arrbusList[i] = getBusArray.getBusArray();
				if(arrbusList[i].get(0).getNo()==null){
					break;
				}
			} else {
				SplashData2.setNotice_con(getBusArray.HttpInfoConnect());
			}
			i++;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return false;
		return super.onKeyDown(keyCode, event);
	}

	private void internetSetting() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
}
