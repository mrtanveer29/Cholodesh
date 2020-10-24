/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.olivine.cholodesh.R;

import java.util.ArrayList;
import java.util.List;

import adapters.BookingListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import userDefinder.Booking;
import userDefinder.PackageReservation;

/**
 * Created by Olivine on 6/22/2017.
 */

public class BookingFragment extends Fragment {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public BookingFragment() {
    }

    public static BookingFragment newInstance() {
        BookingFragment bookingFragment=new BookingFragment();
        return bookingFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_booking_container,container,false);

        ReservedHotelFragment reservedPackageFragment= ReservedHotelFragment.newInstance(1);
        ReservedHotelFragment reservedHotelFragment= ReservedHotelFragment.newInstance(0);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(reservedHotelFragment);
        mSectionsPagerAdapter.addFragment(reservedPackageFragment);


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        return view;
    }



    public static class ReservedHotelFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private List<Object> bookings=new ArrayList<>();
        Realm realm;
        @BindView(R.id.bookinList)
        RecyclerView bookinList;
        public ReservedHotelFragment() {
        }

        public static BookingFragment.ReservedHotelFragment newInstance(int sectionNumber) {
            BookingFragment.ReservedHotelFragment fragment = new BookingFragment.ReservedHotelFragment();
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
