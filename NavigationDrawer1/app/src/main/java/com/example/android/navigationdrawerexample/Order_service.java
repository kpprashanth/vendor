package com.example.android.navigationdrawerexample;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.concurrent.ExecutionException;


public class Order_service extends ActionBarActivity {

    public static TextView name,orderid,item,price;

    public static Button accept,reject;

    public static int position;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_service);
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);




        name= (TextView) findViewById(R.id.name);
        orderid= (TextView) findViewById(R.id.order_id);
        item= (TextView) findViewById(R.id.item);
        price= (TextView) findViewById(R.id.price);
        position=getIntent().getIntExtra("position",-1);
        name.setText(MainActivity.orders[position][1]);
        orderid.setText(MainActivity.orders[position][0]);
        item.setText(MainActivity.orders[position][2]);
        price.setText("Rs " + MainActivity.orders[position][3]);


        accept= (Button) findViewById(R.id.accept);
        reject= (Button) findViewById(R.id.reject);

        accept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //new Order_insert().execute(new ApiConnector());
               // MainActivity.order_list.removeViewAt(position);
                //setResult(RESULT_OK, null);
                try {
                    JSONArray j=new Order_insert().execute(new ApiConnector()).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                finish();


            }
        });

        reject.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

               // new Order_insert().execute(new ApiConnector());
                // MainActivity.order_list.removeViewAt(position);
                setResult(RESULT_OK, null);
                finish();


            }
        });

//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cart_icon);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_service, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private class Order_insert extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Order_service.this);
            pDialog.setMessage("Fetching Orders..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread

            return params[0].Insert_Orders(MainActivity.orders,position);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            pDialog.dismiss();
            //arrange_order(jsonArray);


        }
    }

}
