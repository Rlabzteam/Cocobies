package com.first.cocobies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Production.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Production#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Production extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    CardView c1,c2,c3;

    boolean connected = false;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Production() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Production.
     */
    // TODO: Rename and change types and number of parameters
    public static Production newInstance(String param1, String param2) {
        Production fragment = new Production();
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
        View view= inflater.inflate(R.layout.fragment_production, container, false);
        ((Homepage) getActivity())
                .setActionBarTitle("Production process");

//        RecyclerView rv =(RecyclerView)view.findViewById(R.id.rvproduction);
//        rv.setHasFixedSize(true);
//        initializeData();
//        rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
//        MachineryAdapter dashAdapter=new MachineryAdapter(data);
//        rv.setAdapter(dashAdapter);

        connected = true;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                //we are connected to a network
                connected = true;
            } else
                connected = false;
        }
        catch(Exception e1)
        {
            connected = false;
        }
        c1=(CardView)view.findViewById(R.id.card_valueaddproducts);
        c2=(CardView)view.findViewById(R.id.card_shellbasedproducts);
        c3=(CardView)view.findViewById(R.id.card_huskbasedproducts);

        Animation animation1 =
                AnimationUtils.loadAnimation(getActivity(), R.anim.updown);
        c1.startAnimation(animation1);
        c2.startAnimation(animation1);
        c3.startAnimation(animation1);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (haveNetwork() == true) {



                    Intent i=new Intent(getContext(),Itembycategory.class);
                    i.putExtra("Production_ID", "1");
                    i.putExtra("call_fragment", "process");
                    startActivity(i);
                }
                else {
                    Toast.makeText(getActivity(), "No Internet connection", Toast.LENGTH_LONG).show();
                }



            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (haveNetwork() == true) {
                    Intent i = new Intent(getContext(), Itembycategory.class);
                    i.putExtra("Production_ID", "2");
                    i.putExtra("call_fragment", "process");
                    startActivity(i);
                }
                else {
                    Toast.makeText(getActivity(), "No Internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(getContext(),Itembycategory.class);
//                i.putExtra("Production_ID", "3");
//                i.putExtra("call_fragment", "process");
//                startActivity(i);
            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.pullToRefreshproduction);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                initializeData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
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

    private boolean haveNetwork(){
        boolean have_wifi=false;
        boolean have_MobileData=false;


        ConnectivityManager connectivityManager = (ConnectivityManager)this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfos=connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info:networkInfos)
        {

            if(info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected())
                    have_wifi=true;
            if(info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected())
                    have_MobileData=true;
        }

        return  have_MobileData||have_wifi;



//    return connected;
//
    }
}
