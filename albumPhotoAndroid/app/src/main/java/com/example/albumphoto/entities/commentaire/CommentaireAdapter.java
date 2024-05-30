package com.example.albumphoto.entities.commentaire;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.albumphoto.R;
import com.example.albumphoto.entities.InterrogationBD;

import com.example.albumphoto.entities.utilisateur.Utilisateur;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Adapter pour afficher une liste de commentaires dans un RecyclerView.
 */
public class CommentaireAdapter extends RecyclerView.Adapter<CommentaireViewHolder> {

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    private ArrayList<Commentaire> listeCommentaire;
    private Utilisateur utilisateur;

    /**
     * Constructeur de l'adapter.
     * @param listeCommentaire La liste des commentaires à afficher.
     * @param utilisateur L'utilisateur actuellement connecté.
     */
    public CommentaireAdapter(ArrayList<Commentaire> listeCommentaire, Utilisateur utilisateur) {
        this.listeCommentaire = listeCommentaire;
        this.utilisateur = utilisateur;
    }

    @NonNull
    @Override
    public CommentaireViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Crée une nouvelle vue pour chaque élément de la liste
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vue_commentaire, parent, false);
        return new CommentaireViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentaireViewHolder holder, int position) {
        // Obtient l'objet Commentaire à cette position dans la liste
        Commentaire com = listeCommentaire.get(position);

        // Associe les données de l'objet Commentaire aux vues de CommentaireViewHolder
        holder.getPseudo().setText(com.getUtilisateurPseudo());
        holder.getCommentaire().setText(com.getCommentaire());
        holder.getDatePublication().setText(com.getDate_com());
        holder.getIdUtil().setText(String.valueOf(com.getUtilisateurId()));

        // Affiche le bouton de suppression uniquement si l'utilisateur actuel est l'auteur du commentaire ou un administrateur
        if (Objects.equals(com.getUtilisateurId(), utilisateur.getId()) || Objects.equals(utilisateur.getId(), 1)) {
            holder.getBtnSupprimeCommentaire().setVisibility(View.VISIBLE);
        } else {
            holder.getBtnSupprimeCommentaire().setVisibility(View.GONE);
        }

        // Action lors du clic sur le bouton de suppression de commentaire
        holder.getBtnSupprimeCommentaire().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Supprime le commentaire de la liste et de la base de données
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Commentaire commentaireASupprimer = listeCommentaire.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                    notifyItemRangeChanged(currentPosition, listeCommentaire.size());
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            InterrogationBD.exportSupprCom(gson.toJson(commentaireASupprimer));
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // Affiche un message de confirmation de suppression
                                    Context context = v.getContext();
                                    Toast.makeText(context, "Commentaire supprimé", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listeCommentaire.size();
    }
}