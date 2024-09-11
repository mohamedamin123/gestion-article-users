package com.example.gestion_de_stock.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_de_stock.R;
import com.example.gestion_de_stock.database.interne.entity.Photo;
import com.example.gestion_de_stock.databinding.CustomImageCommandeBinding;

import java.util.List;

public class PhotoCommandeAdapter extends RecyclerView.Adapter<PhotoCommandeAdapter.MyViewHolder> {

    private Context context;
    private List<Photo> photoList;
    private OnManipulePhoto listener;
    private GestureDetector gestureDetector;

    public PhotoCommandeAdapter(List<Photo> photoList, Context context, OnManipulePhoto listener) {
        this.context = context;
        this.photoList = photoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhotoCommandeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_image_commande, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoCommandeAdapter.MyViewHolder holder, int position) {
        Photo photo = photoList.get(position);

        // Convert bytes to Bitmap for display
        Bitmap bitmap = BitmapFactory.decodeByteArray(photo.getPhotoBytes(), 0, photo.getPhotoBytes().length);
        holder.binding.imageView.setImageBitmap(bitmap);

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        holder.binding.imageView.setOnClickListener(v -> {
            // Single tap action
            listener.manipule(photo);
        });




    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomImageCommandeBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomImageCommandeBinding.bind(itemView);
        }
    }

    public interface OnManipulePhoto {
        void manipule(Photo photo);
        void manipuleDouble(Photo photo);  // Handle double tap
    }
}
