package com.example.saksham.speedcode;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddDisease extends Activity {
    EditText disease, doctor, date, symptoms, prescription, lab_report;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disease);
        disease = (EditText)findViewById(R.id.txtDisease);
        doctor = (EditText)findViewById(R.id.txtDiagnosedBy);
        date = (EditText)findViewById(R.id.txtDate);
        symptoms = (EditText)findViewById(R.id.txtSymptoms);
        prescription = (EditText)findViewById(R.id.txtPrescription);
        lab_report = (EditText)findViewById(R.id.txtLabReport);

        final PostData postData = new PostData(AddDisease.this, "https://docs.google.com/forms/d/13T67pQ3E2YUhNgNhmyWWava7RnkEJHkQPOFqieFgp8Q/formResponse", "entry_133423045", "entry_1336733546", "entry_1119460716", "entry_34263270", "entry_72536820", "entry_1090614742", "entry_177287963");
        b = (Button)findViewById(R.id.btnAddRecord);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData.execute("random", disease.getText().toString(), prescription.getText().toString(), doctor.getText().toString(), date.getText().toString(), lab_report.getText().toString(), symptoms.getText().toString());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_disease, menu);
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
}
