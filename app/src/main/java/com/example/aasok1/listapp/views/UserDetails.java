package com.example.aasok1.listapp.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aasok1.listapp.R;
import com.example.aasok1.listapp.entities.UserData;
import com.example.aasok1.listapp.persistence.DatabaseHandler;

import org.w3c.dom.Text;

public class UserDetails extends AppCompatActivity implements View.OnClickListener {

    TextView mUserName, mPasword, mFullname, mEmail, mMobile;
    Button mUpdateButton;
    UserData mUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        initViews();
        getUserData();

    }

    private void initViews(){
        mUserName=(TextView)findViewById(R.id.username_display);
        mFullname=(TextView)findViewById(R.id.fullname_display);
        mEmail=(TextView)findViewById(R.id.email_display);
        mMobile=(TextView)findViewById(R.id.mobile_display);
        mUpdateButton=(Button)findViewById(R.id.details_update_button);
        mUpdateButton.setOnClickListener(this);
    }

    private void getUserData(){
        mUserData=(UserData) getIntent().getSerializableExtra("userDetails");
        mUserName.setText(mUserData.getUserName());
        mFullname.setText(mUserData.getUserFullName());
        mEmail.setText(mUserData.getUserEmail());
        mMobile.setText(mUserData.getUserMobile());
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.details_update_button){
            Intent intent = new Intent(UserDetails.this, UpdateDetails.class);
            intent.putExtra("userDetailsforUpdate",mUserData);
            startActivity(intent);
            finish();

        }

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
