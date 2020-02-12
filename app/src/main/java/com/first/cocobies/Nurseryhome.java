package com.first.cocobies;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static com.first.cocobies.R.id.lback_nursery;

public class Nurseryhome extends AppCompatActivity {
    TextView tnurseies_name,tnursies_dist;
    ImageView imageView_nurserie;
    Drawable drawable;
    Bitmap bitmap;
    String nurseries_name,nursery_dist,nursery_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nurseryhome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nurseries List");
//SELECT * FROM house_det WHERE cast(longitude as decimal(7,4))=76.3831 and cast(lattitude as decimal(7,4))=10.0867
//        Window w=getWindow();
//        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        LinearLayout l1=(LinearLayout)findViewById(R.id.lback_nursery);
        tnurseies_name=(TextView)findViewById(R.id.nurseiesdetails_name);
        tnursies_dist=(TextView)findViewById(R.id.nurseiesdetails_place);
      //  imageView_nurserie=(ImageView)findViewById(R.id.nurseries_image);
         nurseries_name = getIntent().getStringExtra("name");
        nursery_image = getIntent().getStringExtra("image_of_nursies");
      //  drawable=Drawable.createFromPath(nursery_image);

//       Bitmap bmImg = BitmapFactory.decodeFile(nursery_image);
//        BitmapDrawable background = new BitmapDrawable(bmImg);
//        l1.setBackgroundDrawable(background);
       // drawable= BitmapDrawable.createFromPath(nursery_image);
//        bitmap== Bitmap.createBitmap(bitmap,300,Config);
//         bitmap = Bitmap.createScaledBitmap(bitmap, and options)

        nursery_dist = getIntent().getStringExtra("district");
      //  LinearLayout.LayoutParams l1= new LinearLayout.LayoutParams(R.id.lback_nursery);

//        @SuppressLint("WrongViewCast") Customlayout mCustomLayout = (Customlayout)findViewById(lback_nursery);
//        Picasso.get().load("http://imageUrl").into((Target) mCustomLayout);
        ImageView imageView= new ImageView(getApplication());
                Picasso.get().load(nursery_image)
                       // .fit()
                        .into(imageView);
//

////    //    Display display = getActivity().getWindowManager().getDefaultDisplay();
////        int width = ((display.getWidth()*50)/100); // ((display.getWidth()*20)/100)
////        int height = display.getHeight();//
//
//
              //l1.addView(imageView,1100,665);
                l1.addView(imageView);


                tnursies_dist.setText(nursery_dist);
                tnurseies_name.setText(nurseries_name);
//        Picasso.get().load(nursery_image)
//                .into(l1);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
