package com.abhisek.prorecgeoloc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LogIn extends AppCompatActivity {

    public static LinearLayout linearLayout_login, linearLayout_acc;
    public static TextView txtview_login;
    public static EditText editText_mob_no ;
    public static EditText editText_pwd ;
    public static Button btn_login , btn_my_acc, btn_register;
    public static Boolean is_login_successful = Boolean.FALSE;
    public static Boolean response_back = Boolean.FALSE;

    String mobile_no, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        linearLayout_acc = (LinearLayout) findViewById(R.id.linearLayout_acc);
        linearLayout_login = (LinearLayout) findViewById(R.id.linearLayout_login);
        txtview_login = (TextView) findViewById(R.id.textView_login);
        editText_mob_no = (EditText) findViewById(R.id.editText_mob);
        editText_pwd = (EditText) findViewById(R.id.editText_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_my_acc = (Button) findViewById(R.id.btn_gotoprofile);
        btn_register = (Button) findViewById(R.id.btn_rgn);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile_no = editText_mob_no.getText().toString();
                password = editText_pwd.getText().toString();


                new LoginActivity(getBaseContext(),1).execute(mobile_no,password);


//                while (response_back != Boolean.TRUE){
//                    try {
//                        if (is_login_successful) {
//
////                            Toast.makeText(getApplicationContext(),"Login Successfulaaa",Toast.LENGTH_LONG).show();
////                            i = new Intent(getApplicationContext(), LocationUpdate.class);
////                            startActivity(i);
//
//                        } else {
////                            Toast.makeText(getApplicationContext(),"Login Failed !",Toast.LENGTH_LONG).show();
//
//                        }
//                    } catch (Exception e){
//                        Log.d("mylog",e.getMessage());
//                    }
//                }

            }
        });


        btn_my_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),InformationSyncActivity.class);
                i.putExtra("mobile_no",mobile_no);
                startActivity(i);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });


    }

}
