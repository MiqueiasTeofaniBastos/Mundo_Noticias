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

public class NoticiaCardAdapter extends ArrayAdapter<Noticia> {
    private Context context;
    private ArrayList<Noticia> elements;

    public NoticiaCardAdapter(Context context, ArrayList<Noticia> objects) {
        super(context, R.layout.item_card, objects);
        this.context = context;
        this.elements = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_card, parent, false);

        Noticia noticia =  elements.get(position);

        TextView Categoria = (TextView) rowView.findViewById(R.id.txt_Categoria);
        ImageView Imagem = (ImageView) rowView.findViewById(R.id.img_Image);
        TextView Titulo = (TextView) rowView.findViewById(R.id.txt_Titulo);
        TextView Corpo = (TextView) rowView.findViewById(R.id.txt_Corpo);
        TextView Rodape = (TextView) rowView.findViewById(R.id.txt_Rodape);

        Categoria.setText(noticia.getCategoria());
        Bitmap Image = DbBitmapUtility.convert(noticia.getNoticiaImg());
        Imagem.setImageBitmap(Image);
        Titulo.setText(noticia.getTitulo());
        Corpo.setText(noticia.Resumo_Noticia());
        String rodape = noticia.getAutor()+" - "+noticia.getPublicacao();
        Rodape.setText(rodape);


        return rowView;
    }
}
