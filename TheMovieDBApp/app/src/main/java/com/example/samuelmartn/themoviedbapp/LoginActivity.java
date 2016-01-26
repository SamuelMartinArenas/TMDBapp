package com.example.samuelmartn.themoviedbapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginActivity extends Activity {
    static ArrayList<String> respuesta;
    public String loginID;
    private String newToken;
    private String secToken;
    private String usuario;
    private String contraseña;
    static String API_KEY = "4e1d84ada617a4a0ec4f6d253915c661";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button boton=(Button)findViewById(R.id.button);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario=((EditText)findViewById(R.id.editTextUsuario)).getText().toString();
                contraseña=((EditText)findViewById(R.id.editTextCont)).getText().toString();
                if(usuario.isEmpty()||contraseña.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Por favor, Rellene todos los campos "+usuario,
                            Toast.LENGTH_LONG).show();
                }
                else if(contraseña.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Por favor, introduzca su contraseña "+usuario,
                            Toast.LENGTH_LONG).show();
                }
                else
                {

                //AQUI SE HARIA LA CONEXION Y CONSEGUIRIAMOS EL SESSION_ID PARA PEDIR LAS PELICULAS FAVORITAS
                   // "loginID" ES LO QUE UTILIZARIA DESPUES PARA LA PETICION DE LA LISTA DE FAVORITOS
                    //NO ME HA DADO TIEMPO A CONSEGUIR QUE FUNCIONE


                   /* try {
                        newToken=getIDsFromAPI("https://api.themoviedb.org/3/authentication/token/new?api_key=" + API_KEY);
                        if(newToken!=null){
                            String secToken=getIDsFromAPI("https://api.themoviedb.org/3/authentication/token/validate_with_login?username="+usuario+"&request_token="+newToken+"&api_key=" + API_KEY +"&password="+contraseña)[0];
                            if(secToken!=null){
                                 loginID=getIDsFromAPI("http://api.themoviedb.org/3/authentication/session/new?request_token="+secToken+"&api_key=" + API_KEY)[0];
                            }

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(loginID!=null)
                    {
                        Toast.makeText(LoginActivity.this, "usuario registrado correctamente",
                                Toast.LENGTH_LONG).show();
                    }
                     */
                   Toast.makeText(LoginActivity.this, "Bienvenido "+usuario,
                       Toast.LENGTH_LONG).show();

                Intent mainIntent = new Intent().setClass(
                        LoginActivity.this, MainActivity.class);
                startActivity(mainIntent);

                }

            }
        });
    }


    public String getIDsFromAPI(String urlString) throws IOException {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JSONResult;
        String res=null;
        try {
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        //Read the input stream into a String
        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) {
            return null;
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }
        if (buffer.length() == 0) {
            return null;
        }
        JSONResult = buffer.toString();

        try {
            res= getPathsFromJSON(JSONResult);
        } catch (JSONException e) {
            return null;
        }
    }catch(Exception e)
    {

    }finally {
        if(urlConnection!=null)
        {
            urlConnection.disconnect();
        }
        if(reader!=null)
        {
            try{
                reader.close();
            }catch(final IOException e)
            {
            }
        }
    }
        return res;
    }
    public String getPathsFromJSON(String JSONStringParam) throws JSONException{

        JSONObject JSONString = new JSONObject(JSONStringParam);

        JSONArray idsArray = JSONString.getJSONArray("results");
        String sendPath = "nada";

        for(int i = 0; i<idsArray.length();i++)
        {
            JSONObject token = idsArray.getJSONObject(i);
            sendPath = token.getString("request_token");

        }
        return sendPath;
    }


    @Override
         public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
