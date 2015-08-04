package ishpal.hungrystillmustwait;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ResultsPage extends AppCompatActivity implements View.OnClickListener {
    EditText current;
    TextView textUpdate, textAverage, textAverageTime, textCurrent, textCurrentTime, textUsers, textNumUsers, textAddress, textName;
    Button update;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);

        current = (EditText) findViewById(R.id.currentTime);
        textUpdate = (TextView) findViewById(R.id.textView5);

        textAverage = (TextView) findViewById(R.id.ave);
        textAverageTime = (TextView) findViewById(R.id.aTime);

        textCurrent = (TextView) findViewById(R.id.textView3);
        textCurrentTime = (TextView) findViewById(R.id.cTime);

        textUsers = (TextView) findViewById(R.id.txtUsers);
        textNumUsers = (TextView) findViewById(R.id.usrs);

        update = (Button) findViewById(R.id.updateTime);
        update.setOnClickListener(this);

        textAddress = (TextView) findViewById(R.id.address);
        textName = (TextView) findViewById(R.id.name);

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (authenticate() == true) {

            Restaurant restaurant = userLocalStore.getCurrentRest();

            textAverageTime.setText(Integer.toString(restaurant.getAverageWaitingTime()));
            textCurrentTime.setText(Integer.toString(restaurant.getCurrentWaitingTime()));
            textNumUsers.setText(Integer.toString(restaurant.getNumUsers()));
            textAddress.setText(restaurant.getAddress());
            textName.setText(restaurant.getName());

        } else {
            startActivity(new Intent(this, Search.class));
        }
    }

    private boolean authenticate() {
        return userLocalStore.getRestLoggedIn();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.updateTime) {
            Restaurant restaurant = userLocalStore.getCurrentRest();

            int newCurr = Integer.parseInt(current.getText().toString());
            if (newCurr >= 60) {
                newCurr = 60;
            }
            int newTotal = restaurant.getTotalWaitingTime() + newCurr;
            int newNumUsers = (restaurant.getNumUsers() + 1);

            int newAverage;
            if ((newTotal == 0) || (newNumUsers == 0)) {
                newAverage = 0;
            } else {
                newAverage = newTotal / newNumUsers;
            }
            restaurant.setCurrentWaitingTime(newCurr);
            restaurant.setTotalWaitingTime(newTotal);
            restaurant.setNumUsers(newNumUsers);
            restaurant.setAverageWaitingTime(newAverage);

            updateRestaurant(restaurant);


            Toast.makeText(getApplicationContext(), "Data has been updated, Thank You!", Toast.LENGTH_SHORT).show();
            userLocalStore.clearCurrentRest();
            userLocalStore.setRestLoggedIn(false);
            finish();
        }
    }


    private void updateRestaurant(Restaurant restaurant) {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.updateUserDataInBackground(restaurant, new GetCallback() {
            @Override
            public void done(Restaurant returnedRestaurant) {
            }
        });
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

