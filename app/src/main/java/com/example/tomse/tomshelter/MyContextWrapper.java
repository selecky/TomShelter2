package com.example.tomse.tomshelter;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MyContextWrapper extends ContextWrapper {



    public MyContextWrapper(Context base) {
        super(base);
    }

    public Bitmap getDefaultIconBitmap(){
        return BitmapFactory.decodeResource(getResources(), R.drawable.ic_pets);
    }
}
