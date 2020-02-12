package com.first.cocobies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.squareup.picasso.Picasso;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Process.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Process#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Process extends Fragment {
    String s1;
    List<all_recipes>statuss;
    int logFlag;
    boolean connected = false;
    RecyclerView rv;
    SwipeRefreshLayout swipeRefreshLayout;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Process() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Process.
     */
    // TODO: Rename and change types and number of parameters
    public static Process newInstance(String param1, String param2) {
        Process fragment = new Process();
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
        Log.d("m",mParam1);
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
                    URL url = new URL(Urls.Production_process_URL);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();

                    http.setRequestMethod("POST");
                    http.setDoInput(true);
                    http.setDoOutput(true);

                    OutputStream outputStream = http.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data2 = URLEncoder.encode("prid", "UTF-8") + "=" + URLEncoder.encode(p, "UTF-8");
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
                        statuss = new ArrayList<>();
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);
                                    all_recipes s = new all_recipes();

        s.recid=obj.getString("id");
        s.images=obj.getString("image");
        s.linkvedio=obj.getString("vedio_link");
        s.typeid=obj.getString("type_id");
        s.prepare=obj.getString("type");
        s.recipe_name=obj.getString("recipe_name");
                            statuss.add(s);



                        }

                        // finish();
                        getActivity().runOnUiThread(new Runnable() {
                            //@Override
                            @SuppressLint("WrongConstant")
                            public void run() {
                                rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                                Process_adapter q1=new Process_adapter(getContext(),statuss);
                                rv.setAdapter(q1);
//                                ((Homepage) getActivity())
//                                        .setActionBarTitle(t);


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

    public class Process_adapter extends RecyclerView.Adapter<Process_adapter.ViewHolder> {
        Context context;

        List<all_recipes> statuss;

        public Process_adapter(Context context, List<all_recipes> statuss) {
            this.statuss=statuss;
            this.context=context;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list, parent, false);
            ViewHolder pvh = new ViewHolder(v);
            return  pvh;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {

            final String id=statuss.get(i).recid;
            final String dec=statuss.get(i).prepare;

            final String imagestring=Urls.ipproductsimg+statuss.get(i).images;
            Picasso.get().load(Urls.ipproductsimg+statuss.get(i).images)
                    .into(holder.img);
            holder.t1.setText(statuss.get(i).recipe_name);
            holder.t2.setText(statuss.get(i).linkvedio);
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(holder.t2.getText().toString()); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
                }
            });


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final AppCompatActivity activity = (AppCompatActivity) getContext();

                    Fragment f10=Productdetails.newInstance (id,holder.t1.getText().toString(),holder.t2.getText().toString(),imagestring,dec);
                    FragmentManager fm =activity.getSupportFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    ft.addToBackStack(null);
                    ft.add(R.id.frameitembycategory,f10);
                    ft.commit();


//                    Fragment myFragment = new Productdetails();
//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameitembycategory, myFragment).addToBackStack(null).commit();
                }
            });

        }

        @Override
        public int getItemCount() {
            return statuss.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            TextView t1,t2,t3;
            ImageView img,img_bookmark,img_addbookmark;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                t1=(TextView)itemView.findViewById(R.id.txttitle_all_process);
                img=(ImageView)itemView.findViewById(R.id.img_all_process);
                cardView=(CardView)itemView.findViewById(R.id.c_all_process);


                img_bookmark=(ImageView)itemView.findViewById(R.id.bookmark_product_rec);
                img_addbookmark=(ImageView)itemView.findViewById(R.id.bookmarkadd_product_rec);
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
                t2=(TextView)itemView.findViewById(R.id.txt_all_process_id);
                Animation animation1 =
                        AnimationUtils.loadAnimation(getActivity(), R.anim.updown);
                cardView.startAnimation(animation1);
            }
        }
    }
    public class all_recipes{


        String images;
        String recid;
        String recipe_name;
        String typeid;
        String prepare;
        String linkvedio;

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_process, container, false);

       //
            ((Itembycategory) getActivity())
                    .setActionBarTitle("Products");
       // TextView textView=(TextView)view.findViewById(R.id.testing1);
//        textView.setText(mParam1);
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
     rv=(RecyclerView)view.findViewById(R.id.rv_process);
        rv.setHasFixedSize(true);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.pullToRefresh_process);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                if (haveNetwork() == true) {
                    firstexecute(mParam1);
                    swipeRefreshLayout.setRefreshing(false);
                }
                else {
                    Toast.makeText(getActivity(), "No Internet connection", Toast.LENGTH_LONG).show();
                }

            }
        });
//        Uri uri = Uri.parse("https://www.youtube.com/watch?v=B3vUrvT-tcM"); // missing 'http://' will cause crashed
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
//        if (mParam1.equals("1"))
//        {
//            textView.setText(mParam1);
//
//        }
//        else if (mParam1.equals("2"))
//        {
//            textView.setText(mParam1);
//        }
//        else if (mParam1.equals("3"))
//        {
//            textView.setText(mParam1);
//        }
//        else {
//            ((Itembycategory) getActivity())
//                    .setActionBarTitle("products process");
//        }

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
