package in.co.nebulax.maestro;

import in.co.nebulax.maestro.utils.ConnectionDetector;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragment;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class PortalStudent extends SherlockFragment implements TabListener,
		OnItemSelectedListener {

	View v;

	ParseUser currUser;
	TextView tv_usrName;
	ImageButton submit, btn_requestTutor;
	LinearLayout requestBuilder;
	Spinner subject_select;

	EditText topic_name;

	ProgressDialog pDialog;
	public ConnectionDetector cd;
	public Boolean isInternetPresent;

	String subject = "English";

	public PortalStudent() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.fragment_student, container, false);

		initialiseFields();
		setFields();

		return v;
	}

	public void initialiseFields() {

		// Initialising fields
		tv_usrName = (TextView) v.findViewById(R.id.fragStudent_tv_name);
		btn_requestTutor = (ImageButton) v
				.findViewById(R.id.fragStudent_btn_tutRequest);

		requestBuilder = (LinearLayout) v
				.findViewById(R.id.fragStudent_ll_tut_request);

		subject_select = (Spinner) v
				.findViewById(R.id.fragStudent_spinner_subjectSelect);

		currUser = ParseUser.getCurrentUser();
		tv_usrName.setText("Hi " + currUser.getString("Name"));

		topic_name = (EditText) v.findViewById(R.id.fragStudent_topic_name);
		submit = (ImageButton) v.findViewById(R.id.fragStudent_btn_submit);
	}

	public void setFields() {

		// set fields
		btn_requestTutor.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				requestBuilder.setVisibility(View.VISIBLE);
			}
		});

		subject_select.setOnItemSelectedListener(this);

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isValidated()) {

					ParseObject request = new ParseObject("RequestLog");
					request.put("StudentId", currUser.getObjectId());
					request.put("Subject", subject);
					request.put("Topic", topic_name.getText().toString());
					request.put("Status", 0);
					request.saveInBackground();

					// Add a call to cloud function to search for suitable
					// maestro

					HashMap<String, Object> params = new HashMap<String, Object>();
					params.put("ObjectId", currUser.getObjectId());
					params.put("Subject", subject);
					params.put("Topic", topic_name.getText().toString());
					params.put("Place", "Roorkee");
					ParseCloud.callFunctionInBackground("maestroRequest",
							params, new FunctionCallback<String>() {
								public void done(String result, ParseException e) {
									if (e == null) {
										Log.v("Cloud", result.toString());
										Toast.makeText(getSherlockActivity(),
												"Request Sent",
												Toast.LENGTH_LONG).show();
									}
								}
							});
				}
			}
		});
	}

	public boolean isValidated() {

		return true;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.add(android.R.id.content, this, "Student");
		ft.attach(this);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.detach(this);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int pos, long arg3) {

		subject = parent.getItemAtPosition(pos).toString();

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}
