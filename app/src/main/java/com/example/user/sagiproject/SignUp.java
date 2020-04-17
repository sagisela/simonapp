package com.example.user.sagiproject;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private EditText uname, pword1, pword2;
    private Button back, cancel, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        uname = (EditText) findViewById(R.id.etUserName);
        pword1 = (EditText) findViewById(R.id.etPassW1);
        pword2 = (EditText) findViewById(R.id.etPassW2);
        back = (Button) findViewById(R.id.btnBack);
        cancel = (Button) findViewById(R.id.btnCancel);
        signUp = (Button) findViewById(R.id.btnSignUp);



    }



    public void sendSMS()
    {
        //Toast.makeText(this, "Works.", Toast.LENGTH_SHORT).show();
        SharedPreferences sp;
        sp = getSharedPreferences("userDetails", MODE_PRIVATE);
        String phoneNum = "+972584010502";
        String name1 = sp.getString("uname", "");
        String message = name1 + ", thanks for signing up Simon Piano!";
        SmsManager smsMng = SmsManager.getDefault();
        smsMng.sendTextMessage(phoneNum, null, message, null, null);
    }

    public void onClick(View view)
    {
        if (view == signUp)
        {
            if(uname.getText().toString().length() < 6){
                Toast.makeText(this, "Username must contain at list 6 characters", Toast.LENGTH_LONG).show();
                return;
            }
            if(pword1.getText().toString().equals(pword2.getText().toString())){
                if(pword1.getText().toString().length() < 6){
                    Toast.makeText(this, "Password must contain at list 6 characters", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();
                SharedPreferences sp;
                sp = getSharedPreferences("userDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                String tmpName = uname.getText().toString();
                editor.putString("uname", tmpName);
                String tmp = pword1.getText().toString();
                editor.putString("pword", tmp);

                editor.commit();
                int MY_PERMISSION_REQUEST_SEND_SMS = 111;
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS))
                        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST_SEND_SMS);
                }
                sendSMS();

                Intent i3 = new Intent(this, SingedIn.class);
                startActivity(i3);
            }
            else
            {
                Toast.makeText(this, "Passwords Don't match, Try Again", Toast.LENGTH_SHORT).show();
                pword1.setText("");
                pword2.setText("");
            }
        }
        else if (view == cancel) {
            uname.setText("");
            pword1.setText("");
            pword2.setText("");
            Toast.makeText(this, "Cancelllll", Toast.LENGTH_SHORT).show();
        }
        else if (view == back)
        {
            Intent i4 = new Intent(this, MainActivity.class);
            startActivity(i4);
        }
    }


}

