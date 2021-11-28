package com.example.ejerciciopracticoconcredito;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddProspect extends AppCompatActivity {

    private EditText prospectName,
                     prospectLastName,
                     prospectSecondLastName,
                     prospectStreet,
                     prospectNumber,
                     prospectSuburb,
                     prospectZip,
                     prospectPhone,
                     prospectRfc;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prospect);

        db = new DB(this);

        initializeElements();
        setEvents();
    }

    public void saveProspect(View view) {
        boolean resValidation = validateNotNull();

        if (resValidation) {
            Toast.makeText(getApplicationContext(), "Se guardaran los datos.", Toast.LENGTH_SHORT).show();
            try {
                Prospecto prospecto = new Prospecto();
                prospecto.setName(prospectName.getText().toString());
                prospecto.setLast_name(prospectLastName.getText().toString());
                prospecto.setSecond_last_name(prospectSecondLastName.getText().toString());
                prospecto.setStreet(prospectStreet.getText().toString());
                prospecto.setNumber(prospectNumber.getText().toString());
                prospecto.setSuburb(prospectSuburb.getText().toString());
                prospecto.setZip(prospectZip.getText().toString());
                prospecto.setPhone(prospectPhone.getText().toString());
                prospecto.setRfc(prospectRfc.getText().toString());
                prospecto.setStatus("Enviado");
                prospecto.setObservacion("");

                boolean res = db.addProspect(prospecto);

                if (res) {
                    Toast.makeText(getApplicationContext(), "Prospecto capturado con exito.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddProspect.this, MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Error al capturar los datos.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public void cancelAddProspect(View view) {
        startActivity(new Intent(AddProspect.this, MainActivity.class));
    }

    public void initializeElements(){
        prospectName = findViewById(R.id.prospectName);
        prospectLastName = findViewById(R.id.prospectLastName);
        prospectSecondLastName = findViewById(R.id.prospectSecondLastName);
        prospectStreet = findViewById(R.id.prospectStreet);
        prospectNumber = findViewById(R.id.prospectNumber);
        prospectSuburb = findViewById(R.id.prospectSuburb);
        prospectZip = findViewById(R.id.prospectZip);
        prospectPhone = findViewById(R.id.prospectPhone);
        prospectRfc = findViewById(R.id.prospectRfc);
    }

    public void setEvents() {
        prospectName.addTextChangedListener(new TextValidator(prospectName) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.matches(".*[^a-zA-Z\\s!].*")){
                    text = text.replaceAll("[^a-zA-Z\\s!]", "");
                    String[] nameArray = text.split("\\s");
                    String newText = text;
                    if (nameArray.length>0 && text.trim().length()>0){
                        newText = "";
                        for (String name: nameArray) {
                            newText += name.substring(0, 1).toUpperCase() + name.substring(1) + " ";
                        }
                        newText = newText.substring(0, newText.length() - 1);
                    }
                    textView.setText(newText);
                    textView.setError("No se permiten numeros o caracteres especiales.");
                }
            }
        });
        prospectName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String text = prospectName.getText().toString().trim();
                String[] nameArray = text.split("\\s");
                String newText = text;
                if (nameArray.length>0 && text.trim().length()>0){
                    newText = "";
                    for (String name: nameArray) {
                        newText += name.substring(0, 1).toUpperCase() + name.substring(1) + " ";
                    }
                    newText = newText.substring(0, newText.length() - 1);
                }
                prospectName.setText(newText);
            }
        });

        prospectLastName.addTextChangedListener(new TextValidator(prospectLastName) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.matches(".*[^a-zA-Z\\s!].*")){
                    text = text.replaceAll("[^a-zA-Z\\s!]", "");
                    String[] nameArray = text.split("\\s");
                    String newText = text;
                    if (nameArray.length>0 && text.trim().length()>0){
                        newText = "";
                        for (String name: nameArray) {
                            newText += name.substring(0, 1).toUpperCase() + name.substring(1) + " ";
                        }
                        newText = newText.substring(0, newText.length() - 1);
                    }
                    textView.setText(newText);
                    textView.setError("No se permiten numeros o caracteres especiales.");
                }
            }
        });
        prospectLastName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String text = prospectLastName.getText().toString().trim();
                String[] nameArray = text.split("\\s");
                String newText = text;
                if (nameArray.length>0 && text.trim().length()>0){
                    newText = "";
                    for (String name: nameArray) {
                        newText += name.substring(0, 1).toUpperCase() + name.substring(1) + " ";
                    }
                    newText = newText.substring(0, newText.length() - 1);
                }
                prospectLastName.setText(newText);
            }
        });

        prospectSecondLastName.addTextChangedListener(new TextValidator(prospectSecondLastName) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.matches(".*[^a-zA-Z\\s!].*")){
                    text = text.replaceAll("[^a-zA-Z\\s!]", "");
                    String[] nameArray = text.split("\\s");
                    String newText = text;
                    if (nameArray.length>0 && text.trim().length()>0){
                        newText = "";
                        for (String name: nameArray) {
                            newText += name.substring(0, 1).toUpperCase() + name.substring(1) + " ";
                        }
                        newText = newText.substring(0, newText.length() - 1);
                    }
                    textView.setText(newText);
                    textView.setError("No se permiten numeros o caracteres especiales.");
                }
            }
        });
        prospectSecondLastName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String text = prospectSecondLastName.getText().toString().trim();
                String[] nameArray = text.split("\\s");
                String newText = text;
                if (nameArray.length>0 && text.trim().length()>0){
                    newText = "";
                    for (String name: nameArray) {
                        newText += name.substring(0, 1).toUpperCase() + name.substring(1) + " ";
                    }
                    newText = newText.substring(0, newText.length() - 1);
                }
                prospectSecondLastName.setText(newText);
            }
        });

        prospectStreet.addTextChangedListener(new TextValidator(prospectStreet) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.matches(".*[^a-zA-Z0-9\\s!].*")){
                    text = text.replaceAll("[^a-zA-Z0-9\\s!]", "");
                    textView.setText(text);
                    textView.setError("No se permiten caracteres especiales.");
                }
            }
        });

        prospectNumber.addTextChangedListener(new TextValidator(prospectNumber) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.matches(".*[^a-z0-9-\\s!].*")){
                    text = text.replaceAll("[^a-z0-9-\\s!]", "");
                    textView.setText(text);
                    textView.setError("No se permiten caracteres especiales.");
                }
            }
        });

        prospectSuburb.addTextChangedListener(new TextValidator(prospectSuburb) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.matches(".*[^a-zA-Z0-9\\s!].*")){
                    text = text.replaceAll("[^a-zA-Z0-9\\s!]", "");
                    textView.setText(text);
                    textView.setError("No se permiten caracteres especiales.");
                }
            }
        });

        prospectZip.addTextChangedListener(new TextValidator(prospectZip) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.matches(".*[^0-9!].*")){
                    text = text.replaceAll("[^0-9!]", "");
                    textView.setText(text);
                    textView.setError("No se permiten caracteres alfabeticos o especiales.");
                }
            }
        });

        prospectPhone.addTextChangedListener(new TextValidator(prospectPhone) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.matches(".*[^0-9!].*")){
                    text = text.replaceAll("[^0-9!]", "");
                    textView.setText(text);
                    textView.setError("No se permiten caracteres alfabeticos o especiales.");
                }
            }
        });

        prospectRfc.addTextChangedListener(new TextValidator(prospectRfc) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.matches(".*[^a-zA-Z0-9\\s!].*")){
                    text = text.replaceAll("[^a-zA-Z0-9\\s!]", "");
                    textView.setText(text);
                    textView.setError("No se permiten caracteres especiales.");
                }
            }
        });
    }

    public boolean validateNotNull() {
        boolean flag = true;
        if (prospectName.getText().toString().equals("")) {
            flag = false;
            prospectName.requestFocus();
            prospectName.setError("Es necesario agregar el nombre del prospecto.");
        } else if (prospectLastName.getText().toString().equals("")){
            flag = false;
            prospectLastName.requestFocus();
            prospectLastName.setError("Es necesario agregar el apellido paterno.");
        } else if (prospectStreet.getText().toString().equals("")){
            flag = false;
            prospectStreet.requestFocus();
            prospectStreet.setError("Es necesario agregar la dirección del prospecto.");
        } else if (prospectNumber.getText().toString().equals("")){
            flag = false;
            prospectNumber.requestFocus();
            prospectNumber.setError("Es necesario agregar el número de la dirección.");
        } else if (prospectSuburb.getText().toString().equals("")){
            flag = false;
            prospectSuburb.requestFocus();
            prospectSuburb.setError("Es necesario agregar la colonia.");
        } else if (prospectZip.getText().toString().equals("")){
            flag = false;
            prospectZip.requestFocus();
            prospectZip.setError("Es necesario agregar el código postal.");
        } else if (prospectPhone.getText().toString().equals("")){
            flag = false;
            prospectPhone.requestFocus();
            prospectPhone.setError("Es necesario agregar algun telefono.");
        } else if (prospectRfc.getText().toString().equals("")){
            flag = false;
            prospectRfc.requestFocus();
            prospectRfc.setError("Es necesario agregar el RFC del prospecto.");
        }
        return flag;
    }
}