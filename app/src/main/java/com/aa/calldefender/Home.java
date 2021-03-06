package com.aa.calldefender;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

//Class for 2nd activity which is the main 'hub' that contains 3 fragments
public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    int frag_id = 1; //set the fragment id to 1

    @Override
    protected void onCreate(Bundle savedInstanceState) { //On creation

        super.onCreate(savedInstanceState);

        //Set view and navigation bar
        setContentView(R.layout.activity_home);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        //Determine what fragment should be loaded via saved instances
        if(savedInstanceState != null){
            int frag = savedInstanceState.getInt("Fragment", 1);

            if (frag == 1) { //If 1 HomeFragment
                loadFragment(new HomeFragment());
                frag_id = 1;
            }
            else if (frag == 2) { //If 2 AddFragment
                loadFragment(new AddFragment());
                frag_id = 2;
            }

            else if (frag == 3) { //If 3 ViewFragment
                ViewFragment f = new ViewFragment();
                loadFragment(f);
                frag_id = 3;

            }
        } else { //Else load homeFragment if no fragment has been specified
            loadFragment(new HomeFragment());
        }
    }

    //Function that loads fragments in
    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    //Function that will determine which fragment will displayed on screen after a user has clicked on the bottom nav
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                frag_id = 1;
                break;

            case R.id.navigation_add:
                fragment = new AddFragment();
                frag_id = 2;
                break;

            case R.id.navigation_view:
                fragment = new ViewFragment();
                frag_id = 3;
                break;
        }

        return loadFragment(fragment); //call function for loading the desired fragment

    }

    //Function that enables the custom broadcast receiver
    public void enableBroadcastReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, CallInterceptor.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

    }

    //Function that disables the custom broadcast receiver
    public void disableBroadcastReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, CallInterceptor.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

    }

    //Function that saves the current fragment to saved instances
    // (required so correct fragment is displayed when the screen is rotated)
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("Fragment", frag_id);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
}
