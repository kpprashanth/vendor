package com.example.android.navigationdrawerexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ShowItemDetails extends ActionBarActivity {

    public int position;
    public static ImageView itemimage;
    public static EditText name,discription,price;
    public static TextView tiltle_name;
    public static Button submit,back;
    public static Bitmap upimage=null;
    public static Boolean flag=false;
    public static boolean change=false;
    public ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item_details);
        position=getIntent().getIntExtra("position",-1);

        itemimage= (ImageView) findViewById(R.id.item_image);
        name= (EditText) findViewById(R.id.name);
        discription= (EditText) findViewById(R.id.discription);
        price= (EditText) findViewById(R.id.price);
        tiltle_name= (TextView) findViewById(R.id.title);
        submit= (Button) findViewById(R.id.submit);
        back= (Button) findViewById(R.id.back);


        name.setText(MenuFragment.items[position][0]);
        discription.setText(MenuFragment.items[position][1]);
        price.setText(MenuFragment.items[position][3]);
        tiltle_name.setText(MenuFragment.items[position][0]);
        Bitmap bitmap=null;

        itemimage.setImageDrawable(MenuFragment.imagedetail);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetImage(upimage);
                finish();
            }

        });

        itemimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Contact Image"), 1);
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_item_details, menu);
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

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                //imageUri = data.getData();
                itemimage.setImageURI(data.getData());
                Uri selectedImageUri = data.getData();
                if (Build.VERSION.SDK_INT < 19) {
                    String selectedImagePath = getPath(selectedImageUri);
                    upimage= BitmapFactory.decodeFile(selectedImagePath);
                   // SetImage(bitmap);
                } else {
                    ParcelFileDescriptor parcelFileDescriptor;
                    try {
                        parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri, "r");
                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                        upimage = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                        parcelFileDescriptor.close();
                       // SetImage(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void SetImage(Bitmap image) {
        //this.itemimage.setImageBitmap(image);
        if (image==null){
            image=MenuFragment.image_item[position];
        }

        // upload
        String imageData = encodeTobase64(image);
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("image", imageData));
        params.add(new BasicNameValuePair("CustomerID",MenuFragment.items[position][0] ));
        params.add(new BasicNameValuePair("discription",discription.getText().toString()));
        params.add(new BasicNameValuePair("price",price.getText().toString()));
        params.add(new BasicNameValuePair("name",name.getText().toString()));
        params.add(new BasicNameValuePair("vendor_id",MainActivity.user_id));

        try {
            flag=new AsyncTask<ApiConnector,Long, Boolean >() {
                @Override
                protected Boolean doInBackground(ApiConnector... apiConnectors) {
                    return apiConnectors[0].updateImageToserver(params);
                }
            }.execute(new ApiConnector()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
         Toast.makeText(getApplicationContext(), "Updated... "+flag,
                 Toast.LENGTH_LONG).show();

    }

    public static String encodeTobase64(Bitmap image) {
        System.gc();

        if (image == null)return null;

        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] b = baos.toByteArray();

        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT); // min minSdkVersion 8

        return imageEncoded;
    }


    public String getPath(Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }
    class ImageDownloaderTask extends AsyncTask<String, Bitmap, Bitmap> {

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getApplicationContext());
            pDialog.setMessage("Fetching Items..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            // Toast.makeText(getActivity().getApplicationContext(),"url: "+params[0],Toast.LENGTH_SHORT).show();


          Bitmap icon = null;

                String url = "http://prashantha.zz.mu/images/" + MenuFragment.items[position][0] + ".jpg";
                try {
                    InputStream inputStream = new java.net.URL(url).openStream();

                    icon = BitmapFactory.decodeStream(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            return icon;
        }

    }

}
