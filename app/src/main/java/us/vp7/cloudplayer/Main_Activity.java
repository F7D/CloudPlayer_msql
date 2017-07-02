package us.vp7.cloudplayer;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farzad-msi7 on 5/21/2017.
 */
public class Main_Activity extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewpager;
    SQLiteDatabase myDB;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        myDB=openOrCreateDatabase("database",MODE_PRIVATE,null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS music (id INT(3)," +
                "name TEXT," +
                "artist TEXT," +
                "picture TEXT," +
                "link TEXT);");

        start();



    }

    private void start() {
        viewpager= (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(PagerFragment.newInstant(1),"Playlist");
        adapter.addFragment(PagerFragment.newInstant(2),"My Favorites");
        viewpager.setAdapter(adapter);
        tablayout= (TabLayout) findViewById(R.id.tablayout);
        tablayout.setupWithViewPager(viewpager);
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList=new ArrayList<>();
        private final List<String> mFragmentTitleList=new ArrayList<>();


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }
}
