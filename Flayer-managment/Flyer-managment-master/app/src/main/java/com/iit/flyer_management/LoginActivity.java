package com.iit.flyer_management;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    Intent intent=null, redirect,home;
    EditText username;
    EditText password;
    Button login,signin;
    TextView error;
    //query.whereEqualTo("Boy", tf3.getText().toString());   //This is to filter things!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        redirect=new Intent(this,RegisterActivity.class);
        home=new Intent(this,MainActivity.class);
        username=(EditText)findViewById(R.id.main_username);
        password=(EditText)findViewById(R.id.main_password);
        error=(TextView)findViewById(R.id.main_error);
        login=(Button)findViewById(R.id.main_login);
        signin=(Button)findViewById(R.id.main_register);
        intent = getIntent();
        if (intent!=null)
        {
            String email=intent.getStringExtra(RegisterActivity.EXTRA_USERNAME);
            String pass=intent.getStringExtra(RegisterActivity.EXTRA_PASSWORD);
            username.setText(email);
            password.setText(pass);
        }
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(redirect);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();
                String pass=password.getText().toString();
                log(user,pass);
            }
        });
    }
    public void log (final String username, final String password)
    {
        final ProgressDialog pro=new ProgressDialog(LoginActivity.this,R.style.NewDialog);
        pro.setIndeterminate(true);
        pro.setMessage("Authentification...");
        pro.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (valider(username,password))
                        {
                            ParseUser.logInInBackground(username, password, new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (e==null)
                                    {
                                        startActivity(home);
                                    }
                                    else
                                    {
                                        pro.dismiss();
                                        error=(TextView) findViewById(R.id.main_error);
                                        error.setText(null);
                                        error.setText("Invalid Username OR Password..");

                                    }
                                }
                            });
                        }
                        else
                        {
                            pro.dismiss();
                        }
                    }
                }, 3000);
    }
    @Override
    public void onBackPressed() {
        // disable going back
        moveTaskToBack(true);
    }


    private boolean valider(String username,String password) {
        String msg;
        error = (TextView) findViewById(R.id.main_error);
        error.setText(null);
        error.setTextColor(getResources().getColor(R.color.Red));
        if (username.isEmpty() ) {
            msg = "Verify Your Username";
            error.setText(msg);
            return false;
        }
        if (password.isEmpty()) {
            msg = "Enter a password";
            error.setText(msg);
            return false;
        }
        return true;
    }
}
