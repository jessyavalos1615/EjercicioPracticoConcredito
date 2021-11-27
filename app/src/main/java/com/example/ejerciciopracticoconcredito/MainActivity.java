package com.example.ejerciciopracticoconcredito;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ejerciciopracticoconcredito.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DB(this);

        com.example.ejerciciopracticoconcredito.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddProspect.class)));

        showRecords();

    }

    public void showRecords() {
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<Prospecto> prospectoList = db.getAllStudentContacts();

        if (prospectoList.size() > 0){
            for (Prospecto prospecto : prospectoList) {
                String id = prospecto.getId();
                String name = prospecto.getName();
                String last_name = prospecto.getLast_name();
                String second_last_name = prospecto.getSecond_last_name();
                String status = prospecto.getStatus();
                String textViewContents = name + " " + last_name + " " + second_last_name + " - " + status;
                TextView textViewProspectos= new TextView(this);
                textViewProspectos.setTextSize(20);
                textViewProspectos.setPadding(20,20, 0,0);
                textViewProspectos.setText(textViewContents);
                textViewProspectos.setTag(id);
                textViewProspectos.setOnClickListener(v -> {
                    String idSelected = v.getTag().toString();
                    Intent i = new Intent(MainActivity.this, ProspectEvaluation.class);
                    i.putExtra("id",idSelected);
                    startActivity(i);

                });
                linearLayoutRecords.addView(textViewProspectos);
            }
        } else {
            TextView textViewProspectos = new TextView(this);
            textViewProspectos.setGravity(Gravity.CENTER);
            textViewProspectos.setPadding(0,20,0,0);
            textViewProspectos.setTextSize(20);
            textViewProspectos.setText("Aun no existen registros.");
            linearLayoutRecords.addView(textViewProspectos);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}