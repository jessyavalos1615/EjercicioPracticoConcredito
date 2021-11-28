package com.example.ejerciciopracticoconcredito;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class AddProspect extends AppCompatActivity {

    private static final String TAG = "AddProspect";
    LinearLayout linearLayoutFiles;
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
    private List<String> filesSelected = new ArrayList<>();
    private static final int FILE_SELECT_CODE = 2;

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
                String files = "";
                int index = 0;
                for (String name : filesSelected) {
                    if (index == filesSelected.size()){
                        files += name;
                    } else {
                        files += name + ",";
                    }
                    index++;
                }
                prospecto.setFiles(files);

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

    public void showfilesSelected() {
        linearLayoutFiles.removeAllViews();

        int index = 0;
        if (filesSelected.size() > 0){
            for(String name : filesSelected){
                TextView textViewFiles= new TextView(this);
                textViewFiles.setTextSize(20);
                textViewFiles.setPadding(20,20, 0,0);
                textViewFiles.setText(name);
                textViewFiles.setTag(index);
                int finalIndex = index;
                textViewFiles.setOnClickListener(v -> {
                    filesSelected.remove(finalIndex);
                    showfilesSelected();
                });
                linearLayoutFiles.addView(textViewFiles);
                index++;
            }

            if (filesSelected.size() == 1) Toast.makeText(getApplicationContext(), "Has click sobre el archivo para removerlo.", Toast.LENGTH_SHORT).show();
        }
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
        linearLayoutFiles = findViewById(R.id.linearLayoutFiles);
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
        } else if (filesSelected.size() == 0) {
            flag = false;
            Toast.makeText(getApplicationContext(), "Es necesario agregar almenos un documento que ayude en la evaluación del prospeto.", Toast.LENGTH_LONG).show();
        }
        return flag;
    }

    public void showFileChooser(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = null;
                    if (data != null) {
                        uri = data.getData();
                        // Perform operations on the document using its URI.
                        String name = dumpImageMetaData(uri);

                        if (name.trim().length()>0){
                            filesSelected.add(name);

                            showfilesSelected();
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String dumpImageMetaData(Uri uri) {

        String fileName = "";
        // The query, because it only applies to a single document, returns only
        // one row. There's no need to filter, sort, or select fields,
        // because we want all fields for one document.
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null, null);

        try {
            // moveToFirst() returns false if the cursor has 0 rows. Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {

                // Note it's called "Display Name". This is
                // provider-specific, and might not necessarily be the file name.
                @SuppressLint("Range") String displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                Log.i(TAG, "Display Name: " + displayName);

                fileName = displayName;

                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                // If the size is unknown, the value stored is null. But because an
                // int can't be null, the behavior is implementation-specific,
                // and unpredictable. So as
                // a rule, check if it's null before assigning to an int. This will
                // happen often: The storage API allows for remote files, whose
                // size might not be locally known.
                String size = null;
                if (!cursor.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString()
                    // will do the conversion automatically.
                    size = cursor.getString(sizeIndex);
                } else {
                    size = "Unknown";
                }
                Log.i(TAG, "Size: " + size);
            }
        } finally {
            cursor.close();
        }

        return fileName;
    }
}