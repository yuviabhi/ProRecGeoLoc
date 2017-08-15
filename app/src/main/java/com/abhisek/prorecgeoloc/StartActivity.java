package com.abhisek.prorecgeoloc;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class StartActivity extends AppCompatActivity {

    EditText editText_serverIP, editText_serverPort;
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        editText_serverIP = (EditText) findViewById(R.id.editText_serverIP);
        editText_serverPort = (EditText) findViewById(R.id.editText_serverPort);
        btn_next = (Button) findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serverIP = editText_serverIP.getText().toString().trim();
                String serverPort = editText_serverPort.getText().toString().trim();

//                Constants.serverIP = serverIP;
//                Constants.serverPort = serverPort;



                if(serverIP.compareTo("") !=0 && serverPort.compareTo("") != 0){

                    Intent i = new Intent(getApplicationContext(),LogIn.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(),"Enter Server IP & Port",Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(),Constants.serverIP+" : "+serverIP,Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
