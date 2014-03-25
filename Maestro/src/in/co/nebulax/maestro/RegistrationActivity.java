package in.co.nebulax.maestro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.Parse;

public class RegistrationActivity extends MyActivity {

	EditText name, email, pass, confirmpass;
	CheckBox student, maestro;
	Button register;
	TextView redirect_login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		initialiseFields();
		setFields();
		Parse.initialize(this, getResources().getString(R.string.APP_ID),
				getResources().getString(R.string.CLIENT_KEY));
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
			break;
		case R.id.tv_login_redirect:
			try {
				super.finish();
			} catch (Exception e) {
			}
			finish();
			Intent i = new Intent(this, LoginActivity.class);
			startActivity(i);
			break;
		}
	}
}
