package com.example.agenda;

import android.app.ComponentCaller;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    Button cerrar, buscar, btnCalculadora, btnEditar;
    TextView tvNombre, tvApellidos, tvEdad, tvEmail, tvTelefono,
            tvDireccion, tvGenero, tvAcademica, tvPreferencias, tvPais;
    EditText editTextCodigo;
    ArrayList<ArrayList<String>> usuarios;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);



        cerrar = findViewById(R.id.bt2);
        buscar = findViewById(R.id.buscar);
        editTextCodigo = findViewById(R.id.editTextText);

        tvNombre = findViewById(R.id.tvNombre);
        tvApellidos = findViewById(R.id.tvApellidos);
        tvEdad = findViewById(R.id.tvEdad);
        tvGenero = findViewById(R.id.tvGenero);
        tvEmail = findViewById(R.id.tvEmail);
        tvTelefono = findViewById(R.id.tvTelefono);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvAcademica = findViewById(R.id.tvAcademica);
        tvPreferencias = findViewById(R.id.tvPreferencias);
        tvPais = findViewById(R.id.tvPais);



        // Obtener lista de usuarios
        usuarios = (ArrayList<ArrayList<String>>) getIntent().getSerializableExtra("usuarios");

        btnCalculadora = findViewById(R.id.btnCalculadora);
    }



    public void Buscar(View v) {
        String codigoStr = editTextCodigo.getText().toString();

        if (codigoStr.isEmpty()) {
            Toast.makeText(this, "Ingrese un código", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoStr);
            boolean encontrado = false;

            for (ArrayList<String> datosUsuario : usuarios) {
                if (Integer.parseInt(datosUsuario.get(0)) == codigo) {

                    tvNombre.setText(datosUsuario.get(1));
                    tvApellidos.setText(datosUsuario.get(2));
                    tvEdad.setText(datosUsuario.get(3));
                    tvGenero.setText(datosUsuario.get(4));
                    tvEmail.setText(datosUsuario.get(5));
                    tvTelefono.setText(datosUsuario.get(6));
                    tvDireccion.setText(datosUsuario.get(7));
                    tvAcademica.setText(datosUsuario.get(8));
                    tvPreferencias.setText(datosUsuario.get(9));
                    tvPais.setText(datosUsuario.get(10));

                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Código inválido", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        tvNombre.setText("Nombre");
        tvApellidos.setText("Apellidos");
        tvEdad.setText("Edad");
        tvGenero.setText("Género");
        tvEmail.setText("Email");
        tvTelefono.setText("Teléfono");
        tvDireccion.setText("Dirección");
        tvAcademica.setText("Formación");
        tvPreferencias.setText("Preferencias");
        tvPais.setText("País");
    }



    public void irACalculadora(View v) {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }


    public void Cerrar(View v) {
        finish();
    }
}