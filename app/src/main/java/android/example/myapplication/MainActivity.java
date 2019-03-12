package android.example.myapplication;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText Email;
    EditText password;
    EditText username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.layout_register);
        setContentView(R.layout.layout_login);
        //setContentView(R.layout.activity_main);
    }
    //登陆
    public void btnLogin(View view) {
        Email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        String email = Email.getText().toString();
        String pass = password.getText().toString();
        new Login().execute(email,pass);
    }
    //退出登陆
    public void btnLogout(View view) {
        setContentView(R.layout.layout_login);
        return;
    }
    //跳转注册页面
    public void btnRegister(View view) {
        setContentView(R.layout.layout_register);
    }

    public void register(View view) {
        Email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        username = findViewById(R.id.name);
        //获取输入文字
        String E = Email.getText().toString();
        String u = username.getText().toString();
        String p = password.getText().toString();
        //信息提交
        new Signup().execute(E, u, p);
    }

    private class Signup extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... strings) {
            //信息提交
            SharedPreferences userInfo = getSharedPreferences("data", MODE_PRIVATE);
            SharedPreferences.Editor editor = userInfo.edit();//获取Editor
            editor.putString("Email", strings[0]);
            editor.putString("username", strings[1]);
            editor.putString("password", strings[2]);
            editor.commit();//提交注册
            return true;
        }
        protected void onPostExecute(Boolean success) {
            Toast.makeText(getApplicationContext(), "Registration success", Toast.LENGTH_LONG).show();
            setContentView(R.layout.layout_login);//
        }
    }

    private class Login extends  AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... strings) {
            SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE); //读取数据，判断是否存在
            String e = preferences.getString("Email",null);
            Log.i("e:", e);
            String p = preferences.getString("password",null);
            Log.i("p:",p);
            Log.i("fe",strings[0]);
            Log.i("fp",strings[1]);
            if(e.equals(strings[0]) && p.equals(strings[1])) {

                return true;
            }
            else {
                return false;
            }

        }
        protected void onPostExecute(Boolean success) {
            if (success) {
                setContentView(R.layout.activity_main);
                Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Wrong!!!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
