package com.example.saksham.speedcode;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by saksham on 17/10/15.
 */
public class PostData extends AsyncTask<String, Void, Boolean> {
    String url;
    String[] inputId;
    Context context;
    public static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public PostData(Context c, String Url, String... params) {
        context = c;
        url = Url;
        inputId = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            inputId[i] = params[i];
        }
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Boolean result = true;
        String postBody = "";
        try {
            for (int i = 0; i < inputId.length; i++) {
                postBody = postBody + inputId[i] + "=" + URLEncoder.encode(strings[i], "UTF-8");
                if (i!=inputId.length-1) {
                    postBody = postBody + "&";
                }
            }
        } catch (UnsupportedEncodingException ex) {
            result = false;
        }

        try{
            //Create OkHttpClient for sending request
            OkHttpClient client = new OkHttpClient();
            //Create the request body with the help of Media Type
            RequestBody body = RequestBody.create(FORM_DATA_TYPE, postBody);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            //Send the request
            Response response = client.newCall(request).execute();
            result = true;
        }catch (IOException exception){
            result=false;
        }
        return result;

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean) {
            Toast.makeText(context, "ThankYou", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
        }

    }
}

