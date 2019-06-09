package com.tebook.activity;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tebook.fragment.bookshelf_fragment;
import com.tebook.fragment.bookcity_fragment;
import com.tebook.fragment.mine_fragment;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;
import com.tebook.R;
import com.tebook.fragment.bookshelf_fragment;

public class MainActivity extends FragmentActivity {

    private IndicatorViewPager indicatorViewPager;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_main);
        SViewPager viewPager = (SViewPager) findViewById(R.id.tabmain_viewPager);
        Indicator indicator = (Indicator) findViewById(R.id.tabmain_indicator);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] tabNames = {"书架", "书城", "我的"};
        private int[] tabIcons = {
                R.drawable.maintab_1_selector,
                R.drawable.maintab_2_selector,
                R.drawable.maintab_3_selector,
        };
        private LayoutInflater inflater;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }

        public int getCount() {
            return tabNames.length;
        }

        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = (TextView) inflater.inflate(R.layout.tab_main,
                        container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(tabNames[position]);
            Drawable d = getResources().getDrawable(tabIcons[position]);


            d.setBounds(0, 0, 46, 46);
            textView.setCompoundDrawables(null, d, null, null);
            return textView;
        }

        public Fragment getFragmentForPage(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new bookshelf_fragment();
                    break;
                case 1:
                    fragment = new bookcity_fragment();
                    break;
                case 2:
                    fragment = new mine_fragment();
                    break;
                default:
                    break;
            }
            return fragment;
        }
    }
}
