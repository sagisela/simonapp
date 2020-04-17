package com.example.user.sagiproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SingedIn extends AppCompatActivity implements View.OnClickListener {

    private TextView title, tvBattery;
    private Button classic, songs, freePiano, gameH, add;
    private BroadCastBattery bcb =  new BroadCastBattery();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singed_in);

        tvBattery = (TextView) findViewById(R.id.tvBattery);
        title = (TextView) findViewById(R.id.tvTitle1);
        classic = (Button) findViewById(R.id.btn1);
        freePiano = (Button) findViewById(R.id.btn3);
        gameH = (Button) findViewById(R.id.btn4);

    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // בניית ה- AlertDialog
        builder.setTitle("Are you sure you want to exit the game?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i5 = new Intent(SingedIn.this, MainActivity.class);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == R.id.action_classicGame) {
            Intent i6 = new Intent(this, Play.class);
            startActivity(i6);

        }
        else if (id == R.id.action_FreePiano) {
            Intent i7 = new Intent(this, FreePiano.class);
            startActivity(i7);
        }
        else if (id == R.id.action_GameHistory)     {
            Intent i5 = new Intent(this, ListviewDisplay.class);
            startActivity(i5);
        }
        else if (id == R.id.action_logout)     {
            AlertDialog.Builder builder = new AlertDialog.Builder(this); // בניית ה- AlertDialog
            builder.setTitle("Are you sure you want to Log Out?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent i5 = new Intent(SingedIn.this, MainActivity.class);
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


    public class BroadCastBattery extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int battery = intent.getIntExtra("level", 0);
            tvBattery.setText("\uD83D\uDD0B" + String.valueOf(battery) + "%");
        }
    }
    protected void onResume()
    {
        super.onResume();
        registerReceiver(bcb, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    protected void onPause()
    {
        super.onPause();
        registerReceiver(bcb, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }



    @Override
    public void onClick(View view) {
        if(view == gameH)
        {
            Intent i5 = new Intent(this, ListviewDisplay.class);
            startActivity(i5);
        }
        else if(view == freePiano)
        {
            Intent i6 = new Intent(this, FreePiano.class);
            startActivity(i6);
        }
        else if(view == classic)
        {
            Intent i7 = new Intent(this, Play.class);
            startActivity(i7);
        }
    }







}
