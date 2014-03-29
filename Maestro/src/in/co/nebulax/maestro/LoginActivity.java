package in.co.nebulax.maestro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class LoginActivity extends MyActivity {

	EditText usrName;
	EditText usrPass;
	Button btnLogin, forgotPass;
	TextView reg_redirect;
	CheckBox rememberMe;
	boolean isLoginSuccess = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initialiseFields();
		setFields();

		getSupportActionBar().setTitle("Login");
	}

	public void initialiseFields() {

		usrName = (EditText) findViewById(R.id.login_username);
		usrPass = (EditText) findViewById(R.id.login_password);
		btnLogin = (Button) findViewById(R.id.btn_login);
		reg_redirect = (TextView) findViewById(R.id.tv_reg_redirect);
		forgotPass = (Button) findViewById(R.id.btn_forgotPass);
		rememberMe = (CheckBox) findViewById(R.id.check_rememberMe);
	}

	private void setFields() {
		btnLogin.setOnClickListener(this);
		reg_redirect.setOnClickListener(this);
		forgotPass.setOnClickListener(this);
		rememberMe.setOnCheckedChangeListener(this);

		// Setting initial state of checkbox

		if (isRemembered == true)
			rememberMe.setChecked(true);
		else
			rememberMe.setChecked(false);
	}

	@Override
	public void onClick(View v) {

		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			switch (v.getId()) {
			case R.id.btn_login:
				// Add code to send request for login
				if(isValidated()){
					ParseInstallation curInstallation = ParseInstallation
							.getCurrentInstallation();
					curInstallation.remove("username");
					curInstallation.add("username", usrName.getText().toString());					
					curInstallation.saveInBackground();
					
					new LoginHandler().execute();
				}
					break;
			case R.id.tv_reg_redirect:
				finish();
				Intent intent = new Intent(this, RegistrationActivity.class);
				startActivity(intent);
				break;
			case R.id.btn_forgotPass:
				Intent i = new Intent(this, ForgotPassword.class);
				startActivity(i);
				break;
			}
		} else {
			Toast.makeText(this, "Internet Connection not available",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		// Read data from a shared preferance and store it and change it if
		// necessary

		if (isRemembered == false) {
			rememberEditor.putBoolean("remember", true);
			rememberEditor.commit();
		} else {
			rememberEditor.putBoolean("remember", false);
			rememberEditor.commit();
		}
	}

	public boolean isValidated() {

		if (usrName.getText().toString().equalsIgnoreCase("")
				|| !usrName.getText().toString()
						.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
				|| usrPass.getText().toString() == null) {
			Toast.makeText(this, "Fields Cannot be left empty",
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

	// Async Task class for making HTTP call
	private class LoginHandler extends AsyncTask<Integer, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Integer... arg0) {

			isInternetPresent = cd.isConnectingToInternet();
			if (isInternetPresent) {
				try {
					ParseUser.logIn(usrName.getText().toString(), usrPass
							.getText().toString());
				} catch (Exception e) {
					isLoginSuccess = false;
					Toast.makeText(LoginActivity.this,
							"Something Wicked Happened !!", Toast.LENGTH_SHORT)
							.show();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			if (isLoginSuccess) {
				finish();
				Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
				startActivity(intent);
			}
		}

	}
}