package com.first.cocobies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
 * {@link Research_innovation.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Research_innovation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Research_innovation extends Fragment {
    List<machinedata>machinedata;
    RecyclerView rv;
    int logFlag;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Research_innovation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Research_innovation.
     */
    // TODO: Rename and change types and number of parameters
    public static Research_innovation newInstance(String param1, String param2) {
        Research_innovation fragment = new Research_innovation();
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


    private void firstexecute( final String mParam1) {

        class Producationprocess extends AsyncTask {
            String p;

            public Producationprocess(String mParam1) {
                this.p=mParam1;
            }


            @SuppressLint("WrongThread")
            @Override
            protected Object doInBackground(Object[] objects) {
                String result = "";

                //String conne="http://192.168.21.197/main/mobdata/test2dep.php";
                try {
                    URL url = new URL(Urls.View_tool_machine_URL);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();

                    http.setRequestMethod("POST");
                    http.setDoInput(true);
                    http.setDoOutput(true);

                    OutputStream outputStream = http.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data2 = URLEncoder.encode("mtype_id", "UTF-8") + "=" + URLEncoder.encode(p, "UTF-8");
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
                                Machinery.madapter q1=new Machinery.madapter(getContext(),machinedata);
                                rv.setAdapter(q1);




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
//        String m;
//        this.m=mParam1;
        Producationprocess alln = new Producationprocess(mParam1);
        alln.execute();




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_research_innovation, container, false);
        ((Homepage) getActivity())
                .setActionBarTitle("Innovative:");
        rv =(RecyclerView)view.findViewById(R.id.rvresearch_in);
        rv.setHasFixedSize(true);
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
}
