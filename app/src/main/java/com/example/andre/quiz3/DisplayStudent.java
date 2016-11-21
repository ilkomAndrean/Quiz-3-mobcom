package com.example.andre.quiz3;

/**
 * Created by xiiip on 11/21/2016.
 */

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayStudent extends Activity {
    int i = 0;
    private DBHelper mydb ;

    TextView id;
    TextView name ;
    TextView phone;
    TextView email;
    TextView noreg;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_form);
        id = (TextView) findViewById(R.id.idku);
        name = (TextView) findViewById(R.id.editTextName);
        phone = (TextView) findViewById(R.id.editTextPhone);
        noreg = (TextView) findViewById(R.id.editTextNoreg);
        email = (TextView) findViewById(R.id.editTextEmail);


        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");

            if(Value>0){
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();
                String id = rs.getString(rs.getColumnIndex(DBHelper.STUDENT_COLUMN_ID));
                String nam = rs.getString(rs.getColumnIndex(DBHelper.STUDENT_COLUMN_NAME));
                String nor = rs.getString(rs.getColumnIndex(DBHelper.STUDENT_COLUMN_NOREG));
                String emai = rs.getString(rs.getColumnIndex(DBHelper.STUDENT_COLUMN_EMAIL));
                String phon = rs.getString(rs.getColumnIndex(DBHelper.STUDENT_COLUMN_PHONE));

                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);


                name.setText((CharSequence)nam);
                name.setFocusable(false);
                name.setClickable(false);

                noreg.setText((CharSequence)nor);
                noreg.setFocusable(false);
                noreg.setClickable(false);

                email.setText((CharSequence)emai);
                email.setFocusable(false);
                email.setClickable(false);

                phone.setText((CharSequence)phon);
                phone.setFocusable(false);
                phone.setClickable(false);
                
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_student, menu);
            } else{
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Edit_Student:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);

                id.setText((CharSequence)id);
                id.setFocusable(false);
                id.setClickable(false);

                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                noreg.setEnabled(true);
                noreg.setFocusableInTouchMode(true);
                noreg.setClickable(true);

                email.setEnabled(true);
                email.setFocusableInTouchMode(true);
                email.setClickable(true);

                phone.setEnabled(true);
                phone.setFocusableInTouchMode(true);
                phone.setClickable(true);
                
                return true;
            case R.id.Delete_Student:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteStudent)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteStudent(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateStudent(id_To_Update,name.getText().toString(),
                        noreg.getText().toString(), email.getText().toString(),
                        phone.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else{
                if(mydb.insertStudent(name.getText().toString(), noreg.getText().toString(),
                        email.getText().toString(), phone.getText().toString())){
                    Toast.makeText(getApplicationContext(), "done",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "not done",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
