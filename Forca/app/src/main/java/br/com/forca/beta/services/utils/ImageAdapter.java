package br.com.forca.beta.services.utils;
/**
 * @author G Rodrigues
 * @date 10/06/2013
 */
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.forca.R;
import br.com.forca.beta.services.entity.Tema;


public class ImageAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Bitmap[] temas;	 
	private List<Tema> titulo;
	public static final String IMAGEM_TEMA ="temaimg";


	public ImageAdapter(Context contexto,  List<Tema> listTemas) {
		mInflater = LayoutInflater.from(contexto);
		this.titulo= listTemas;
		this.temas = new Bitmap[titulo.size()];


		for (int j = 0; j < titulo.size(); j++) {
			this.temas[j] = BitmapFactory.decodeResource(contexto.getResources(), contexto.getResources().getIdentifier("@drawable/"+ImageAdapter.IMAGEM_TEMA+j,null,contexto.getPackageName()));
		}

	}

	public int getCount() {
		return titulo.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) { 
		ViewHolder holder;
		if(convertView==null){
			convertView= mInflater.inflate(R.layout.activity_item_configuracao, null);
			holder = new ViewHolder();
			holder.icone = (ImageView)convertView.findViewById(R.id.imgItem);
			holder.titulo= (TextView)convertView.findViewById(R.id.txtTema_Item);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}

		holder.icone.setImageBitmap(temas[position]);
		holder.titulo.setText(this.titulo.get(position).getNome());
        
		return convertView;
	}


    static class ViewHolder{
    	ImageView icone;
    	TextView titulo;
    }

}
