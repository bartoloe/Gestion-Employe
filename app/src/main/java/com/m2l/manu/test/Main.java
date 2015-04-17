package com.m2l.manu.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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


public class Main extends Login {

    public static String id_emp;
    public static String nom_emp;
    public static String prenom_emp;
    public static String mail_emp;
    public static Integer count = 0;
    public static String nom_ligue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getData gData = new getData();
        gData.execute();


        // Puis on récupère l'âge donné dans l'autre activité, ou 0 si cet extra n'est pas dans l'intent


        // Puis on récupère l'âge donné dans l'autre activité, ou 0 si cet extra n'est pas dans l'intent

    }

    public class getData extends AsyncTask<Void, Integer, Void> {
        public final static String extraId = "m2l.intent.IDADMIN";
        public final static String extraToken = "m2l.intent.TOKEN";
        public final static String extraId_emp = "m2l.intent.ID_EMP";
         public final static String extraNom_emp = "m2l.intent.NOM";
         public final static String extraPrenom_emp = "m2l.intent.PRENOM";
        public final static String extraMail_emp = "m2l.intent.EMAIL";

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

                Intent i = getIntent();
                final String iextraId = i.getStringExtra(LoginTask.extraId);
                final String iextraToken = i.getStringExtra(LoginTask.extraToken);

                JSONArray user = null;
                JSONObject ligue = null;

                TextView msgligue = (TextView) findViewById(R.id.lblligue);


                DefaultHttpClient httpClient = new DefaultHttpClient();

                HttpParams params = new BasicHttpParams();

                List<NameValuePair> paramstr = new ArrayList<NameValuePair>();
                paramstr.add(new BasicNameValuePair("id", iextraId));
                paramstr.add(new BasicNameValuePair("token", iextraToken));

                String paramString = URLEncodedUtils.format(paramstr, "utf-8");

                String url = "http://5.39.28.169/GestionEmploye/ws-main.php";

                HttpGet httpGet = new HttpGet(url + "?" + paramString);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode() == 0) {
                    Log.w("WARNING", "Problème au niveau du serveur");
                }
                HttpEntity entity = httpResponse.getEntity();
                String json = EntityUtils.toString(entity);


                Log.v("INFO", json);
                JSONObject jObj = new JSONObject(json);

                user = jObj.getJSONArray("user");

                ligue = jObj.getJSONObject("ligue");
                String messageligue = "Ligue de ";
                JSONObject infoligueobject = ligue.getJSONObject("infoligue");
                nom_ligue = infoligueobject.getString("nom_ligue");
                msgligue.setText(messageligue + nom_ligue);

               TextView t;

                t=(TextView)findViewById(R.id.lblligue);
                t.setText(msgligue.getText());

                TableLayout t1;

                TableLayout tl = (TableLayout) findViewById(R.id.main_table);


                TableRow tr_head = new TableRow(Main.this);
                tr_head.setId(View.generateViewId());
                tr_head.setBackgroundColor(Color.LTGRAY);
                tr_head.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT));
                TextView prenom = new TextView(Main.this);
                prenom.setId(View.generateViewId());
                prenom.setText("Nom");
                prenom.setTextColor(Color.DKGRAY);
                prenom.setPadding(5, 5, 5, 5);
                prenom.setTextSize(20);
                prenom.setTypeface(null, Typeface.BOLD);
                tr_head.addView(prenom);// add the column to the table row here

                TextView nom = new TextView(Main.this);
                nom.setId(View.generateViewId());// define id that must be unique
                nom.setText("Prénom"); // set the text for the header
                nom.setTextColor(Color.DKGRAY); // set the color
                nom.setPadding(5, 5, 5, 5); // set the padding (if required)
                nom.setTextSize(20);
                nom.setTypeface(null, Typeface.BOLD);
                tr_head.addView(nom); // add the column to the table row here

                TextView email = new TextView(Main.this);
                email.setId(View.generateViewId());// define id that must be unique
                email.setText("E-mail"); // set the text for the header
                email.setTextColor(Color.DKGRAY); // set the color
                email.setTextSize(20);
                email.setTypeface(null, Typeface.BOLD);
                email.setPadding(5, 5, 5, 5); // set the padding (if required)
                tr_head.addView(email); // add the column to the table row here


                tl.addView(tr_head, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.FILL_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));



                for (count = 0; count < user.length(); count++) {

                    JSONObject z = user.getJSONObject(count);
                    id_emp = z.getString("id_emp");
                    nom_emp = z.getString("nom_emp");
                    prenom_emp = z.getString("prenom_emp");
                    mail_emp = z.getString("mail_emp");
                    boolean tailleoverflow  = false;
                    boolean tailleoverflow2 = false;
                    if((nom_emp.length() + prenom_emp.length() + mail_emp.length()) > 30){
                        tailleoverflow = true;
                        if((nom_emp.length() + prenom_emp.length() + mail_emp.length()) > 45){
                            tailleoverflow2 = true;
                        }
                     }
                    Log.w("<!>", z.getString("nom_emp"));
                    Log.w("<!>", z.getString("prenom_emp"));
                    Log.w("<!>", z.getString("mail_emp"));


                    //while (cursor.moveToNext()) {
                    //  String date = cursor.getString(2);// get the first variable
                    //Double weight_kg = cursor.getString(4);// get the second variable

// Create the table row
                   final TableRow tr = new TableRow(Main.this);
                    if (count % 2 != 0) tr.setBackgroundColor(Color.LTGRAY);
                    tr.setId(View.generateViewId());
                    tr.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.FILL_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));

//Create two columns to add as table data
                    // Create a TextView to add date

                    final TextView txtnom = new TextView(Main.this);
                    txtnom.setId(View.generateViewId());
                    txtnom.setTextSize(16);
                    if(tailleoverflow) {
                        txtnom.setTextSize(12);
                    }
                    if(tailleoverflow2){
                        txtnom.setTextSize(7);
                    }
                    txtnom.setText(nom_emp);
                    txtnom.setPadding(2, 0, 5, 0);
                    txtnom.setTextColor(Color.GRAY);
                    tr.addView(txtnom);
                    final TextView txtprenom = new TextView(Main.this);
                    txtprenom.setId(View.generateViewId());
                    txtprenom.setText(prenom_emp);
                    txtprenom.setTextSize(16);
                    if(tailleoverflow) {
                        txtprenom.setTextSize(12);
                    }
                    if(tailleoverflow2){
                        txtprenom.setTextSize(7);
                    }

                    txtprenom.setTextColor(Color.GRAY);
                    tr.addView(txtprenom);
                    final TextView txtmail = new TextView(Main.this);
                    txtmail.setId(View.generateViewId());
                    txtmail.setText(mail_emp);
                    txtmail.setTextColor(Color.GRAY);
                    if(tailleoverflow) {
                        txtmail.setTextSize(12);
                    }
                    if(tailleoverflow2){
                        txtmail.setTextSize(7);
                    }

                    tr.addView(txtmail);
                    final TextView txtid = new TextView(Main.this);
                    txtid.setId(View.generateViewId());
                    txtid.setText(id_emp);
                    txtid.setVisibility(View.GONE);
                    tl.addView(txtid);

                    ImageButton add = (ImageButton) findViewById(R.id.ajouter);
                    add.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {

                                                   Intent secondeActivite = new Intent(Main.this, Add.class);

                                                   // On rajoute des extra

                                                   Intent i = getIntent();

                                                   String xtraId = i.getStringExtra(LoginTask.extraId);
                                                   String xtraToken = i.getStringExtra(LoginTask.extraToken);

                                                   secondeActivite.putExtra(extraId, xtraId);
                                                   secondeActivite.putExtra(extraToken, xtraToken);


                                                   // Puis on lance l'intent !
                                                   startActivity(secondeActivite);
                                               }
                                           }
                    );

                ImageButton modify = new ImageButton(Main.this);
                modify.setBackgroundColor(Color.TRANSPARENT);
                modify.setImageResource(R.drawable.update3);
                modify.setPadding(2, 10, 10, 0);
                modify.setId(View.generateViewId());
                modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent secondeActivite = new Intent(Main.this, Update.class);

                        // On rajoute des extra

                        Intent i = getIntent();

                        String xtraId = i.getStringExtra(LoginTask.extraId);
                        String xtraToken = i.getStringExtra(LoginTask.extraToken);

                        secondeActivite.putExtra(extraId, xtraId);
                        secondeActivite.putExtra(extraToken, xtraToken);
                        secondeActivite.putExtra(extraId_emp, txtid.getText());
                        secondeActivite.putExtra(extraNom_emp, txtnom.getText());
                        secondeActivite.putExtra(extraPrenom_emp, txtprenom.getText());
                        secondeActivite.putExtra(extraMail_emp, txtmail.getText());


                        // Puis on lance l'intent !
                        startActivity(secondeActivite);


                    }
                });

                tr.addView(modify);
                ImageButton delete = new ImageButton(Main.this);
                delete.setBackgroundColor(Color.TRANSPARENT);
                delete.setImageResource(R.drawable.delete3);
                delete.setPadding(2, 10, 10, 0);
                delete.setId(View.generateViewId());
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main.this);

                        // set title
                        alertDialogBuilder.setTitle("Suppression d'un employé");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Etes vous sur de vouloir supprimer " + txtnom.getText() + " " + txtprenom.getText())
                                .setCancelable(false)
                                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Delete delete = new Delete(txtid.getText().toString());
                                        delete.execute();
                                        tr.setVisibility(View.GONE);

                                    }
                                })
                                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
                });

                tr.addView(delete);


// finally add this to the table row
                tl.addView(tr, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.FILL_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));


            }

        }catch (IOException e1) {
                e1.printStackTrace();
                Log.w("IO exception :", e1);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.v("JSON exception :", e.toString());
            }


        }
    }

    public class Delete extends AsyncTask<Void, Integer, Void> {

        String id;
        public Delete(String id_emp){
            this.id = id_emp;
          }
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
            JSONArray user = null;

            try {

                Intent i = getIntent();
                String extraId = i.getStringExtra(LoginTask.extraId);
                String extraToken = i.getStringExtra(LoginTask.extraToken);

                TextView msgligue = (TextView) findViewById(R.id.lblligue);


                DefaultHttpClient httpClient = new DefaultHttpClient();

                HttpParams params = new BasicHttpParams();

                List<NameValuePair> paramstr = new ArrayList<NameValuePair>();
                paramstr.add(new BasicNameValuePair("id", extraId));
                paramstr.add(new BasicNameValuePair("token", extraToken));
                paramstr.add(new BasicNameValuePair("idelete", id));

                Log.w("id", extraId);
                Log.w("token", extraToken);
                String paramString = URLEncodedUtils.format(paramstr, "utf-8");

                String url = "http://5.39.28.169/GestionEmploye/ws-main-delete.php";

                HttpGet httpGet = new HttpGet(url + "?" + paramString);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode() == 0) {
                    Log.w("WARNING", "Problème au niveau du serveur");
                }
                HttpEntity entity = httpResponse.getEntity();
                String json = EntityUtils.toString(entity);


                Log.v("INFO", json);

            }  catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


