package nomas.nomas;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_denunciar);
    }

    public class CreadorDenuncia extends AsyncTask<String,Integer,Boolean> {
        /** constructor **/
        private Denuncia denuncia;

        public CreadorDenuncia(Denuncia denuncia) {
            this.denuncia = denuncia;
        }

        protected Boolean doInBackground(String... params) {
            try {
                URL url = new URL("https://nomas-2b0d9.firebaseio.com/denuncias.json");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                JSONArray jsonArray = new JSONArray();
                JSONObject denunciaJson = new JSONObject();
                String seller = params[0];
                denunciaJson.put("situacion", denuncia.situacion);
                denunciaJson.put("nombre", denuncia.nombre);
                denunciaJson.put("edad", denuncia.edad);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(denunciaJson.toString());
                wr.flush();
                //do somehting with response
                int statusCode = conn.getResponseCode();
                wr.close();
                return statusCode == 200;
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
