package com.example.user.sagiproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText uname, pword;
    private Button OK, cancel, register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uname = (EditText) findViewById(R.id.etUsername);
        pword = (EditText) findViewById(R.id.etPassword);
        OK = (Button) findViewById(R.id.btnOK);
        cancel = (Button) findViewById(R.id.btnCancel);
        register = (Button) findViewById(R.id.btnRegister);    }







    @Override
    public void onClick(View view) {
        if (view == OK) {
            SharedPreferences sp;
            sp = getSharedPreferences("userDetails", MODE_PRIVATE);
            String spUserName = sp.getString("uname", "");
            String spPAssWord = sp.getString("pword", "");

            if(spUserName.equals(uname.getText().toString()) && spPAssWord.equals(pword.getText().toString()))
            {
                Intent i2 = new Intent(this, SingedIn.class);
                startActivity(i2);
            }
            else
            {
                Toast.makeText(this, "Username or Password are incorrect", Toast.LENGTH_SHORT).show();
            }

        }
        else if (view == cancel) {
            uname.setText("");
            pword.setText("");
            Toast.makeText(this, "Cancelllll", Toast.LENGTH_SHORT).show();
        }
        else if (view == register) {
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
            }
    }
}

