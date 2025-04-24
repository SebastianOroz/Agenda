package com.example.agenda;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    Button cerrar, buscar, btnCalculadora, btnEditar;
    TextView tvNombre, tvApellidos, tvEdad, tvEmail, tvTelefono,
            tvDireccion, tvGenero, tvAcademica, tvPreferencias, tvPais;
    EditText editTextCodigo;

    private usuariosSQLiteOpenHelper admin;
    private SQLiteDatabase bd;
    private int usuarioActualId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        admin = new usuariosSQLiteOpenHelper(this);
        bd = admin.getReadableDatabase();


        cerrar = findViewById(R.id.bt2);
        buscar = findViewById(R.id.buscar);
        btnEditar = findViewById(R.id.btnEditar);
        btnCalculadora = findViewById(R.id.btnCalculadora);
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
    }

    public void Buscar(View v) {
        String codigoStr = editTextCodigo.getText().toString();

        if (codigoStr.isEmpty()) {
            Toast.makeText(this, "Ingrese un código", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoStr);
            this.usuarioActualId = codigo;

            Cursor cursor = bd.query(
                    "usuarios",
                    new String[]{"nombre", "apellidos", "edad", "genero", "email",
                            "telefono", "direccion", "formacion", "preferencias", "pais"},
                    "id = ?",
                    new String[]{String.valueOf(codigo)},
                    null, null, null);

            if (cursor.moveToFirst()) {

                tvNombre.setText(cursor.getString(0));
                tvApellidos.setText(cursor.getString(1));
                tvEdad.setText(cursor.getString(2));
                tvGenero.setText(cursor.getString(3));
                tvEmail.setText(cursor.getString(4));
                tvTelefono.setText(cursor.getString(5));
                tvDireccion.setText(cursor.getString(6));
                tvAcademica.setText(cursor.getString(7));
                tvPreferencias.setText(cursor.getString(8));
                tvPais.setText(cursor.getString(9));


                btnEditar.setEnabled(true);
            } else {
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                limpiarCampos();
                btnEditar.setEnabled(false);
            }

            cursor.close();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Código inválido", Toast.LENGTH_SHORT).show();
        }
    }

    public void Editar(View v) {
        if (usuarioActualId == -1) {
            Toast.makeText(this, "Primero busque un usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("MODO_EDICION", true);
        intent.putExtra("USUARIO_ID", usuarioActualId);
        startActivity(intent);
        finish();
    }

    public void irACalculadora(View v) {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

    public void Cerrar(View v) {
        finish();
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