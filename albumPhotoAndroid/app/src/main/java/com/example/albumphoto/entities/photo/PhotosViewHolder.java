package com.example.albumphoto.entities.photo;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.albumphoto.R;

/**
 * Représente une vue individuelle pour un élément de la liste des photos dans un RecyclerView.
 */
public class PhotosViewHolder extends RecyclerView.ViewHolder{

    private TextView pseudo,legende,datePublication,idPhoto,idUtil;

    private Button btnSupprimePhoto;
    private ImageButton photo;


    /**
     * Constructeur de la classe PhotoViewHolder.
     * @param itemView La vue de l'élément de la liste.
     */
    public PhotosViewHolder(@NonNull View itemView) {
        super(itemView);
        pseudo=itemView.findViewById(R.id.textPseudoCom);
        legende=itemView.findViewById(R.id.textCommentaire);
        datePublication=itemView.findViewById(R.id.textDateCom);
        idPhoto=itemView.findViewById(R.id.textIdPhoto);
        idUtil=itemView.findViewById(R.id.textIdUtil_Com);
        photo=itemView.findViewById(R.id.imagePhoto);
        btnSupprimePhoto=itemView.findViewById(R.id.btnSupprimeCommentaire);
    }

    /**
     * Renvoie le TextView affichant le pseudo de l'utilisateur.
     * @return Le TextView affichant le pseudo de l'utilisateur.
     */
    public TextView getPseudo() {
        return pseudo;
    }

    /**
     * Renvoie le TextView affichant la légende de la photo.
     * @return Le TextView affichant la légende de la photo.
     */
    public TextView getLegende() {
        return legende;
    }

    /**
     * Renvoie le TextView affichant la date de publication de la photo.
     * @return Le TextView affichant la date de publication de la photo.
     */
    public TextView getDatePublication() {
        return datePublication;
    }

    /**
     * Renvoie le TextView affichant l'identifiant de la photo.
     * @return Le TextView affichant l'identifiant de la photo.
     */
    public TextView getIdPhoto() {
        return idPhoto;
    }

    /**
     * Renvoie le TextView affichant l'identifiant de l'utilisateur.
     * @return Le TextView affichant l'identifiant de l'utilisateur.
     */
    public TextView getIdUtil() {
        return idUtil;
    }

    /**
     * Renvoie l'ImageButton affichant la photo.
     * @return L'ImageButton affichant la photo.
     */
    public ImageButton getPhoto() {
        return photo;
    }

    /**
     * Renvoie le bouton de suppression de la photo.
     * @return Le bouton de suppression de la photo.
     */
    public Button getBtnSupprimePhoto() {
        return btnSupprimePhoto;
    }
}
