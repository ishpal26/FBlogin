package ishpal.hungrystillmustwait;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Search extends AppCompatActivity implements View.OnClickListener {
    EditText search;
    Button button;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = (EditText) findViewById(R.id.name);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button) {
            Restaurant restaurant = new Restaurant(search.getText().toString());

            authenticate(restaurant);


        }
    }

    private void authenticate(Restaurant restaurant){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchUserDataInBackground(restaurant, new GetCallback() {
            @Override
            public void done(Restaurant returnedRestaurant) {
                if (returnedRestaurant == null) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Search.this);
                    dialogBuilder.setMessage("not found in database");
                    dialogBuilder.setPositiveButton("ok", null);
                    dialogBuilder.show();
                } else {
                    logIn(returnedRestaurant);
                }
            }
        });
    }

    private void logIn(Restaurant returnedRestaurant){
        userLocalStore.storeUserData(returnedRestaurant);
        userLocalStore.setRestLoggedIn(true);

        startActivity(new Intent(this, ResultsPage.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

