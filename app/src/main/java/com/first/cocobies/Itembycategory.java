package com.first.cocobies;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Itembycategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itembycategory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Items");
        String callfrg = getIntent().getStringExtra("call_fragment");
        if (callfrg.equals("process"))
        {
            String sessionId = getIntent().getStringExtra("Production_ID");

            Fragment f10=Process.newInstance (sessionId,"");
            setme(f10);
        }
        else{


        }





    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    private void setme(Fragment f) {
        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.frameitembycategory,f);
        ft.commit();

    }

}
