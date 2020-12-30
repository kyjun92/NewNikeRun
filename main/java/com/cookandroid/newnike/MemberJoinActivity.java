package com.cookandroid.newnike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class MemberJoinActivity extends AppCompatActivity {
    private EditText editid, editpw, editpwconfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_join);

        setTitle("회원가입");
        editid = findViewById(R.id.editid);
        editpw = findViewById(R.id.editpw);
        editpwconfirm = findViewById(R.id.editpwconfirm);

        findViewById(R.id.buttonDoJoin).setOnClickListener(v -> {
            doJoin();

        });


        findViewById(R.id.buttonCancel).setOnClickListener(v -> {
            finish();
        });


    }
    private void doJoin(){
        String loginId = editid.getText().toString().trim();
        String loginPw = editpw.getText().toString().trim();
        String loginPwConfirm = editpwconfirm.getText().toString().trim();

        if(loginId.length() == 0){
            Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
            editid.requestFocus();

            return;
        }
        if(loginPw.length() == 0){
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            editpw.requestFocus();

            return;
        }
        if(loginPw.equals(loginPwConfirm) == false){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            editpwconfirm.requestFocus();

            return;
        }

        Member member = AppDatabase.findMember(loginId, loginPw);

        if (member != null){
            Toast.makeText(this, "이미 사용중인 아이디 입니다.", Toast.LENGTH_SHORT).show();
            editid.requestFocus();
            return;
        }
        Toast.makeText(this, "가입 성공!", Toast.LENGTH_SHORT).show();
    }
}
