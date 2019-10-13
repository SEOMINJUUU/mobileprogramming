package com.example.mobileprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.JsonReader;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {
    InputMethodManager imm;
    String dirPath;

    EditText idText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("로그인");

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        idText = (EditText) findViewById(R.id.idText);
        passwordText = (EditText) findViewById(R.id.passwordText);

        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl);

        rl.setOnClickListener(myClickListener);
        loginBtn.setOnClickListener(myClickListener);

        Button registerBtn = (Button) findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    View.OnClickListener myClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            hideKeyboard();
            switch (v.getId())
            {
                case R.id.rl :
                    break;

                case R.id.loginBtn :
                    if(idText.getText().length() == 0){
                        Toast.makeText(getApplicationContext(), "아이디를 입력해 주세요.", Toast.LENGTH_LONG).show();
                        idText.requestFocus();
                        imm.showSoftInput(idText, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }
                    if(passwordText.getText().length() == 0) {
                        Toast.makeText(getApplicationContext(), "비밀번호를 입력해 주세요.", Toast.LENGTH_LONG).show();
                        passwordText.requestFocus();
                        imm.showSoftInput(passwordText, InputMethodManager.SHOW_IMPLICIT);
                        return;
                    }

                    try {
                        dirPath = getFilesDir().getAbsolutePath();

                        BufferedReader br = new BufferedReader(new FileReader(dirPath+"/"+idText.getText().toString()+".txt"));
                        StringBuilder sb = new StringBuilder();

                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }

                        JSONObject json = new JSONObject(sb.toString());

                        if(json.getString("pw").equals(passwordText.getText().toString())){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                        passwordText.setText("");

                        br.close();

                    }catch(FileNotFoundException e){
                        Toast.makeText(getApplicationContext(), "존재하지 않는 아이디 입니다.", Toast.LENGTH_LONG).show();
                        idText.setText("");
                        passwordText.setText("");

                    }catch(Exception e) {
                        System.out.println(e);
                    }

                    break;
            }
        }
    };

    private void hideKeyboard() {
        imm.hideSoftInputFromWindow(idText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordText.getWindowToken(), 0);
    }
}

