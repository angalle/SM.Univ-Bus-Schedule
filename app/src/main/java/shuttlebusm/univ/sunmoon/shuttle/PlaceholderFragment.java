package shuttlebusm.univ.sunmoon.shuttle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import shuttlebusm.univ.sunmoon.R;
import shuttlebusm.univ.sunmoon.data.BusAdapter;
import shuttlebusm.univ.sunmoon.data.Shuttle;
import shuttlebusm.univ.sunmoon.data.SplashData;

/**
 * Created by heesun on 2016-03-12.
 */
public class PlaceholderFragment extends Fragment implements View.OnClickListener{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    static int arrIndex=0;
    static int sunindex = 0,satindex = 0;
    private static PlaceholderFragment fragment = null;
    public static boolean[] checkValue ;
    String ColorValue = "#CCCCCC", OtherColorValue = "#FFFFFF";
    public static PlaceholderFragment newInstance(int sectionNumber) {
        checkValue = new boolean[5];
        checkValue[0] = false;
        checkValue[1] = true;
        checkValue[2] = false;
        checkValue[3] = false;
        checkValue[4] = false;
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment = new PlaceholderFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public PlaceholderFragment() {

    }

    TextView t1, t2, t3, t4, t5, t6;
    ArrayList<Shuttle> list;
    static ListView lv;
    BusAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        arrIndex = getArguments().getInt("section_number");
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        t1 = (TextView) v.findViewById(R.id.tv1);
        t2 = (TextView) v.findViewById(R.id.tv2);
        t3 = (TextView) v.findViewById(R.id.tv3);
        t4 = (TextView) v.findViewById(R.id.tv4);
        t5 = (TextView) v.findViewById(R.id.tv5);
        t6 = (TextView) v.findViewById(R.id.tv6);
        lv = (ListView) v.findViewById(R.id.listView1);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter = new BusAdapter(getActivity(),Splash.arrbusList[arrIndex]);
        lv.setAdapter(adapter);

        list = Splash.arrbusList[arrIndex];
        t1.setText(list.get(0).No);
        String[] bus = list.get(0).getB();
        t2.setText(bus[0]);
        t3.setText(bus[1]);
        t4.setText(bus[2]);
        t5.setText(bus[3]);
        t6.setText(bus[4]);

        //정류창 초기값 설정
        t2.setBackgroundColor(Color.parseColor(ColorValue));
        checkValue[0] = true;
        checkValue[1] = false;
        checkValue[2] = false;
        checkValue[3] = false;
        checkValue[4] = false;

        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        t3.setOnClickListener(this);
        t4.setOnClickListener(this);
        t5.setOnClickListener(this);
        t6.setOnClickListener(this);

        return v;
    }

    private Menu menu;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
    }

    boolean sunNotSun = true;
    boolean satNotSat = true;
    static int weekSatSun = 1; //기본 평일 정의
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
            switch (item.getItemId()) {
                // action with ID action_refresh was selected
                // int 1~3 값 1: 평일 2: 토요일 3: 일요일
                case R.id.sunday:
                    arrIndex = MainActivity.mViewPager.getCurrentItem();

                    Log.e("index::::::", arrIndex + "");
                    if(arrIndex == 0 ) {
                        satindex = arrIndex + 7;
                        sunindex = arrIndex + 8;
                    }
                    else if(arrIndex == 1) {
                        satindex = arrIndex + 8;
                        sunindex = arrIndex + 9;
                    }
                        if(arrIndex == 0 || arrIndex == 1) {
                            if (weekSatSun != 3) {
                                setweekNotWeekArr(sunindex, "일요일");
                                menu.getItem(1).setIcon(
                                        getResources().getDrawable(R.drawable.week));
                                menu.getItem(0).setIcon(
                                        getResources().getDrawable(R.drawable.saturday));
                                weekSatSun = 3;
                            } else if(weekSatSun != 1 || weekSatSun == 3){
                                setweekNotWeekArr(arrIndex, "평일");
                                weekSatSun = 1;
                                menu.getItem(1).setIcon(
                                        getResources().getDrawable(R.drawable.sunday));
                            }
                        }
                        break;
                    case R.id.saturday:
                        arrIndex = MainActivity.mViewPager.getCurrentItem();
                        Log.e("index::::::", arrIndex + "");
                        if(arrIndex == 0 ) {
                            satindex = arrIndex + 7;
                            sunindex = arrIndex + 8;
                        }
                        else if(arrIndex == 1) {
                            satindex = arrIndex + 8;
                            sunindex = arrIndex + 9;
                        }
                        if(arrIndex == 0 || arrIndex == 1) {
                            if (weekSatSun != 2) {
                                setweekNotWeekArr(satindex, "토요일");
                                menu.getItem(0).setIcon(
                                        getResources().getDrawable(R.drawable.week));
                                menu.getItem(1).setIcon(
                                        getResources().getDrawable(R.drawable.sunday));
                                weekSatSun = 2;
                            } else if(weekSatSun != 1 || weekSatSun == 2) {
                                setweekNotWeekArr(arrIndex, "평일");
                                satNotSat = true;
                                menu.getItem(0).setIcon(
                                        getResources().getDrawable(R.drawable.saturday));
                                weekSatSun = 1;
                            }
                        }
                        break;

                case R.id.infoNotice:
                    AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
                    builder
                            .setMessage(SplashData.notice_con)
                            .setTitle("개발 정보")
                            .show();
                    AlertDialog dialog = builder.create();
                    break;
                default:
                    break;
            }
        return true;
    }

    private void setweekNotWeekArr(int index, String day) {
        adapter = new BusAdapter(getActivity(), Splash.arrbusList[index]);
//        lv = (ListView)MainActivity.mViewPager.getRootView().findViewById(R.id.listView1);
        lv = (ListView)this.getActivity().getSupportFragmentManager().getFragments().get(arrIndex).getView().findViewById(R.id.listView1);
        lv.setAdapter(adapter);
        Log.e("Chage here","");
        Toast.makeText(getActivity().getApplicationContext(), day, Toast.LENGTH_SHORT).show();
    }

    public Fragment getVisibleFragment() {
        for (Fragment fragment: this.getActivity().getSupportFragmentManager().getFragments()) {
            if (fragment.isVisible()) {
                return ((Fragment)fragment);
            }
        }
        return null;
    }

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        arrIndex = MainActivity.mViewPager.getCurrentItem();
        switch (id){
            case R.id.tv1:

                break;
            case R.id.tv2:
                if(arrIndex<5) {
                    t2.setBackgroundColor(Color.parseColor(ColorValue));
                    t3.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t4.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t5.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t6.setBackgroundColor(Color.parseColor(OtherColorValue));
                    checkValue[0] = true;
                    checkValue[1] = false;
                    checkValue[2] = false;
                    checkValue[3] = false;
                    checkValue[4] = false;
                }
                break;
            case R.id.tv3://
                    t2.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t3.setBackgroundColor(Color.parseColor(ColorValue));
                    t4.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t5.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t6.setBackgroundColor(Color.parseColor(OtherColorValue));
                checkValue[0] = false;
                checkValue[1] = true;
                checkValue[2] = false;
                checkValue[3] = false;
                checkValue[4] = false;
                break;
            case R.id.tv4:
                if(arrIndex < 5 && arrIndex != 1) {
                    t2.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t3.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t4.setBackgroundColor(Color.parseColor(ColorValue));
                    t5.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t6.setBackgroundColor(Color.parseColor(OtherColorValue));
                    checkValue[0] = false;
                    checkValue[1] = false;
                    checkValue[2] = true;
                    checkValue[3] = false;
                    checkValue[4] = false;
                }
                break;
            case R.id.tv5://
                if(arrIndex != 1) {
                    t2.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t3.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t4.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t5.setBackgroundColor(Color.parseColor(ColorValue));
                    t6.setBackgroundColor(Color.parseColor(OtherColorValue));
                    checkValue[0] = false;
                    checkValue[1] = false;
                    checkValue[2] = false;
                    checkValue[3] = true;
                    checkValue[4] = false;
                }
                break;
            case R.id.tv6:
                if(arrIndex<5) {
                    t2.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t3.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t4.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t5.setBackgroundColor(Color.parseColor(OtherColorValue));
                    t6.setBackgroundColor(Color.parseColor(ColorValue));
                    checkValue[0] = false;
                    checkValue[1] = false;
                    checkValue[2] = false;
                    checkValue[3] = false;
                    checkValue[4] = true;
                }
                break;
        }
    }
}
