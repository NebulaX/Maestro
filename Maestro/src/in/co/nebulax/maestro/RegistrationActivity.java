package in.co.nebulax.maestro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;

public class RegistrationActivity extends MyActivity{

	EditText name, email, pass, confirmpass;
	CheckBox student, maestro;
	Button register;
	TextView redirect_login;
	boolean isRegSuccess = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		initialiseFields();
		setFields();
	}

	public void initialiseFields() {

		name = (EditText) findViewById(R.id.reg_name);
		email = (EditText) findViewById(R.id.reg_email);
		pass = (EditText) findViewById(R.id.reg_password);
		confirmpass = (EditText) findViewById(R.id.reg_password_confirm);
		student = (CheckBox) findViewById(R.id.check_student);
		maestro = (CheckBox) findViewById(R.id.check_maestro);
		register = (Button) findViewById(R.id.btn_register);
		redirect_login = (TextView) findViewById(R.id.tv_login_redirect);
	}

	private void setFields() {
		register.setOnClickListener(this);
		redirect_login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_register:
			// Send request to register
			new RegistrationHandler().execute();
			break;

		case R.id.tv_login_redirect:
			showLoginActivity();
			break;
		}
	}

	// Async Task class for making HTTP call
	private class RegistrationHandler extends AsyncTask<Integer, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(RegistrationActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Integer... arg0) {

			// Validate the entries first...
			ParseUser user = new ParseUser();
			user.setUsername(email.getText().toString());
			user.setPassword(pass.getText().toString());
			user.setEmail(email.getText().toString());

			// other fields can be set just like with ParseObject
			user.put("Name", name.getText().toString());

//			user.signUpInBackground(new SignUpCallback() {
//				public void done(ParseException e) {
//					if (e == null) {
//						Toast.makeText(RegistrationActivity.this,
//								"Succesfully Registered !! ",
//								Toast.LENGTH_SHORT).show();
//						showLoginActivity();
//
//					} else {
//						// Sign up didn't succeed. Look at the ParseException
//						// to figure out what went wrong
//						Toast.makeText(
//								RegistrationActivity.this,
//								"Oops !! Something wicked happened . "
//										+ "Please ty again later",
//								Toast.LENGTH_SHORT).show();
//					}
//				}
//			});
			
			try {
				user.signUp();
			} catch (ParseException e) {
				e.printStackTrace();
				isRegSuccess = false;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			if(isRegSuccess){
				Toast.makeText(RegistrationActivity.this, "Successfully Registered !!", 
						Toast.LENGTH_SHORT).show();
				showLoginActivity();}
		}
	}

	private void showLoginActivity() {
		finish();
		Intent i = new Intent(this, LoginActivity.class);
		startActivity(i);
	}

}