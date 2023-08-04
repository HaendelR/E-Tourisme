package first.app.e_tourisme.tools;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import first.app.e_tourisme.R;
import first.app.e_tourisme.view.EnlargedImageActivity;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImageViewHolder> {

    private int[] images;

    public GalleryAdapter(int[] images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view, images);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private int[] images;

        public ImageViewHolder(@NonNull View itemView, int[] images) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            this.images = images;

            // Gérer les événements de clic sur l'image
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Ouvrir l'activité pour afficher l'image agrandie
                        int imageResId = images[position];
                        Intent intent = new Intent(context, EnlargedImageActivity.class);
                        intent.putExtra("imageResId", imageResId);
                        context.startActivity(intent);
                    }
                }
            });
        }

        public void bind(int position) {
            int imageResId = images[position];
            imageView.setImageResource(imageResId);
        }
    }
}

