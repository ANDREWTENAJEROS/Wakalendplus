package com.herokuapp.andrewtenajeros.wakalend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientProfileActivity extends AppCompatActivity {

    DatabaseReference Clientdb;
    String customer1;
    TextView Barangay;
    TextView District;
    TextView Balance;
    TextView loan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Clientdb = FirebaseDatabase.getInstance().getReference("client").child(intent.getStringExtra(adminmenu.CLIENT_ID));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView textViewName = (TextView)findViewById(R.id.textView_Clientval);
        final TextView textViewBalance = (TextView)findViewById(R.id.textView_balanceval);
//        final TextView textViewLoan = (TextView)findViewById(R.id.textView_loanval);
        final TextView textViewBarangay = (TextView)findViewById(R.id.textView_barangayval);
        final TextView textViewDistrict = (TextView)findViewById(R.id.textView_districtval);
        String id = getIntent().getStringExtra(adminmenu.CLIENT_ID);
        Clientdb = FirebaseDatabase.getInstance().getReference("Client").child(id);
//        textViewName.setText(intent.getStringExtra(adminmenu.CLIENT_NAME));
//        textViewBalance.setText(intent.getStringExtra(adminmenu.CLIENT_BALANCE));
//        textViewBarangay.setText(intent.getStringExtra(adminmenu.CLIENT_BARANGAY));


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                textViewBalance.setText(dataSnapshot.getValue(Client.class).getBalance());
////                textViewLoan.setText(dataSnapshot.getValue(Client.class).getLoan());
//                textViewBarangay.setText(dataSnapshot.getValue(Client.class).getBarangay());
//                textViewDistrict.setText(dataSnapshot.getValue(Client.class).getDistrict());
//                String name = dataSnapshot.getValue(Client.class).getFirstname() + " " + dataSnapshot.getValue(Client.class).getLastname();
//

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        Clientdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Client client = dataSnapshot.getValue(Client.class);
                String cname = client.getFirstname() + " " + client.getLastname();

                textViewName.setText(cname);
                textViewBalance.setText(client.getBalance());
//                textViewLoan.setText();
                textViewBarangay.setText(client.getBarangay());
                textViewDistrict.setText(client.getDistrict());
                textViewBalance.setText(client.getBalance());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });












        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "user is deleted", Snackbar.LENGTH_LONG);
              deleteClient();
                Toast.makeText(ClientProfileActivity.this, adminmenu.CLIENT_ID , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), adminmenu.class );
                startActivity(intent);
            }
        });
    }

    private void deleteClient() {
        Intent intent = getIntent();
        String clientId = intent.getStringExtra(adminmenu.CLIENT_ID);
        Clientdb = FirebaseDatabase.getInstance().getReference("client").child(clientId);
        Toast.makeText(ClientProfileActivity.this, "delete: "+clientId , Toast.LENGTH_SHORT).show();
        Clientdb.child("client").removeValue();
//        Clientdb.removeValue();
    }


}
