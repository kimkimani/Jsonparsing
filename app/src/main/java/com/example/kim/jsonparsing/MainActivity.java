package com.example.kim.jsonparsing;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
Button click;
public  static TextView data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          click = (Button) findViewById(R.id.button);
         data= (TextView) findViewById(R.id.fetcheddata);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData process = new fetchData();
                process.execute();
            }
        });

    }
    public class fetchData extends AsyncTask<Void,Void,Void> {
        String data="";
        String singleParsed ="";
        String dataParsed ="";
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL("  http://172.19.152.113/tipscoreJSON/dailytips.php");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while(line != null){
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                JSONArray JA = new JSONArray(data);
                for(int i =0 ;i <JA.length(); i++){
                    JSONObject JO = (JSONObject) JA.get(i);
                    singleParsed =  "Time:" + JO.get("Time") + "\n"+
                            "league:" + JO.get("league") + "\n"+
                            "Match:" + JO.get("Match") + "\n"+
                            "Tips:" + JO.get("Tips") + "\n"+
                            "Odds:" + JO.get("Odds") + "\n"+
                            "Results:" + JO.get("Results") + "\n";



                    dataParsed = dataParsed + singleParsed +"\n" ;

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            MainActivity.data.setText(this.dataParsed);
        }

    }}

