package com.herokuapp.andrewtenajeros.wakalend;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientProfileActivity extends AppCompatActivity {

    DatabaseReference Clientdb;
    String customer1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView textViewBalance = (TextView)findViewById(R.id.textView_balanceval);
        final TextView textViewLoan = (TextView)findViewById(R.id.textView_loanval);
        final TextView textViewBarangay = (TextView)findViewById(R.id.textView_barangayval);
        final TextView textViewDistrict = (TextView)findViewById(R.id.textView_districtval);
        String id = getIntent().getStringExtra(adminmenu.CLIENT_ID);
        Clientdb = FirebaseDatabase.getInstance().getReference("Client").child(id);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                textViewBalance.setText(dataSnapshot.getValue(Client.class).getBalance());
                textViewLoan.setText(dataSnapshot.getValue(Client.class).getLoan());
                textViewBarangay.setText(dataSnapshot.getValue(Client.class).getBarangay());
                textViewDistrict.setText(dataSnapshot.getValue(Client.class).getDistrict());
                String name = dataSnapshot.getValue(Client.class).getFirstname() + " " + dataSnapshot.getValue(Client.class).getLastname();
                toolbar.setTitle(name);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };














        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
