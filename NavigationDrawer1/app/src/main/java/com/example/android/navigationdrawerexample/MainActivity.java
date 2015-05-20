/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigationdrawerexample;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * This example illustrates a common usage of the DrawerLayout widget
 * in the Android support library.
 * <p/>
 * <p>When a navigation (left) drawer is present, the host activity should detect presses of
 * the action bar's Up affordance as a signal to open and close the navigation drawer. The
 * ActionBarDrawerToggle facilitates this behavior.
 * Items within the drawer should fall into one of two categories:</p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic policies as
 * list or tab navigation in that a view switch does not create navigation history.
 * This pattern should only be used at the root activity of a task, leaving some form
 * of Up navigation active for activities further down the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an alternate
 * parent for Up navigation. This allows a user to jump across an app's navigation
 * hierarchy at will. The application should treat this as it treats Up navigation from
 * a different task, replacing the current task stack using TaskStackBuilder or similar.
 * This is the only form of navigation drawer that should be used outside of the root
 * activity of a task.</li>
 * </ul>
 * <p/>
 * <p>Right side drawers should be used for actions, not navigation. This follows the pattern
 * established by the Action Bar that navigation should be to the left and actions to the right.
 * An action should be an operation performed on the current contents of the window,
 * for example enabling or disabling a data overlay on top of the current content.</p>
 */
public class MainActivity extends FragmentActivity {
    //private static final attr R = ;
    private DrawerLayout mDrawerLayout;
    public static ListView mDrawerList,order_list,menu_list;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout login_layout,order_list_layout,signup_layout,empty_login;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private MyAdapter adapter;
    public static MyAdapter2 adapter2,menu_adapter;
    private View inflatedView;
    private TextView text,login_option,loginPopup;
    private EditText Sign_username,Sign_name,Sign_pass,Sign_phone,Sign_cpass,
            Login_email,Login_pass;
    private View myview;
    private ImageView profile;

    public static FloatingActionButton actionButton;
    public static FloatingActionMenu actionMenu;
    public static SubActionButton additem,refresh;
    public static SubActionButton.Builder itemBuilder;
    public static SubActionButton loginout;
    public static ImageView floatin,floatout;

    private Button Signup,Gosignup;
    ImageButton Login;

    public static DBHelper db;

    public static boolean Selected=true,logged_in=false;


    public static ActionBar actionBar;

    public  static  String user_name="",user_pass="",user_id="";
    public  static int total_order=0,order_count=0,pos,selected_position=0;
    private Session session;
    public static String orders[][];

    private ProgressDialog pDialog;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.enableDefaults(); //STRICT MODE ENABLED



        db=new DBHelper(this);
        session = new Session(getApplicationContext());

         inflatedView = getLayoutInflater().inflate(R.layout.full_layout, null);
        mTitle = mDrawerTitle = getTitle();
        login_layout= (RelativeLayout) findViewById(R.id.login_layout);
     //   order_list_layout= (RelativeLayout) findViewById(R.id.order_list_layout);
        signup_layout= (RelativeLayout) findViewById(R.id.signup_layout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        actionBar=getActionBar();

        text = (TextView) inflatedView.findViewById(R.id.user_name);
        login_option = (TextView) inflatedView.findViewById(R.id.textView1);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        order_list = (ListView) findViewById(R.id.order_list);

        final LayoutInflater factory = getLayoutInflater();

        final View vendor_menu = factory.inflate(R.layout.vendor_menu, null);
        final View order_menu = factory.inflate(R.layout.my_order, null);
        menu_list= (ListView) vendor_menu.findViewById(R.id.menu_list);
       // loginPopup= (TextView) vendor_menu.findViewById(R.id.menu_title);
       // Log.d
        //menu_list.addView(order_menu,0);


        Signup=(Button) findViewById(R.id.Signup);
        Gosignup=(Button) findViewById(R.id.Gosignup);
        Login=(ImageButton) findViewById(R.id.Login);


        Sign_username= (EditText) findViewById(R.id.SignUsername);
        Sign_name= (EditText) findViewById(R.id.vendor);
        Sign_pass= (EditText) findViewById(R.id.SignPass);
        Sign_cpass= (EditText) findViewById(R.id.SignCpass);
        Sign_phone= (EditText) findViewById(R.id.Phone);

        Login_pass= (EditText) findViewById(R.id.Logpass);
        Login_email= (EditText) findViewById(R.id.Logusername);

       // Log_username= (EditText) findViewById(R.id.Logusername);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        adapter=new MyAdapter(this);
        //adapter2=new MyAdapter2(this);
       // menu_adapter=new MyAdapter2(this);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setDividerHeight(0);
        //mDrawerList.setAdapter(adapter);
        // order_list.setAdapter(adapter2);
        // menu_list.setAdapter(menu_adapter);


        menu_list.setAdapter(new ArrayAdapter<String>(this,
             R.layout.drawer_list_item, adapter.mPlanetTitles));

        ImageView floatoption=new ImageView(this);
        floatoption.setImageResource(R.drawable.foption);

        ImageView floatadd=new ImageView(this);
        floatadd.setImageResource(R.drawable.fplus);

         floatin=new ImageView(this);
        floatin.setImageResource(R.drawable.flogin);

        floatout=new ImageView(this);
        floatout.setImageResource(R.drawable.flogout);

        ImageView floatrefresh=new ImageView(this);
        floatrefresh.setImageResource(R.drawable.refresh);

         itemBuilder = new SubActionButton.Builder(this);

        loginout = itemBuilder.setContentView(floatin).build();
         refresh = itemBuilder.setContentView(floatrefresh).build();
         additem = itemBuilder.setContentView(floatadd).build();



        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(floatoption)
                .build();

           actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(loginout)
                .addSubActionView(additem)
                .addSubActionView(refresh)

                        // ...
                .attachTo(actionButton)
                .build();



        Signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                View v = mDrawerList.getChildAt(0);
                View v2 = mDrawerList.getChildAt(5);

                text = (TextView) v.findViewById(R.id.user_name);
                login_option = (TextView) v2.findViewById(R.id.textView1);
                profile = (ImageView) v.findViewById(R.id.user_image);
                profile.setImageResource(R.drawable.welcome);
                text.setTextSize(20);
                text.setTextColor(Color.MAGENTA);
                text.setText("" + Sign_name.getText().toString());

                login_option.setText("Logout");

                floatout.setImageResource(R.drawable.flogout);
                // loginout.setContentView(floatout);
                long id = db.Insert(Sign_username.getText().toString(), Sign_name.getText().toString(),
                        Sign_pass.getText().toString(), Sign_phone.getText().toString());


                Log.d("This is-----------==", (String) text.getText() + id);


                //mDrawerList.invalidateViews();


                //mDrawerList.removeViewAt(0);
                adapter.notifyDataSetChanged();


                loginout.removeView(floatin);
                loginout.setContentView(floatout);

                order_list.setVisibility(View.VISIBLE);
                signup_layout.setVisibility(View.INVISIBLE);
                login_layout.setVisibility(View.INVISIBLE);

            }
        });


        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                user_name=Login_email.getText().toString();
                user_pass=Login_pass.getText().toString();

                new GetAllCustomerTask().execute(new ApiConnector());
                new Order_details().execute(new ApiConnector());


            }
        });

        if(!session.getusename().equals("")){
            user_id=session.getusename();
            user_name=session.getusename();
            user_pass=session.getpassword();
            login_layout.setVisibility(View.INVISIBLE);
            new GetAllCustomerTask().execute(new ApiConnector());
           // new Order_details().execute(new ApiConnector());
           // View vt = mDrawerList.getChildAt(0);
            //login_check(jsonArray);

            //new Order_details().execute(new ApiConnector());
        }


        additem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(logged_in) {
                    Intent i = new Intent(getApplicationContext(), Add_Item.class);
                    startActivity(i);
                }
                else
                    Toast.makeText(getApplicationContext(), "You Need To Login...",
                            Toast.LENGTH_LONG).show();

            }
        });
        loginout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(logged_in) {
                    session.setusename("","");
                    finish();
                    startActivity(getIntent());
                    Toast.makeText(getApplicationContext(), "You are Loged Out",
                            Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "You Need To Login...",
                            Toast.LENGTH_LONG).show();

            }
        });

        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                user_name = Login_email.getText().toString();
                user_pass = Login_pass.getText().toString();
                new GetAllCustomerTask().execute(new ApiConnector());
             //   new Order_details().execute(new ApiConnector());


            }
        });

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        order_list.setOnItemClickListener(new OrderItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF3BBD8F));

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                text = (TextView) inflatedView.findViewById(R.id.user_name);
                Log.d("Thit is----------------", (String) text.getText());
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };



        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        menu.findItem(R.id.cart).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_websearch:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                makeText(this, R.string.app_not_available, LENGTH_LONG).show();
            }
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private class OrderItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           if(total_order!=0) {
            selectOrder(position);
            pos=position;
           }
        }
    }




    private void selectItem(int position) {
       //Selected=true;
        //setTitle(mPlanetTitles[position]);
        switch (position) {
            case 0:
                if(logged_in==true){
                if(Selected==false) {
                    if(selected_position==2) {
                        android.support.v4.app.Fragment frag = getSupportFragmentManager().findFragmentByTag("Item_Fragment");
                        getSupportFragmentManager().beginTransaction().remove(frag).commit();
                    }
                    else if (selected_position==3){
                        android.support.v4.app.Fragment frag2 = getSupportFragmentManager().findFragmentByTag("vendor_details_Fragment");
                        getSupportFragmentManager().beginTransaction().remove(frag2).commit();
                    }
                    //Toast.makeText(getApplicationContext(), "working",
                    //        Toast.LENGTH_LONG).show();
                    if(logged_in)
                        order_list.setVisibility(View.VISIBLE);
                    else
                        login_layout.setVisibility(View.VISIBLE);

                }
                selected_position=position;
                    Selected=true;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Need To Login",
                            Toast.LENGTH_SHORT).show();
                }
                mDrawerLayout.closeDrawers();

                break;
            case 1:
                if(logged_in==true) {
                    Intent i = new Intent(this, MyOrderActivity.class);
                    startActivity(i);
                    Selected=false;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Need To Login",
                            Toast.LENGTH_SHORT).show();
                }

               // Log.d("Hey-----------------------------------------------------------","Working------------------");

                mDrawerLayout.closeDrawers();

                break;
            case 2:
                if(logged_in==true) {
                    MenuFragment fragment = new MenuFragment();

                    Bundle args = new Bundle();

                    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //ExampleFragments fragment = new ExampleFragments();
                    fragmentTransaction.replace(R.id.content_frame, fragment, "Item_Fragment");
                    fragmentTransaction.commit();
                    login_layout.setVisibility(View.INVISIBLE);

                    Selected = false;
                    selected_position = position;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Need To Login",
                            Toast.LENGTH_SHORT).show();
                }
                mDrawerLayout.closeDrawers();
                break;
            case 3:
                if (logged_in==true) {
                    VendorDetails vd = new VendorDetails();
                    android.support.v4.app.FragmentManager fragmentManager_vd = getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction_vd = fragmentManager_vd.beginTransaction();
                    //ExampleFragments fragment = new ExampleFragments();
                    fragmentTransaction_vd.replace(R.id.content_frame, vd, "vendor_details_Fragment");
                    fragmentTransaction_vd.commit();
                    login_layout.setVisibility(View.INVISIBLE);
                    order_list.setVisibility(View.INVISIBLE);

                    Selected = false;
                    selected_position = position;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Need To Login",
                            Toast.LENGTH_SHORT).show();
                }

                mDrawerLayout.closeDrawers();
                break;

            case 5:
                if (logged_in==true) {
                    if(selected_position==2) {
                        android.support.v4.app.Fragment frag = getSupportFragmentManager().findFragmentByTag("Item_Fragment");
                        getSupportFragmentManager().beginTransaction().remove(frag).commit();
                    }
                    else if (selected_position==3){
                        android.support.v4.app.Fragment frag2 = getSupportFragmentManager().findFragmentByTag("vendor_details_Fragment");
                        getSupportFragmentManager().beginTransaction().remove(frag2).commit();
                    }
                    //Toast.makeText(getApplicationContext(), "working",
                    //        Toast.LENGTH_LONG).show();

                        order_list.setVisibility(View.INVISIBLE);

                        login_layout.setVisibility(View.VISIBLE);
                        Login_email.setText("");
                        Login_pass.setText("");
                    logged_in=false;
                    session.setusename("","");
                    finish();
                    startActivity(getIntent());
                    Toast.makeText(getApplicationContext(), "You are Loged Out",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Need To Login",
                            Toast.LENGTH_SHORT).show();
                }

                mDrawerLayout.closeDrawers();
                break;



        }
    }


    public void selectOrder(int position){
        Intent service=new Intent(this,Order_service.class);
        service.putExtra("user_id",orders[position][0]);
        service.putExtra("position",position);
        startActivity(service);

    }






    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



    /**
     * Fragment that appears in the "content_frame", shows a planet
     */

    class MyAdapter extends BaseAdapter{

        public View myview;
        private Context context;
         String[] mPlanetTitles;
        int []images={R.drawable.myorder1,R.drawable.food1,R.drawable.detail1,R.drawable.aboutus,R.drawable.logout1};

        public MyAdapter(Context context){
            this.context=context;
            mPlanetTitles=context.getResources().getStringArray(R.array.planets_array);
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int position) {
            return mPlanetTitles[position-1];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row=null;
            if (convertView==null){
                LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if(position==0) {
                    row = inflater.inflate(R.layout.full_layout, parent, false);
                    myview = inflater.inflate(R.layout.full_layout, parent, false);
                }

                else{
                    row = inflater.inflate(R.layout.custom_row, parent, false);
                }
               // Log.d("Hey-----------------------------------------------------------","Working------------------");

            }
            else{
                    row=convertView;

            }
            if(position!=0 && position<6) {
                TextView title_text = (TextView) row.findViewById(R.id.textView1);
                ImageView title_image = (ImageView) row.findViewById(R.id.imageView1);
              //  Log.d("Postion=========================================================",": "+position);
                title_text.setText(mPlanetTitles[position-1]);
                title_image.setImageResource(images[position-1]);
            }


            return row;
        }
    }



    class MyAdapter2 extends BaseAdapter{

        private Context context;
        String[] mPlanetTitles;
        int i=0;
        int []images={R.drawable.myorder1,R.drawable.food1,R.drawable.detail1,R.drawable.logout1,R.drawable.aboutus};

        public MyAdapter2(Context context){
            this.context=context;
            mPlanetTitles=context.getResources().getStringArray(R.array.orders);
        }

        @Override
        public int getCount() {
            return orders.length;
        }

        @Override
        public Object getItem(int position) {
            return orders[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row=null;
            if (convertView==null){
                LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


                    row = inflater.inflate(R.layout.show_order, parent, false);

                // Log.d("Hey-----------------------------------------------------------","Working------------------");

            }
            else{
                row=convertView;

            }

                TextView order_id = (TextView) row.findViewById(R.id.order_id_title);
                TextView order_user = (TextView) row.findViewById(R.id.order_user);
                TextView order_item = (TextView) row.findViewById(R.id.item_name);
                order_id.setText("Order ID: " + orders[position][0]);
                order_user.setText("customer: " + orders[position][1]);
                order_item.setText("Item: " + orders[position][2]);
               // Log.d("values: ->>>>>>>>>>>>"," "+orders[position][0]);

             //Toast.makeText(this.context,"url: "+orders[position][0],Toast.LENGTH_SHORT).show();

            return row;
        }
    }



    public void login_check(JSONArray jsonArray)
    {
        String s="";
        View vt = mDrawerList.getChildAt(0);

        text = (TextView) vt.findViewById(R.id.user_name);

        for(int i=0; i<jsonArray.length();i++){

            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                s =json.getString("username");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
       if(s.equals(user_name)){
           logged_in=true;
           user_id=user_name;
       }
        else
           logged_in=false;


        if (logged_in) {
            session.setusename(user_id,user_pass);

            View v = mDrawerList.getChildAt(0);
            View v2 = mDrawerList.getChildAt(5);;
            mDrawerList.getChildAt(1).setVisibility(View.VISIBLE);
            mDrawerList.getChildAt(2).setVisibility(View.VISIBLE);
            mDrawerList.getChildAt(3).setVisibility(View.VISIBLE);
            mDrawerList.getChildAt(4).setVisibility(View.VISIBLE);
            mDrawerList.getChildAt(5).setVisibility(View.VISIBLE);
            mDrawerList.setDividerHeight(2);

            text = (TextView) v.findViewById(R.id.user_name);
            login_option = (TextView) v2.findViewById(R.id.textView1);

            profile = (ImageView) v.findViewById(R.id.user_image);

            text.setTextSize(15);
            text.setText(user_name);
            login_option.setText("Logout");


            adapter.notifyDataSetChanged();


            loginout.removeView(floatin);
            loginout.setContentView(floatout);



            order_list.setVisibility(View.VISIBLE);
            signup_layout.setVisibility(View.INVISIBLE);
            login_layout.setVisibility(View.INVISIBLE);
            try {
                JSONArray js=new Order_details().execute(new ApiConnector()).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        else{
            Toast.makeText(getApplicationContext(), "username and password not matching",
                    Toast.LENGTH_LONG).show();

        }



    }

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        new Order_details().execute(new ApiConnector());

    }

    public void arrange_order(JSONArray jsonArray){

        total_order=jsonArray.length();
        if(total_order>0) {
            orders = new String[total_order][7];

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject json = null;
                try {
                    json = jsonArray.getJSONObject(i);
                    orders[i][0] = json.getString("order_id");
                    orders[i][1] = json.getString("name");
                    orders[i][2] = json.getString("item");
                    orders[i][3] = json.getString("price");
                    orders[i][4] = json.getString("vendor_id");
                    orders[i][5] = json.getString("phone");
                    orders[i][6] = json.getString("address");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            order_count=0;
            adapter2=new MyAdapter2(this);
            order_list.setAdapter(adapter2);
        }
        else{
            order_list.setAdapter(adapter);
        }



    }


    private class GetAllCustomerTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Attempting for login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread

            return params[0].GetAllCustomers(user_name,user_pass);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            pDialog.dismiss();
            login_check(jsonArray);


        }
    }

    private class Order_details extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
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
                arrange_order(jsonArray);
                Toast.makeText(getApplicationContext(), "No Orders",
                        Toast.LENGTH_LONG).show();
            }


        }
    }

    public void login_session(String user){
        View v = mDrawerList.getChildAt(0);
        View v2 = mDrawerList.getChildAt(5);;
        mDrawerList.getChildAt(1).setVisibility(View.VISIBLE);
        mDrawerList.getChildAt(2).setVisibility(View.VISIBLE);
        mDrawerList.getChildAt(3).setVisibility(View.VISIBLE);
        mDrawerList.getChildAt(4).setVisibility(View.VISIBLE);
        mDrawerList.getChildAt(5).setVisibility(View.VISIBLE);
        mDrawerList.setDividerHeight(2);

        text = (TextView) v.findViewById(R.id.user_name);
        login_option = (TextView) v2.findViewById(R.id.textView1);

        profile = (ImageView) v.findViewById(R.id.user_image);

        text.setTextSize(15);
        text.setText(user);
        login_option.setText("Logout");


        adapter.notifyDataSetChanged();


        loginout.removeView(floatin);
        loginout.setContentView(floatout);



        order_list.setVisibility(View.VISIBLE);
        signup_layout.setVisibility(View.INVISIBLE);
        login_layout.setVisibility(View.INVISIBLE);

    }

    public boolean isNetworkConnected() {
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED;
    }



}