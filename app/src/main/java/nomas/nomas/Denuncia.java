package nomas.nomas;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

/**
 * Created by Ilanit Jamilis on 4/11/2017.
 */

class Denuncia {

    Denuncia (String Nombre, Integer Edad, String Sexo, String Horario, String Situacion, LatLng Ubicacion, String Vestimenta,
    String Zona, String RutaFoto, String DatosAdicionales){
        this.nombre = Nombre;
        this.edad = Edad;
        this.sexo = Sexo;
        this.horario = Horario;
        this.situacion = Situacion;
        this.ubicacion = Ubicacion;
        this.vestimenta = Vestimenta;
        this.zona = Zona;
        this.rutaFoto = RutaFoto;
        this.datosAdicionales = DatosAdicionales;
    }

    String nombre;
    Integer edad;
    String sexo;
    String horario;
    String situacion;
    LatLng ubicacion;
    String vestimenta;
    String zona;
    String rutaFoto;
    String datosAdicionales;

    public Denuncia(String nombreDenuncia, Integer edadDenuncia, String situacionDenuncia, String horario) {
        this.nombre = nombreDenuncia;
        this.edad = edadDenuncia;
        this.horario = horario;
        this.situacion = situacionDenuncia;
    }
}
