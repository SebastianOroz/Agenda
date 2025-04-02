package com.example.agenda;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

    EditText etPantalla;
    String operacion = "";
    double numero1 = 0;
    double numero2 = 0;
    boolean puntoDecimalPresionado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        etPantalla = findViewById(R.id.etPantalla);

        int[] botonesNumericos = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};

        for (int id : botonesNumericos) {
            findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button boton = (Button) v;
                    String valorActual = etPantalla.getText().toString();
                    if (valorActual.equals("0")) {
                        etPantalla.setText(boton.getText());
                    } else {
                        etPantalla.setText(valorActual + boton.getText());
                    }
                }
            });
        }

        findViewById(R.id.btnDecimal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorActual = etPantalla.getText().toString();


                if (!valorActual.contains(".")) {

                    if (valorActual.isEmpty() || valorActual.equals("0")) {
                        etPantalla.setText("0.");
                    } else {
                        etPantalla.setText(valorActual + ".");
                    }
                    puntoDecimalPresionado = true;
                }
            }
        });

        int[] botonesOperaciones = {R.id.btnSumar, R.id.btnRestar, R.id.btnMultiplicar, R.id.btnDividir};
        for (int id : botonesOperaciones) {
            findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button boton = (Button) v;
                    numero1 = Double.parseDouble(etPantalla.getText().toString());
                    operacion = boton.getText().toString();
                    etPantalla.setText("0");
                    puntoDecimalPresionado = false;
                }
            });
        }

        findViewById(R.id.btnIgual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numero2 = Double.parseDouble(etPantalla.getText().toString());
                double resultado = 0;
                switch(operacion){
                    case "+":
                        resultado = numero1 + numero2;
                        break;
                    case "-":
                        resultado = numero1 - numero2;
                        break;
                    case "*":
                        resultado = numero1 * numero2;
                        break;
                    case "/":
                        if (numero2 != 0) {
                            resultado = numero1 / numero2;
                        } else {
                            etPantalla.setText("Error");
                            return;
                        }
                        break;
                }

                etPantalla.setText(String.valueOf(resultado));
                operacion = "";
                puntoDecimalPresionado = etPantalla.getText().toString().contains(".");
            }
        });

        findViewById(R.id.btnBorrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPantalla.setText("0");
                operacion = "";
                numero1 = 0;
                numero2 = 0;
                puntoDecimalPresionado = false;
            }
        });

        findViewById(R.id.btnRetroceso).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoActual = etPantalla.getText().toString();
                if(!textoActual.isEmpty()){

                    if (textoActual.endsWith(".")) {
                        puntoDecimalPresionado = false;
                    }

                    String nuevoTexto = textoActual.substring(0, textoActual.length() - 1);
                    etPantalla.setText(nuevoTexto);

                    if (nuevoTexto.isEmpty()){
                        etPantalla.setText("0");
                    }
                }
            }
        });
    }
}