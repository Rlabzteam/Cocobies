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
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link All_nursery.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link All_nursery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class All_nursery extends Fragment {
    int logFlag;
    List<allnursery> allnursery;
    RecyclerView rv;
    String t;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public All_nursery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment All_nursery.
     */
    // TODO: Rename and change types and number of parameters
    public static All_nursery newInstance(String param1, String param2) {
        All_nursery fragment = new All_nursery();
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
        firstexecute(mParam1);
    }

    private void firstexecute(final String mParam1) {

        class all_nursery_data extends AsyncTask {
            String p;
            public all_nursery_data(String mParam1) {
                this.p=mParam1;

            }

            @SuppressLint("WrongThread")
            @Override
            protected Object doInBackground(Object[] objects) {
                String result = "";

                //String conne="http://192.168.21.197/main/mobdata/test2dep.php";
                try {
                    URL url = new URL(Urls.all_nursery_URL);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();

                    http.setRequestMethod("POST");
                    http.setDoInput(true);
                    http.setDoOutput(true);

                    OutputStream outputStream = http.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data2 = URLEncoder.encode("did", "UTF-8") + "=" + URLEncoder.encode(p, "UTF-8");
                    writer.write(data2);

                    writer.flush();
                    writer.close();
                    outputStream.close();
                    InputStream ips = http.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                    String line = "";
                    while ((line = reader.readLine()) != null) {

                        result += line;

                    }

                    reader.close();
                    ips.close();
                    result.trim();

                    if (result.length() > 2) {

                        logFlag = 1;
                        JSONArray arr = new JSONArray(result);
                        allnursery = new ArrayList<>();
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            allnursery p = new allnursery();
                            p.id=obj.getString("id");
                            p.name = obj.getString("name");
                            p.image = obj.getString("image");
                            p.district=obj.getString("district");
                            p.no=obj.getString("number");
                            t=obj.getString("district");
//                            Log.d("result", obj.getString("quest"));
                            allnursery.add(p);

                        }

                        // finish();
                        getActivity().runOnUiThread(new Runnable() {
                            //@Override
                            @SuppressLint("WrongConstant")
                            public void run() {
                                rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                                nursery_district_wise_adapter q1=new nursery_district_wise_adapter(getContext(),allnursery);
                                rv.setAdapter(q1);
                                ((Homepage) getActivity())
                                        .setActionBarTitle(t);


                            }
                        });

                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            //@Override
                            @SuppressLint("WrongConstant")
                            public void run() {
//                                ((Homepage) getActivity())
//                                        .setActionBarTitle(t);
                                Toast.makeText(getActivity(), "no values", 2).show();
                            }
                        });
                    }

                    http.disconnect();
                    return result;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("q vlaues\n", result);
                return result;
            }

            protected void onPreExecute() {

            }

            protected void onPostExecute(String result) {
                Log.d("POST METHOD", result);

            }



        }
        all_nursery_data alln = new all_nursery_data(mParam1);
        alln.execute();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_all_nursery, container, false);

        rv = (RecyclerView) view.findViewById(R.id.rv_all_nursery);
        rv.setHasFixedSize(true);

        return view;
    }
    public class nursery_district_wise_adapter extends RecyclerView.Adapter<nursery_district_wise_adapter.ViewHolder> {
        Context context;
        List<allnursery>allnurseries;

        public nursery_district_wise_adapter(Context context, List<All_nursery.allnursery> allnursery) {
            this.context=context;
            this.allnurseries=allnursery;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nursery, viewGroup, false);
            ViewHolder pvh = new ViewHolder(v);
            return  pvh;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
            Picasso.get().load(Urls.ipnursery+allnurseries.get(i).image)
                    .into(holder.m1);
            final String imageString=Urls.ipnursery+allnurseries.get(i).image;
            holder.t1.setText(allnurseries.get(i).name);
            holder.t2.setText(allnurseries.get(i).id);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getActivity(),Nurseryhome.class);

                    i.putExtra("image_of_nursies",imageString);
                    i.putExtra("name",holder.t1.getText().toString());
                    i.putExtra("district",t);
                    startActivity(i);

                }
            });
        }

        @Override
        public int getItemCount() {
            return allnurseries.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView t1,t2,t3;
            ImageView m1;
            CardView cardView;
            public ViewHolder(View itemView) {
                super(itemView);
                t1=(TextView)itemView.findViewById(R.id.txttitle_all_nursery);
                m1=(ImageView)itemView.findViewById(R.id.img_allnursery);
                cardView=(CardView)itemView.findViewById(R.id.c_all_nursery);
                t2=(TextView)itemView.findViewById(R.id.txt_all_nursery_id);
            }
        }
    }
    public class allnursery{
        String image;
        String id;
        String name;
        String place;
        String district;
        String no;

        public String getId() {
            return id;
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
