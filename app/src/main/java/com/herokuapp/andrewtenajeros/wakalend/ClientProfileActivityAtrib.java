package com.herokuapp.andrewtenajeros.wakalend;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Andrew Tenajeros on 9/10/2017.
 */

public class ClientProfileActivityAtrib extends ArrayAdapter<Client> {
    private Activity context;
    List<Client> artists;

    public ClientProfileActivityAtrib(Activity context, List<Client> clients) {
        super(context, R.layout.list_customer, clients);
        this.context = context;
        this.artists = artists;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.content_client_profile, null, true);

        TextView textViewbarangay = (TextView) listViewItem.findViewById(R.id.textView_barangayval);
        TextView textViewdistrict = (TextView) listViewItem.findViewById(R.id.textView_districtval);
        TextView textViewBalance = (TextView) listViewItem.findViewById(R.id.textView_balanceval);

        Client client = artists.get(position);
        textViewbarangay.setText(client.getBarangay());
        textViewdistrict.setText(client.getDistrict());
        textViewBalance.setText(client.getBalance());

        return listViewItem;
    }
}