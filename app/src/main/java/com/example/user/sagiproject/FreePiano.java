package com.example.user.sagiproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class FreePiano extends AppCompatActivity {

    public SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_piano);
        //soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0); // create soundpool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(4)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        }


        final int a = soundPool.load(this, R.raw.a, 1);
        ImageView playA = (ImageView) this.findViewById(R.id.TileW6);
        playA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(a, 1, 1, 0, 0, 1);
            }
        });

        final int aSharp = soundPool.load(this, R.raw.a_sharp, 1); // a#
        ImageView playASharp = (ImageView) this.findViewById(R.id.TileB5);
        playASharp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(aSharp, 1, 1, 0, 0, 1);
            }
        });

        final int b = soundPool.load(this, R.raw.b, 1); // b
        ImageView playB = (ImageView) this.findViewById(R.id.TileW7);
        playB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(b, 1, 1, 0, 0, 1);
            }
        });

        final int c = soundPool.load(this, R.raw.c, 1);
        ImageView playC = (ImageView) this.findViewById(R.id.TileW1); // c
        playC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(c, 1, 1, 0, 0, 1);
            }
        });


        final int cSharp = soundPool.load(this, R.raw.c_sharp, 1); // c#
        ImageView playCSharp = (ImageView) this.findViewById(R.id.TileB1);
        playCSharp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(cSharp, 1, 1, 0, 0, 1);
            }
        });

        final int d = soundPool.load(this, R.raw.d, 1); // d
        ImageView playD = (ImageView) this.findViewById(R.id.TileW2);
        playD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(d, 1, 1, 0, 0, 1);
            }
        });

        final int dSharp = soundPool.load(this, R.raw.d_sharp, 1); // d#
        ImageView playDSharp = (ImageView) this.findViewById(R.id.TileB2);
        playDSharp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(dSharp, 1, 1, 0, 0, 1);
            }
        });

        final int e = soundPool.load(this, R.raw.e, 1); // e
        ImageView playE = (ImageView) this.findViewById(R.id.TileW3);
        playE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(e, 1, 1, 0, 0, 1);
            }
        });

        final int f = soundPool.load(this, R.raw.f, 1); // f
        ImageView playF = (ImageView) this.findViewById(R.id.TileW4);
        playF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(f, 1, 1, 0, 0, 1);
            }
        });

        final int fSharp = soundPool.load(this, R.raw.f_sharp, 1); // f#
        ImageView playFSharp = (ImageView) this.findViewById(R.id.TileB3);
        playFSharp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(fSharp, 1, 1, 0, 0, 1);
            }
        });

        final int g = soundPool.load(this, R.raw.g, 1); // g
        ImageView playG = (ImageView) this.findViewById(R.id.TileW5);
        playG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(g, 1, 1, 0, 0, 1);
            }
        });

        final int gSharp = soundPool.load(this, R.raw.g_sharp, 1); // g#
        ImageView playGSharp = (ImageView) this.findViewById(R.id.TileB4);
        playGSharp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(gSharp, 1, 1, 0, 0, 1);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_freepiano, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == R.id.action_classicGame) {
            Intent i6 = new Intent(this, Play.class);
            startActivity(i6);

        } else if (id == R.id.action_MainMenu) {
            Intent i7 = new Intent(this, SingedIn.class);
            startActivity(i7);
        } else if (id == R.id.action_GameHistory) {
            Intent i5 = new Intent(this, ListviewDisplay.class);
            startActivity(i5);
        } else if (id == R.id.action_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this); // בניית ה- AlertDialog
            builder.setTitle("Are you sure you want to Log Out?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent i5 = new Intent(FreePiano.this, MainActivity.class);
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