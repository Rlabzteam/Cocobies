package com.first.cocobies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Research.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Research#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Research extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Research() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Research.
     */
    // TODO: Rename and change types and number of parameters
    public static Research newInstance(String param1, String param2) {
        Research fragment = new Research();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_research, container, false);
        ((Homepage) getActivity())
                .setActionBarTitle("Innovative:");

        tabLayout=(TabLayout)view.findViewById(R.id.tabLayout);
        viewPager=(ViewPager)view.findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Tools"));
        tabLayout.addTab(tabLayout.newTab().setText("Machine"));
        tabLayout.addTab(tabLayout.newTab().setText("Devices"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



        final MyAdapter adapter = new MyAdapter(getContext(),getFragmentManager() , tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {




            }
        });


        return view;
    }
    public class MyAdapter extends FragmentPagerAdapter {

        private Context myContext;
        int totalTabs;



        public MyAdapter(Context context, FragmentManager fragmentManager, int tabCount) {
            super(fragmentManager);
            myContext = context;
            this.totalTabs = tabCount;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            final AppCompatActivity activity = (AppCompatActivity) getContext();
            switch (position) {
                case 0:
                   Fragment homeFragment=Research_innovation.newInstance("1","");
                    FragmentManager fm =activity.getSupportFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    ft.addToBackStack(null);
                    ft.attach(homeFragment);
                    ft.commit();

                    return homeFragment;
                case 1:
                    Fragment homeFragment1=Research_innovation.newInstance("2","");
                    FragmentManager fm1 =activity.getSupportFragmentManager();
                    FragmentTransaction ft1=fm1.beginTransaction();
                    ft1.addToBackStack(null);
                    ft1.attach(homeFragment1);
                    ft1.commit();
                    return homeFragment1;
                case 2:
                    Fragment homeFragment2=Research_innovation.newInstance("3","");
                    FragmentManager fm2 =activity.getSupportFragmentManager();
                    FragmentTransaction ft2=fm2.beginTransaction();
                    ft2.addToBackStack(null);
                    ft2.attach(homeFragment2);
                    ft2.commit();
                    return homeFragment2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return totalTabs;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
//        else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
