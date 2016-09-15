package shuttlebusm.univ.sunmoon.shuttle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.text.SimpleDateFormat;
import java.util.Date;

import shuttlebusm.univ.sunmoon.R;
import shuttlebusm.univ.sunmoon.gcm.QuickstartPreferences;
import shuttlebusm.univ.sunmoon.gcm.RegistrationIntentService;


public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    public static ViewPager mViewPager;
    int weekset =0;
    int indexPage = 0;
    private BroadcastReceiver mRegistrationBroadcastReceiver = null;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    ListView lv = null;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setMinimumWidth(50);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#CC3366"));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{


                int currentIndex =  mViewPager.getCurrentItem();
                //현재 Fragment의 listview를 가져오는 부분
                lv = ((ListView)getSupportFragmentManager().getFragments().get(currentIndex).getView().findViewById(R.id.listView1));
                int index = 0;       // 리스트뷰로 반환할 index 값
                int currentTime = 0; //현재시간
                int campareTime = 0; //비교시간
                String noti = "";    //해당하는 메세지 담아둘 변수
                int size = Splash.arrbusList[currentIndex].size(); //  현재 시간표의 총 사이즈를 반환

                // 현재 시간의 포멧을 지정한다..
                Date d = new Date();
                SimpleDateFormat dayTime = new SimpleDateFormat("HH:mm");
                String str = dayTime.format(d);
                currentTime = Integer.parseInt(str.replace(":", ""));
                //////////////////////////////////////////////////////////

                //주말 체크 후 페이지 넘길때의 버그 처리
                //페이지 인덱스 저장 변수와
                //현재 페이지 인덱스 변수를 비교하여
                //주말을 체크하고 페이지를 넘기면 자동으로 평일으로 세팅한다.
                if(indexPage != mViewPager.getCurrentItem())
                    PlaceholderFragment.weekSatSun = 1;
                indexPage = mViewPager.getCurrentItem();

                //주말일때의 경우의 수를 설정한다.
                //for문의 최적화를 위해 'size'변수를 받아오고
                //주말 변수를 받아오기 위해서 'weekset' 변수에 저장을 하게 된다,
                if(PlaceholderFragment.weekSatSun == 3) { // 일요일 index
                    weekset = PlaceholderFragment.sunindex;
                    size = Splash.arrbusList[weekset].size();
                    currentIndex = weekset;
                }else if(PlaceholderFragment.weekSatSun == 2) { //토요일 index
                    weekset = PlaceholderFragment.satindex;
                    size = Splash.arrbusList[weekset].size();
                    currentIndex = weekset;
                }else if(PlaceholderFragment.weekSatSun == 1){
                    currentIndex = mViewPager.getCurrentItem();
                    size = Splash.arrbusList[currentIndex].size();
                }

                for(int i=1;i < size;i++) { // 전체 크기
                    String compareStr = "";
                    int spot =0;
                    for(int j=0; j<PlaceholderFragment.checkValue.length;j++) {
                        if (PlaceholderFragment.checkValue[j] == true)
                            spot = j;
                    }
                    compareStr = Splash.arrbusList[currentIndex].get(i).b[spot];

//
//                    if(currentIndex >= 4 || currentIndex == 1) {
//                        compareStr = Splash.arrbusList[currentIndex].get(i).b[1];
//                        if (currentIndex == 1) {                             noti = "터미널";
//                            if (weekset == 9 || weekset == 10)             compareStr = Splash.arrbusList[weekset].get(i).b[1];
//                        }
//                        else if (currentIndex == 4)                          noti = "아산역";
//                        else if (currentIndex == 5 || currentIndex == 6)     noti = "선문대";
//                    }else {
//                        compareStr = Splash.arrbusList[currentIndex].get(i).b[2];
//                        if (currentIndex == 0) {                             noti = "천안역";
//                            if (weekset == 7 || weekset == 8)              compareStr = Splash.arrbusList[weekset].get(i).b[2];
//                        }
//                        else if (currentIndex == 2)                         noti = "온양터미널";
//                        else if (currentIndex == 3)                         noti = "천안캠퍼스";
//                    }
//



                    if(compareStr.contentEquals("*"))                                continue;
                    else if(compareStr.equals("10분")){
                        compareStr = Splash.arrbusList[currentIndex].get(i).b[0];
                        compareStr = compareStr.replace("(금Ⅹ)", "").replace(":", "");
                        int test = Integer.parseInt(compareStr);
                        test = test+10;
                        compareStr = String.valueOf(test);
                    } else if(compareStr.equals("15분")){
                        compareStr = Splash.arrbusList[currentIndex].get(i).b[0];
                        compareStr = compareStr.replace("(금Ⅹ)", "").replace(":", "");
                        int test = Integer.parseInt(compareStr);
                        test = test+15;
                        compareStr = String.valueOf(test);
                    }
                    //예외글자 처리 부분.
                    compareStr = compareStr.replace("(금Ⅹ)", "").replace(":", "");
                    if(compareStr.contentEquals(""))                                continue;
                    campareTime = Integer.parseInt(compareStr);
                    if(currentTime <= campareTime) {
                        index = i;
                        break;
                    }
                }
//                현재 인덱스(currentIndex)는 잘 받아와 지고 있다.
//                index 비교할때 비교가 제대로 이루어지고 있지 않다.
                Log.e("index ! : ",index+"");
                Log.e("current Index",currentIndex+"");
                Log.e("current Tiem",currentTime+"");
                Log.e("weekset",weekset+"");
                Log.e("weekset",lv.getCount()+"");

                lv.smoothScrollToPositionFromTop(index, 10);

//              lv.getAdapter().getView(index, (LinearLayout) findViewById(R.id.list_row),lv);
//              이 메소드로 뷰를 불러올 수 있다.

                Snackbar.make(view, noti+"가장 빠른 셔틀시간은 =>"+index+"번", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this,"찾을 수 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
            }
        };
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    public Fragment getVisibleFragment() {
        for (Fragment fragment: getSupportFragmentManager().getFragments()) {
            if (fragment.isVisible()) {
                return ((Fragment)fragment);
            }
        }
        return null;
    }


    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("checkService", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbtn, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
        super.onPause();
    }
}
