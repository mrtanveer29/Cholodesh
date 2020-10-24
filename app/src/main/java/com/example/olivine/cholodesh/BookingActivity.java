/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.olivine.cholodesh;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapters.BookingListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import model.HotelBooking;
import model.HotelInclusion;
import userDefinder.Booking;
import userDefinder.PackageReservation;

import static android.R.attr.defaultValue;

public class BookingActivity extends AppCompatActivity {

    /**
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive,
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ReservedHotelFragment reservedPackageFragment=ReservedHotelFragment.newInstance(1);
        ReservedHotelFragment reservedHotelFragment=ReservedHotelFragment.newInstance(0);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(reservedHotelFragment);
        mSectionsPagerAdapter.addFragment(reservedPackageFragment);


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_booking_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ReservedHotelFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private List<Object> bookings=new ArrayList<>();
        Realm realm;
        @BindView(R.id.bookinList)
        RecyclerView bookinList;
        public ReservedHotelFragment() {
        }

        public static ReservedHotelFragment newInstance(int sectionNumber) {
            ReservedHotelFragment fragment = new ReservedHotelFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_booking, container, false);
            ButterKnife.bind(this,rootView);

            BookingListAdapter bookingListAdapter=new BookingListAdapter(getActivity(),bookings );
            bookinList.setAdapter(bookingListAdapter);

            return rootView;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            Realm.init(getActivity());
            realm=Realm.getDefaultInstance();

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                int type = bundle.getInt(ARG_SECTION_NUMBER, 0);
                if(type==0){
                    RealmResults<Booking> bookings=realm.where(Booking.class).findAll();
                    List <Booking> hotelBooking=realm.copyFromRealm(bookings);
                    this.bookings.clear();
                    this.bookings.addAll(hotelBooking);
                }else if(type==1){
                    RealmResults<PackageReservation> packageReservations=realm.where(PackageReservation.class).findAll();
                    List <PackageReservation> hotelBooking=realm.copyFromRealm(packageReservations);
                    this.bookings.clear();
                    this.bookings.addAll(hotelBooking);
                }

            }


            super.onCreate(savedInstanceState);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments=new ArrayList<>();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Hotel Bookings";
                case 1:
                    return "Package Booking";
            }
            return null;
        }
        public void addFragment(Fragment fragment){
            fragments.add(fragment);
        }

    }
}
