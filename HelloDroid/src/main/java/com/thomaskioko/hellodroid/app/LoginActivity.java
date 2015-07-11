package com.thomaskioko.hellodroid.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.thomaskioko.hellodroid.app.model.User;
import com.thomaskioko.hellodroid.app.utils.AppUtils;
import com.thomaskioko.hellodroid.app.utils.Constants;
import com.thomaskioko.hellodroid.app.utils.UserFunctions;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = LoginActivity.class.getSimpleName();
    boolean checkBoxState;
    EditText mEtEmail, mEtPassword;
    User mUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setTitle("Login");

        }

        //Create an instance of {@link: User} class
        mUserModel = new User();

        mEtEmail = (EditText) findViewById(R.id.etLoginEmail);
        mEtPassword = (EditText) findViewById(R.id.etLoginPassword);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        Button btnCancel = (Button) findViewById(R.id.btnRegister);
        btnCancel.setOnClickListener(this);

        CheckBox rememberMe = (CheckBox) findViewById(R.id.cbRememberMe);
        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxState = isChecked;
                AppUtils.displayToastMessage(String.valueOf(checkBoxState), getApplicationContext());
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (!AppUtils.isValidEmail(mEtEmail.getText().toString())) {
                    mEtEmail.setError("Please enter a valid Email");
                } else if (mEtPassword.getText().toString().isEmpty()) {
                    mEtPassword.setError("You cannot have empty Fields.");
                } else {
                    LoginAsyncTask loginTask = new LoginAsyncTask();
                    loginTask.execute();
                }
                break;
            case R.id.btnRegister:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;
            default:
                Log.e(TAG, "We have a problem. Sermon @code_wizard!!!");
                break;
        }
    }

    /**
     * AsyncTask class that performs Login operations.
     * For more information on the Async task {@see android.os.AsyncTask}
     */
    private class LoginAsyncTask extends AsyncTask<String, Void, JSONObject> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Setup the progress Dialog
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Logging in ....");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            UserFunctions userFunction = new UserFunctions();
            return userFunction.loginUser(mUserModel.getEmail(), mUserModel.getPassword());
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            try {
                if (jsonObject.getString(Constants.KEY_JSON_SUCCESS) != null) {
                    String res = jsonObject.getString(Constants.KEY_JSON_SUCCESS);

                    if (Integer.parseInt(res) == 1) {// user successfully logged in
                        // Store user details in SQLite Database
                        JSONObject json_user = jsonObject.getJSONObject("user");

                        Log.d(TAG, json_user.getString(Constants.KEY_JSON_FIRST_NAME));
                        Log.d(TAG, json_user.getString(Constants.KEY_JSON_EMAIL));


                        // Launch Home Screen
                        Intent intentHomeScreen = new Intent(getApplicationContext(), MainActivity.class);

                        // Close all views before launching Dashboard
                        intentHomeScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intentHomeScreen);

                        AppUtils.displayToastMessage("Welcome", getApplicationContext());
                        // Close Login Screen
                        finish();
                    } else {
                        // Error in login
                        AppUtils.displayToastMessage("Incorrect username/password", getApplicationContext());
                    }
                }
            } catch (NullPointerException np) {
                np.printStackTrace();
            }catch (JSONException np) {
                np.printStackTrace();
            }
        }
    }
}
