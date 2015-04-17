package com.m2l.manu.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Update extends Login {


    public final static String intentextraId = "m2l.intent.IDADMIN";
    public final static String intentextraToken = "m2l.intent.TOKEN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        // try {

        Intent i = getIntent();


        final String extraId = i.getStringExtra(Main.getData.extraId);
        final String extraToken = i.getStringExtra(Main.getData.extraToken);

         String extraId_emp = i.getStringExtra(Main.getData.extraId_emp);
         String extraNom_emp = i.getStringExtra(Main.getData.extraNom_emp);
         String extraPrenom_emp = i.getStringExtra(Main.getData.extraPrenom_emp);
         String extraMail_emp = i.getStringExtra(Main.getData.extraMail_emp);
        JSONArray user = null;
        JSONObject ligue = null;


        final EditText txtnom = (EditText) findViewById(R.id.txtnom);
        final EditText txtprenom = (EditText) findViewById(R.id.txtprenom);
        final EditText txtemail = (EditText) findViewById(R.id.txtEmail);
        final TextView lblid = (TextView) findViewById(R.id.lblid);
        lblid.setText(extraId_emp);
        lblid.setVisibility(View.GONE);
        txtnom.setText(extraNom_emp);
        txtprenom.setText(extraPrenom_emp);
        txtemail.setText(extraMail_emp);
        Button generate = (Button) findViewById(R.id.btngenerer);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (txtnom.getText().toString().equals("")) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Update.this);

// set title
                        alertDialogBuilder.setTitle("Erreur lors de la modification");

// set dialog message
                        alertDialogBuilder
                                .setMessage("Veuillez saisir un nom")
                                .setCancelable(true);


// create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    } else {
                        if (txtprenom.getText().toString().equals("")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Update.this);

                            // set title
                            alertDialogBuilder.setTitle("Erreur lors de la modification");

                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Veuillez saisir un prénom")
                                    .setCancelable(true);


                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();

                        } else {
                            if (txtemail.getText().toString().equals("")) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Update.this);

                                // set title
                                alertDialogBuilder.setTitle("Erreur lors de la modification");

                                // set dialog message
                                alertDialogBuilder
                                        .setMessage("Veuillez saisir un e-mail")
                                        .setCancelable(true);


                                // create alert dialog
                                AlertDialog alertDialog = alertDialogBuilder.create();

                                // show it
                                alertDialog.show();

                            } else {
                                DefaultHttpClient httpClient = new DefaultHttpClient();

                                List<NameValuePair> paramstr = new ArrayList<>();
                                paramstr.add(new BasicNameValuePair("id", extraId));
                                paramstr.add(new BasicNameValuePair("token", extraToken));
                                paramstr.add(new BasicNameValuePair("nom", txtnom.getText().toString()));
                                paramstr.add(new BasicNameValuePair("prenom", txtprenom.getText().toString()));
                                paramstr.add(new BasicNameValuePair("mail", txtemail.getText().toString()));
                                paramstr.add(new BasicNameValuePair("idupdate", lblid.getText().toString()));
                                paramstr.add(new BasicNameValuePair("pass", "1"));
                                Log.w("nom", txtnom.getText().toString());
                                Log.w("prenom", txtprenom.getText().toString());
                                Log.w("email", txtemail.getText().toString());
                                Log.w("idemp", lblid.getText().toString());

                                String paramString = URLEncodedUtils.format(paramstr, "utf-8");

                                String url = "http://5.39.28.169/GestionEmploye/ws-main-update.php";

                                HttpGet httpGet = new HttpGet(url + "?" + paramString);
                                HttpResponse httpResponse = httpClient.execute(httpGet);
                                if (httpResponse.getStatusLine().getStatusCode() == 0) {
                                    Log.w("WARNING", "Problème au niveau du serveur");
                                }
                                HttpEntity entity = httpResponse.getEntity();
                                String json = EntityUtils.toString(entity);


                                Log.v("INFO", json);
                                if (json.equals("invalidmail")) {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Update.this);

                                    // set title
                                    alertDialogBuilder.setTitle("Erreur lors de la modification");

                                    // set dialog message
                                    alertDialogBuilder
                                            .setMessage("L'email " + txtemail.getText().toString() + " n'est pas valide")
                                            .setCancelable(true);


                                    // create alert dialog
                                    AlertDialog alertDialog = alertDialogBuilder.create();

                                    // show it
                                    alertDialog.show();

                                }else{
                                  TextView passcode = (TextView) findViewById(R.id.lblpasse);
                                    passcode.setText(json);
                                }

                            }
                        }
                    }
                }  catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    });


                Button modify = (Button) findViewById(R.id.btnmodifier);
                modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {

                            if (txtnom.getText().toString().equals("")) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Update.this);

                                // set title
                                alertDialogBuilder.setTitle("Erreur lors de la modification");

                                // set dialog message
                                alertDialogBuilder
                                        .setMessage("Veuillez saisir un nom")
                                        .setCancelable(true);


                                // create alert dialog
                                AlertDialog alertDialog = alertDialogBuilder.create();

                                // show it
                                alertDialog.show();

                            } else {
                                if (txtprenom.getText().toString().equals("")) {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Update.this);

                                    // set title
                                    alertDialogBuilder.setTitle("Erreur lors de la modification");

                                    // set dialog message
                                    alertDialogBuilder
                                            .setMessage("Veuillez saisir un prénom")
                                            .setCancelable(true);


                                    // create alert dialog
                                    AlertDialog alertDialog = alertDialogBuilder.create();

                                    // show it
                                    alertDialog.show();

                                } else {
                                    if (txtemail.getText().toString().equals("")) {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Update.this);

                                        // set title
                                        alertDialogBuilder.setTitle("Erreur lors de la modification");

                                        // set dialog message
                                        alertDialogBuilder
                                                .setMessage("Veuillez saisir un e-mail")
                                                .setCancelable(true);


                                        // create alert dialog
                                        AlertDialog alertDialog = alertDialogBuilder.create();

                                        // show it
                                        alertDialog.show();

                                    } else {
                                        DefaultHttpClient httpClient = new DefaultHttpClient();

                                        List<NameValuePair> paramstr = new ArrayList<>();
                                        paramstr.add(new BasicNameValuePair("id", extraId));
                                        paramstr.add(new BasicNameValuePair("token", extraToken));
                                        paramstr.add(new BasicNameValuePair("nom", txtnom.getText().toString()));
                                        paramstr.add(new BasicNameValuePair("prenom", txtprenom.getText().toString()));
                                        paramstr.add(new BasicNameValuePair("mail", txtemail.getText().toString()));
                                        paramstr.add(new BasicNameValuePair("idupdate", lblid.getText().toString()));
                                        Log.w("nom", txtnom.getText().toString());
                                        Log.w("prenom", txtprenom.getText().toString());
                                        Log.w("email", txtemail.getText().toString());
                                        Log.w("idemp", lblid.getText().toString());

                                        String paramString = URLEncodedUtils.format(paramstr, "utf-8");

                                        String url = "http://5.39.28.169/GestionEmploye/ws-main-update.php";

                                        HttpGet httpGet = new HttpGet(url + "?" + paramString);
                                        HttpResponse httpResponse = httpClient.execute(httpGet);
                                        if (httpResponse.getStatusLine().getStatusCode() == 0) {
                                            Log.w("WARNING", "Problème au niveau du serveur");
                                        }
                                        HttpEntity entity = httpResponse.getEntity();
                                        String json = EntityUtils.toString(entity);


                                        Log.v("INFO", json);
                                        if (json.equals("invalidmail")) {
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Update.this);

                                            // set title
                                            alertDialogBuilder.setTitle("Erreur lors de la modification");

                                            // set dialog message
                                            alertDialogBuilder
                                                    .setMessage("L'email " + txtemail.getText().toString() + " n'est pas valide")
                                                    .setCancelable(true);


                                            // create alert dialog
                                            AlertDialog alertDialog = alertDialogBuilder.create();

                                            // show it
                                            alertDialog.show();

                                        } else {

                                            Intent secondeActivite = new Intent(Update.this, Main.class);

                                            // On rajoute des extra
                                            secondeActivite.putExtra(intentextraId, id);
                                            secondeActivite.putExtra(intentextraToken, token);

                                            // Puis on lance l'intent !
                                            startActivity(secondeActivite);
                                        }
                                    }
                                }
                            }
                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }
        }

