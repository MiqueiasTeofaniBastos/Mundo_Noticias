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

public class CategoriaAdapter extends ArrayAdapter<Categoria> {
    private Context context;
    private ArrayList<Categoria> elements;

    public CategoriaAdapter(Context context, ArrayList<Categoria> objects) {
        super(context, R.layout.item_list, objects);
        this.context = context;
        this.elements = objects;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_list, parent, false);

        Categoria categoria =  elements.get(position);

        TextView CategoriaID = (TextView) rowView.findViewById(R.id.txt_1);
        TextView Categoria = (TextView) rowView.findViewById(R.id.txt_2);


        CategoriaID.setText("");
        Categoria.setText("Categoria: "+categoria.getCategoria().toString());


        return rowView;
    }
}
