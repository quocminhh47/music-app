package com.ptithcm.mobile.service;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.mobile.R;


public class SendMailActivity extends AppCompatActivity {
    EditText  txtSubject, txtMessage;
    Button btnSendMail;
    private final String EMAIL = "quocminhh47@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();

            }
        });
    }

    private void sendMail() {

        String subject = txtSubject.getText().toString();
        String mess = txtMessage.getText().toString();
        //send mail

        JavaMailAPI javaMailAPI = new JavaMailAPI(this, EMAIL, subject, mess);
        javaMailAPI.execute();

    }

    private void setControl() {

        txtSubject = findViewById(R.id.txtSubject);
        txtMessage = findViewById(R.id.txtMessage);
        btnSendMail = findViewById(R.id.btnSend);
    }
}