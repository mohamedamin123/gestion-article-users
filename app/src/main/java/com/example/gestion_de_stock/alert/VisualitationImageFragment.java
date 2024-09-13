package com.example.gestion_de_stock.alert;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gestion_de_stock.R;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelCommande;
import com.example.gestion_de_stock.database.interne.viewModel.ViewModelPhoto;
import com.example.gestion_de_stock.databinding.FragmentAjouterCommandeBinding;
import com.example.gestion_de_stock.databinding.FragmentVisualitationImageBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VisualitationImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisualitationImageFragment extends DialogFragment {

    private static final String ARG_PHOTO_ID = "id";
    private Integer idPhoto;
    private FragmentVisualitationImageBinding binding;
    private ViewModelPhoto viewModelPhoto;

    public VisualitationImageFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static VisualitationImageFragment newInstance(Integer param1) {
        VisualitationImageFragment fragment = new VisualitationImageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PHOTO_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idPhoto = getArguments().getInt(ARG_PHOTO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVisualitationImageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        viewModelPhoto=new ViewModelProvider(this).get(ViewModelPhoto.class);
        // Observing the photo data by its ID
        viewModelPhoto.findPhtoById(idPhoto).observe(getViewLifecycleOwner(), photo -> {
            if (photo != null) {
                // Convert the byte array to Bitmap
                byte[] photoBytes = photo.getPhotoBytes();
                if (photoBytes != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
                    binding.image.setImageBitmap(bitmap);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
}