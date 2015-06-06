package com.example.angel.localizaciongeografica;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    //Declaramos variables
    private Button btnActualizar, btnDesactivar;
    private TextView lblLatitud, lblLongitud, lblPresicion, lblEstadoProveedor;

    private LocationManager locManager;
    private LocationListener locListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenemos instancia de la vista
        btnActualizar = (Button) findViewById(R.id.BtnActualizar);
        btnDesactivar = (Button) findViewById(R.id.BtnDesactivar);
        lblLatitud = (TextView) findViewById(R.id.LblPosLatitud);
        lblLongitud = (TextView) findViewById(R.id.LblPosLongitud);
        lblPresicion = (TextView) findViewById(R.id.LblPosPresicion);
        lblEstadoProveedor = (TextView) findViewById(R.id.LblEstado);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                comenzarLocalizacion();
            }
        });

        btnDesactivar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                locManager.removeUpdates(locListener);
            }
        });
    }

    //Metodo el cual nos mostrara la Latitud y Longitud donde nos encontramos
    private void mostrarPosicion(Location loc){
        if (loc !=null){
            lblLatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            lblLongitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
            lblPresicion.setText("Presicion: " + String.valueOf(loc.getAccuracy()));
        }
        else
        {
            lblLatitud.setText("Latitud: (sin_datos)");
            lblLongitud.setText("Longitud: (sin_datos)");
            lblPresicion.setText("Presicion: (sin_datos)");
        }
    }

    private void comenzarLocalizacion(){

        //Obtener un referencia al LocationManager
        locManager= (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //Obtenemos la ultima posicion conocida
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        mostrarPosicion(loc);

        //Nos registramos para recibir actualizaciones de la posicion
        locListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }

            public void onProviderDisabled(String provider) {
                lblEstadoProveedor.setText("Provider OFF");
            }

            public void onProviderEnabled(String provider) {
                lblEstadoProveedor.setText("Provider ON");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                lblEstadoProveedor.setText("Provider Status: " + status);
            }
        };

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
