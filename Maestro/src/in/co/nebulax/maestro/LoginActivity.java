package in.co.nebulax.maestro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class LoginActivity extends SherlockActivity implements OnClickListener {

	EditText usrName;
	EditText usrPass;
	Button btnLogin;
	TextView reg_redirect;

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
			break;
		case R.id.tv_reg_redirect :
			finish();
			Intent intent = new Intent(this , RegistrationActivity.class);
			startActivity(intent);
			break;
		}

	}
}