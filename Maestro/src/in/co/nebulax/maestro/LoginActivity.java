package in.co.nebulax.maestro;

import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends MyActivity {

	EditText usrName;
	EditText usrPass;
	Button btnLogin;
	TextView reg_redirect;
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
	}

	private void setFields() {
		btnLogin.setOnClickListener(this);
		reg_redirect.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		case R.id.btn_login:
			//Add code to send request for login
			new LoginHandler().execute();
			break;
		case R.id.tv_reg_redirect :
			finish();
			Intent intent = new Intent(this , RegistrationActivity.class);
			startActivity(intent);
			break;
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
				
				try {
					ParseUser.logIn(usrName.getText().toString(), 
							usrPass.getText().toString());
				} catch (ParseException e) {
					e.printStackTrace();
					isLoginSuccess = false;
					Toast.makeText(LoginActivity.this, "Something Wicked Happened !!",
							Toast.LENGTH_SHORT).show();
				}
				
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				// Dismiss the progress dialog
				if (pDialog.isShowing())
					pDialog.dismiss();
				if(isLoginSuccess){
					finish();
					Intent intent = new Intent(LoginActivity.this , HomeScreen.class);
					startActivity(intent);
				}
			}
			
		}
}