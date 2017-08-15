package com.abhisek.prorecgeoloc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhisek Chowdhury on 9/15/2016.
 */
public class DataSynchronizer {

    public static void syncData(JSONArray jsonArray, String url, Context context){

        new asyncTask_syncData(context).execute(jsonArray.toString(), url);

    }

    public static class asyncTask_syncData extends AsyncTask<String, Integer, JSONArray> {

        private ProgressDialog dialog;

        Context context;

        public asyncTask_syncData(Context context) {
            this.context = context;
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            // TODO Auto-generated method stub

            JSONArray jsonObjectResponse = postData(params[0], params[1]);

            return jsonObjectResponse;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Please wait...");
//             dialog.setIndeterminate(true);
            dialog.show();

        }


        protected void onPostExecute(JSONArray jsonObjectResponse) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if (jsonObjectResponse != null) {


                final AlertDialog.Builder submitForm = new AlertDialog.Builder(context);
                submitForm.setMessage("Synced Successfully !");
                submitForm.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
//                                 alertLogin.dismiss();

                            }
                        });
//                 submitForm.show();

                final AlertDialog alertLogin = submitForm.create();
                alertLogin.show();


            } else {

                AlertDialog.Builder submitForm = new AlertDialog.Builder(context);
                submitForm.setMessage("Can't connect to server !");
                submitForm.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
//                                 alertLogin.dismiss();
                            }
                        });
//                 submitForm.show();

                final AlertDialog alertLogin = submitForm.create();
                alertLogin.show();

            }
        }

        public JSONArray postData(String jsonArray, String url) {

            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("jsonArray", jsonArray));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

//                System.out.println("JSON ARRAY \n" + jsonArray);

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

                String resFromServer = org.apache.http.util.EntityUtils.toString(response.getEntity());

                System.out.println("JSONARRAY RESPONSE BACK FROM SERVER \n" + resFromServer);
                JSONArray jsonObjectResponse = new JSONArray(resFromServer);

                return jsonObjectResponse;

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
//                e.printStackTrace();
                System.out.println(e.toString());
                Log.d("app_log", e.toString());
                return null;

            } catch (HttpHostConnectException e) {
//                e.printStackTrace();
                System.out.println(e.toString());
                Log.d("app_log", e.toString());
                return null;

            } catch (IOException e) {
                // TODO Auto-generated catch block
//                e.printStackTrace();
                System.out.println(e.toString());
                Log.d("app_log", e.toString());
                return null;

            } catch (Exception e) {
//                e.printStackTrace();
                System.out.println(e.toString());
                Log.d("app_log", e.toString());
                return null;

            }

        }
    }
}
