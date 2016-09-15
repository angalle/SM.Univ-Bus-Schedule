package shuttlebusm.univ.sunmoon.data;

import java.util.ArrayList;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import shuttlebusm.univ.sunmoon.R;


public class BusAdapter extends BaseAdapter{
	LayoutInflater mInfaltor;
	ArrayList<Shuttle> buslist;
	Context con;	
	//int row,col=9;
	LinearLayout.LayoutParams lp ;
	int k = 0;
	String[] busdata;
	private Handler mHandler;
	String color = "#BDBDBD";
	@SuppressWarnings("deprecation")
	public BusAdapter(Context c,ArrayList<Shuttle> s)
	{
		mInfaltor = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		buslist = s;		
		//Log.i("test",""+buslist[5].b1);
		con = c;
		busdata = new String[7];		
		lp = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT,0.2f
		);	
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//Log.i("buslist",""+buslist.length);		
		return buslist.size();
		//return 0;
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		//Log.i("buslist data",buslist[getCount()].toString());
		return getView(index,null,null);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	TextView t1,t2,t3,t4,t5,t6;
	LinearLayout ll;
	@Override
	public View getView(int a, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View v = mInfaltor.inflate(R.layout.buslist_item, arg2, false);

		if(a == 0){return v;}

		t1 = (TextView) v.findViewById(R.id.tv1);
		t2 = (TextView) v.findViewById(R.id.tv2);
		t3 = (TextView) v.findViewById(R.id.tv3);
		t4 = (TextView) v.findViewById(R.id.tv4);
		t5 = (TextView) v.findViewById(R.id.tv5);
		t6 = (TextView) v.findViewById(R.id.tv6);

			busdata[0] = buslist.get(a).getNo();
			String[] bus = buslist.get(a).getB();
			for(int i = 0 ; i  < bus.length ; i++)
				busdata[i+1] = bus[i];
			t1.setText(busdata[0]);

			if(busdata[1].equals("왕복5번")){
				v.setBackgroundColor(Color.parseColor("#BDBDBD"));
				t2.setText(busdata[1]);
			}

			t2.setText(busdata[1]);			
			t3.setText(busdata[2]);
			t4.setText(busdata[3]);
			t5.setText(busdata[4]);
			t6.setText(busdata[5]);
		return v;
	}
}
	
	