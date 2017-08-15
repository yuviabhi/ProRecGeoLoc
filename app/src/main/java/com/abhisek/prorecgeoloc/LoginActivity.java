package com.abhisek.prorecgeoloc;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Abhisek Chowdhury on 9/5/2016.
 */
public class LoginActivity extends AsyncTask<String,Void,String>{

    private Context context;
    private int byGetOrPost = 0;

    //flag 0 means get and 1 means post.(By default it is post [1].)

    public LoginActivity(Context context, int flag) {
        this.context = context;
        byGetOrPost = flag;
    }


    @Override
    protected String doInBackground(String... arg0) {
        if(byGetOrPost == 0){ //means by Get Method

            try{
                String mobile_no = (String)arg0[0];
                String password = (String)arg0[1];
                String link = Constants.php_server_login_url_getMethod+"?mobile_no="+mobile_no+"& password="+password;

                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line="";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            }

            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }
        else{
            try{

                String mobile_no = (String)arg0[0];
                String password = (String)arg0[1];

                String link=Constants.php_server_login_url;
                String data  = URLEncoder.encode("mobile_no", "UTF-8") + "=" + URLEncoder.encode(mobile_no, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }
                return sb.toString();
            }
            catch(Exception e){
                System.out.println("Exception: " + e.getMessage());
                Log.d("mylog","Exception: " + e.getMessage());
                return new String("Exception: " + e.getMessage());

            }
        }
    }

    @Override
    protected void onPostExecute(String result){

        if(result.compareToIgnoreCase("success") == 0){

            Toast.makeText(this.context,"Login Successful",Toast.LENGTH_LONG).show();
//            LogIn.is_login_successful = Boolean.TRUE;
//            LogIn.response_back = Boolean.TRUE;

//            LogIn.txtview_login.setVisibility(View.INVISIBLE);
//            LogIn.btn_login.setVisibility(View.INVISIBLE);
//            LogIn.editText_mob_no.setVisibility(View.INVISIBLE);
//            LogIn.editText_pwd.setVisibility(View.INVISIBLE);
//            LogIn.btn_my_acc.setVisibility(View.VISIBLE);
            LogIn.btn_register.setVisibility(View.INVISIBLE);
            LogIn.linearLayout_login.setVisibility(View.INVISIBLE);
            LogIn.linearLayout_acc.setVisibility(View.VISIBLE);


        } else{
//            LogIn.is_login_successful = Boolean.FALSE;
//            LogIn.response_back = Boolean.TRUE;
            Toast.makeText(this.context,"Login Failed",Toast.LENGTH_LONG).show();
        }


    }
}
