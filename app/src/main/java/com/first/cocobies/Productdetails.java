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
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
 * {@link Productdetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Productdetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Productdetails extends Fragment {
    int logFlag;
    RecyclerView rv;
    ImageView img,img1,img2,img3,img4,img5;
    TextView tproductname,tlink,tdec;
    List<details_data>details_data;
    String s;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;


    private OnFragmentInteractionListener mListener;

    public Productdetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Productdetails.
     */
    // TODO: Rename and change types and number of parameters
    public static Productdetails newInstance(String param1, String param2,String param3,String param4,String param5) {
        Productdetails fragment = new Productdetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
        }
        firstexecute(mParam1);
    }


    public  class details_data{


        String ingrents;

    }
    private void firstexecute(String mParam1) {



        class ProductDetails extends AsyncTask {
            String p;

            public ProductDetails(String mParam1) {
                this.p=mParam1;
            }


            @SuppressLint("WrongThread")
            @Override
            protected Object doInBackground(Object[] objects) {
                String result = "";

                //String conne="http://192.168.21.197/main/mobdata/test2dep.php";
                try {
                    URL url = new URL(Urls.Product_Ingredients_URL);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();

                    http.setRequestMethod("POST");
                    http.setDoInput(true);
                    http.setDoOutput(true);

                    OutputStream outputStream = http.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data2 = URLEncoder.encode("recipe_id", "UTF-8") + "=" + URLEncoder.encode(p, "UTF-8");
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
                        details_data = new ArrayList<>();
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            details_data s = new details_data();
                            s.ingrents=obj.getString("contents");

                            details_data.add(s);

                        }

                        // finish();
                        getActivity().runOnUiThread(new Runnable() {
                            //@Override
                            @SuppressLint("WrongConstant")
                            public void run() {
                                rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                                contents_adapter q1=new contents_adapter(getContext(),details_data);
                                rv.setAdapter(q1);
//                                ((Homepage) getActivity())
//                                        .setActionBarTitle(m);


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
        ProductDetails alln = new ProductDetails(mParam1);
        alln.execute();



    }

    public class contents_adapter extends RecyclerView.Adapter<contents_adapter.ViewHolder> {
        Context context;
        List<details_data>details_data;

        public contents_adapter(Context context, List<Productdetails.details_data> details_data) {
            this.context=context;
            this.details_data=details_data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.productdetails, parent, false);
            ViewHolder pvh = new ViewHolder(v);
            return  pvh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
            holder.t1.setText(details_data.get(i).ingrents);

        }


        @Override
        public int getItemCount() {
            return details_data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView t1;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                t1=(TextView)itemView.findViewById(R.id.txttitle_all_items);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_productdetails, container, false);
        img=(ImageView)view.findViewById(R.id.product_image) ;
        img1=(ImageView)view.findViewById(R.id.bookmark_product_details);
        img2=(ImageView)view.findViewById(R.id.bookmarkadd_product_details);
        img3=(ImageView)view.findViewById(R.id.shar_product_details);
        img4=(ImageView)view.findViewById(R.id.save_product_details);
        img5=(ImageView)view.findViewById(R.id.saved_product_details);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);

                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CoCobies");
                    String shareMessage= "\nRecipes you may like\n\n"+mParam2+"\n\n"+ mParam5+"\n";
                    shareMessage = shareMessage + mParam3 + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

       s="0";
       String s1="0";
        if (s1.equals("1")) {
            img4.setVisibility(View.GONE);
            img5.setVisibility(View.VISIBLE);
        }
        else {
            img5.setVisibility(View.GONE);
            img4.setVisibility(View.VISIBLE);
        }
      if (s=="1") {
          img1.setVisibility(View.GONE);
          img2.setVisibility(View.VISIBLE);
      }
      else {
          img2.setVisibility(View.GONE);
          img1.setVisibility(View.VISIBLE);
      }






        tproductname=(TextView)view.findViewById(R.id.product_name);
        tdec=(TextView)view.findViewById(R.id.txt_Method_of_Preparation);
        tproductname.setText(mParam2);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(mParam3); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img1.setVisibility(View.GONE);
                img2.setVisibility(View.VISIBLE);

            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img2.setVisibility(View.GONE);
                img1.setVisibility(View.VISIBLE);
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img4.setVisibility(View.GONE);
                img5.setVisibility(View.VISIBLE);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img5.setVisibility(View.GONE);
                img4.setVisibility(View.VISIBLE);
            }
        });
        tdec.setText(mParam5);
        Picasso.get().load(mParam4)
                .into(img);


        rv =(RecyclerView)view.findViewById(R.id.recyclerview_Ingredients);

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
