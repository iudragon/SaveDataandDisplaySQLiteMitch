package dragon.bakuman.iu.savedatamitch;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabaseHelper;

    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        mListView = findViewById(R.id.listView);

        mDatabaseHelper = new DatabaseHelper(this);


        populateListView();
    }

    //Display the data into the listView

    private void populateListView() {

        Log.d(TAG, "populateListView: Displaying data in the listView");

        //get the data and append to the list
        Cursor data = mDatabaseHelper.getData();

        //create ArrayList and loop through all the rows of the query and get the data
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {

            //get the value from the database in column 1
            //then add it to the ArrayList

            listData.add(data.getString(1));

        }

        //create the List Adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //grab the name from the listView
                String name = adapterView.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemClick: you clicked on: " + name);

                //get the item Id associated with that name
                Cursor data = mDatabaseHelper.getItemId(name);

                int itemID = -1;
                while (data.moveToNext()){

                    itemID  = data.getInt(0);
                }

                if (itemID > -1){

                    Log.d(TAG, "onItemClick: the ID is: " + itemID);

                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditDataActivity.class);
                    //here in the EditDataActivity we need to know what Id is and what name is; so we attach extras
                    editScreenIntent.putExtra("id", itemID);
                    editScreenIntent.putExtra("name", name);
                    startActivity(editScreenIntent);








                } else {
                    toastMessage("No Id associated with that name");
                }



            }
        });



    }

    //customizable toast
    private void toastMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
