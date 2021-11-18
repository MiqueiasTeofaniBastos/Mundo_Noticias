package com.example.mundonoticias.Modelos;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mundonoticias.R;

import java.util.ArrayList;

public class NoticiaListAdapter extends ArrayAdapter<Noticia> {

    private Context context;
    private ArrayList<Noticia> elements;

    public NoticiaListAdapter(Context context, ArrayList<Noticia> objects) {
        super(context, R.layout.item_list, objects);
        this.context = context;
        this.elements = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_list, parent, false);

        Noticia noticia =  elements.get(position);

        TextView txt_1 = (TextView) rowView.findViewById(R.id.txt_1);
        TextView txt_2 = (TextView) rowView.findViewById(R.id.txt_2);
        ImageView imagem = (ImageView) rowView.findViewById(R.id.imagemview);


        txt_1.setText("Titulo: "+noticia.getTitulo().toString());
        txt_2.setText("Categoria: "+noticia.getCategoria().toString());
        Bitmap Image = DbBitmapUtility.convert(noticia.getNoticiaImg());
        imagem.setImageBitmap(Image);

        return rowView;
    }
}
