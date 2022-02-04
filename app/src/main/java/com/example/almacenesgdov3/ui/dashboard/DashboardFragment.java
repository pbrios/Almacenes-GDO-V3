package com.example.almacenesgdov3.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.almacenesgdov3.R;
import com.example.almacenesgdov3.fragments.FragmentGlobal;
import com.example.almacenesgdov3.objetos.Usuarios;

public class DashboardFragment extends Fragment {
    private FragmentManager fragmentManager;
    private FragmentGlobal fragmentGlobal = new FragmentGlobal(2);
    private ImageButton btnGuardar;
    private Usuarios usuarios;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        inicializa(root);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentGlobal.setEstante("2");
                fragmentGlobal.setSucursal(usuarios.getIdSucursal());
                fragmentGlobal.setAlmacenO(usuarios.getIdAlmacen());
                fragmentGlobal.setUsuario(usuarios.getId());
                fragmentGlobal.ejecutarServicio();
            }
        });

        return root;
    }

    private void inicializa(View root){
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.lInventario, fragmentGlobal).commit();
        btnGuardar = root.findViewById(R.id.btnGuardarI);
        usuarios = (Usuarios) getActivity().getIntent().getExtras().getSerializable("usuario");
    }
}