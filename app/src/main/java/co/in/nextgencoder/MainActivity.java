package co.in.nextgencoder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import co.in.nextgencoder.service.UserService;
import co.in.nextgencoder.serviceIMPL.UserServiceIMPL;
import co.in.nextgencoder.util.CallBack;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    UserService userService = new UserServiceIMPL();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DietFragment()).commit();
            bottomNav.setSelectedItemId(R.id.item_diet);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.item_feed:
                            selectedFragment = new FeedFragment();
                            break;
                        case R.id.item_diet:
                            selectedFragment = new DietFragment();
                            break;
                        case R.id.item_add:
                            selectedFragment = new AddFragment();
                            break;
                        case R.id.item_exercise:
                            selectedFragment = new ExerciseFragment();
                            break;
                        case R.id.item_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    public void submitSurvey(View view) {

        EditText heightEdit = findViewById(R.id.heightEdit);
        EditText weightEdit = findViewById(R.id.weightEdit);
        Double height = Double.parseDouble(heightEdit.getText().toString()) ;
        Double weight = Double.parseDouble(weightEdit.getText().toString()) ;

        userService.getSurvey(new CallBack<Boolean>() {
            @Override
            public void callback(Boolean isSuccessful) {

                if(isSuccessful){
                    Toast.makeText(MainActivity.this, "Survey Submitted", Toast.LENGTH_SHORT).show();
                }
            }
        }, firebaseAuth.getUid(), height, weight);
    }

}