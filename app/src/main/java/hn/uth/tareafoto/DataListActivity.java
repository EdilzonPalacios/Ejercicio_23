package hn.uth.tareafoto;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

public class DataListActivity extends AppCompatActivity {

    ListView listView;
    CustomAdapter customAdapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        listView = findViewById(R.id.listView);
        customAdapter = new CustomAdapter(this);
        listView.setAdapter(customAdapter);

        databaseHelper = new DatabaseHelper(this);
        loadData();
    }

    private void loadData() {
        Cursor cursor = databaseHelper.getAllData();
        if (cursor != null && cursor.moveToFirst()) {
            int imageColumnIndex = cursor.getColumnIndex(databaseHelper.getImageColumnName());
            int descriptionColumnIndex = cursor.getColumnIndex(databaseHelper.getDescriptionColumnName());

            do {
                byte[] imageBytes = cursor.getBlob(imageColumnIndex);
                String description = cursor.getString(descriptionColumnIndex);

                // Agregar los datos al adaptador
                customAdapter.addData(imageBytes, description);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
