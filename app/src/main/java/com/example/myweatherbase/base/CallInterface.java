package com.example.myweatherbase.base;

import android.content.Context;
import android.widget.Toast;

public interface CallInterface {

    void doInBackground() throws Exception;
    void doInUI();
    default void doInError(Context context, Exception e){
        Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
    }

}
