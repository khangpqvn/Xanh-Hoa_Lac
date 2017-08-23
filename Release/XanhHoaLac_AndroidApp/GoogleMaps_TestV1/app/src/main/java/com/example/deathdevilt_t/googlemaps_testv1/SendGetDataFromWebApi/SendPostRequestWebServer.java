package com.example.deathdevilt_t.googlemaps_testv1.SendGetDataFromWebApi;

import android.os.AsyncTask;
import android.util.Log;

import com.example.deathdevilt_t.googlemaps_testv1.Activity.SigninActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by DeathDevil.T_T on 13-Apr-17.
 */

public class SendPostRequestWebServer extends AsyncTask<String, Void, String> {
 //   Context mContext;
   // SigninActivity signinActivity = new SigninActivity();
    String email = SigninActivity.email;
    public static   String IdToken1;
  //  public SendPostRequestWebServer(Context mContext) {
//        this.mContext = mContext;
//    }

    public SendPostRequestWebServer(){}



        @Override
        protected String doInBackground(String... urls) {


            return POST(urls[0],IdToken1);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

           // Toast.makeText(signinActivity.getApplicationContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            try {
                JSONObject json = new JSONObject(result);

                String str = "";
                json.getJSONObject("");
   //             JSONArray articles = json.getJSONArray("UserInformation");
//            str += "articles length = "+json.getJSONArray("UserInformation").length();
//            str += "\n--------\n";
//            str += "names: "+articles.getJSONObject(0).names();
//            str += "\n--------\n";
                str +=json.getJSONObject("status");
//                UserObject userObject = new UserObject();
//                userObject.setIsLogin("1");
                Log.d("BugDmm",str);



                //etResponse.setText(json.toString(1));

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }



    public static String POST(String url, String idToken){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("id_token", IdToken1);
            jsonObject.accumulate("check", 13);

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
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
