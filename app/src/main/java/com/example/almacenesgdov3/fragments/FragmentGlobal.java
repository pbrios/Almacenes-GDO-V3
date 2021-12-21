package com.example.almacenesgdov3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.almacenesgdov3.R;
import com.example.almacenesgdov3.objetos.Productos;
import com.example.almacenesgdov3.objetos.Usuarios;
import com.example.almacenesgdov3.recursos.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FragmentGlobal extends Fragment {
    private List<Productos> productosArrayList;
    private Gson gson = new Gson();
    private Productos[] productosList;
    private Productos[] productos;
    private ImageButton btnCamara, btnBuscar;
    private EditText edtCodigo, edtCantidad;
    private TextView txvCodigo, txvDescripcion, txvUnidad;
    private CheckBox chTipo;
    private String almacenO, almacenD, servidor, estante, archivoPhp;
    private Usuarios usuarios;
    private int tipo;

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public void setAlmacenO(String almacenO) {
        this.almacenO = almacenO;
    }

    public void setAlmacenD(String almacenD) {
        this.almacenD = almacenD;
    }

    private String getEstante() {
        return estante;
    }

    private String getAlmacenO() {
        return almacenO;
    }

    private String getAlmacenD() {
        return almacenD;
    }

    public FragmentGlobal(int tipo){
        this.tipo = tipo;
        archivoPhp = ruta(tipo);
    }

    private String ruta(int tipo){
        String seleccion = null;
        switch (tipo){
            case 1:
                seleccion = "insertar_traspaso.php";
                break;
            case 2:
                seleccion = "insertar_producto.php";
                break;
            default:
                break;
        }
        return seleccion;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_global, container, false);

        inicializa(root);

        return root;
    }

    private void inicializa(View root){
        btnCamara = root.findViewById(R.id.btnCamara);
        btnBuscar = root.findViewById(R.id.btnBuscar);
        edtCodigo = root.findViewById(R.id.edtCodigo);
        edtCantidad = root.findViewById(R.id.edtCantidad);
        txvCodigo = root.findViewById(R.id.txvCodigo);
        txvDescripcion = root.findViewById(R.id.txvDescripcion);
        txvUnidad = root.findViewById(R.id.txvUnidad);
        chTipo = root.findViewById(R.id.chTipo);
    }

    private void buscarProducto(String codigo){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(servidor+"buscar_producto.php?codigo="+codigo, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                productos = null;
                productos = gson.fromJson(response.toString(), Productos[].class);
                //cargaCampos(productos[0]);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Codigo no registrado "+edtCodigo.getText(), Toast.LENGTH_LONG).show();
                //limpiaCampos();
            }
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }

    private void ejecutarServicio(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        Date date = new Date();
        final String fechaI = dateFormat.format(date);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, servidor+"insertar_producto.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //limpiaCampos();
                Toast.makeText(getContext(), "Conteo Agregado", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usuario", usuarios.getId());
                parametros.put("sucursal", usuarios.getIdSucursal());
                parametros.put("almacenO", usuarios.getIdAlmacen());
                parametros.put("codigo", txvCodigo.getText().toString());
                parametros.put("cantidad", edtCantidad.getText().toString());
                if(tipo == 1)
                    parametros.put("almacenD", almacenD);
                else if(tipo == 2)
                    parametros.put("estante", estante);
                parametros.put("fecha", fechaI);
                return parametros;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
    }
}