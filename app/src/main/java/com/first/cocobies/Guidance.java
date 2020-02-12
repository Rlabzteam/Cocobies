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
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
 * {@link Guidance.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Guidance#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Guidance extends Fragment {
    List<Allguidence> statuss;
    LinearLayoutManager lm;
    RecyclerView rv;
    SwipeRefreshLayout swipeRefreshLayout;
    String slink;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Guidance() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Guidance.
     */
    // TODO: Rename and change types and number of parameters
    public static Guidance newInstance(String param1, String param2) {
        Guidance fragment = new Guidance();
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
        View view= inflater.inflate(R.layout.fragment_guidance, container, false);


        ((Homepage) getActivity())
                .setActionBarTitle("Guidence");

        rv = (RecyclerView) view.findViewById(R.id.rvguidence);
        rv.setHasFixedSize(true);
//     //   initializeData();
        lm=new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(lm);
//        MachineryAdapter dashAdapter=new MachineryAdapter(data);
//        rv.setAdapter(dashAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.pullToRefresh_guidence);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                firstexecute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private void firstexecute() {
        class statusdata extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost request = new HttpPost();
                    request.setURI(new URI(Urls.GUIDE_URL));
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
                            statuss = new ArrayList<>();
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);
                                Allguidence s = new Allguidence();
                                s.id=obj.getString("id");
                                // s.images=obj.getString("images");
                                s.link=obj.getString("link");
                                s.name=obj.getString("name");

//                            Log.d("result", obj.getString("quest"));
                                statuss.add(s);
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                //@Override
                                public void run() {
                                    guidanceadapter q1=new guidanceadapter(getContext(),statuss);
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
        statusdata dp = new statusdata();
        dp.execute();
    }
    public class Allguidence
    {
        String id;
        String images;
        String link;
        String name;

        public void setLink(String link) {
            this.link = link;
        }

        public String getLink() {
            return link;
        }
    }

    public class guidanceadapter extends RecyclerView.Adapter<guidanceadapter.ViewHolder> {
        Context context;
        List<Allguidence>statuss;

        public guidanceadapter(Context context, List<Allguidence> statuss) {
            this.context=context;
            this.statuss=statuss;
        }

        @NonNull
        @Override
        public guidanceadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.guidence_layout, parent, false);
            ViewHolder pvh = new ViewHolder(v);
            return  pvh;
        }

        @Override
        public void onBindViewHolder(@NonNull final guidanceadapter.ViewHolder holder, int position) {
//            Picasso.get().load(Urls.ipimg+statuss.get(position).images)
//                    .into(holder.m1);
            holder.t1.setText(statuss.get(position).link);
            holder.t2.setText(statuss.get(position).name);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(holder.t1.getText().toString()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            Log.d("value", String.valueOf(statuss.size()));
            return statuss.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView t1,t2,t3;
            ImageView m1;
            CardView cardView;
            public ViewHolder(View itemView) {
                super(itemView);
                t1=(TextView)itemView.findViewById(R.id.txtlink);
                t2=(TextView)itemView.findViewById(R.id.name_guidance);
                   m1=(ImageView)itemView.findViewById(R.id.newimage);
                cardView=(CardView)itemView.findViewById(R.id.cartguidence);
//                cardView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Uri uri = Uri.parse(t1.toString()); // missing 'http://' will cause crashed
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intent);
//                    }
//                });
                Animation animation1 =
                        AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
                m1.startAnimation(animation1);

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
}
