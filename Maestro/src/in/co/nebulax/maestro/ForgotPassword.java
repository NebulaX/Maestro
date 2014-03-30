package in.co.nebulax.maestro;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;

public class ForgotPassword extends MyActivity {

	EditText usrname;
	ImageButton submit;
	boolean resetSuccess = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgotpassword);
		
		usrname = (EditText) findViewById(R.id.forgot_username);
		submit = (ImageButton) findViewById(R.id.btn_forgotpass_submit);

		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);

		new ForgotPassHandler().execute();
	}

	// Async Task class for making HTTP call
	private class ForgotPassHandler extends AsyncTask<Integer, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(ForgotPassword.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Integer... arg0) {

			try {
				ParseUser.requestPasswordReset(usrname.getText().toString());
			} catch (ParseException e) {
				e.printStackTrace();
				resetSuccess = false;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			if(resetSuccess){
				Toast.makeText(ForgotPassword.this, "Please check ur mail",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}

	}
}