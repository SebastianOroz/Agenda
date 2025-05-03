
package com.example.agenda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity5 extends AppCompatActivity {

    private EditText etUsuario, etPassword;
    private Button btnLogin, btnRegistro;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);

        sharedPreferences = getSharedPreferences("credenciales", MODE_PRIVATE);

        btnLogin.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString();
            String password = etPassword.getText().toString();

            if(validarCredenciales(usuario, password)) {
                Intent intent = new Intent(MainActivity5.this, MainActivity4.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity5.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity5.this, MainActivity6.class);
            startActivity(intent);
        });
    }

    private boolean validarCredenciales(String usuario, String password) {
        String savedUser = sharedPreferences.getString("usuario", "");
        String savedPass = sharedPreferences.getString("password", "");
        return usuario.equals(savedUser) && password.equals(savedPass);
    }
}