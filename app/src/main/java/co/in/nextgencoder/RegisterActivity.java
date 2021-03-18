package co.in.nextgencoder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.in.nextgencoder.model.User;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText fullNameEdit;
    private TextInputEditText mailEdit;
    private TextInputEditText phoneEdit;
    private TextInputEditText passEdit;
    private TextInputEditText confPassEdit;

    private TextView toLogin;
    private Button registerBtn;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        firebaseAuth = FirebaseAuth.getInstance();

        fullNameEdit = findViewById( R.id.regNameEdit);
        mailEdit = findViewById( R.id.regEmailEdit);
        phoneEdit = findViewById( R.id.regPhoneEdit);
        passEdit = findViewById( R.id.regPassEdit);
        confPassEdit = findViewById( R.id.regConfPassEdit);

        toLogin = findViewById( R.id.toLoginBtn);
        registerBtn = findViewById( R.id.registerSubmitBtn);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), MainActivity.class);
                startActivity( intent);
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fullName = fullNameEdit.getText().toString().trim();
                final String mail = mailEdit.getText().toString().trim();
                final String phone = phoneEdit.getText().toString().trim();
                final String pass = passEdit.getText().toString().trim();

                final User newUser = new User( fullName, mail, phone);

                final String userId = firebaseAuth.getUid();
                newUser.setId( userId);

                firebaseAuth.createUserWithEmailAndPassword( mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful()) {

                            databaseReference.child( firebaseAuth.getUid()).setValue( newUser).addOnCompleteListener(
                                    new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if( task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent( getApplicationContext(), MainActivity.class);
                                                startActivity( intent);
                                            }
                                        }
                                    }
                            );
                        } else {
                            Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if( firebaseUser != null) {
            Intent intent = new Intent( getApplicationContext(), RegisterActivity.class);
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity( intent);
        }
    }
}