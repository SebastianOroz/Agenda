package com.example.agenda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity6 extends AppCompatActivity {

    private EditText etNuevoUsuario, etNuevoPassword;
    private Button btnGuardarRegistro, btnCerrarRegistro;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        etNuevoUsuario = findViewById(R.id.etNuevoUsuario);
        etNuevoPassword = findViewById(R.id.etNuevoPassword);
        btnGuardarRegistro = findViewById(R.id.btnGuardarRegistro);
        btnCerrarRegistro = findViewById(R.id.btnCerrarRegistro);

        sharedPreferences = getSharedPreferences("credenciales", MODE_PRIVATE);

        btnGuardarRegistro.setOnClickListener(v -> {
            String usuario = etNuevoUsuario.getText().toString();
            String password = etNuevoPassword.getText().toString();

            if(usuario.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                guardarCredenciales(usuario, password);
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnCerrarRegistro.setOnClickListener(v -> finish());
    }

    private void guardarCredenciales(String usuario, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usuario", usuario);
        editor.putString("password", password);
        editor.apply();
    }
}