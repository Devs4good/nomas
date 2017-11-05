package nomas.nomas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DenunciasAdapter extends RecyclerView.Adapter<DenunciasAdapter.ViewHolder> {
    private final ArrayList<Denuncia> denuncias;
    Context context;

    public DenunciasAdapter(Context context, ArrayList<Denuncia> denuncias) {
        this.context = context;
        this.denuncias = denuncias;
    }

    public DenunciasAdapter(ActividadPrincipal actividadPrincipal) {
        this.context = actividadPrincipal;
        this.denuncias = new ArrayList<Denuncia>();
    }

    public void agregarDenuncia(Denuncia denuncia) {
        this.denuncias.add(denuncia);
        this.notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_filtro, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Denuncia denuncia = denuncias.get(position);
        holder.nombre.setText(denuncia.nombre);
        holder.edad.setText(Integer.toString(denuncia.edad));
        Bitmap bmp = null;
        new DownLoadImageTask(holder.foto).execute(denuncia.rutaFoto);
    }

    @Override
    public int getItemCount() {
        return denuncias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public TextView edad;
        public TextView dias;
        public ImageView foto;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            edad = (TextView) itemView.findViewById(R.id.edad);
            dias = (TextView) itemView.findViewById(R.id.dias);
            foto = (ImageView) itemView.findViewById(R.id.foto);
        }
    }
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}