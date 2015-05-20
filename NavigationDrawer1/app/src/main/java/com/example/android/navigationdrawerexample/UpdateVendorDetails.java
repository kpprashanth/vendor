package com.example.android.navigationdrawerexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;


public class UpdateVendorDetails extends ActionBarActivity {
    public EditText name,phone,address,station,password,cpassword;
    public Button change_password,submit;
    String u_name,u_phone,u_station,u_address;
    public ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vendor_details);

        name= (EditText) findViewById(R.id.name);
        phone= (EditText) findViewById(R.id.phone);
        address= (EditText) findViewById(R.id.address);
        station= (EditText) findViewById(R.id.station);

        name.setText(VendorDetails.vendor_arr[0]);
        address.setText(VendorDetails.vendor_arr[3]);
        phone.setText(VendorDetails.vendor_arr[2]);
        station.setText(VendorDetails.vendor_arr[4]);

        change_password= (Button) findViewById(R.id.change_pass);
        submit= (Button) findViewById(R.id.submit);


        change_password.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            u_name=name.getText().toString();
            u_phone=phone.getText().toString();
            u_station=station.getText().toString();
            u_address=station.getText().toString();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_vendor_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class Order_details extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getApplicationContext());
            pDialog.setMessage("Fetching Orders..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread

            return params[0].Get_Orders();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            pDialog.dismiss();
            if(jsonArray!=null) {

            }


        }
    }

}
