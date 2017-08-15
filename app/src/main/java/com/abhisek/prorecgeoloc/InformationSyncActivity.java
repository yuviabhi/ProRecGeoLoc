package com.abhisek.prorecgeoloc;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InformationSyncActivity extends AppCompatActivity {


    Button btn_gps, btn_acceletrometer, btn_magnetometer, btn_facebook, btn_callLogs, btn_contacts, btn_sync;
    GPSLocator gpsLocator;
    TextView textView_lat, textView_lng;

    EditText edtText_pincode, editText_age;

    Double lat = 0.0, lng = 0.0;
    List<String> contact_list;
    List<String> call_log_list;

    Spinner spn_salary_grp;


    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_update);

        btn_gps = (Button) findViewById(R.id.btn_getGPS);
        btn_contacts = (Button) findViewById(R.id.btn_contacts);
        btn_sync = (Button) findViewById(R.id.btn_ok);
        btn_callLogs = (Button) findViewById(R.id.btn_callLogs);
        btn_acceletrometer = (Button) findViewById(R.id.btn_accelerometer);
        btn_magnetometer = (Button) findViewById(R.id.btn_magnetometer);
        btn_facebook = (Button) findViewById(R.id.btn_facebook);

        textView_lat = (TextView) findViewById(R.id.textView2_lat_value);
        textView_lng = (TextView) findViewById(R.id.textView4_lng_value);

        edtText_pincode = (EditText) findViewById(R.id.editText5_pincode);
        editText_age = (EditText) findViewById(R.id.editText_age);

        spn_salary_grp = (Spinner) findViewById((R.id.spn_salary));

        btn_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gpsLocator = new GPSLocator(InformationSyncActivity.this);

                if (gpsLocator.canGetLocation()) {

                    lat = gpsLocator.getLatitude();
                    lng = gpsLocator.getLongitude();

                    Toast.makeText(getApplicationContext(), "Latitude: " + lat + "\nLongitude: " + lng, Toast.LENGTH_LONG).show();

                    textView_lat.setText("Latitude : " + String.valueOf(lat));
                    textView_lng.setText("Longitude : " + String.valueOf(lng));

                } else {

                    gpsLocator.showSettingsAlert();

                }

            }
        });


        btn_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contact_list = new ArrayList<>();
                retrieveContacts();
            }
        });

        btn_callLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_log_list = new ArrayList<String>();
                retrieveCallLogs(getApplicationContext());
            }
        });

        btn_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //lat, lng, pincode, salary, age, calllogs, contacts, facebook, accelerometer, magnetometer

                String pincode = String.valueOf(edtText_pincode.getText());
                String salary = String.valueOf(spn_salary_grp.getSelectedItem());
                Integer age = Integer.parseInt(editText_age.getText().toString());

                Map<String,String> my_data = new LinkedHashMap<>();
                Intent intent = getIntent();
                my_data.put("mobile",intent.getStringExtra("mobile_no"));
                my_data.put("lat",lat.toString());
                my_data.put("lng",lng.toString());
                my_data.put("pincode",pincode);
                my_data.put("salary",salary);
                my_data.put("age",age.toString());
                my_data.put("calllogs",call_log_list.toString());
                my_data.put("contacts",contact_list.toString());
                my_data.put("facebook","-");
                my_data.put("accelerometer","-");
                my_data.put("magnetometer","-");

                JSONObject jsonObject = new JSONObject(my_data);
                List<JSONObject> list_jsonObj = new ArrayList<>();
                list_jsonObj.add(jsonObject);

                JSONArray jsonArray = new JSONArray(list_jsonObj);

//                Log.d("mylog",jsonArray.toString());
                DataSynchronizer.syncData(jsonArray,Constants.php_server_syncdata_url,InformationSyncActivity.this);


            }
        });

        btn_acceletrometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Yet to implement !",Toast.LENGTH_SHORT).show();
            }
        });

        btn_magnetometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Yet to implement !",Toast.LENGTH_SHORT).show();
            }
        });

        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Yet to implement !",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void retrieveCallLogs(Context context) {
//        StringBuffer stringBuffer = new StringBuffer();

        try{

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            } else {
                Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                        null, null, null, CallLog.Calls.DATE + " DESC");
                int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
                int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
                int date = cursor.getColumnIndex(CallLog.Calls.DATE);
                int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
                while (cursor.moveToNext()) {
                    String phNumber = cursor.getString(number);
                    String callType = cursor.getString(type);
                    String callDate = cursor.getString(date);
                    Date callDayTime = new Date(Long.valueOf(callDate));
                    String callDuration = cursor.getString(duration);
                    String dir = null;
                    int dircode = Integer.parseInt(callType);
                    switch (dircode) {
                        case CallLog.Calls.OUTGOING_TYPE:
                            dir = "OUTGOING";
                            break;
                        case CallLog.Calls.INCOMING_TYPE:
                            dir = "INCOMING";
                            break;

                        case CallLog.Calls.MISSED_TYPE:
                            dir = "MISSED";
                            break;
                    }
//                stringBuffer.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
//                        + dir + " \nCall Date:--- " + callDayTime
//                        + " \nCall duration in sec :--- " + callDuration);
//                stringBuffer.append("\n----------------------------------");

//                    Toast.makeText(getApplicationContext(),"\nPhone Number:--- " + phNumber + " \nCall Type:--- "
//                            + dir + " \nCall Date:--- " + callDayTime
//                            + " \nCall duration in sec :--- " + callDuration,Toast.LENGTH_SHORT).show();

                    call_log_list.add(phNumber);
                }

                Toast.makeText(context,"Total "+call_log_list.size()+" call logs ready to be synced",Toast.LENGTH_SHORT).show();
                btn_callLogs.setText(call_log_list.size()+" Call Logs ");
                btn_callLogs.setClickable(Boolean.FALSE);
                btn_callLogs.setBackgroundColor(Color.CYAN);
                cursor.close();

//            return stringBuffer.toString();

            }


        } catch (Exception e) {
            Log.d("mylog", e.getMessage());
        }

    }

    private void retrieveContacts() {

        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            getContactNumber();
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
//            lstNames.setAdapter(adapter);
        }
    }

    private void getContactNumber() {

        try{

            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
            while (phones.moveToNext())
            {
//                String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                Toast.makeText(getApplicationContext(),name +" : "+phoneNumber, Toast.LENGTH_LONG).show();
                contact_list.add(phoneNumber);

            }
            Toast.makeText(getApplicationContext(),"Total "+contact_list.size()+" contacts ready to be synced", Toast.LENGTH_SHORT).show();
            btn_contacts.setText(contact_list.size()+" Contacts ");
            btn_contacts.setClickable(Boolean.FALSE);
            btn_contacts.setBackgroundColor(Color.CYAN);
            phones.close();

        } catch (Exception e){
            Log.d("myLog",e.getMessage());
        }
    }




}
