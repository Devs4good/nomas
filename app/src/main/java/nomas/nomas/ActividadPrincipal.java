package nomas.nomas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActividadPrincipal extends AppCompatActivity {

    static String url = "https://nomas-2b0d9.firebaseio.com/denuncias.json";
    RecyclerView view;
    private DenunciasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        view = (RecyclerView) (findViewById(R.id.tarjetas));
        adapter = new DenunciasAdapter(ActividadPrincipal.this);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);
        new BuscarDatosDenuncias().execute(url);
    }

    public void irDenunciar(View vista) {
        Intent intent = new Intent(this, ActividadDenunciar.class);
        startActivity(intent);
    }

    public void irMiPerfil(View vista) {

    }


    private class BuscarDatosDenuncias extends AsyncTask<String, Void, ArrayList<Denuncia>> {

        String resultado;

        protected void onPostExecute(ArrayList<Denuncia> listaDenuncias) {
            super.onPostExecute(listaDenuncias);
            if (listaDenuncias != null) {
                for (int i = 0; i < listaDenuncias.size(); ++i) {
                    Denuncia denuncia = listaDenuncias.get(i);
                    adapter.agregarDenuncia(denuncia);
                }
                adapter.notifyDataSetChanged();
            } else {
                MostrarMensaje("Hubo un error, intente nuevamente");
            }
        }

        @Override
        protected ArrayList<Denuncia> doInBackground(String... parametros) {

            String miURL = parametros[0];


            ArrayList<Denuncia> misDenuncias = new ArrayList<>();

            if (miURL.compareTo(url) == 0) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(miURL)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    resultado = response.body().string();

                    if (resultado.compareTo("error") != 0) {
                        misDenuncias = ParsearResultado(new JSONObject(resultado));
                        Log.d("RESULTADO DENUNCIAS", String.valueOf(misDenuncias));
                        return misDenuncias;
                    } else {
                        return null;
                    }

                } catch (IOException e) {
                    return null;
                } catch (JSONException e) {
                    return null;
                }
            } else {
                return null;
            }
        }


        private ArrayList<Denuncia> ParsearResultado(JSONObject resultado) throws JSONException {
            ArrayList<Denuncia> denuncias = new ArrayList<>();
            Iterator<?> keys = resultado.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONObject jsonDenuncia = resultado.getJSONObject(key);
                String nombre = jsonDenuncia.getString("nombre");
                Integer edad = jsonDenuncia.getInt("edad");
                String sexo = jsonDenuncia.getString("sexo");
                String horario = jsonDenuncia.getString("horario");
                String situacion = jsonDenuncia.getString("situacion");
                JSONObject jsonDenunciaUbicacion = jsonDenuncia.getJSONObject(("ubicacion"));
                double latitudD = jsonDenunciaUbicacion.getDouble("latitud");
                double longitudD = jsonDenunciaUbicacion.getDouble("longitud");
                LatLng ubicacion = new LatLng(latitudD, longitudD);
                String vestimenta = jsonDenuncia.getString("vestimenta");
                String zona = jsonDenuncia.getString("zona");
                String rutaFoto = jsonDenuncia.getString("foto");
                String datosAdicionales = jsonDenuncia.getString("datos-adicionales");

                Denuncia d = new Denuncia(nombre, edad, sexo, horario, situacion, ubicacion, vestimenta, zona, rutaFoto, datosAdicionales);
                denuncias.add(d);
            }
            return denuncias;
        }
    }

    public void MostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
