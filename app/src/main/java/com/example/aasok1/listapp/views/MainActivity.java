package com.example.aasok1.listapp.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aasok1.listapp.R;
import com.example.aasok1.listapp.entities.UserData;
import com.example.aasok1.listapp.persistence.DatabaseHandler;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mUserName, mPasword, mFullname, mEmail, mMobile;
    Button mRegisterButton, mUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        initViews();
    }

    private void initViews() {
        mUserName = (EditText) findViewById(R.id.username_edit);
        mPasword = (EditText) findViewById(R.id.password_edit);
        mFullname = (EditText) findViewById(R.id.fullname_edit);
        mEmail = (EditText) findViewById(R.id.email_edit);
        mMobile = (EditText) findViewById(R.id.mobile_edit);
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(this);
        mUserList = (Button) findViewById(R.id.userlistButton);
        mUserList.setOnClickListener(this);
    }

    private void saveData() {

        if (isEmpty(mUserName)) {
            mUserName.setError(getResources().getString(R.string.username_empty_text));
            return;
        }
        if (isEmpty(mPasword)) {
            mPasword.setError(getResources().getString(R.string.password_empty_text));
            return;
        }
        if (isEmpty(mFullname)) {
            mFullname.setError(getResources().getString(R.string.fullname_empty_text));
            return;
        }
        if (isEmpty(mEmail)) {
            mEmail.setError(getResources().getString(R.string.email_empty_text));
            return;
        }
        if (isEmpty(mMobile)) {
            mMobile.setError(getResources().getString(R.string.mobile_empty_text));
            return;
        }
        if (!isValidEmaillId(mEmail.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.invalid_email_text), Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        String username = mUserName.getText().toString();
        String password = mPasword.getText().toString();
        String fullname = mFullname.getText().toString();
        String email = mEmail.getText().toString();
        String mobile = mMobile.getText().toString();

        if (username != null && username.length() != 0 && password != null && password.length() != 0 && fullname != null && fullname
                .length() != 0 && email != null && email.length() != 0 && mobile != null && mobile.length() != 0) {
            UserData userdata = databaseHandler.getUserData(username);
            if (userdata != null) {
                Toast.makeText(this,
                        getResources().getString(R.string.username_exist_text),
                        Toast.LENGTH_LONG).show();
                return;
            } else {
                databaseHandler.addUser(new UserData(username, password, fullname, email, mobile),
                        this);
            }
        }

        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        startActivity(intent);
        finish();
    }

    private void listData() {
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register_button) {
            saveData();
        }
        if (view.getId() == R.id.userlistButton) {
            listData();
        }
    }

    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }

    private boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}
