package com.example.agenda;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {

    EditText etPantalla;
    String operacionPendiente = "";
    double resultadoParcial = 0;
    boolean nuevaOperacion = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        etPantalla = findViewById(R.id.etPantalla);


        findViewById(R.id.btnCerrar).setOnClickListener(v -> finish());


        int[] botonesNumericos = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};

        for (int id : botonesNumericos) {
            findViewById(id).setOnClickListener(v -> {
                Button boton = (Button) v;
                String valorActual = etPantalla.getText().toString();

                if (nuevaOperacion) {
                    etPantalla.setText(boton.getText());
                    nuevaOperacion = false;
                } else {
                    if (valorActual.equals("0")) {
                        etPantalla.setText(boton.getText());
                    } else {
                        etPantalla.setText(valorActual + boton.getText());
                    }
                }
            });
        }


        findViewById(R.id.btnDecimal).setOnClickListener(v -> {
            String valorActual = etPantalla.getText().toString();
            if (!valorActual.contains(".")) {
                if (valorActual.isEmpty() || nuevaOperacion) {
                    etPantalla.setText("0.");
                    nuevaOperacion = false;
                } else {
                    etPantalla.setText(valorActual + ".");
                }
            }
        });


        int[] botonesOperaciones = {R.id.btnSumar, R.id.btnRestar, R.id.btnMultiplicar, R.id.btnDividir};
        for (int id : botonesOperaciones) {
            findViewById(id).setOnClickListener(v -> {
                Button boton = (Button) v;
                String operacion = boton.getText().toString();
                String valorPantalla = etPantalla.getText().toString();

                if (!nuevaOperacion) {
                    calcularResultadoParcial();
                }

                operacionPendiente = operacion;
                nuevaOperacion = true;
            });
        }


        findViewById(R.id.btnIgual).setOnClickListener(v -> {
            if (!nuevaOperacion) {
                calcularResultadoParcial();
            }
            operacionPendiente = "";
            nuevaOperacion = true;
        });


        findViewById(R.id.btnBorrar).setOnClickListener(v -> {
            etPantalla.setText("0");
            resultadoParcial = 0;
            operacionPendiente = "";
            nuevaOperacion = true;
        });


        findViewById(R.id.btnRetroceso).setOnClickListener(v -> {
            String textoActual = etPantalla.getText().toString();
            if (textoActual.length() > 1) {
                etPantalla.setText(textoActual.substring(0, textoActual.length() - 1));
            } else {
                etPantalla.setText("0");
                nuevaOperacion = true;
            }
        });
    }

    private void calcularResultadoParcial() {
        try {
            double numeroActual = Double.parseDouble(etPantalla.getText().toString());

            if (operacionPendiente.isEmpty()) {
                resultadoParcial = numeroActual;
            } else {
                switch (operacionPendiente) {
                    case "+":
                        resultadoParcial += numeroActual;
                        break;
                    case "-":
                        resultadoParcial -= numeroActual;
                        break;
                    case "*":
                        resultadoParcial *= numeroActual;
                        break;
                    case "/":
                        if (numeroActual != 0) {
                            resultadoParcial /= numeroActual;
                        } else {
                            etPantalla.setText("Error");
                            return;
                        }
                        break;
                }
            }


            if (resultadoParcial % 1 == 0) {
                etPantalla.setText(String.valueOf((long) resultadoParcial));
            } else {
                etPantalla.setText(String.valueOf(resultadoParcial));
            }
        } catch (NumberFormatException e) {
            etPantalla.setText("Error");
        }
    }
}