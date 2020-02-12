//package com.first.cocobies;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.util.AttributeSet;
//import android.widget.LinearLayout;
//import androidx.annotation.RequiresApi;
//import com.squareup.picasso.Picasso;
//
//import java.lang.annotation.Annotation;
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Target;
//
//public class Customlayout extends LinearLayout implements Target {
//
//    public Customlayout(Context context) {
//        super(context);
//    }
//
//    public Customlayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public Customlayout(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//        setBackground(new BitmapDrawable(getResources(), bitmap));
//    }
//
//    @Override
//    public void onBitmapFailed(Drawable errorDrawable) {
//        //Set your error drawable
//    }
//
//    @Override
//    public void onPrepareLoad(Drawable placeHolderDrawable) {
//        //Set your placeholder
//    }
//
//    @Override
//    public ElementType[] value() {
//        return new ElementType[0];
//    }
//
//    @Override
//    public Class<? extends Annotation> annotationType() {
//        return null;
//    }
//}
