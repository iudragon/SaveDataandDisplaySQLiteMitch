package dragon.bakuman.iu.savedatamitch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnViewData;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Declare in onCreate()
        mDatabaseHelper = new DatabaseHelper(this);
        btnAdd = findViewById(R.id.btnAdd);
        btnViewData = findViewById(R.id.btnView);
        mEditText = findViewById(R.id.editText);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = mEditText.getText().toString();

                //exception handling
                if (mEditText.length() != 0) {

                    AddData(newEntry);
                    mEditText.setText("");


                } else {

                    toastMessage("you must put something in the text field");
                }
            }
        });

        //button that navigates to the ListView

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);

            }
        });

    }

    //addData method for adding data to the database

    public void AddData(String newEntry) {

        //as when we were inserting data  in DatabasHelper class, we returned a boolean so...

        boolean insertData = mDatabaseHelper.addData(newEntry);

        //if boolean insetData is false it will not be inserted correctly

        if (insertData) {

            toastMessage("Data successfully inserted");


        } else {

            toastMessage("Something went wrong");
        }

    }


    //customizable toast

    private void toastMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
