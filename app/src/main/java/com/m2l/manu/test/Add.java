package com.m2l.manu.test;

import android.app.AlertDialog;
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


public class Add extends Login {


        public final static String intentextraId = "m2l.intent.IDADMIN";
        public final static String intentextraToken = "m2l.intent.TOKEN";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add);

            // try {

            Intent i = getIntent();


            final String extraId = i.getStringExtra(Main.getData.extraId);
            final String extraToken = i.getStringExtra(Main.getData.extraToken);

            JSONArray user = null;
            JSONObject ligue = null;


            final EditText txtnom = (EditText) findViewById(R.id.txtnom);
            final EditText txtprenom = (EditText) findViewById(R.id.txtprenom);
            final EditText txtemail = (EditText) findViewById(R.id.txtEmail);

            Button modify = (Button) findViewById(R.id.btnajouter);
            modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        if(txtnom.getText().toString().equals("")){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Add.this);

                            // set title
                            alertDialogBuilder.setTitle("Erreur lors de l'ajout");

                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Veuillez saisir un nom")
                                    .setCancelable(true);


                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();

                        }else {
                            if (txtprenom.getText().toString().equals("")) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Add.this);

                                // set title
                                alertDialogBuilder.setTitle("Erreur lors de l'ajout");

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
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Add.this);

                                    // set title
                                    alertDialogBuilder.setTitle("Erreur lors de l'ajout");

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
                                    paramstr.add(new BasicNameValuePair("nom", txtnom.getText().toString().trim()));
                                    paramstr.add(new BasicNameValuePair("prenom", txtprenom.getText().toString().trim()));
                                    paramstr.add(new BasicNameValuePair("mail", txtemail.getText().toString().trim()));
                                    Log.w("nom", txtnom.getText().toString());
                                    Log.w("prenom", txtprenom.getText().toString());
                                    Log.w("email", txtemail.getText().toString());
                                    String paramString = URLEncodedUtils.format(paramstr, "utf-8");

                                    String url = "http://5.39.28.169/GestionEmploye/ws-main-create.php";

                                    HttpGet httpGet = new HttpGet(url + "?" + paramString);
                                    HttpResponse httpResponse = httpClient.execute(httpGet);
                                    if (httpResponse.getStatusLine().getStatusCode() == 0) {
                                        Log.w("WARNING", "Problème au niveau du serveur");
                                    }
                                    HttpEntity entity = httpResponse.getEntity();
                                    String json = EntityUtils.toString(entity);

                                    Log.v("INFO", json);

                                    if (json.equals("invalidmail")) {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Add.this);

                                        // set title
                                        alertDialogBuilder.setTitle("Erreur lors de l'ajout");

                                        // set dialog message
                                        alertDialogBuilder
                                                .setMessage("L'email " + txtemail.getText().toString() + " n'est pas valide")
                                                .setCancelable(true);


                                        // create alert dialog
                                        AlertDialog alertDialog = alertDialogBuilder.create();

                                        // show it
                                        alertDialog.show();

                                    } else {

                                        final TextView lblpass = (TextView) findViewById(R.id.lblinfo);
                                        lblpass.setText("Voici le nouveau mot de passe de l'utilisateur :");
                                        final TextView lbltext = (TextView) findViewById(R.id.txtpassword);
                                        lbltext.setText(json);
                                        lbltext.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                final EditText edit = (EditText)findViewById(R.id.texty);
                                                edit.setText(lbltext.getText());
                                            }


                                        });

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
            Button btnreturn = (Button) findViewById(R.id.btnretour);
            btnreturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent secondeActivite = new Intent(Add.this, Main.class);

                    // On rajoute des extra
                    secondeActivite.putExtra(intentextraId, id);
                    secondeActivite.putExtra(intentextraToken, token);

                    // Puis on lance l'intent !
                    startActivity(secondeActivite);
                }
            });
        }
    }



