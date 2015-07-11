package com.thomaskioko.hellodroid.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

/**
 * This Activity allows the user to create a new account. It then stores the data in shared preferences.
 * This will be updated, allowing the app to store the data in SQLite and later into an online server.
 *
 * @author <a href="kiokotomas@gmail.com">Thomas Kioko</a>
 * @version Version 1.0
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * This tag is used for debugging.
     */
    private static final String TAG = RegisterActivity.class.getSimpleName();

    EditText etUserName, etEmail, etFirstName, etLastName, etPassword, etConfirmPassword;

    //Classes
    Constants myConstants;
    User userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setTitle("Register");
        }

        userModel = new User();
        myConstants = new Constants();

        etUserName = (EditText) findViewById(R.id.etUserName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        Button register = (Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(this);
        Button btnLogin = (Button) findViewById(R.id.btnBack);
        btnLogin.setOnClickListener(this);

        //This checkbox displays the users password
        CheckBox passwordCheckBox = (CheckBox) findViewById(R.id.checkBoxRegister);
        passwordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
            case R.id.btnRegister:
                /**
                 * Here we do some data Validation. You can do it better than this. I am just showing
                 * you the basic flow.
                 */

                //Check if the data is null
                if (!AppUtils.isValidEmail(etEmail.getText().toString())) {
                    etEmail.setError("Please enter a valid Email");
                }
                if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("You cannot have empty Fields!!!");
                }
                if (etFirstName.getText().toString().isEmpty()) {
                    etFirstName.setError("Enter First Name!!!");
                }
                if (etLastName.getText().toString().isEmpty()) {
                    etLastName.setError("Enter Last Name!!!");
                } else if (!etPassword.getText().toString()
                        .equals(etConfirmPassword.getText().toString())) {
                    AppUtils.displayToastMessage("Passwords Do not Match!!!", RegisterActivity.this);
                } else {

                    userModel.setFullName(etFirstName.getText().toString());
                    userModel.setLastName(etLastName.getText().toString());
                    userModel.setEmail(etEmail.getText().toString());
                    userModel.setPassword(etPassword.getText().toString());
                    userModel.setUserName(etUserName.getText().toString());

                    RegisterAsyncTask task = new RegisterAsyncTask();
                    task.execute();
                }
                break;
            case R.id.btnBack:
                startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                break;
            default:
                Log.e(TAG, "@onClick: Wizard says NO!!!");
                break;
        }
    }

    /**
     * This class enables us to perform long operation in the background without affecting the UI.
     * <p>Title: Parameters passed in the AsyncTask.<p/>
     * 1. Params: Parameters sent to the background task for execution.
     * 2. Progress: Display when the task is happening
     * 3. Result: This is the type of result we expect once the background task is completed
     * <p/>
     * For more information check {@link android.os.AsyncTask}
     */
    private class RegisterAsyncTask extends AsyncTask<String, Void, JSONObject> {

        ProgressDialog progressDialog;


        /**
         * Invoked on the UI thread before the task is executed.
         * This step is normally used to setup the task,
         * for instance by showing a progress bar in the user interface.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Setup the progressBar
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Please Wait....");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        /**
         * Invoked on the background thread immediately after onPreExecute() finishes executing.
         * This step is used to perform background computation that can take a long time.
         *
         * @param params URL
         * @return JsonObject
         */
        @Override
        protected JSONObject doInBackground(String... params) {

            UserFunctions userFunction = new UserFunctions();

            return userFunction.registerUser(userModel.getFullName(), userModel.getLastName(),
                    userModel.getUserName(), userModel.getEmail(), userModel.getPassword());
        }


        /**
         * Invoked on the UI thread after the background computation finishes.
         *
         * @param jsonObjectResult Result from the background task
         */
        @Override
        protected void onPostExecute(JSONObject jsonObjectResult) {
            super.onPostExecute(jsonObjectResult);
            /**
             * Check if the dialog is showing and dismiss it.
             */
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            // check for login response
            try {
                if (jsonObjectResult.getString(Constants.KEY_JSON_SUCCESS) != null) {

                    String res = jsonObjectResult.getString(Constants.KEY_JSON_SUCCESS);
                    if (Integer.parseInt(res) == 1) {

                        JSONObject jsonObject = jsonObjectResult.getJSONObject("user");
                        /**
                         * user successfully registered
                         * Store user details in SQLite Database
                         */

                        userModel.setFullName(jsonObject.getString(Constants.KEY_JSON_FIRST_NAME));
                        userModel.setLastName(jsonObject.getString(Constants.KEY_JSON_LAST_NAME));
                        userModel.setUserName(jsonObject.getString(Constants.KEY_JSON_USER_NAME));
                        userModel.setEmail(jsonObject.getString(Constants.KEY_JSON_EMAIL));


                        // Launch  LoginScreen
                        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        // Close all views before launching the intent
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);

                        AppUtils.displayToastMessage("Registration was Successful", getApplicationContext());
                        // Close Registration Screen
                        finish();
                    } else {
                        // Error in registration
                        AppUtils.displayToastMessage("Error occurred in registration", getApplicationContext());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "@onPostExecute JSONException Message: " + e.getMessage());
            } catch (NullPointerException np) {
                Log.e(TAG, "@onPostExecute NullPointerException Message: " + np.getMessage());
            }
        }
    }

}
