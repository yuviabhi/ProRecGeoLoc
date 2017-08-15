package com.abhisek.prorecgeoloc;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

/**
 * Created by Abhisek Chowdhury on 9/5/2016.
 */
public class Constants {

//    public static String serverIP = "10.15.9.65";
    public static String serverIP = "192.168.1.6";
    public static String serverPort = "80";


    final public static String php_server_login_url_getMethod = "http://"+serverIP+":"+serverPort+"/prorec/login.php";
    final public static String php_server_login_url = "http://"+serverIP+":"+serverPort+"/prorec/login.php";
    final public static String php_server_syncdata_url = "http://"+serverIP+":"+serverPort+"/prorec/sync.php";


}
