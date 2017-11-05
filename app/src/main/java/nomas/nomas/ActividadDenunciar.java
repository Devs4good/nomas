package nomas.nomas;

import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class ActividadDenunciar extends AppCompatActivity {

    EditText nombre;
    EditText edad;
    EditText situacion;

    String nombreDenuncia;
    Integer edadDenuncia;
    String situacionDenuncia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_denunciar);

        nombre = (EditText) findViewById(R.id.nombreApellido);
        edad = (EditText) findViewById(R.id.edadEstimada);
        situacion = (EditText) findViewById(R.id.situacion);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void publicar(View vista) {
        nombreDenuncia = nombre.getText().toString();
        edadDenuncia = Integer.parseInt(edad.getText().toString());
        situacionDenuncia = situacion.getText().toString();
        Calendar c = Calendar.getInstance();
        Denuncia d = new Denuncia(nombreDenuncia, edadDenuncia, situacionDenuncia, c.getTime().toString());

        new CreadorDenuncia().execute(d);
    }

    public class CreadorDenuncia extends AsyncTask<Denuncia, Integer, Boolean> {
        /**
         * constructor
         **/
        private Denuncia denuncia;

        public CreadorDenuncia() {
        }

        protected Boolean doInBackground(Denuncia... params) {
            try {
                URL url = new URL("https://nomas-2b0d9.firebaseio.com/denuncias.json");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                JSONArray jsonArray = new JSONArray();
                JSONObject denunciaJson = new JSONObject();
                Denuncia denuncia = params[0];
                denunciaJson.put("situacion", denuncia.situacion);
                denunciaJson.put("nombre", denuncia.nombre);
                denunciaJson.put("edad", denuncia.edad);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(denunciaJson.toString());
                wr.flush();
                //do somehting with response
                int statusCode = conn.getResponseCode();
                wr.close();
                return statusCode < 400;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

}
