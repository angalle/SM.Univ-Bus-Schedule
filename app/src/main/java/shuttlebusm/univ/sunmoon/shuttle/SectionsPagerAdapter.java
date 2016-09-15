package shuttlebusm.univ.sunmoon.shuttle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by heesun on 2016-03-12.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    int test =0;
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "천안 아산역";
            case 1:
                return "터미널";
            case 2:
                return "온양역";
            case 3:
                return "천안캠퍼스";
            case 4:
                return "아산캠->아산역(시내버스)";
            case 5:
                return "아산역<-아산캠(시내버스)";
            case 6:
                return "온양역(시내버스)";
        }
        return null;
    }
}
