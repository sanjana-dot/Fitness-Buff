package co.in.nextgencoder.serviceIMPL;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.in.nextgencoder.model.User;
import co.in.nextgencoder.service.UserService;
import co.in.nextgencoder.util.CallBack;

public class UserServiceIMPL implements UserService {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public UserServiceIMPL() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void updateUser(CallBack<Boolean> finishedCallBack, User user) {

    }

    @Override
    public void getSurvey(CallBack<Boolean> finishedCallBack, String id, Double height, Double weight) {
        DatabaseReference userReference = databaseReference.child("users").child(id);
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userReference.child("height").setValue(height);
                userReference.child("weight").setValue(weight);
                finishedCallBack.callback(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                finishedCallBack.callback(false);
            }
        });
    }

    @Override
    public void getUserById(@NonNull CallBack<User> finishedCallback, String id) {

    }
}
