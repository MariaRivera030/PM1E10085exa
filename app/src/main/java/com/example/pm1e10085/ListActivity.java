package com.example.PM1E10085;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.PM1E10085.Configuracion.SQLiteConexion;
import com.example.PM1E10085.Configuracion.Transacciones;
import com.example.PM1E10085.Models.Contactos;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listContactos;
    ArrayList <Contactos> lista;
    ArrayList<String> ArregloContactos;

    Button btnatras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        btnatras= (Button) findViewById(R.id.btnregresar);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        listContactos = (ListView) findViewById(R.id.listaContactos);

        ObtenerTabla();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ArregloContactos);
        listContactos.setAdapter(adp);

        listContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el elemento seleccionado en la lista
                String selectedItem = (String) parent.getItemAtPosition(position);

                String[] datos = selectedItem.split(" ");
                String datoEspecifico = datos[2];

                // Realizar alguna acción con el elemento seleccionado
                Toast.makeText(getApplicationContext(), "Seleccionaste: " + selectedItem, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("Acción")
                        .setMessage("¿Deseas llamar a '" + datoEspecifico + "'?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), ActivityCall.class);
                                startActivity(intent);

                            }

                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresarPantallaAnterior();
            }
        });

    }


    private void ObtenerTabla()
    {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Contactos contacto = null;
        lista = new ArrayList<Contactos>();

        // Cursor de Base de Datos
        Cursor cursor = db.rawQuery(Transacciones.SelectTableContacto,null);

        // recorremos el cursor
        while(cursor.moveToNext())
        {
            contacto = new Contactos();
            contacto.setId_contacto(cursor.getInt(0));
            contacto.setPais(cursor.getString(1));
            contacto.setNombre(cursor.getString(2));
            contacto.setTelefono(cursor.getInt(3));
            contacto.setNota(cursor.getString(4));
            contacto.setFoto(cursor.getString(5));

            lista.add(contacto);
        }

        cursor.close();

        fillList();
    }


    private void fillList()
    {
        ArregloContactos = new ArrayList<String>();

        for(int i=0; i < lista.size(); i++)
        {
            ArregloContactos.add(lista.get(i).getId_contacto() + " - "
                    +lista.get(i).getNombre() + " - "
                    +lista.get(i).getTelefono());
        }
    }

    private void regresarPantallaAnterior() {
        finish();
    }


}