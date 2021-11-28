package com.example.ejerciciopracticoconcredito;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class ProspectEvaluation extends AppCompatActivity {

    LinearLayout linearLayoutFilesEvaluate;
    EditText txtObcercaciones;
    Button btnUpdateProspect;
    RadioGroup rgStatus;
    RadioButton rbtnRechazar, rbtnAutorizar;
    TextView prospectFullName, prospectFullAddress, prospectPhoneNumber, prospectRFC, prospectCurrentStatus;
    DB db;
    int idUpdate = 0;
    String statusSelected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospect_evaluation);
        initializeElements();
        db = new DB(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString("id");
            showProspect(id);
        } else {
            Toast.makeText(getApplicationContext(), "Error al cargar datos del prospecto.", Toast.LENGTH_LONG).show();
        }

        setElementsEvents();
    }

    public void initializeElements() {
        prospectFullName = findViewById(R.id.prospectFullName);
        prospectFullAddress = findViewById(R.id.prospectFullAddress);
        prospectPhoneNumber = findViewById(R.id.prospectPhoneNumber);
        prospectRFC = findViewById(R.id.prospectRFC);
        prospectCurrentStatus = findViewById(R.id.prospectCurrentStatus);
        rgStatus = findViewById(R.id.rgStatus);
        txtObcercaciones = findViewById(R.id.txtObcercaciones);
        rbtnRechazar = findViewById(R.id.rbtnRechazar);
        rbtnAutorizar = findViewById(R.id.rbtnAutorizar);
        btnUpdateProspect = findViewById(R.id.btnUpdateProspect);
        linearLayoutFilesEvaluate = findViewById(R.id.linearLayoutFilesEvaluate);
    }

    @SuppressLint("SetTextI18n")
    public void showProspect(String id) {
        Prospecto prospecto = db.getProspect(Integer.parseInt(id));
        idUpdate = Integer.parseInt(id);
        prospectFullName.setText("Nombre: " + prospecto.getName() + " " + prospecto.getLast_name() + " " + prospecto.getSecond_last_name());
        prospectFullAddress.setText("Dirección: " + prospecto.getStreet() + " " + prospecto.getNumber() + " " + prospecto.getSuburb() + " " + prospecto.getZip());
        prospectPhoneNumber.setText("Telefono: " + prospecto.getPhone());
        prospectRFC.setText("RFC: " + prospecto.getRfc());
        prospectCurrentStatus.setText("Estado: " + prospecto.getStatus());

        List<String> filesList = Arrays.asList(prospecto.getFiles().split(","));

        for (String name : filesList){
            TextView textViewFiles= new TextView(this);
            textViewFiles.setTextSize(20);
            textViewFiles.setPadding(20,20, 0,0);
            textViewFiles.setText(name);
            linearLayoutFilesEvaluate.addView(textViewFiles);
        }

        if (!prospecto.getStatus().equals("Enviado")) {
            btnUpdateProspect.setVisibility(View.GONE);
            switch (prospecto.getStatus()){
                case "Autorizar":
                    rbtnAutorizar.setChecked(true);
                    rbtnRechazar.setEnabled(false);
                    rbtnAutorizar.setEnabled(false);
                    break;
                case "Rechazar":
                    rbtnRechazar.setChecked(true);
                    rbtnRechazar.setEnabled(false);
                    rbtnAutorizar.setEnabled(false);
                    txtObcercaciones.setText(prospecto.getObservacion());
                    break;
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void setElementsEvents() {
        rgStatus.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case R.id.rbtnAutorizar:
                    txtObcercaciones.setEnabled(false);
                    statusSelected = "Autorizar";
                    break;
                case R.id.rbtnRechazar:
                    txtObcercaciones.setEnabled(true);
                    statusSelected = "Rechazar";
                    break;
                default:
                    break;
            }
        });
    }

    public void updateProspect(View view) {
        try {
            if (statusSelected.equals("")) {
                Toast.makeText(getApplicationContext(), "Seleccione algunos de los estatus.", Toast.LENGTH_SHORT).show();
            } else {
                if (statusSelected.equals("Rechazar") && txtObcercaciones.getText().toString().trim().equals("")){
                    txtObcercaciones.setError("Al ser rechazado es necesario alguna obcervacion.");
                } else {
                    boolean res = db.updateProspect(idUpdate, statusSelected, txtObcercaciones.getText().toString());
                    if (res){
                        Toast.makeText(getApplicationContext(), "Prospecto evaluado con exito.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProspectEvaluation.this, MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Error al evaluar los datos.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCancel(View view) {
        startActivity(new Intent(ProspectEvaluation.this, MainActivity.class));
    }
}