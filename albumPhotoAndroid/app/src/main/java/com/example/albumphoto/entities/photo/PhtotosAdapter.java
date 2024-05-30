package com.example.albumphoto.entities.photo;

import static com.example.albumphoto.entities.InterrogationBD.getUrlCommune;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.albumphoto.CommentairePhoto;
import com.example.albumphoto.R;
import com.example.albumphoto.entities.InterrogationBD;
import com.example.albumphoto.entities.commentaire.Commentaire;
import com.example.albumphoto.entities.utilisateur.Utilisateur;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Adaptateur pour lier les données de la liste des photos à un RecyclerView.
 */
public class PhtotosAdapter extends RecyclerView.Adapter<PhotosViewHolder> {

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    private ArrayList<Photos> listePhotos;
    private Utilisateur utilisateur;

    /**
     * Constructeur de la classe PhtotosAdapter.
     * @param listePhotos Liste des photos à afficher.
     * @param utilisateur Utilisateur actuel de l'application.
     */
    public PhtotosAdapter(ArrayList<Photos> listePhotos, Utilisateur utilisateur) {
        this.listePhotos = listePhotos;
        this.utilisateur = utilisateur;
    }

    @NonNull
    @Override
    public PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vue_photo, parent, false);
        return new PhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosViewHolder holder, int position) {
        // Obtenez l'objet Photos à cette position dans la liste
        Photos photo = listePhotos.get(position);

        String url = getUrlCommune() + "img/" + listePhotos.get(holder.getAdapterPosition()).getLien_photo();

        // Associez les données de l'objet Photos aux vues de PhotoViewHolder
        holder.getPseudo().setText(photo.getPseudo());
        holder.getLegende().setText(photo.getLegende());
        holder.getDatePublication().setText(photo.getDate_poster());
        holder.getIdPhoto().setText(String.valueOf(photo.getId_photo()));
        holder.getIdUtil().setText(String.valueOf(photo.getId()));

        // Charger l'image avec Picasso dans l'ImageView approprié
        Picasso.get().load(url).into(holder.getPhoto());

        // Gérer la visibilité du bouton de suppression en fonction de l'utilisateur
        if (Objects.equals(photo.getId(), utilisateur.getId()) || Objects.equals(utilisateur.getId(), 1)) {
            holder.getBtnSupprimePhoto().setVisibility(View.VISIBLE);
        } else {
            holder.getBtnSupprimePhoto().setVisibility(View.GONE);
        }

        // Gérer la suppression d'une photo lorsqu'on appuie sur le bouton
        holder.getBtnSupprimePhoto().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Supprime la photo de la liste et de la base de données
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Photos photoASupprimer = listePhotos.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                    notifyItemRangeChanged(currentPosition, listePhotos.size());
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            InterrogationBD.exportSupprPhoto(gson.toJson(photoASupprimer));
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // Affiche un message de confirmation de suppression
                                    Context context = v.getContext();
                                    Toast.makeText(context, "Photo supprimé", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }
        });

        // Lancer l'activité de commentaire lorsque l'utilisateur appuie sur une photo
        holder.getPhoto().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent lanceActivity = new Intent(context, CommentairePhoto.class);
                lanceActivity.putExtra("Photo", listePhotos.get(holder.getAdapterPosition()));
                context.startActivity(lanceActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listePhotos.size();
    }
}
