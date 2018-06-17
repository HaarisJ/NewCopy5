package com.example.haaris.newcopy5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PlayGamesAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignInActivity extends AppCompatActivity {

    private final static int RC_SIGN_IN = 2;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //String playerName = currentUser.getDisplayName();
        TextView dispname = findViewById(R.id.txt_dispname);
        EditText chosenUsername = findViewById(R.id.txt_username);
        signInSilently();

  UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
               .setDisplayName("Paradox")
//              .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

//          currentUser.updateProfile(profileUpdates)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("TAG", "User profile updated.");
//                        }
//                    }
//                });

         // dispname.setText(currentUser.getDisplayName()); //display player name



        //updateUI(currentUser);
    }

    @Override
    protected void onResume() {
        super.onResume();
        signInSilently();
    }

    //Manual Sign In
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            if (result.isSuccess()) {
//                // The signed in account is stored in the result.
//                GoogleSignInAccount signedInAccount = result.getSignInAccount();
//                firebaseAuthWithPlayGames(signedInAccount);
//            } else {
//                String message = result.getStatus().getStatusMessage();
//                if (message == null || message.isEmpty()) {
//                    message = getString(R.string.signin_other_error);
//                }
//                new AlertDialog.Builder(this).setMessage(message)
//                        .setNeutralButton(android.R.string.ok, null).show();
//            }
//        }
//    }

    private void signInSilently() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            // The signed in account is stored in the task's result.
                            GoogleSignInAccount signedInAccount = task.getResult();
                            firebaseAuthWithPlayGames(signedInAccount);
                        } else {
                            //startSignInIntent();
                            // Player will need to sign-in explicitly using via UI
                        }
                    }
                });
    }
    // Start manual sign-in when silent fails
//    private void startSignInIntent() {
//        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
//                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
//        Intent intent = signInClient.getSignInIntent();
//        startActivityForResult(intent, RC_SIGN_IN);
//    }


    // Call this both in the silent sign-in task's OnCompleteListener and in the
    // Activity's onActivityResult handler.
    private void firebaseAuthWithPlayGames(GoogleSignInAccount acct) {
        //Log.d("TAG", "firebaseAuthWithPlayGames:" + acct.getId());

        AuthCredential credential = PlayGamesAuthProvider.getCredential(acct.getServerAuthCode());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }






}
