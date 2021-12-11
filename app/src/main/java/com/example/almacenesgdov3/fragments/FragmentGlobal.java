package com.example.almacenesgdov3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.almacenesgdov3.R;

public class FragmentGlobal extends Fragment {
    private Button btnCamara, btnBuscar;
    private EditText edtCodigo, edtCantidad;
    private TextView txvCodigo, txvDescripcion, txvUnidad;
    private CheckBox chTipo;
    //private String servidor, estante = "", almacenO = "", almacenD="";

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
}
