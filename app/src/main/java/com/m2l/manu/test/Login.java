package com.m2l.manu.test;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.os.StrictMode;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Login extends Activity {

    private Button connect = null;
    public TextView message = null;
    public ProgressBar mProgressBar = null;
    public static String id = null;
    public static String token = null;
    public EditText usr = null;
    public EditText pass = null;
    public String json = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        connect = (Button) findViewById(R.id.btnconnect);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vue) {
                LoginTask log = new LoginTask();
                log.execute();


            }
        });


    }


    public class LoginTask extends AsyncTask<Void, Integer, Void> {


        public final static String extraId = "m2l.intent.IDADMIN";
        public final static String extraToken = "m2l.intent.TOKEN";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(getApplicationContext(), "Connexion à la base de donnée", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... arg0) {



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {
                JSONArray user = null;

                message = (TextView) findViewById(R.id.txtmessage);
                usr = (EditText) findViewById(R.id.editText);
                pass = (EditText) findViewById(R.id.editText2);

                String url = "http://5.39.28.169/GestionEmploye/ws-login.php";//"http://192.168.0.32/ppe/GestionEmploye/GestionEmploye/json.php";


                DefaultHttpClient httpClient = new DefaultHttpClient();

                HttpParams params = new BasicHttpParams();
                if (!usr.getText().toString().equals("") || !pass.getText().toString().equals("")) {
                    Log.w("Username = ", usr.getText().toString());
                    Log.w("Password = ", pass.getText().toString());
                    List<NameValuePair> paramstr = new ArrayList<NameValuePair>();
                    paramstr.add(new BasicNameValuePair("username", usr.getText().toString()));
                    paramstr.add(new BasicNameValuePair("pass", pass.getText().toString()));
                    String paramString = URLEncodedUtils.format(paramstr, "utf-8");
                    //params.setParameter("username", usr.getText().toString());
                    //params.setParameter("pass", pass.getText().toString());
                    //httpGet.setParams(params);


                    HttpGet httpGet = new HttpGet(url + "?" + paramString);
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 0) {
                        Log.w("WARNING", "Problème au niveau du serveur");
                    }
                    HttpEntity entity = httpResponse.getEntity();
                    String json = EntityUtils.toString(entity);

                    Log.v("INFO", json);
                    JSONObject jObj = new JSONObject(json);


//                         user = jObj.getJSONArray("user");
                    JSONObject c = jObj.getJSONObject("user");
                    if (!c.has("0")) {

                        message.setText("Nom d'utilisateur ou mot de passe incorrect");
                    } else {
                        id = c.getString("id_emp");

                        token = c.getString("0");

                        Intent secondeActivite = new Intent(Login.this, Main.class);

                        // On rajoute des extra
                        secondeActivite.putExtra(extraId, id);
                        secondeActivite.putExtra(extraToken, token);

                        // Puis on lance l'intent !
                        startActivity(secondeActivite);

                    }
                }else{
                    Log.w("WARNING", "Username or password empty !");
                    message.setText("Veuillez saisir tous les champs");
                }
                    Log.w("json :", json);
                }catch(IOException e1){
                    e1.printStackTrace();
                    Log.w("IO exception :", e1);
                }catch(JSONException e){
                    e.printStackTrace();
                    Log.v("JSON exception :", e.toString());
                }



        }
    }
}







