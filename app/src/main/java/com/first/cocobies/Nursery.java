package com.first.cocobies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import com.squareup.picasso.Picasso;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static androidx.media.session.MediaButtonReceiver.handleIntent;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Nursery.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Nursery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Nursery extends Fragment {
    RecyclerView rv,rv1,rv2;
    List<alldistrict> alldistricts;
    List<topnursery>topnurseries;
    List<shopbycategory> data;
    SwipeRefreshLayout pullToRefresh;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Nursery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Nursery.
     */
    // TODO: Rename and change types and number of parameters
    public static Nursery newInstance(String param1, String param2) {
        Nursery fragment = new Nursery();
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
        firstexecute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_nursery, container, false);
        ((Homepage) getActivity())
                .setActionBarTitle("Nurseries");
       // handleIntent(getActivity()));
        LinearLayout l1=(LinearLayout)view.findViewById(R.id.lnurseries_search);
        l1.setVisibility(View.GONE);
        LinearLayout l2=(LinearLayout)view.findViewById(R.id.type_of_nurseries);
        l2.setVisibility(View.GONE);
        rv = (RecyclerView) view.findViewById(R.id.rv_district_nursery);
        rv.setHasFixedSize(true);
        rv1 = (RecyclerView) view.findViewById(R.id.rv_top_nursery);
        rv1.setHasFixedSize(true);
        rv2 = (RecyclerView) view.findViewById(R.id.rv_gov_nursery);
        rv2.setHasFixedSize(true);
        pullToRefresh = (SwipeRefreshLayout)view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                firstexecute();
                pullToRefresh.setRefreshing(false);
            }
        });
        return view;
    }

    public class alldistrict{
        String image;
        String id;
        String name;
        String state;

        public String getId() {
            return id;
        }
    }

    public class topnursery{
        String image;
        String id;
        String name;
        String dist;

    }
    private void firstexecute() {
        class districtdata extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost request = new HttpPost();
                    request.setURI(new URI(Urls.District_URL));
                    HttpResponse response = client.execute(request);
                    BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }
                    in.close();
                    String str = sb.toString();
                    final String datastr = str.trim();
//                    Log.d("STRVALUE", "" + str);
                    if(str.length() > 2)
                    {
                        try
                        {
                            JSONArray arr = new JSONArray(datastr);
                            alldistricts = new ArrayList<>();
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);
                                alldistrict s = new alldistrict();
                                s.id=obj.getString("id");
                                s.image=obj.getString("image");
                                //s.link=obj.getString("link");
                                s.name=obj.getString("name");

//                            Log.d("result", obj.getString("quest"));
                                alldistricts.add(s);
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                //@Override
                                public void run() {
                                    rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                                    districtadapter q1=new districtadapter(getContext(),alldistricts);
                                    rv.setAdapter(q1);
                                }
                            });
                        }
                        catch(Exception e)
                        {
                            Log.d("ERROR55",e.toString());
                        }
                    }
                } catch (Exception e) {
                    Log.d("ERROR2", e.toString());
                }
                // TODO: register the new account here.
                return true;
            }
        }
        districtdata dp = new districtdata();
        dp.execute();

        class topnurserydata extends AsyncTask<Void, Void, Boolean>{
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost request = new HttpPost();
                    request.setURI(new URI(Urls.Topnursery_URL));
                    HttpResponse response = client.execute(request);
                    BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }
                    in.close();
                    String str = sb.toString();
                    final String datastr = str.trim();
//                    Log.d("STRVALUE", "" + str);
                    if(str.length() > 2)
                    {
                        try
                        {
                            JSONArray arr = new JSONArray(datastr);
                            topnurseries = new ArrayList<>();
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);
                                topnursery s = new topnursery();
                                s.id=obj.getString("id");
                                s.image=obj.getString("image");
                                s.dist=obj.getString("district");
                                s.name=obj.getString("name");

//                            Log.d("result", obj.getString("quest"));
                                topnurseries.add(s);
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                //@Override
                                public void run() {

                                    rv1.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                                    topnurseryadapter q1=new topnurseryadapter(getContext(),topnurseries);
                                    rv1.setAdapter(q1);
                                }
                            });
                        }
                        catch(Exception e)
                        {
                            Log.d("ERROR55",e.toString());
                        }
                    }
                } catch (Exception e) {
                    Log.d("ERROR2", e.toString());
                }
                // TODO: register the new account here.
                return true;
            }
        }
        topnurserydata tdp = new topnurserydata();
        tdp.execute();

        class govtnurserydata extends AsyncTask<Void, Void, Boolean>{
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost request = new HttpPost();
                    request.setURI(new URI(Urls.govt_nursery_URL));
                    HttpResponse response = client.execute(request);
                    BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }
                    in.close();
                    String str = sb.toString();
                    final String datastr = str.trim();
//                    Log.d("STRVALUE", "" + str);
                    if(str.length() > 2)
                    {
                        try
                        {
                            JSONArray arr = new JSONArray(datastr);
                            topnurseries = new ArrayList<>();
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);
                                topnursery s = new topnursery();
                                s.id=obj.getString("id");
                                s.image=obj.getString("image");
                                s.dist=obj.getString("district");
                                s.name=obj.getString("name");

//                            Log.d("result", obj.getString("quest"));
                                topnurseries.add(s);
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                //@Override
                                public void run() {

                                    rv2.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                                    topnurseryadapter q1=new topnurseryadapter(getContext(),topnurseries);
                                    rv2.setAdapter(q1);
                                }
                            });
                        }
                        catch(Exception e)
                        {
                            Log.d("ERROR55",e.toString());
                        }
                    }
                } catch (Exception e) {
                    Log.d("ERROR2", e.toString());
                }
                // TODO: register the new account here.
                return true;
            }
        }
        govtnurserydata govtdp = new govtnurserydata();
        govtdp.execute();


    }

    public class topnurseryadapter extends RecyclerView.Adapter<topnurseryadapter.ViewHolder>{
        Context context;
        List<topnursery>topnurseries;

        public topnurseryadapter(Context context, List<topnursery> topnurseries) {
            this.context=context;
            this.topnurseries=topnurseries;
        }

        @NonNull
        @Override
        public topnurseryadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.topnursery, parent, false);
            ViewHolder pvh = new ViewHolder(v);
            return  pvh;
        }

        @Override
        public void onBindViewHolder(@NonNull final topnurseryadapter.ViewHolder holder, int position) {

            Picasso.get().load(Urls.ipnursery+topnurseries.get(position).image)
                    .into(holder.m1);
            final String imageString=Urls.ipnursery+topnurseries.get(position).image;
            holder.t1.setText(topnurseries.get(position).name);
            holder.t2.setText(topnurseries.get(position).dist);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getActivity(),Nurseryhome.class);
                    i.putExtra("image_of_nursies",imageString);
                    i.putExtra("name",holder.t1.getText().toString());
                    i.putExtra("district",holder.t2.getText().toString());
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return topnurseries.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView t1,t2,t3;
            ImageView m1;
            CardView cardView;
            public ViewHolder(View itemView) {
                super(itemView);
                t1=(TextView)itemView.findViewById(R.id.topnursery_name);
                t2=(TextView)itemView.findViewById(R.id.topnursery_place);
                m1=(ImageView)itemView.findViewById(R.id.imgtopnursery);
                cardView=(CardView)itemView.findViewById(R.id.ctopnursery);
                Animation animation1 =
                        AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
                cardView.startAnimation(animation1);

            }
        }
    }

    public class districtadapter extends RecyclerView.Adapter<districtadapter.ViewHolder> {
        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;
        List<alldistrict>alldistricts;
        Context context;
        public districtadapter(Context context, List<alldistrict> alldistricts) {
            this.context=context;
            this.alldistricts=alldistricts;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View root=null;
//            if (viewType == VIEW_TYPE_ITEM) {
//                root = LayoutInflater.from(parent.getContext()).inflate(R.layout.district, parent, false);
//                return new ViewHolder(root);
//            } else {
//                root = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading, parent, false);
//                return new ProgressViewHolder(root);
//            }
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.district, parent, false);
            ViewHolder pvh = new ViewHolder(v);
            return  pvh;
        }
//        @Override
//        public int getItemViewType(int position) {
//            if (alldistricts.get(position) != null)
//                return VIEW_TYPE_ITEM;
//            else
//                return VIEW_TYPE_LOADING;
//        }


        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
//            if (holder instanceof ViewHolder) {

                //((DataViewHolder) holder)
                Picasso.get().load(Urls.ipdistrict+alldistricts.get(position).image)
                        .into(holder.m1);
                holder.t1.setText(alldistricts.get(position).name);
                holder.t2.setText(alldistricts.get(position).id);
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment f10=All_nursery.newInstance (holder.t2.getText().toString(),"");
                        setme(f10);

                    }
                });
//            }
//            else
//            {
//
//            }
//            Picasso.get().load(Urls.ipdistrict+alldistricts.get(position).image)
//                    .into(holder.m1);
//            holder.t1.setText(alldistricts.get(position).name);
//            holder.t2.setText(alldistricts.get(position).id);
//            holder.cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Fragment f10=All_nursery.newInstance (holder.t2.getText().toString(),"");
//                    setme(f10);
//
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return alldistricts.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView t1,t2,t3;
            ImageView m1;
            CardView cardView;
            public ViewHolder(View itemView) {
                super(itemView);
                t1=(TextView)itemView.findViewById(R.id.districtname);
                m1=(ImageView)itemView.findViewById(R.id.imgdistrict);
                cardView=(CardView)itemView.findViewById(R.id.cdistrict);
                t2=(TextView)itemView.findViewById(R.id.districtid);
                Animation animation1 =
                        AnimationUtils.loadAnimation(getActivity(), R.anim.slidein);
                cardView.startAnimation(animation1);
            }
        }
        public class ProgressViewHolder extends RecyclerView.ViewHolder {
//            TextView t1,t2,t3;
//            ImageView m1;
//            CardView cardView;
            public ProgressViewHolder(View itemView) {
                super(itemView);

            }
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
    private void setme(Fragment f) {
        AppCompatActivity activity= (AppCompatActivity) getContext();
        FragmentManager fm =activity.getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.frame,f);
        ft.commit();

    }
}
