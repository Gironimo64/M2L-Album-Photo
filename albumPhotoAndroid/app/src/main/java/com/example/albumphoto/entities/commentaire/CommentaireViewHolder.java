package com.example.albumphoto.entities.commentaire;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.albumphoto.R;

/**
 * Vue qui représente un élément individuel dans la liste des commentaires.
 */
public class CommentaireViewHolder extends RecyclerView.ViewHolder {
    private TextView pseudo, datePublication, commentaire, idUtil;
    private Button btnSupprimeCommentaire;

    /**
     * Constructeur de la classe CommentaireViewHolder.
     * @param itemView La vue de l'élément de commentaire.
     */
    public CommentaireViewHolder(@NonNull View itemView) {
        super(itemView);
        pseudo = itemView.findViewById(R.id.textPseudoCom);
        commentaire = itemView.findViewById(R.id.textCommentaire);
        datePublication = itemView.findViewById(R.id.textDateCom);
        idUtil = itemView.findViewById(R.id.textIdUtil_Com);
        btnSupprimeCommentaire = itemView.findViewById(R.id.btnSupprimeCommentaire);
    }

    /**
     * Obtient le TextView affichant le pseudo de l'utilisateur.
     * @return Le TextView du pseudo.
     */
    public TextView getPseudo() {
        return pseudo;
    }

    /**
     * Obtient le TextView affichant le commentaire.
     * @return Le TextView du commentaire.
     */
    public TextView getCommentaire() {
        return commentaire;
    }

    /**
     * Obtient le TextView affichant la date de publication du commentaire.
     * @return Le TextView de la date de publication.
     */
    public TextView getDatePublication() {
        return datePublication;
    }

    /**
     * Obtient le TextView affichant l'identifiant de l'utilisateur.
     * @return Le TextView de l'identifiant de l'utilisateur.
     */
    public TextView getIdUtil() {
        return idUtil;
    }

    /**
     * Obtient le bouton pour supprimer le commentaire.
     * @return Le bouton de suppression du commentaire.
     */
    public Button getBtnSupprimeCommentaire() {
        return btnSupprimeCommentaire;
    }
}