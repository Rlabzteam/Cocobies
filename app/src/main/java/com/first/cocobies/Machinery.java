package com.first.cocobies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Machinery.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Machinery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Machinery extends Fragment {
    List<shopbycategory> data;
    List<machinedata>machinedata;
    RecyclerView rv;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Machinery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Machinery.
     */
    // TODO: Rename and change types and number of parameters
    public static Machinery newInstance(String param1, String param2) {
        Machinery fragment = new Machinery();
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

    private void firstexecute() {

        class viewmachine extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost request = new HttpPost();
                    request.setURI(new URI(Urls.View_all_machine_URL));
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
                            machinedata = new ArrayList<>();
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);
                                machinedata s = new machinedata();
                                s.id=obj.getString("id");
                                s.machineName=obj.getString("machine_name");
                                s.machineImage=obj.getString("machine_image");
                                s.machineDec=obj.getString("machine_dec");
                                s.machineType=obj.getString("machine_type");


//                            Log.d("result", obj.getString("quest"));
                                machinedata.add(s);
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                //@Override
                                @SuppressLint("WrongConstant")
                                public void run() {
                                    rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                                    madapter q1=new madapter(getContext(),machinedata);
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
        viewmachine vm = new viewmachine();
        vm.execute();



    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_machinery, container, false);
        ((Homepage) getActivity())
                .setActionBarTitle("Machinery");
       rv =(RecyclerView)view.findViewById(R.id.rvm);
        rv.setHasFixedSize(true);

       // initializeData();
       // rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

       // MachineryAdapter dashAdapter=new MachineryAdapter(data);
        //  rv.setAdapter(dashAdapter);



        return view;
    }

    private void initializeData() {

        data=new ArrayList<>();
        data.add(new shopbycategory("Oil maker",R.drawable.peppermint));
      //  data.add(new shopbycategory("Coconut husk peeler",R.drawable.coconutpeeler));
      //  data.add(new shopbycategory("Coconut climbing",R.drawable.climbing));
        data.add(new shopbycategory("Coconut leaf Straw Maker",R.drawable.coconutleafstraw));
        data.add(new shopbycategory("Coconut Fiber Pot Maker",R.drawable.coconutfiberpot));
        data.add(new shopbycategory("Broom Stick Maker",R.drawable.broomstickmaking));

        data.add(new shopbycategory("Broom Stick Maker",R.drawable.broomstick2));
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

    public static class madapter extends RecyclerView.Adapter<madapter.ViewHolder> {

        List<machinedata>machinedata;
        Context context;

        public madapter(Context context, List<machinedata> machinedata) {
            this.machinedata=machinedata;
            this.context=context;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.machinery, parent, false);
            ViewHolder pvh = new ViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Picasso.get().load(Urls.ipmachine+machinedata.get(position).machineImage)
                    .into(holder.m1);
            final String imageString=Urls.ipmachine+machinedata.get(position).machineImage;
            holder.t1.setText(machinedata.get(position).machineName);
            holder.t2.setText(machinedata.get(position).machineType);
//            holder.cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent i=new Intent(getActivity(),Nurseryhome.class);
//                    i.putExtra("image_of_nursies",imageString);
//                    i.putExtra("name",holder.t1.getText().toString());
//                    i.putExtra("district",holder.t2.getText().toString());
//                    startActivity(i);
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return machinedata.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView t1,t2,t3;
            CardView c1;
            ImageView m1,img_bookmark,img_addbookmark;
            public ViewHolder(View itemView) {
                super(itemView);
                t1=(TextView)itemView.findViewById(R.id.txttitlemac);
                m1=(ImageView)itemView.findViewById(R.id.imgmac);
                c1=(CardView)itemView.findViewById(R.id.c1);
                t2=(TextView)itemView.findViewById(R.id.mtype);
//            Animation animation1 =AnimationUtils.loadAnimation(getc, R.anim.slidein);
//            c1.startAnimation(animation1);
                img_bookmark=(ImageView)itemView.findViewById(R.id.bookmark_machinery);
                img_addbookmark=(ImageView)itemView.findViewById(R.id.bookmarkadd_machinery);
                String s1="0";
                if (s1.equals("1")) {
                    img_bookmark.setVisibility(View.GONE);
                    img_addbookmark.setVisibility(View.VISIBLE);
                }
                else {
                    img_addbookmark.setVisibility(View.GONE);
                    img_bookmark.setVisibility(View.VISIBLE);
                }
                img_addbookmark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        img_addbookmark.setVisibility(View.GONE);
                        img_bookmark.setVisibility(View.VISIBLE);

                    }
                });
                img_bookmark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        img_bookmark.setVisibility(View.GONE);
                        img_addbookmark.setVisibility(View.VISIBLE);

                    }
                });






            }
        }


    }




}
