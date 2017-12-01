package com.example.aasok1.listapp.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aasok1.listapp.R;
import com.example.aasok1.listapp.entities.UserData;
import com.example.aasok1.listapp.persistence.DatabaseHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        final DatabaseHandler databaseHandler = new DatabaseHandler(this);
        final List<UserData> userlist = databaseHandler.getAllUserDetails();

        if (userlist.size()>0) {
            ArrayList<String> usernameList = new ArrayList<>();
            for (int i = 0; i < userlist.size(); i++) {
                UserData userData = userlist.get(i);
                usernameList.add(userData.getUserName());
            }
            ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.iten_listview,
                    R.id.item_list, usernameList);
            ListView listView = (ListView) findViewById(R.id.user_list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    UserData userData = userlist.get(i);
                    Intent intent = new Intent(ListActivity.this, UserDetails.class);
                    intent.putExtra("userDetails", userData);
                    startActivity(intent);
                }
            });
        }
        else {
            Toast.makeText(this,getResources().getString(R.string.nousers_text),Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getResources().getString(R.string.exit_text))
                .setMessage(getResources().getString(R.string.exit_app_text))
                .setPositiveButton(getResources().getString(R.string.yes_text),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.no_text), null)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}