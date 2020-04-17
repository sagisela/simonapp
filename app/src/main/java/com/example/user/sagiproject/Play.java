package com.example.user.sagiproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

public class Play extends AppCompatActivity {

    public SoundPool soundPool;
    public ArrayList<Integer> tilesWatched;
    public int score;
    public int highScore;
    public int repeatIndex;
    public int watchIndex;


    public int a, b, c, d, e, f, g, aSharp, cSharp, dSharp, fSharp, gSharp, wrongAns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        lockTiles();

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

        a = soundPool.load(this, R.raw.a, 1);
        b = soundPool.load(this, R.raw.b, 1);
        c = soundPool.load(this, R.raw.c, 1);
        d = soundPool.load(this, R.raw.d, 1);
        e = soundPool.load(this, R.raw.e, 1);
        f = soundPool.load(this, R.raw.f, 1);
        g = soundPool.load(this, R.raw.g, 1);
        aSharp = soundPool.load(this, R.raw.a_sharp, 1);
        cSharp = soundPool.load(this, R.raw.c_sharp, 1);
        dSharp = soundPool.load(this, R.raw.d_sharp, 1);
        fSharp = soundPool.load(this, R.raw.f_sharp, 1);
        gSharp = soundPool.load(this, R.raw.g_sharp, 1);
        wrongAns = soundPool.load(this, R.raw.wrong, 1);


        TextView highScoreText = (TextView) findViewById(R.id.tvHighScore);
        SharedPreferences prefs = this.getSharedPreferences("HIGH SCORE", Context.MODE_PRIVATE);
        highScore = prefs.getInt("score", 0);
        highScoreText.setText("HIGH SCORE: " + highScore);


        tilesWatched = new ArrayList<Integer>();
        score = 0;
        //highScore = 0;
        watchIndex = 0;
        repeatIndex = 0;
        addTile();
        loadSounds();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                watch();
            }
        }, 1000);

    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // בניית ה- AlertDialog
        builder.setTitle("Are you sure you want to exit the game?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i5 = new Intent(Play.this, SingedIn.class);
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
        getMenuInflater().inflate(R.menu.menu_classicgame, menu);
        return true;
    }






    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == R.id.action_FreePiano) {
            Intent i7 = new Intent(this, FreePiano.class);
            startActivity(i7);
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
                    Intent i5 = new Intent(Play.this, MainActivity.class);
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


    public void lockTiles() {
        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
        tvRepeat.setText("REPEAT!");
        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
        tvWatch.setText("WATCH!");
        tvRepeat.setVisibility(View.INVISIBLE);
        tvWatch.setVisibility(View.VISIBLE);
        ImageView TileW1 = (ImageView) this.findViewById(R.id.TileW1P);
        TileW1.setEnabled(false);
        ImageView TileW2 = (ImageView) this.findViewById(R.id.TileW2P);
        TileW2.setEnabled(false);
        ImageView TileW3 = (ImageView) this.findViewById(R.id.TileW3P);
        TileW3.setEnabled(false);
        ImageView TileW4 = (ImageView) this.findViewById(R.id.TileW4P);
        TileW4.setEnabled(false);
        ImageView TileW5 = (ImageView) this.findViewById(R.id.TileW5P);
        TileW5.setEnabled(false);
        ImageView TileW6 = (ImageView) this.findViewById(R.id.TileW6P);
        TileW6.setEnabled(false);
        ImageView TileW7 = (ImageView) this.findViewById(R.id.TileW7P);
        TileW7.setEnabled(false);
        ImageView TileB1 = (ImageView) this.findViewById(R.id.TileB1P);
        TileB1.setEnabled(false);
        ImageView TileB2 = (ImageView) this.findViewById(R.id.TileB2P);
        TileB2.setEnabled(false);
        ImageView TileB3 = (ImageView) this.findViewById(R.id.TileB3P);
        TileB3.setEnabled(false);
        ImageView TileB4 = (ImageView) this.findViewById(R.id.TileB4P);
        TileB4.setEnabled(false);
        ImageView TileB5 = (ImageView) this.findViewById(R.id.TileB5P);
        TileB5.setEnabled(false);

    }


    public void unlockTiles() {
        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
        tvRepeat.setText("REPEAT!");
        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
        tvWatch.setText("WATCH!");
        tvRepeat.setVisibility(View.VISIBLE);
        tvWatch.setVisibility(View.INVISIBLE);
        ImageView TileW1 = (ImageView) this.findViewById(R.id.TileW1P);
        TileW1.setEnabled(true);
        ImageView TileW2 = (ImageView) this.findViewById(R.id.TileW2P);
        TileW2.setEnabled(true);
        ImageView TileW3 = (ImageView) this.findViewById(R.id.TileW3P);
        TileW3.setEnabled(true);
        ImageView TileW4 = (ImageView) this.findViewById(R.id.TileW4P);
        TileW4.setEnabled(true);
        ImageView TileW5 = (ImageView) this.findViewById(R.id.TileW5P);
        TileW5.setEnabled(true);
        ImageView TileW6 = (ImageView) this.findViewById(R.id.TileW6P);
        TileW6.setEnabled(true);
        ImageView TileW7 = (ImageView) this.findViewById(R.id.TileW7P);
        TileW7.setEnabled(true);
        ImageView TileB1 = (ImageView) this.findViewById(R.id.TileB1P);
        TileB1.setEnabled(true);
        ImageView TileB2 = (ImageView) this.findViewById(R.id.TileB2P);
        TileB2.setEnabled(true);
        ImageView TileB3 = (ImageView) this.findViewById(R.id.TileB3P);
        TileB3.setEnabled(true);
        ImageView TileB4 = (ImageView) this.findViewById(R.id.TileB4P);
        TileB4.setEnabled(true);
        ImageView TileB5 = (ImageView) this.findViewById(R.id.TileB5P);
        TileB5.setEnabled(true);

    }


    public void markTwice(int repeatIndex) {
        Handler handler = new Handler();

        switch (tilesWatched.get(repeatIndex)) {

            case 0: // W1 C
                final ImageView playC = (ImageView) this.findViewById(R.id.TileW1P); // c
                Handler handler1 = new Handler();
                //playC.setImageResource(R.drawable.white_tile_border);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playC.setImageResource(R.drawable.white_tile_repeat_right);
                    }
                }, 100);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playC.setImageResource(R.drawable.white_tile_border);

                    }
                }, 550);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playC.setImageResource(R.drawable.white_tile_repeat_right);
                    }
                }, 1000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playC.setImageResource(R.drawable.white_tile_border);

                    }
                }, 1450);


                break;
            case 1:
                final ImageView playD = (ImageView) this.findViewById(R.id.TileW2P); // d

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playD.setImageResource(R.drawable.white_tile_repeat_right);
                    }
                }, 100);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playD.setImageResource(R.drawable.white_tile_border);

                    }
                }, 550);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playD.setImageResource(R.drawable.white_tile_repeat_right);
                    }
                }, 1000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playD.setImageResource(R.drawable.white_tile_border);

                    }
                }, 1450);
                break;

            case 2:
                final ImageView playE = (ImageView) this.findViewById(R.id.TileW3P); // e
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playE.setImageResource(R.drawable.white_tile_repeat_right);
                    }
                }, 100);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playE.setImageResource(R.drawable.white_tile_border);

                    }
                }, 550);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playE.setImageResource(R.drawable.white_tile_repeat_right);
                    }
                }, 1000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playE.setImageResource(R.drawable.white_tile_border);

                    }
                }, 1450);
                break;
            case 3:
                final ImageView playF = (ImageView) this.findViewById(R.id.TileW4P); // f
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playF.setImageResource(R.drawable.white_tile_repeat_right);
                    }
                }, 100);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playF.setImageResource(R.drawable.white_tile_border);

                    }
                }, 550);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playF.setImageResource(R.drawable.white_tile_repeat_right);
                    }
                }, 1000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playF.setImageResource(R.drawable.white_tile_border);

                    }
                }, 1450);


                break;
            case 4:
                final ImageView playG = (ImageView) this.findViewById(R.id.TileW5P); // g
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playG.setImageResource(R.drawable.white_tile_repeat_right);
                    }
                }, 100);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playG.setImageResource(R.drawable.white_tile_border);

                    }
                }, 550);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playG.setImageResource(R.drawable.white_tile_repeat_right);
                    }
                }, 1000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playG.setImageResource(R.drawable.white_tile_border);

                    }
                }, 1450);
                break;
            case 5:
                final ImageView playA = (ImageView) this.findViewById(R.id.TileW6P); // a
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playA.setImageResource(R.drawable.white_tile_repeat_right);
                    }
                }, 100);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playA.setImageResource(R.drawable.white_tile_border);

                    }
                }, 550);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playA.setImageResource(R.drawable.white_tile_repeat_right);
                    }
                }, 1000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playA.setImageResource(R.drawable.white_tile_border);

                    }
                }, 1450);
                break;
            case 6:

                final ImageView playB = (ImageView) this.findViewById(R.id.TileW7P); // b
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playB.setImageResource(R.drawable.white_tile_repeat_right);
                    }
                }, 100);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playB.setImageResource(R.drawable.white_tile_border);

                    }
                }, 550);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playB.setImageResource(R.drawable.white_tile_repeat_right);
                    }
                }, 1000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playB.setImageResource(R.drawable.white_tile_border);

                    }
                }, 1450);
                break;
            case 7:
                final ImageView playCSharp = (ImageView) this.findViewById(R.id.TileB1P); // cSharp
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playCSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    }
                }, 100);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playCSharp.setImageResource(R.drawable.black_tile_border);

                    }
                }, 550);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playCSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    }
                }, 1000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playCSharp.setImageResource(R.drawable.black_tile_border);

                    }
                }, 1450);
                break;
            case 8:
                final ImageView playDSharp = (ImageView) this.findViewById(R.id.TileB2P); // dSharp
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playDSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    }
                }, 100);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playDSharp.setImageResource(R.drawable.black_tile_border);

                    }
                }, 550);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playDSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    }
                }, 1000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playDSharp.setImageResource(R.drawable.black_tile_border);

                    }
                }, 1450);
                break;
            case 9:
                final ImageView playFSharp = (ImageView) this.findViewById(R.id.TileB3P); // fSharp
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playFSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    }
                }, 100);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playFSharp.setImageResource(R.drawable.black_tile_border);

                    }
                }, 550);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playFSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    }
                }, 1000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playFSharp.setImageResource(R.drawable.black_tile_border);

                    }
                }, 1450);
                break;
            case 10:
                final ImageView playGSharp = (ImageView) this.findViewById(R.id.TileB4P); // gSharp
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playGSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    }
                }, 100);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playGSharp.setImageResource(R.drawable.black_tile_border);

                    }
                }, 550);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playGSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    }
                }, 1000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playGSharp.setImageResource(R.drawable.black_tile_border);

                    }
                }, 1450);
                break;
            case 11:
                final ImageView playASharp = (ImageView) this.findViewById(R.id.TileB5P); // c

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playASharp.setImageResource(R.drawable.black_tile_repeat_right);
                    }
                }, 100);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playASharp.setImageResource(R.drawable.black_tile_border);

                    }
                }, 550);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playASharp.setImageResource(R.drawable.black_tile_repeat_right);
                    }
                }, 1000);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playASharp.setImageResource(R.drawable.black_tile_border);

                    }
                }, 1450);


                break;
            default:
                break;
        }
    }


    //tileW1-W7 = 0-6
    //tileB1-B5 = 7-11

    public void setHighScore() {
        TextView highScoreText = (TextView) findViewById(R.id.tvHighScore);
        SharedPreferences prefs = this.getSharedPreferences("HIGH SCORE", Context.MODE_PRIVATE);
        highScore = prefs.getInt("score", 0);
        highScoreText.setText("HIGH SCORE: " + highScore);
        if (highScore > score) {
            highScoreText.setText("HIGH SCORE: " + highScore);
        } else {
            highScore = score;
            highScoreText.setText("HIGH SCORE: " + highScore);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("score", highScore);
            editor.commit();
        }
    }

    public void watch() {
        lockTiles();
        repeatIndex = 0;
        int FastTile = score;
        if (FastTile > 12) {
            FastTile = 12;
        }
        Handler handler = new Handler();
        switch (tilesWatched.get(watchIndex)) {
            case 0: // W1 C
                final ImageView playC = (ImageView) this.findViewById(R.id.TileW1P); // c
                playC.setImageResource(R.drawable.white_tile_watch);
                soundPool.play(c, 1, 1, 0, 0, 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playC.setImageResource(R.drawable.white_tile_border);
                    }
                }, 700 - (FastTile * 30));
                break;
            case 1:
                final ImageView playD = (ImageView) this.findViewById(R.id.TileW2P); // d
                playD.setImageResource(R.drawable.white_tile_watch);
                soundPool.play(d, 1, 1, 0, 0, 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playD.setImageResource(R.drawable.white_tile_border);
                    }
                }, 700 - (FastTile * 30));
                break;
            case 2:
                final ImageView playE = (ImageView) this.findViewById(R.id.TileW3P); // e
                playE.setImageResource(R.drawable.white_tile_watch);
                soundPool.play(e, 1, 1, 0, 0, 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playE.setImageResource(R.drawable.white_tile_border);
                    }
                }, 700 - (FastTile * 30));
                break;
            case 3:
                final ImageView playF = (ImageView) this.findViewById(R.id.TileW4P); // f
                playF.setImageResource(R.drawable.white_tile_watch);
                soundPool.play(f, 1, 1, 0, 0, 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playF.setImageResource(R.drawable.white_tile_border);
                    }
                }, 700 - (FastTile * 30));
                break;
            case 4:
                final ImageView playG = (ImageView) this.findViewById(R.id.TileW5P); // g
                playG.setImageResource(R.drawable.white_tile_watch);
                soundPool.play(g, 1, 1, 0, 0, 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playG.setImageResource(R.drawable.white_tile_border);
                    }
                }, 700 - (FastTile * 30));
                break;
            case 5:
                final ImageView playA = (ImageView) this.findViewById(R.id.TileW6P); // a
                playA.setImageResource(R.drawable.white_tile_watch);
                soundPool.play(a, 1, 1, 0, 0, 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playA.setImageResource(R.drawable.white_tile_border);
                    }
                }, 700 - (FastTile * 30));
                break;
            case 6:
                final ImageView playB = (ImageView) this.findViewById(R.id.TileW7P); // b
                playB.setImageResource(R.drawable.white_tile_watch);
                soundPool.play(b, 1, 1, 0, 0, 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playB.setImageResource(R.drawable.white_tile_border);
                    }
                }, 700 - (FastTile * 30));
                break;
            case 7:
                final ImageView playCSharp = (ImageView) this.findViewById(R.id.TileB1P); // cSharp
                playCSharp.setImageResource(R.drawable.black_tile_watch);
                soundPool.play(cSharp, 1, 1, 0, 0, 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playCSharp.setImageResource(R.drawable.black_tile_border);
                    }
                }, 700 - (FastTile * 30));
                break;
            case 8:
                final ImageView playDSharp = (ImageView) this.findViewById(R.id.TileB2P); // dSharp
                playDSharp.setImageResource(R.drawable.black_tile_watch);
                soundPool.play(dSharp, 1, 1, 0, 0, 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playDSharp.setImageResource(R.drawable.black_tile_border);
                    }
                }, 700 - (FastTile * 30));
                break;
            case 9:
                final ImageView playFSharp = (ImageView) this.findViewById(R.id.TileB3P); // fSharp
                playFSharp.setImageResource(R.drawable.black_tile_watch);
                soundPool.play(fSharp, 1, 1, 0, 0, 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playFSharp.setImageResource(R.drawable.black_tile_border);
                    }
                }, 700 - (FastTile * 30));
                break;
            case 10:
                final ImageView playGSharp = (ImageView) this.findViewById(R.id.TileB4P); // gSharp
                playGSharp.setImageResource(R.drawable.black_tile_watch);
                soundPool.play(gSharp, 1, 1, 0, 0, 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playGSharp.setImageResource(R.drawable.black_tile_border);
                    }
                }, 700 - (FastTile * 30));
                break;
            case 11:
                final ImageView playASharp = (ImageView) this.findViewById(R.id.TileB5P); // c
                playASharp.setImageResource(R.drawable.black_tile_watch);
                soundPool.play(aSharp, 1, 1, 0, 0, 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playASharp.setImageResource(R.drawable.black_tile_border);
                    }
                }, 700 - (FastTile * 30));
                break;
            default:
                break;
        }
        watchIndex++;
        if (watchIndex < tilesWatched.size()) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    watch();
                }
            }, 730 - (FastTile * 30));
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    unlockTiles();
                    watchIndex = 0;
                }
            }, 700 - (FastTile * 30));
        }
    }

    public void addTile() {
        int tile = (int) (Math.random() * 12);
        tilesWatched.add(tile);
    }


    public void lose() {
        String currentDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String inputDate = currentDate + " " + currentTime;

        SharedPreferences prefs = this.getSharedPreferences("SCORES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(inputDate, score);
        editor.commit();

        AlertDialog.Builder builder = new AlertDialog.Builder(this); // בניית ה- AlertDialog
        builder.setTitle("GAME OVER!");
        builder.setMessage("SCORE: " + score + "                     HIGH SCORE: " + highScore);
        builder.setCancelable(false);
        builder.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Play.this, Play.class);
                startActivity(i);
            }
        });

        builder.setNeutralButton("Main Menu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Play.this, SingedIn.class);
                startActivity(i);
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public void loadSounds() {

        TextView scoreText = (TextView) findViewById(R.id.tvScore);
        scoreText.setText("SCORE: " + score);
        final float Volume = 0.1f;
        final ImageView playA = (ImageView) this.findViewById(R.id.TileW6P);
        playA.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    soundPool.play(a, 1, 1, 0, 0, 1);
                    if (tilesWatched.get(repeatIndex) == 5) {
                        playA.setImageResource(R.drawable.white_tile_repeat_right);
                    } else {
                        playA.setImageResource(R.drawable.white_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    playA.setImageResource(R.drawable.white_tile);
                    if (tilesWatched.get(repeatIndex) == 5) {
                        repeatIndex++;
                        if (repeatIndex == score + 1) {
                            score++;
                            setHighScore();
                            repeatIndex = 0;
                            addTile();
                            lockTiles();
                            TextView scoreText = (TextView) findViewById(R.id.tvScore);
                            scoreText.setText("SCORE: " + score);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    watch();
                                }
                            }, 1200);
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (tilesWatched.get(repeatIndex) == 5) {
                        playA.setImageResource(R.drawable.white_tile_repeat_right);
                    } else {
                        playA.setImageResource(R.drawable.white_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                }
                return true;
            }
        });

        final ImageView playASharp = (ImageView) this.findViewById(R.id.TileB5P);
        playASharp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    soundPool.play(aSharp, 1, 1, 0, 0, 1);
                    if (tilesWatched.get(repeatIndex) == 11) {
                        playASharp.setImageResource(R.drawable.black_tile_repeat_right);
                    } else {
                        playASharp.setImageResource(R.drawable.black_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    playASharp.setImageResource(R.drawable.black_tile);
                    if (tilesWatched.get(repeatIndex) == 11) {
                        repeatIndex++;
                        if (repeatIndex == score + 1) {
                            score++;
                            setHighScore();
                            repeatIndex = 0;
                            addTile();
                            lockTiles();
                            TextView scoreText = (TextView) findViewById(R.id.tvScore);
                            scoreText.setText("SCORE: " + score);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    watch();
                                }
                            }, 1200);
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (tilesWatched.get(repeatIndex) == 11) {
                        playASharp.setImageResource(R.drawable.black_tile_repeat_right);
                    } else {
                        playASharp.setImageResource(R.drawable.black_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                }
                return true;
            }
        });

        final ImageView playB = (ImageView) this.findViewById(R.id.TileW7P);
        playB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    soundPool.play(b, 1, 1, 0, 0, 1);
                    if (tilesWatched.get(repeatIndex) == 6) {
                        playB.setImageResource(R.drawable.white_tile_repeat_right);
                    } else {
                        playB.setImageResource(R.drawable.white_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    playB.setImageResource(R.drawable.white_tile);
                    if (tilesWatched.get(repeatIndex) == 6) {
                        repeatIndex++;
                        if (repeatIndex == score + 1) {
                            score++;
                            setHighScore();
                            repeatIndex = 0;
                            addTile();
                            lockTiles();
                            TextView scoreText = (TextView) findViewById(R.id.tvScore);
                            scoreText.setText("SCORE: " + score);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    watch();
                                }
                            }, 1200);
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (tilesWatched.get(repeatIndex) == 6) {
                        playB.setImageResource(R.drawable.white_tile_repeat_right);
                    } else {
                        playB.setImageResource(R.drawable.white_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                }
                return true;
            }
        });

        final ImageView playC = (ImageView) this.findViewById(R.id.TileW1P); // c
        playC.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    soundPool.play(c, 1, 1, 0, 0, 1);
                    if (tilesWatched.get(repeatIndex) == 0) {
                        playC.setImageResource(R.drawable.white_tile_repeat_right);
                    } else {
                        playC.setImageResource(R.drawable.white_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    playC.setImageResource(R.drawable.white_tile);
                    if (tilesWatched.get(repeatIndex) == 0) {
                        repeatIndex++;
                        if (repeatIndex == score + 1) {
                            score++;
                            setHighScore();
                            repeatIndex = 0;
                            addTile();
                            lockTiles();
                            TextView scoreText = (TextView) findViewById(R.id.tvScore);
                            scoreText.setText("SCORE: " + score);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    watch();
                                }
                            }, 1200);
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (tilesWatched.get(repeatIndex) == 0) {
                        playC.setImageResource(R.drawable.white_tile_repeat_right);
                    } else {
                        playC.setImageResource(R.drawable.white_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                }
                return true;
            }
        });


        final ImageView playCSharp = (ImageView) this.findViewById(R.id.TileB1P);
        playCSharp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    soundPool.play(cSharp, 1, 1, 0, 0, 1);
                    if (tilesWatched.get(repeatIndex) == 7) {
                        playCSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    } else {
                        playCSharp.setImageResource(R.drawable.black_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    playCSharp.setImageResource(R.drawable.black_tile);
                    if (tilesWatched.get(repeatIndex) == 7) {
                        repeatIndex++;
                        if (repeatIndex == score + 1) {
                            score++;
                            setHighScore();
                            repeatIndex = 0;
                            addTile();
                            lockTiles();
                            TextView scoreText = (TextView) findViewById(R.id.tvScore);
                            scoreText.setText("SCORE: " + score);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    watch();
                                }
                            }, 1200);
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (tilesWatched.get(repeatIndex) == 7) {
                        playCSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    } else {
                        playCSharp.setImageResource(R.drawable.black_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                }
                return true;
            }
        });

        final ImageView playD = (ImageView) this.findViewById(R.id.TileW2P);
        playD.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    soundPool.play(d, 1, 1, 0, 0, 1);
                    if (tilesWatched.get(repeatIndex) == 1) {
                        playD.setImageResource(R.drawable.white_tile_repeat_right);
                    } else {
                        playD.setImageResource(R.drawable.white_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    playD.setImageResource(R.drawable.white_tile);
                    if (tilesWatched.get(repeatIndex) == 1) {
                        repeatIndex++;
                        if (repeatIndex == score + 1) {
                            score++;
                            setHighScore();
                            repeatIndex = 0;
                            addTile();
                            lockTiles();
                            TextView scoreText = (TextView) findViewById(R.id.tvScore);
                            scoreText.setText("SCORE: " + score);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    watch();
                                }
                            }, 1200);
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (tilesWatched.get(repeatIndex) == 1) {
                        playD.setImageResource(R.drawable.white_tile_repeat_right);
                    } else {
                        playD.setImageResource(R.drawable.white_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                }
                return true;
            }
        });

        final ImageView playDSharp = (ImageView) this.findViewById(R.id.TileB2P);
        playDSharp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    soundPool.play(dSharp, 1, 1, 0, 0, 1);
                    if (tilesWatched.get(repeatIndex) == 8) {
                        playDSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    } else {
                        playDSharp.setImageResource(R.drawable.black_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    playDSharp.setImageResource(R.drawable.black_tile);
                    if (tilesWatched.get(repeatIndex) == 8) {
                        repeatIndex++;
                        if (repeatIndex == score + 1) {
                            score++;
                            setHighScore();
                            repeatIndex = 0;
                            addTile();
                            lockTiles();
                            TextView scoreText = (TextView) findViewById(R.id.tvScore);
                            scoreText.setText("SCORE: " + score);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    watch();
                                }
                            }, 1200);
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (tilesWatched.get(repeatIndex) == 8) {
                        playDSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    } else {
                        playDSharp.setImageResource(R.drawable.black_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                }
                return true;
            }
        });

        final ImageView playE = (ImageView) this.findViewById(R.id.TileW3P);
        playE.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    soundPool.play(e, 1, 1, 0, 0, 1);
                    if (tilesWatched.get(repeatIndex) == 2) {
                        playE.setImageResource(R.drawable.white_tile_repeat_right);
                    } else {
                        playE.setImageResource(R.drawable.white_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    playE.setImageResource(R.drawable.white_tile);
                    if (tilesWatched.get(repeatIndex) == 2) {
                        repeatIndex++;
                        if (repeatIndex == score + 1) {
                            score++;
                            setHighScore();
                            repeatIndex = 0;
                            addTile();
                            lockTiles();
                            TextView scoreText = (TextView) findViewById(R.id.tvScore);
                            scoreText.setText("SCORE: " + score);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    watch();
                                }
                            }, 1200);
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (tilesWatched.get(repeatIndex) == 2) {
                        playE.setImageResource(R.drawable.white_tile_repeat_right);
                    } else {
                        playE.setImageResource(R.drawable.white_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                }
                return true;
            }
        });

        final ImageView playF = (ImageView) this.findViewById(R.id.TileW4P);
        playF.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    soundPool.play(f, 1, 1, 0, 0, 1);
                    if (tilesWatched.get(repeatIndex) == 3) {
                        playF.setImageResource(R.drawable.white_tile_repeat_right);
                    } else {
                        playF.setImageResource(R.drawable.white_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    playF.setImageResource(R.drawable.white_tile);
                    if (tilesWatched.get(repeatIndex) == 3) {
                        repeatIndex++;
                        if (repeatIndex == score + 1) {
                            score++;
                            setHighScore();
                            repeatIndex = 0;
                            addTile();
                            lockTiles();
                            TextView scoreText = (TextView) findViewById(R.id.tvScore);
                            scoreText.setText("SCORE: " + score);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    watch();
                                }
                            }, 1200);
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (tilesWatched.get(repeatIndex) == 3) {
                        playF.setImageResource(R.drawable.white_tile_repeat_right);
                    } else {
                        playF.setImageResource(R.drawable.white_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                }
                return true;
            }
        });

        final ImageView playFSharp = (ImageView) this.findViewById(R.id.TileB3P);
        playFSharp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    soundPool.play(fSharp, 1, 1, 0, 0, 1);
                    if (tilesWatched.get(repeatIndex) == 9) {
                        playFSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    } else {
                        playFSharp.setImageResource(R.drawable.black_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    playFSharp.setImageResource(R.drawable.black_tile);
                    if (tilesWatched.get(repeatIndex) == 9) {
                        repeatIndex++;
                        if (repeatIndex == score + 1) {
                            score++;
                            setHighScore();
                            repeatIndex = 0;
                            addTile();
                            lockTiles();
                            TextView scoreText = (TextView) findViewById(R.id.tvScore);
                            scoreText.setText("SCORE: " + score);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    watch();
                                }
                            }, 1200);
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (tilesWatched.get(repeatIndex) == 9) {
                        playFSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    } else {
                        playFSharp.setImageResource(R.drawable.black_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                }
                return true;
            }
        });

        final ImageView playG = (ImageView) this.findViewById(R.id.TileW5P);
        playG.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    soundPool.play(g, 1, 1, 0, 0, 1);
                    if (tilesWatched.get(repeatIndex) == 4) {
                        playG.setImageResource(R.drawable.white_tile_repeat_right);
                    } else {
                        playG.setImageResource(R.drawable.white_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    playG.setImageResource(R.drawable.white_tile);
                    if (tilesWatched.get(repeatIndex) == 4) {
                        repeatIndex++;
                        if (repeatIndex == score + 1) {
                            score++;
                            setHighScore();
                            repeatIndex = 0;
                            addTile();
                            lockTiles();
                            TextView scoreText = (TextView) findViewById(R.id.tvScore);
                            scoreText.setText("SCORE: " + score);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    watch();
                                }
                            }, 1200);
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (tilesWatched.get(repeatIndex) == 4) {
                        playG.setImageResource(R.drawable.white_tile_repeat_right);
                    } else {
                        playG.setImageResource(R.drawable.white_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                }
                return true;
            }
        });

        final ImageView playGSharp = (ImageView) this.findViewById(R.id.TileB4P);
        playGSharp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    soundPool.play(gSharp, 1, 1, 0, 0, 1);
                    if (tilesWatched.get(repeatIndex) == 10) {
                        playGSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    } else {
                        playGSharp.setImageResource(R.drawable.black_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    playGSharp.setImageResource(R.drawable.black_tile);
                    if (tilesWatched.get(repeatIndex) == 10) {
                        repeatIndex++;
                        if (repeatIndex == score + 1) {
                            score++;
                            setHighScore();
                            repeatIndex = 0;
                            addTile();
                            lockTiles();
                            TextView scoreText = (TextView) findViewById(R.id.tvScore);
                            scoreText.setText("SCORE: " + score);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    watch();
                                }
                            }, 1200);
                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (tilesWatched.get(repeatIndex) == 10) {
                        playGSharp.setImageResource(R.drawable.black_tile_repeat_right);
                    } else {
                        playGSharp.setImageResource(R.drawable.black_tile_repeat_wrong);
                        lockTiles();
                        soundPool.play(wrongAns, Volume, Volume, 0, 0, 1);
                        TextView tvRepeat = (TextView) findViewById(R.id.tvRepeat);
                        tvRepeat.setText("GAME OVER!");
                        TextView tvWatch = (TextView) findViewById(R.id.tvWatch);
                        tvWatch.setText("WATCH!");
                        tvRepeat.setVisibility(View.VISIBLE);
                        tvWatch.setVisibility(View.INVISIBLE);
                        markTwice(repeatIndex);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lose();
                            }
                        }, 1600);
                    }
                }
                return true;
            }
        });
    }

}