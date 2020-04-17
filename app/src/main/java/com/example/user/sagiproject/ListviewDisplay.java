package com.example.user.sagiproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.Collections;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Map;

public class ListviewDisplay extends AppCompatActivity {

    private ListView lvGames;

    private ArrayList<Game> GameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_display);


        lvGames = (ListView) findViewById(R.id.lvGames);
        GameList = new ArrayList<Game>();
        SharedPreferences prefs = this.getSharedPreferences("SCORES", Context.MODE_PRIVATE);
        Map<String, ?> allScores = prefs.getAll();
        for (Map.Entry<String, ?> entry : allScores.entrySet()) {
            String date = entry.getKey();
            String score = entry.getValue().toString();
            GameList.add(new Game(date, Integer.parseInt(score)));


        }
        Collections.sort(GameList);

        ArrayAdapter<Game> adapter = new ArrayAdapter<Game>(this,
                android.R.layout.simple_list_item_1, GameList);
        lvGames.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gamehistory, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == R.id.action_classicGame) {
            Intent i6 = new Intent(this, Play.class);
            startActivity(i6);

        } else if (id == R.id.action_FreePiano) {
            Intent i7 = new Intent(this, FreePiano.class);
            startActivity(i7);
        } else if (id == R.id.action_MainMenu) {
            Intent i7 = new Intent(this, SingedIn.class);
            startActivity(i7);
        }
        else if (id == R.id.action_logout)     {
            AlertDialog.Builder builder = new AlertDialog.Builder(this); // בניית ה- AlertDialog
            builder.setTitle("Are you sure you want to Log Out?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent i5 = new Intent(ListviewDisplay.this, MainActivity.class);
                    startActivity(i5);
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });


            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return true;
    }


}
