package com.example.mobileprogramming;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

/* 아이디 중복검사, 회원가입하면 id로 file write, privacyText scroll */
public class RegisterActivity extends AppCompatActivity {
    String dirPath;
    File file;
    JSONObject jsonObject;

    EditText idEdit;
    EditText passwordEdit;
    EditText nameEdit;
    EditText phoneEdit;
    EditText addressEdit;
    TextView idCheckText;
    TextView passwordCheckText;
    TextView nameCheckText;
    TextView phoneCheckText;
    TextView addressCheckText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("회원가입");

        dirPath = getFilesDir().getAbsolutePath();
        file = new File(dirPath);

        if (!file.exists()) {
            file.mkdirs();
            System.out.println("mkdir");
        }

        idEdit = (EditText) findViewById(R.id.idEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        nameEdit = (EditText) findViewById(R.id.nameEdit);
        phoneEdit = (EditText) findViewById(R.id.phoneEdit);
        addressEdit = (EditText) findViewById(R.id.addressEdit);

        idCheckText = (TextView) findViewById(R.id.idCheckText);
        passwordCheckText = (TextView) findViewById(R.id.passwordCheckText);
        nameCheckText = (TextView) findViewById(R.id.nameCheckText);
        phoneCheckText = (TextView) findViewById(R.id.phoneCheckText);
        addressCheckText = (TextView) findViewById(R.id.addressCheckText);


        idEdit.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                    if (Pattern.matches("^[A-Za-z0-9]{4,12}$", idEdit.getText())) {
                        idCheckText.setText("");
                    } else {
                        idCheckText.setText("4~12자의 영문 소문자,숫자를 혼합해서 사용 가능합니다.");
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        passwordEdit.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                    if (Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", passwordEdit.getText())) {
                        passwordCheckText.setText("");
                    } else {
                        passwordCheckText.setText("8~20자의 영문 소문자, 숫자와 특수기호를 혼합해야 합니다.");
//                        passwordCheckText.setTextColor(Color.parseColor("#ff0000"));
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        nameEdit.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                    if (Pattern.matches("^[ㄱ-ㅎ가-힣]{2,10}|[a-zA-Z]{2,20}$", nameEdit.getText())) {
                        nameCheckText.setText("");
                    } else {
                        nameCheckText.setText("잘못된 이름입니다.");
//                        textViewNameCheck.setTextColor(Color.parseColor("#ff0000"));
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        phoneEdit.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                    if (Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", phoneEdit.getText())) {
                        phoneCheckText.setText("");
                    } else {
                        phoneCheckText.setText("번호를 잘못 입력하습니다.");
//                        textViewNameCheck.setTextColor(Color.parseColor("#ff0000"));
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        addressEdit.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                    if (Pattern.matches("^\\S+.[ㄱ-ㅎ가-힣]*|[a-zA-Z]*|[0-9]*$", addressEdit.getText())) {
                        addressCheckText.setText("");
                    } else {
                        addressCheckText.setText("잘못된 주소입니다.");
//                        textViewNameCheck.setTextColor(Color.parseColor("#ff0000"));
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });


        final TextView privacyText = (TextView) findViewById(R.id.privacyText);

        String policy = " 1. 개인정보 처리방침이란?"
        + "\n 이 어플은 이용자의 ‘동의를 기반으로 개인정보를 수집·이용 및 제공’하고 있으며, ‘이용자의 권리 (개인정보 자기결정권)를 적극적으로 보장’합니다."
        + "\n 회사는 정보통신서비스제공자가 준수하여야 하는 대한민국의 관계 법령 및 개인정보보호 규정, 가이드라인을 준수하고 있습니다."
        + "\n “개인정보처리방침”이란 이용자의 소중한 개인정보를 보호함으로써 이용자가 안심하고 서비스를 이용할 수 있도록 회사가 준수해야 할 지침을 의미합니다."
        + "\n 본 개인정보처리방침은 회사가 제공하는 서비스에 적용됩니다.";
        privacyText.setText(policy);

        final Button registerBtn = (Button) findViewById(R.id.registerBtn);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.getCheckedRadioButtonId() == R.id.acceptBtn){
                    registerBtn.setEnabled(true);
                    registerBtn.setBackgroundColor(Color.parseColor("#A1CDE1"));
                }
                else {
                    registerBtn.setEnabled(false);
                    registerBtn.setBackgroundColor(Color.parseColor("#C0C0C0"));
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String id = idEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                String name = nameEdit.getText().toString().trim();
                String phoneNo = phoneEdit.getText().toString().trim();
                String address = addressEdit.getText().toString().trim();

                if (!id.isEmpty() && !password.isEmpty() && !name.isEmpty() && !phoneNo.isEmpty() && !address.isEmpty()) {
                    if (idCheckText.getText() == "" && passwordCheckText.getText() == "" && nameCheckText.getText() == "" && phoneCheckText.getText() == "" && addressCheckText.getText() == ""){
                        if(registerUser(id, password, name, phoneNo, address)) {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }else {
                            idCheckText.setText("이 아이디는 이미 사용 중입니다.");
                            Toast.makeText(getApplicationContext(), "이미 사용중인 아이디 입니다.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "잘못된 형식의 입력이 있습니다.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "필수사항은 모두 입력하세요!", Toast.LENGTH_LONG).show();
                }

            }
        });


//        String str = input_text.getText().toString();
//// 파일 생성
//        File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/camdata"); // 저장 경로
//// 폴더 생성
//        if(!saveFile.exists()){ // 폴더 없을 경우
//            saveFile.mkdir(); // 폴더 생성
//        }
//        try {
//            long now = System.currentTimeMillis(); // 현재시간 받아오기
//            Date date = new Date(now); // Date 객체 생성
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String nowTime = sdf.format(date);
//
//            BufferedWriter buf = new BufferedWriter(new FileWriter(saveFile+"/CarnumData.txt", true));
//            buf.append(nowTime + " "); // 날짜 쓰기
//            buf.append(str); // 파일 쓰기
//            buf.newLine(); // 개행
//            buf.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    private boolean registerUser(String id, String password, String name, String phoneNo, String address)
    {
        try{
            //파일 객체 생성
            File file = new File(dirPath+"/"+id+".txt");
            try {
                jsonObject = new JSONObject();
                System.out.println("make json obj");
            }catch(Exception e){
                System.out.println(e);
            }

            if(file.exists() == true) {
                Log.d("RegisterActivity", idEdit.getText().toString() + idCheckText.getText().toString());
                return false;
            }else {
                try {
                    // true 지정시 파일의 기존 내용에 이어서 작성
                    //쓰기
                    System.out.println("파일 생성");

                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                    System.out.println(jsonObject);

                    jsonObject.put("pw", password);
                    jsonObject.put("name", name);
                    jsonObject.put("phone", phoneNo);
                    jsonObject.put("address", address);
                    System.out.println(jsonObject);

                    bufferedWriter.write(jsonObject.toString());
                    System.out.println("writing complete");

                    //System.out.println(jsonObject.get("pw"));
                    //중복검사
                    bufferedWriter.close();
                    return true;
                }catch(Exception e){
                    System.out.println(e);
                    return false;
                }
            }

        }catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
