package com.gary.app0501;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private CheckBox autologin;
    private Button btnLogin;

    private SharedPreferences spSettings=null;
    private static final String PREFS_NAME="NamePwd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName=(EditText)findViewById(R.id.et_username);
        password=(EditText)findViewById(R.id.et_password);
        autologin=(CheckBox)findViewById(R.id.cb_autologin);
        btnLogin=(Button)findViewById(R.id.btn_login);

        //绑定控件事件
        setListener();

        //获取数据
        getData();
    }

    private void setListener()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("admin".equals(userName.getText().toString())  && "123456".equals(password.getText().toString()))
                {
                     if (autologin.isChecked())
                     {
                         spSettings=getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                         SharedPreferences.Editor edit=spSettings.edit();

                         edit.putBoolean("isKeep", true);
                         edit.putString("username", userName.getText().toString());
                         edit.putString("password", password.getText().toString());
                         edit.commit();
                     }
                    else
                     {
                         spSettings=getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                         SharedPreferences.Editor edit=spSettings.edit();

                         edit.putBoolean("isKeep", false);
                         edit.putString("username", "");
                         edit.putString("password", "");
                         edit.commit();
                     }

                    Intent intent=new Intent(MainActivity.this, SuccessActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private void getData()
    {
        spSettings=getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        if (spSettings.getBoolean("isKeep",false))
        {
            userName.setText(spSettings.getString("username",""));
            password.setText(spSettings.getString("password",""));
        }
        else
        {
            userName.setText("");
            password.setText("");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
