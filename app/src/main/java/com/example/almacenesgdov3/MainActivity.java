package com.example.almacenesgdov3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.almacenesgdov3.objetos.Usuarios;
import com.example.almacenesgdov3.recursos.VolleySingleton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText edtUsuario, edtPassword;
    private Button btnLogin;
    private String servidor;
    private Gson gson = new Gson();
    private Usuarios[] persona;
    private Usuarios datosPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializa();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setEnabled(false);
                validaUsuario(servidor+"loginApps.php");
            }
        });
    }

    private void inicializa(){
        edtUsuario = findViewById(R.id.edtUsuario);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        servidor = getString(R.string.servername);
    }

    private void validaUsuario(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    persona = gson.fromJson(response, Usuarios[].class);
                    datosPersona = persona[0];
                    if(datosPersona.getStatusUsuario().equals("A") && !datosPersona.getIdPuesto().equals("1") && !datosPersona.getIdSucursal().equals("1")) {
                        Intent intent = new Intent(getApplicationContext(), Menu.class);
                        intent.putExtra("usuario", datosPersona);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Usuario sin permisos para ingresar", Toast.LENGTH_LONG).show();
                    btnLogin.setEnabled(true);
                }else{
                    Toast.makeText(getApplicationContext(), "Error en Usuario o Contraseña", Toast.LENGTH_LONG).show();
                    btnLogin.setEnabled(true);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                btnLogin.setEnabled(true);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usuario", edtUsuario.getText().toString());
                parametros.put("password", edtPassword.getText().toString());
                return parametros;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}