package com.example.ginz.contactapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.Callback {

    private List<Contact> mContacts;
    private RecyclerView mRecyclerContact;
    private RecyclerViewAdapter mAdapter;
    private static final int REQUEST_PERMISSION_CODE = 1;
    private static final int NUMB_ZERO = 0;
    private static final String CONTENT_TEL = "tel:";
    private static final String MESSAGE_PERMISSION_DENIED = "Permission denied to read your External storage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if(checkPermission()){
            showAllContacts();
        }
        else{
            requestPermission();
            showAllContacts();
        }
    }

    private boolean checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
            else {
                return true;
            }
        }
        return true;
    }

    @SuppressLint("NewApi")
    private void requestPermission() {
        requestPermissions( new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE},
                REQUEST_PERMISSION_CODE);
    }

    private void init(){
        mContacts = new ArrayList<>();
        mRecyclerContact = findViewById(R.id.recycler_contact);
    }

    private ArrayList<Contact> getContacts(){
        ArrayList<Contact> contacts = new ArrayList<>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contacts.add(new Contact(name, phoneNumber));
        }
        phones.close();
        return contacts;
    }

    private void showAllContacts()
    {
       if(getContacts() != null){
           updateUI(getContacts());
       }

    }

    private void updateUI(ArrayList<Contact> contacts){
        mAdapter = new RecyclerViewAdapter(this, this, contacts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerContact.setLayoutManager(linearLayoutManager);
        mRecyclerContact.setAdapter(mAdapter);
    }

    private void callPhone(int position){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(CONTENT_TEL + mContacts.get(position).getPhoneNumber()));
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(CONTENT_TEL + mContacts.get(position).getPhoneNumber()));
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > NUMB_ZERO && grantResults[NUMB_ZERO] == PackageManager.PERMISSION_GRANTED) {
                    showAllContacts();
                }
                else {
                    Toast.makeText(MainActivity.this, MESSAGE_PERMISSION_DENIED, Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onClickListener(int position) {
        callPhone(position);
    }
}
