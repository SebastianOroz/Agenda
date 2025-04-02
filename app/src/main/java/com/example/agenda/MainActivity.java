package com.example.agenda;

import android.app.ComponentCaller;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button bt1, b2;
    EditText EdtNombre, TxApellidos, TxEdad, TxEmail, TxTelefono, TxDireccion;
    RadioGroup radioGroupGenero;
    CheckBox cbPrimaria, cbSecundaria, cbUniversidad, cbPostgrado;
    CheckBox cbMusica, cbDeportes, cbCine, cbLectura;
    Spinner spinnerPais;

    ArrayList<ArrayList<String>> usuarios = new ArrayList<>();


    String paises[] = {"Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Costa Rica", "Cuba", "Ecuador", "El Salvador", "Guatemala", "Haití", "Honduras", "México", "Nicaragua", "Panamá", "Paraguay", "Perú", "República Dominicana", "Uruguay", "Venezuela"
    };

    int contador = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        bt1 = findViewById(R.id.bt1);
        b2 = findViewById(R.id.b2);
        EdtNombre = findViewById(R.id.editTextNombre);
        TxApellidos = findViewById(R.id.editTextApellido);
        TxEdad = findViewById(R.id.TextEdad);
        TxEmail = findViewById(R.id.TextEmail);
        TxTelefono = findViewById(R.id.TextPhone);
        TxDireccion = findViewById(R.id.editTextDireccion);


        radioGroupGenero = findViewById(R.id.radioGroupGenero);
        cbPrimaria = findViewById(R.id.cbPrimaria);
        cbSecundaria = findViewById(R.id.cbSecundaria);
        cbUniversidad = findViewById(R.id.cbUniversidad);
        cbPostgrado = findViewById(R.id.cbPostgrado);
        cbMusica = findViewById(R.id.cbMusica);
        cbDeportes = findViewById(R.id.cbDeportes);
        cbCine = findViewById(R.id.cbCine);
        cbLectura = findViewById(R.id.cbLectura);
        spinnerPais = findViewById(R.id.spinnerPais);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paises);

        spinnerPais.setAdapter(adapter);

    }

    public void Abrir(View v) {

        String nombre = EdtNombre.getText().toString();
        String apellidos = TxApellidos.getText().toString();
        String edad = TxEdad.getText().toString();
        String email = TxEmail.getText().toString();
        String telefono = TxTelefono.getText().toString();
        String direccion = TxDireccion.getText().toString();


        int selectedId = radioGroupGenero.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        String genero = radioButton != null ? radioButton.getText().toString() : "";


        StringBuilder academica = new StringBuilder();
        if (cbPrimaria.isChecked()) academica.append("Primaria, ");
        if (cbSecundaria.isChecked()) academica.append("Secundaria, ");
        if (cbUniversidad.isChecked()) academica.append("Universidad, ");
        if (cbPostgrado.isChecked()) academica.append("Postgrado, ");


        StringBuilder preferencias = new StringBuilder();
        if (cbMusica.isChecked()) preferencias.append("Música, ");
        if (cbDeportes.isChecked()) preferencias.append("Deportes, ");
        if (cbCine.isChecked()) preferencias.append("Cine, ");
        if (cbLectura.isChecked()) preferencias.append("Lectura, ");


        String pais = spinnerPais.getSelectedItem() != null ? spinnerPais.getSelectedItem().toString() : "";


        ArrayList<String> datosUsuario = new ArrayList<>();
        datosUsuario.add(String.valueOf(contador)); // ID
        datosUsuario.add(nombre);
        datosUsuario.add(apellidos);
        datosUsuario.add(edad);
        datosUsuario.add(genero);
        datosUsuario.add(email);
        datosUsuario.add(telefono);
        datosUsuario.add(direccion);
        datosUsuario.add(academica.toString());
        datosUsuario.add(preferencias.toString());
        datosUsuario.add(pais);

        usuarios.add(datosUsuario);

        Toast.makeText(this, "Usuario creado con código: " + contador, Toast.LENGTH_SHORT).show();
        contador++;


        EdtNombre.setText("");
        TxApellidos.setText("");
        TxEdad.setText("");
        TxEmail.setText("");
        TxTelefono.setText("");
        TxDireccion.setText("");
        radioGroupGenero.clearCheck();
        cbPrimaria.setChecked(false);
        cbSecundaria.setChecked(false);
        cbUniversidad.setChecked(false);
        cbPostgrado.setChecked(false);
        cbMusica.setChecked(false);
        cbDeportes.setChecked(false);
        cbCine.setChecked(false);
        cbLectura.setChecked(false);
        spinnerPais.setSelection(0);
    }

    public void Intento(View v) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("usuarios", usuarios);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data, @NonNull ComponentCaller caller) {
        super.onActivityResult(requestCode, resultCode, data, caller);

        if (requestCode == 100 && resultCode == RESULT_OK) {

            Toast.makeText(this, "Volviendo de buscar usuarios", Toast.LENGTH_SHORT).show();
        }
    }
}