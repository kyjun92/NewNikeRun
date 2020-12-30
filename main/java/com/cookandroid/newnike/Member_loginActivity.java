package com.cookandroid.newnike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class Member_loginActivity extends AppCompatActivity {
    private EditText editid, editpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_login);

        setTitle("회원 로그인");

        editid = findViewById(R.id.editid);
        editpw = findViewById(R.id.editpw);

        findViewById(R.id.buttonlogin).setOnClickListener(v -> {
            doLogin();
            
        });
        findViewById(R.id.buttonsign).setOnClickListener(view -> {
            startActivity(new Intent(Member_loginActivity.this, MemberJoinActivity.class));
        });
    }
    
    private void doLogin(){
        String loginId = editid.getText().toString().trim();
        String loginPw = editpw.getText().toString().trim();
        
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

        Member member = AppDatabase.findMember(loginId, loginPw);
        if (member == null){
            Toast.makeText(this, "존재하지 않는 아이디 입니다.", Toast.LENGTH_SHORT).show();
            editid.requestFocus();
            return;
        }else if(member.getLoginPasswd().equals(loginPw) == false){
            Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
            editpw.requestFocus();
            return;
        }
        Intent intent = new Intent(this, HomeMainActivity.class);
        intent.putExtra("loginedMemberId", member.getId());
        startActivity(intent);
        Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show();
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public  myDBHelper (Context context){
            super(context, "groupDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("Create table user (id char(15), pw char(15), name char(15), tel char(15), age int);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP table if exists user");
            onCreate(db);
        }
    }
}
