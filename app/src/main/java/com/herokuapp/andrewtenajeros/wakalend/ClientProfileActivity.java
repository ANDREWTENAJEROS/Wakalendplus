package com.herokuapp.andrewtenajeros.wakalend;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class ClientProfileActivity extends AppCompatActivity {

    public static String CID;
    DatabaseReference Clientdb;
    String customer1;
    String customerid;
    TextView Barangay;
    TextView District;
    TextView Balance;
    TextView loan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Intent intent = getIntent();
        Clientdb = FirebaseDatabase.getInstance().getReference("client").child(intent.getStringExtra(adminmenu.CLIENT_ID));
//      todo set activity bar as client name
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final TextView textViewName = (TextView)findViewById(R.id.textView_Clientval);
        final TextView textViewBalance = (TextView)findViewById(R.id.textView_balanceval);
//        final TextView textViewLoan = (TextView)findViewById(R.id.textView_loanval);
        final TextView textViewBarangay = (TextView)findViewById(R.id.textView_barangayval);
        final TextView textViewDistrict = (TextView)findViewById(R.id.textView_districtval);
        String id = getIntent().getStringExtra(adminmenu.CLIENT_ID);
        Clientdb = FirebaseDatabase.getInstance().getReference("Client").child(id);
        final String clientId = intent.getStringExtra(adminmenu.CLIENT_ID);
        Button buttonedit = (Button)findViewById(R.id.button_edit);

        Clientdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    Client client = dataSnapshot.getValue(Client.class);
                    String cname = client.getFirstname() + " " + client.getLastname();
                    customer1 = cname;
                    customerid = client.getId();
                    textViewName.setText(cname);
                    textViewBalance.setText(client.getBalance());
//                textViewLoan.setText();
                    textViewBarangay.setText(client.getBarangay());
                    textViewDistrict.setText(client.getDistrict());
                    textViewBalance.setText(client.getBalance());

                }
//                if(client.getBarangay()=="0"){ //DELETE CLIENT IF BALANCE IS 0
//                    deleteClient(clientId);
//                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

//        getActionBar().setTitle(customer1);


        buttonedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 showUpdateDialog(customerid,customer1 );
            }
        });


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteClient(clientId);
                Intent intent = new Intent(getApplicationContext(), adminmenu.class );
                startActivity(intent);

            }
        });
    }

    private void deleteClient(String clientId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Client").child(clientId);
        Toast.makeText(ClientProfileActivity.this, "Client Removed" , Toast.LENGTH_SHORT).show();
        if(clientId != null)
        databaseReference.removeValue();
        else
            Toast.makeText(ClientProfileActivity.this, "error" , Toast.LENGTH_SHORT).show();
        /*

        Toast.makeText(ClientProfileActivity.this, "delete: "+CID , Toast.LENGTH_SHORT).show();
        */
    }

    public void showUpdateDialog(final String clientid, final String clientname) {
        DatabaseReference Clientdb;

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.editclient_dialog, null);
            builder.setView(dialogView);
            final EditText firstnameField = (EditText)dialogView.findViewById(R.id.firstname);
            final EditText lastnameField = (EditText)dialogView.findViewById(R.id.lastname);
            final EditText BarangayField = (EditText)dialogView.findViewById(R.id.barangay);
            final EditText DistrictField = (EditText)dialogView.findViewById(R.id.district);
            final EditText LoanField = (EditText)dialogView.findViewById(R.id.loan);
            final EditText DaysField = (EditText)dialogView.findViewById(R.id.days);
            final Button buttonupdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        builder.setTitle("Update "+clientname);
        final AlertDialog alertdialog = builder.create();
        alertdialog.show();
            buttonupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String firstname = firstnameField.getText().toString();
                    String lastname = lastnameField.getText().toString();
                    String barangay = BarangayField.getText().toString();
                    String district = DistrictField.getText().toString();
                    String loan = LoanField.getText().toString();
                    String days = DaysField.getText().toString();
                    if(TextUtils.isEmpty(firstname)){
                        firstnameField.setError("First Name is required");
                    }
                    if(TextUtils.isEmpty(lastname)){
                        lastnameField.setError("Last Name is required");
                    }
                    if(TextUtils.isEmpty(barangay)){
                        BarangayField.setError("Address is required");
                    }
                    if(TextUtils.isEmpty(district)){
                        DistrictField.setError("Contact number is required");
                    }
                    if(TextUtils.isEmpty(loan)){
                        LoanField.setError("Loan amount is required");
                    }
                    if(TextUtils.isEmpty(days)){
                        DaysField.setError("Days to pay is required");
                    }

                    updateClient(clientid,firstname,lastname,barangay,district,loan,days);
                    alertdialog.dismiss();

                }
            });
    }

    private boolean updateClient(String ClientID, String firstname , String lastname, String barangay, String district, String loan, String days){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Client").child(ClientID);
        Client client = new Client(ClientID,firstname,lastname,barangay,district,loan,days);

        databaseReference.setValue(client);
//        Toast.makeText(this, ClientID+": updated to: "+firstname+" "+lastname, Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Client updated", Toast.LENGTH_LONG).show();
        return true;
    }

}
