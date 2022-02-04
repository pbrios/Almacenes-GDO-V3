package com.example.almacenesgdov3.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

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

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class FragmentGlobal extends Fragment implements View.OnClickListener{
    //private List<Productos> productosArrayList;
    private Gson gson = new Gson();
    //private Productos[] productosList;
    private Productos[] productos;
    private Productos producto;
    private ImageButton btnCamara, btnBuscar;
    private EditText edtCodigo, edtCantidad;
    private TextView txvCodigo, txvDescripcion, txvUnidad;
    private CheckBox chTipo;
    private String almacenO, almacenD, servidor, estante, archivoPhp, usuario, sucursal, idProducto;
    //private Usuarios usuarios;
    private int tipo;
    private float costoTotal = 0, precioTotal = 0;
    FragmentListaProductos fragmentListaProductos = new FragmentListaProductos();

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
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
                seleccion = "insertar_inventario.php";
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
        servidor = getString(R.string.servername);

        btnBuscar.setOnClickListener(this);
        btnCamara.setOnClickListener(this);
    }

    private void busquedaTexto(String codigo){
        fragmentListaProductos.setBusqueda(codigo);
        fragmentListaProductos.show(getChildFragmentManager(), "lista_productos");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                producto= (Productos) bundle.getSerializable("bundleKey2");
                cargaCampos(producto);
            }
        });
    }

    private void buscarProducto(String codigo){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(servidor+"buscar_producto.php?codigo="+codigo, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                productos = null;
                productos = gson.fromJson(response.toString(), Productos[].class);
                producto = productos[0];
                cargaCampos(producto);
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

    private void cargaCampos(Productos datosProductos){
        txvCodigo.setText(datosProductos.getCodigo());
        txvDescripcion.setText(datosProductos.getDescripcion());
        txvUnidad.setText(datosProductos.getUnidades());
        idProducto = datosProductos.getId();
    }

    public void ejecutarServicio(){
        float cantidadGuardar;
        float costo, precio;
        costo = Float.valueOf(producto.getCosto().toString());
        precio = Float.valueOf(producto.getPrecio().toString());
        cantidadGuardar = Float.valueOf(edtCantidad.getText().toString());
        costoTotal = costo*cantidadGuardar;
        precioTotal = precio*cantidadGuardar;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        Date date = new Date();
        final String fechaI = dateFormat.format(date);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, servidor+archivoPhp, new Response.Listener<String>() {
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
                parametros.put("usuario", getUsuario());
                parametros.put("sucursal", getSucursal());
                parametros.put("almacenO", getAlmacenO());
                parametros.put("codigo", idProducto);
                parametros.put("cantidad", edtCantidad.getText().toString());
                parametros.put("costo", "15.0");
                parametros.put("costoTotal", "15.0");
                if(tipo == 1)
                    parametros.put("almacenD", getAlmacenD());
                else if(tipo == 2) {
                    Log.i(TAG, archivoPhp);
                    parametros.put("idEstante", "1");
                    parametros.put("precio", "56.0");
                    parametros.put("precioTotal", "10.0");
                }
                parametros.put("fecha", fechaI);
                return parametros;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
    }

    private void realizarBusqueda(){
        if(!edtCodigo.getText().toString().isEmpty())
            if(!chTipo.isChecked())
                buscarProducto(edtCodigo.getText().toString());
            else
                busquedaTexto(edtCodigo.getText().toString());
        else
            Toast.makeText(getContext(), "Favor de indicar el producto a buscar", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBuscar:
                realizarBusqueda();
                break;
            case R.id.btnCamara:
                Toast.makeText(getContext(), "Aun esta en construccion", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}