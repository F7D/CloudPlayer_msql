package us.vp7.cloudplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;


public class Splash extends AppCompatActivity {
    BufferedReader reader;
    RotateLoading rt;
    public static ArrayList<String> id=new ArrayList<>();
    public static ArrayList<String> name=new ArrayList<>();
    public static ArrayList<String> artist=new ArrayList<>();
    public static ArrayList<String> picture=new ArrayList<>();
    public static ArrayList<String> link_mp3=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        KenBurnsView kbv= (KenBurnsView) findViewById(R.id.ken);
        kbv.resume();
        check();



    }

    private void check() {
        if (Connected()){
            new get_dta_from_server().execute();
        }else{
            Dialog();
        }

    }

    private void Dialog() {

        AlertDialog.Builder builder=new AlertDialog.Builder(Splash.this);
        builder.setTitle("No internet connection");
        builder.setMessage(" Please check your network connection.");
        builder.setIcon(R.mipmap.disconnect);
        builder.setPositiveButton("Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
                check();
            }
        });
        builder.setNegativeButton("never mind", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                finish();
            }
        });
        AlertDialog dilog=builder.create();
        dilog.show();

    }

    private boolean Connected() {
        ConnectivityManager cm=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni=cm.getActiveNetworkInfo();
        if (ni==null){
            return false;
        }else {
            return true;
        }
    }

    public class get_dta_from_server extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rt= (RotateLoading) findViewById(R.id.rotateloading);
            rt.start();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            GetJsonData_();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            rt.stop();
            startActivity(new Intent(Splash.this,Main_Activity.class));
            finish();

        }
    }
    // (1266) is my password from php file
    // (https://vp7.us/apps_/ZdchY/test/1.php) php connection for msql
    private void GetJsonData_() {
        String code="1266";
        try {

            String getdata= URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(code,"UTF-8");
            URL url=new URL("https://vp7.us/apps_/ZdchY/test/1.php");
            URLConnection conn=url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr=new OutputStreamWriter(conn.getOutputStream());
            wr.write(getdata);
            wr.flush();
            reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb=new StringBuilder();
            String line=null;
            while ((line=reader.readLine())!=null){
                sb.append(line+"\n");
            }
            String result=sb.toString();
            JSONObject json=new JSONObject(result);
            JSONArray data=json.getJSONArray("data");
            for (int i=0;i<data.length();i++){
                JSONObject object=data.getJSONObject(i);
                id.add(object.getString("id"));
                name.add(object.getString("name"));
                artist.add(object.getString("artist"));
                picture.add(object.getString("picture"));
                link_mp3.add(object.getString("link_128"));

                Log.i("Farzad==> ",object.getString("name"));
                Log.i("Farzad==> ",object.getString("artist"));

            }



        }catch(IOException e){
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
