package com.example.PM1E10085;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.PM1E10085.Configuracion.SQLiteConexion;
import com.example.PM1E10085.Configuracion.Transacciones;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Spinner Lista;

    EditText nombre, telefono, notas;
    Button btnsalvar, btnvercontacto;

    //objetos tomarfoto
    static final int peticion_captura_imagen = 101;
    static final int peticion_acceso_camara = 102;
    String currentPhotoPath;
    ImageView Objetoimagen;
    Button btntomarfoto;
    String pathfoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = findViewById(R.id.txtnombre);
        telefono = findViewById(R.id.txttelefono);
        notas = findViewById(R.id.txtnota);


        btnsalvar = (Button) findViewById(R.id.btnsalvar);
        btnvercontacto = (Button) findViewById(R.id.btnvercontactos);


        Lista=(Spinner) findViewById(R.id.selectpais);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.list, android.R.layout.simple_list_item_1);
        Lista.setAdapter(adapter);

        Objetoimagen = (ImageView) findViewById(R.id.imgfoto);
        btntomarfoto = (Button) findViewById(R.id.btnAgimg);

        btnsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textonombre = nombre.getText().toString();
                String textotelefono = telefono.getText().toString();
                String textonota = notas.getText().toString();

                if (textonombre.isEmpty()||textotelefono.isEmpty()||textonota.isEmpty()) {

                    if(textonombre.isEmpty()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Campo del Nombre vacío")
                                .setMessage("Por favor, ingrese algún dato en el campo.")
                                .setPositiveButton("Aceptar", null)
                                .show();

                    }else if(textotelefono.isEmpty()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Campo telefono vacío")
                                .setMessage("Por favor, ingrese algún dato en el campo.")
                                .setPositiveButton("Aceptar", null)
                                .show();
                    } else if (textonota.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Campo notas vacío")
                                .setMessage("Por favor, ingrese algún dato en el campo.")
                                .setPositiveButton("Aceptar", null)
                                .show();

                    }

                } else {
                    AddContacto();
                }


            }
        });

        btnvercontacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });

        btntomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                permisos();
            }
        });





    }

    private void AddContacto()
    {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase,null,1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombre, nombre.getText().toString());
        valores.put(Transacciones.pais, Lista.getSelectedItem().toString());
        valores.put(Transacciones.telefono, telefono.getText().toString());
        valores.put(Transacciones.nota, notas.getText().toString());


        Long result = db.insert(Transacciones.tablaContactos, Transacciones.id, valores);
        Toast.makeText(getApplicationContext(), "Registro ingresado : " + result.toString(),Toast.LENGTH_LONG ).show();

        db.close();

        CleanScreen();

    }


    private void CleanScreen()
    {
        nombre.setText("");
        telefono.setText("");
        notas.setText("");
        Lista.setSelection(0);
    }


    private void permisos()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},peticion_acceso_camara);
        }
        else
        {
            dispatchTakePictureIntent();

        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == peticion_acceso_camara )
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED )
            {
                dispatchTakePictureIntent();

            }
            else
            {
                Toast.makeText(getApplicationContext(), "Se necesita el permiso para accder a la camara", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void TomarFoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(intent.resolveActivity(getPackageManager())!= null)
        {
            startActivityForResult(intent, peticion_captura_imagen );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode  == peticion_captura_imagen)
        {


            try {
                File foto = new File(currentPhotoPath);
                Objetoimagen.setImageURI(Uri.fromFile(foto));
            }
            catch (Exception ex)
            {
                ex.toString();
            }

        }
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );


        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {


            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.PM1E10085.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, peticion_captura_imagen);
            }
        }
    }

}