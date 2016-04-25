package com.iit.flyer_management;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iit.flyer_management.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {
    public final static String EXTRA_USERNAME = "com.iit.flyer_management.USERNAME";
    public final static String EXTRA_PASSWORD = "com.iit.flyer_management.PASSWORD";
    EditText name, mail, pass;
    TextView error;
    Button inscrire;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.register_name);
        mail = (EditText) findViewById(R.id.register_email);
        pass = (EditText) findViewById(R.id.register_password);
        inscrire=(Button)findViewById(R.id.register_login);
        intent=new Intent(this,LoginActivity.class);
        inscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }
    private void signup ()
    {
        final ProgressDialog pro=new ProgressDialog(RegisterActivity.this,R.style.NewDialog);
        pro.setIndeterminate(true);
        pro.setMessage("Creating Account...");
        pro.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        String namev = name.getText().toString();
                        String emailv = mail.getText().toString();
                        String passwordv = pass.getText().toString();
                        if (valider(namev,emailv,passwordv))
                        {
                            ParseUser user = new ParseUser();
                            user.setUsername(namev);
                            user.setPassword(passwordv);
                            user.setEmail(emailv);
                            intent.putExtra(EXTRA_USERNAME,namev);
                            intent.putExtra(EXTRA_PASSWORD,passwordv);
                            user.signUpInBackground(new SignUpCallback() {
                                public void done(ParseException e) {
                                    if (e == null) {

                                        startActivity(intent);
                                    } else {
                                        pro.dismiss();
                                        error.setText(null);
                                        error.setText("SIGN UP FAILED..Account already existing");
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
    private boolean valider(String name, String mail, String pass) {
        String msg;
        error = (TextView) findViewById(R.id.register_error);
        error.setText(null);
        error.setTextColor(getResources().getColor(R.color.Red));
        if (name.isEmpty() || name.length() < 7 || name.contains(" ")) {
            msg = "Verify Your Username";
            error.setText(msg);
            return false;
        }
        if (mail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            msg = "Enter a valid Email address";
            error.setText(msg);
            return false;
        }
        if (pass.isEmpty() || pass.length() < 5 || pass.length() > 10) {
            msg = "Password must be between 5 and 10 alphanumeric characters";
            error.setText(msg);
            return false;
        }

        return true;
    }
}
