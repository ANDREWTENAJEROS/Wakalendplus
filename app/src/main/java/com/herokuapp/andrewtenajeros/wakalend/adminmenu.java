package com.herokuapp.andrewtenajeros.wakalend;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class adminmenu extends AppCompatActivity {

    DatabaseReference Clientdb;
    public ListView listViewClient;
    List<Client> clientList;
    public static final String CLIENT_ID = "";
    public static final String CLIENT_NAME = "";
    public static final String CLIENT_BARANGAY = "";
    public static final String CLIENT_DISTRICT = "";
    public static final String CLIENT_BALANCE = "";
    public static final String CLIENT_LOAN = "";
//    private static final String CLIENT_COLLECTOR = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Clientdb = FirebaseDatabase.getInstance().getReference("Client");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminmenu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewClient = (ListView) findViewById(R.id.ListViewCustomer);
        clientList = new ArrayList<>();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.NewclientButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.NewclientDialog dialog = new LoginActivity.NewclientDialog();
                dialog.show(getFragmentManager(),null);
            }
        });

        listViewClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Client client = clientList.get(i);
                Intent intent = new Intent(getApplicationContext(), ClientProfileActivity.class );
                intent.putExtra(CLIENT_ID,client.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Clientdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clientList.clear();

                for(DataSnapshot clientSnapshot: dataSnapshot.getChildren()){
                    Client client = clientSnapshot.getValue(Client.class);
                    clientList.add(client);
                }

                customerlist adapter = new customerlist(adminmenu.this, clientList);
                listViewClient.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
