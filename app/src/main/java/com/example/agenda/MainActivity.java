package com.example.agenda;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button bt1, b2, b3;
    EditText editTextId, EdtNombre, TxApellidos, TxEdad, TxEmail, TxTelefono, TxDireccion;
    RadioGroup radioGroupGenero;
    CheckBox cbPrimaria, cbSecundaria, cbUniversidad, cbPostgrado;
    CheckBox cbMusica, cbDeportes, cbCine, cbLectura;
    Spinner spinnerPais;

    private usuariosSQLiteOpenHelper admin;
    private SQLiteDatabase bd;

    String[] paises = {"Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Costa Rica",
            "Cuba", "Ecuador", "El Salvador", "Guatemala", "Haití", "Honduras",
            "México", "Nicaragua", "Panamá", "Paraguay", "Perú",
            "República Dominicana", "Uruguay", "Venezuela"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        admin = new usuariosSQLiteOpenHelper(this);
        bd = admin.getWritableDatabase();


        bt1 = findViewById(R.id.btnGuardar);
        b2 = findViewById(R.id.btnBuscar);
        b3 = findViewById(R.id.btnVaciar);
        editTextId = findViewById(R.id.editTextId);
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


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, paises);
        spinnerPais.setAdapter(adapter);


        if (getIntent() != null && getIntent().hasExtra("MODO_EDICION") &&
                getIntent().hasExtra("USUARIO_ID")) {
            int usuarioId = getIntent().getIntExtra("USUARIO_ID", -1);
            if (usuarioId != -1) {
                cargarDatosUsuario(usuarioId);
                bt1.setText("Actualizar"); // Cambiar texto del botón
            }
        }
    }

    private void cargarDatosUsuario(int id) {
        Cursor cursor = bd.query(
                "usuarios",
                new String[]{"nombre", "apellidos", "edad", "genero", "email",
                        "telefono", "direccion", "formacion", "preferencias", "pais"},
                "id = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor.moveToFirst()) {

            editTextId.setText(String.valueOf(id));
            editTextId.setEnabled(false);


            EdtNombre.setText(cursor.getString(0));
            TxApellidos.setText(cursor.getString(1));
            TxEdad.setText(cursor.getString(2));


            String genero = cursor.getString(3);
            if (genero != null) {
                for (int i = 0; i < radioGroupGenero.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) radioGroupGenero.getChildAt(i);
                    if (rb.getText().toString().equals(genero)) {
                        rb.setChecked(true);
                        break;
                    }
                }
            }


            TxEmail.setText(cursor.getString(4));
            TxTelefono.setText(cursor.getString(5));
            TxDireccion.setText(cursor.getString(6));


            String formacion = cursor.getString(7);
            if (formacion != null) {
                cbPrimaria.setChecked(formacion.contains("Primaria"));
                cbSecundaria.setChecked(formacion.contains("Secundaria"));
                cbUniversidad.setChecked(formacion.contains("Universidad"));
                cbPostgrado.setChecked(formacion.contains("Postgrado"));
            }


            String preferencias = cursor.getString(8);
            if (preferencias != null) {
                cbMusica.setChecked(preferencias.contains("Música"));
                cbDeportes.setChecked(preferencias.contains("Deportes"));
                cbCine.setChecked(preferencias.contains("Cine"));
                cbLectura.setChecked(preferencias.contains("Lectura"));
            }


            String pais = cursor.getString(9);
            if (pais != null) {
                for (int i = 0; i < spinnerPais.getCount(); i++) {
                    if (spinnerPais.getItemAtPosition(i).toString().equals(pais)) {
                        spinnerPais.setSelection(i);
                        break;
                    }
                }
            }
        }
        cursor.close();
    }

    public void Guardar(View v) {

        if (editTextId.getText().toString().isEmpty()) {
            editTextId.setError("La identificación es obligatoria");
            return;
        }

        if (EdtNombre.getText().toString().isEmpty()) {
            EdtNombre.setError("Nombre es obligatorio");
            return;
        }

        if (TxApellidos.getText().toString().isEmpty()) {
            TxApellidos.setError("Apellidos son obligatorios");
            return;
        }

        if (radioGroupGenero.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Seleccione un género", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int id = Integer.parseInt(editTextId.getText().toString());
            boolean esEdicion = !editTextId.isEnabled(); // Si el ID está bloqueado, es edición

            ContentValues registro = new ContentValues();
            registro.put("id", id);
            registro.put("nombre", EdtNombre.getText().toString());
            registro.put("apellidos", TxApellidos.getText().toString());
            registro.put("edad", TxEdad.getText().toString());


            RadioButton rb = findViewById(radioGroupGenero.getCheckedRadioButtonId());
            registro.put("genero", rb != null ? rb.getText().toString() : "");


            registro.put("email", TxEmail.getText().toString());
            registro.put("telefono", TxTelefono.getText().toString());
            registro.put("direccion", TxDireccion.getText().toString());


            StringBuilder formacion = new StringBuilder();
            if (cbPrimaria.isChecked()) formacion.append("Primaria,");
            if (cbSecundaria.isChecked()) formacion.append("Secundaria,");
            if (cbUniversidad.isChecked()) formacion.append("Universidad,");
            if (cbPostgrado.isChecked()) formacion.append("Postgrado,");
            registro.put("formacion", formacion.toString());


            StringBuilder preferencias = new StringBuilder();
            if (cbMusica.isChecked()) preferencias.append("Música,");
            if (cbDeportes.isChecked()) preferencias.append("Deportes,");
            if (cbCine.isChecked()) preferencias.append("Cine,");
            if (cbLectura.isChecked()) preferencias.append("Lectura,");
            registro.put("preferencias", preferencias.toString());


            registro.put("pais", spinnerPais.getSelectedItem().toString());

            if (esEdicion) {

                int result = bd.update("usuarios", registro, "id = ?",
                        new String[]{String.valueOf(id)});
                if (result > 0) {
                    Toast.makeText(this, "Usuario actualizado", Toast.LENGTH_SHORT).show();
                    finish(); // Cerrar actividad después de actualizar
                } else {
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            } else {

                boolean existe = verificarSiUsuarioExiste(id);
                if (existe) {
                    Toast.makeText(this, "El ID ya existe", Toast.LENGTH_SHORT).show();
                    return;
                }

                long result = bd.insert("usuarios", null, registro);
                if (result != -1) {
                    Toast.makeText(this, "Usuario creado", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                } else {
                    Toast.makeText(this, "Error al crear usuario", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID debe ser un número válido", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean verificarSiUsuarioExiste(int id) {
        Cursor cursor = bd.query("usuarios",
                new String[]{"id"},
                "id = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    public void Vaciar(View v) {
        limpiarCampos();
        editTextId.requestFocus();
    }

    private void limpiarCampos() {
        editTextId.setText("");
        editTextId.setEnabled(true);
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
        startActivity(intent);
    }

    public void Cerrar(View v) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bd != null) {
            bd.close();
        }
        if (admin != null) {
            admin.close();
        }
    }
}