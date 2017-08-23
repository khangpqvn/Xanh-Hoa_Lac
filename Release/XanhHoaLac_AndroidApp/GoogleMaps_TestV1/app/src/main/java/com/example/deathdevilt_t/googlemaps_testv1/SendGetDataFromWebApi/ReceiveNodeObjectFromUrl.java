package com.example.deathdevilt_t.googlemaps_testv1.SendGetDataFromWebApi;

import android.os.AsyncTask;
import android.util.Log;

import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.UserObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by DeathDevil.T_T on 15-May-17.
 */

public class ReceiveNodeObjectFromUrl extends AsyncTask<String, Void, String> {
    //Context mContext;
   // SigninActivity signinActivity = new SigninActivity();
    @Override
    protected String  doInBackground(String... urls) {
        return GET(urls[0]);
    }
    @Override
    protected void onPostExecute(String result) {
       // Toast.makeText(signinActivity.getApplicationContext(), "Received!", Toast.LENGTH_LONG).show();
        try {
            JSONObject json = new JSONObject(result);

            String str = "";

            JSONArray articles = json.getJSONArray("UserInformation");
//            str += "articles length = "+json.getJSONArray("UserInformation").length();
//            str += "\n--------\n";
//            str += "names: "+articles.getJSONObject(0).names();
//            str += "\n--------\n";
            str +=articles.getJSONObject(0).getString("isLogin");
            UserObject userObject = new UserObject();
//            userObject.setIsLogin("1");
//            Log.d("BugDmm",userObject.getIsLogin());



            //etResponse.setText(json.toString(1));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    protected String isLoginFromServer(String isLogin){
        String result="";

        if(isLogin.equalsIgnoreCase("1")){
        return result="1";
        }else if (isLogin.equals("0")){
            return result="0";
        }
        return result;
    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
