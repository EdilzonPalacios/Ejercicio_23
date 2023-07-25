package hn.uth.tareafoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    Button btnCamara;
    ImageView imgView;
    EditText etDescription;
    Button btnViewList;
    Button btnSave;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamara = findViewById(R.id.btnCamara);
        imgView = findViewById(R.id.imageView);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);
        btnViewList = findViewById(R.id.btnViewList);

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                abrirCamara();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatos();
            }
        });

        btnViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataListActivity.class);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(this);
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imgBitmap);
        }
    }

    private void guardarDatos() {
        // Obtener la descripción del EditText
        String description = etDescription.getText().toString().trim();

        // Obtener la imagen del ImageView
        BitmapDrawable drawable = (BitmapDrawable) imgView.getDrawable();
        Bitmap imgBitmap = drawable.getBitmap();

        // Convertir la imagen a un array de bytes (BLOB)
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imgByteArray = stream.toByteArray();

        // Insertar los datos en la base de datos
        boolean isInserted = databaseHelper.insertData(imgByteArray, description);

        if (isInserted) {
            Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al guardar datos", Toast.LENGTH_SHORT).show();
        }
    }
}