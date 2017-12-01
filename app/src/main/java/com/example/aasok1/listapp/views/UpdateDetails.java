package com.example.aasok1.listapp.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aasok1.listapp.R;
import com.example.aasok1.listapp.entities.UserData;
import com.example.aasok1.listapp.persistence.DatabaseHandler;

import java.util.regex.Pattern;

public class UpdateDetails extends AppCompatActivity implements View.OnClickListener {

    EditText mUserName, mPasword, mFullname, mEmail, mMobile;
    Button mUpdateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        initViews();
        fetchDetails();
    }

    private void fetchDetails() {
        UserData userData = (UserData) getIntent().getSerializableExtra("userDetailsforUpdate");
        if (userData != null) {
            mUserName.setText(userData.getUserName());
            mPasword.setText(userData.getUserPassword());
            mFullname.setText(userData.getUserFullName());
            mEmail.setText(userData.getUserEmail());
            mMobile.setText(userData.getUserMobile());
        }
    }

    private void initViews() {
        mUserName = (EditText) findViewById(R.id.username_edit_update);
        mPasword = (EditText) findViewById(R.id.password_edit_update);
        mFullname = (EditText) findViewById(R.id.fullname_edit_update);
        mEmail = (EditText) findViewById(R.id.email_edit_update);
        mMobile = (EditText) findViewById(R.id.mobile_edit_update);
        mUpdateButton = (Button) findViewById(R.id.update_button);
        mUpdateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.update_button) {
            updateDetails();
        }
    }

    private void updateDetails() {
        if (isEmpty(mUserName)) {
            mUserName.setError("Username field cannot be empty");
            return;
        }
        if (isEmpty(mPasword)) {
            mPasword.setError("Password field cannot be empty");
            return;
        }
        if (isEmpty(mFullname)) {
            mFullname.setError("FullName field cannot be empty");
            return;
        }
        if (isEmpty(mEmail)) {
            mEmail.setError("Email field cannot be empty");
            return;
        }
        if (isEmpty(mMobile)) {
            mMobile.setError("Mobile field cannot be empty");
            return;
        }
        if (!isValidEmaillId(mEmail.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "Invalid Email Address.", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        String username = mUserName.getText().toString();

        String password = mPasword.getText().toString();
        String fullname = mFullname.getText().toString();
        String email = mEmail.getText().toString();
        String mobile = mMobile.getText().toString();
        UserData data = new UserData();
        data.setUserName(username);
        data.setUserPassword(password);
        data.setUserFullName(fullname);
        data.setUserEmail(email);
        data.setUserMobile(mobile);

        if (username != null && username.length() != 0 && password != null && password.length() != 0 && fullname != null && fullname
                .length() != 0 && email != null && email.length() != 0 && mobile != null && mobile.length() != 0) {
            int count = databaseHandler.updateUserData(data);
            Toast.makeText(this, "Data has been Updated" + count, Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(UpdateDetails.this, ListActivity.class);
        startActivity(intent);
        finish();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case android.R.id.home:
                onBackPressed();
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }
}
