package br.usjt.devweb.servicedesk_aula03.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;

import br.usjt.devweb.servicedesk_aula03.R;
import br.usjt.devweb.servicedesk_aula03.model.Data;
import br.usjt.devweb.servicedesk_aula03.model.Pais;
import br.usjt.devweb.servicedesk_aula03.model.PaisNetwork;

public class MainActivity extends Activity {
    public static final String LISTA_PAISES = "br.usjt.devweb.servicedesk_aula03.MainActivity.ListaPaises";
    private Spinner spinContinente;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        spinContinente = findViewById(R.id.continente_selecionado);
    }

    public void listarPaises(View view) {
        String continente = spinContinente.getSelectedItem().toString();

        Intent intent = new Intent(context, ListarPaisesActivity.class);
        intent.putExtra(LISTA_PAISES, Data.listarPaises(continente));
        startActivity(intent);

//      Networking
//        if (continente.equals("Todos")) {
//            new JSONPaises().execute("https://restcountries.eu/rest/v2/all");
//        } else {
//            new JSONPaises().execute("https://restcountries.eu/rest/v2/region/" + continente);
//        }
    }

    private class JSONPaises extends AsyncTask<String, Void, ArrayList<Pais>> {


        @Override
        protected ArrayList<Pais> doInBackground(String... strings) {
            ArrayList<Pais> paises = new ArrayList<>();
            try {
                paises = PaisNetwork.buscarPaises(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return paises;
        }

        protected void onPostExecute(ArrayList<Pais> paises) {
            Intent intent = new Intent(context, ListarPaisesActivity.class);
            intent.putExtra(LISTA_PAISES, paises);
            startActivity(intent);
        }

    }
}
