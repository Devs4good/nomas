package nomas.nomas;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActividadPrincipal extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        String url = "https://nomas-2b0d9.firebaseio.com/denuncias.json";
        new BuscarDatosDenuncias().execute(url);
    }

    public void irDenunciar(View vista){

    }

    public void irMiPerfil(View vista){

    }



    private class BuscarDatosDenuncias extends AsyncTask<String, Void, ArrayList<Denuncia>> {

        String resultado;

        protected void onPostExecute(ArrayList<Denuncia> listaDenuncias) {
            super.onPostExecute(listaDenuncias);
            if(listaDenuncias!=null) {
                if(resultado.compareTo("error")!=0) {
                    for (int i = 0; i < listaDenuncias.size(); i++) {
                        Denuncia unaDenuncia = listaDenuncias.get(i);
                        try {
                            //Agregar una card mas al RecycleView PonerMarcador(unaDenuncia);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    MostrarMensaje("Hubo un error");
                }
            }
            else{
                MostrarMensaje("Hubo un error, intente nuevamente");
            }
        }

        @Override
        protected ArrayList<Denuncia> doInBackground(String... parametros) {

            String miURL = parametros[0];

            resultado = "";

            ArrayList<Denuncia> misDenuncias = new ArrayList<>();

            if (miURL.compareTo("https://nomas-2b0d9.firebaseio.com/denuncias.json") == 0) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(miURL)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    resultado = response.body().string();

                    Log.d("resultado", resultado);

                    if (resultado.compareTo("error") != 0) {
                        misDenuncias = ParsearResultado(resultado);
                        return misDenuncias;
                    } else {
                        return null;
                    }

                } catch (IOException e) {
                    return null;
                } catch (JSONException e) {
                    return null;
                }
            }
            else{
                return null;
            }
        }


        private ArrayList<Denuncia> ParsearResultado(String resultado) throws JSONException {
            ArrayList<Denuncia> denuncias = new ArrayList<>();
            JSONObject jsonDenuncias = new JSONObject(resultado);
            for (int i = 0; i < jsonDenuncias.length(); i++) {
                JSONObject jsonDenuncia = (JSONObject) new JSONTokener(jsonDenuncias.toString()).nextValue();
                JSONObject jsonDenunciaUbicacion = (JSONObject) new JSONTokener(jsonDenuncia.toString()).nextValue();

                String nombre = jsonDenuncia.getString("nombre");

                Log.d("denuncia",nombre);

                Integer edad = jsonDenuncia.getInt("edad");
                String sexo = jsonDenuncia.getString("sexo");
                String horario = jsonDenuncia.getString("horario");
                String situacion = jsonDenuncia.getString("situacion");
                double latitudD = jsonDenunciaUbicacion.getDouble("latitud");
                double longitudD = jsonDenunciaUbicacion.getDouble("longitud");
                LatLng ubicacion = new LatLng(latitudD, longitudD);
                String vestimenta = jsonDenuncia.getString("vestimenta");
                String zona = jsonDenuncia.getString("zona");
                String rutaFoto = jsonDenuncia.getString("rutaFoto");
                String datosAdicionales = jsonDenuncia.getString("datosAdicionales");


                Denuncia d = new Denuncia(nombre, edad, sexo, horario, situacion, ubicacion, vestimenta, zona, rutaFoto, datosAdicionales);
                denuncias.add(d);
            }
            return denuncias;
        }
    }

    public void MostrarMensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }
}
