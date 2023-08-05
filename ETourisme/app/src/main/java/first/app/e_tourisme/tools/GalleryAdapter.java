package first.app.e_tourisme.tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import first.app.e_tourisme.R;
import first.app.e_tourisme.model.MediaSite;
import first.app.e_tourisme.view.EnlargedImageActivity;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImageViewHolder> {

    private List<MediaSite> mediaSites;

    public void setMediaSites(List<MediaSite> mediaSites) {
        this.mediaSites = mediaSites;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bind(mediaSites.get(position));
    }

    @Override
    public int getItemCount() {
        return mediaSites != null ? mediaSites.size() : 0;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        private File getFileByImageName(String imageName) {
            Context context = imageView.getContext();
            File cacheDir = context.getCacheDir();
            if (cacheDir != null) {
                File[] files = cacheDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().equals(imageName)) {
                            return file;
                        }
                    }
                }
            }
            return null;
        }


        private File saveBitmapToFile(Context context, Bitmap bitmap, String imageName) {
            try {
                File file = File.createTempFile(imageName, ".jpg", context.getCacheDir());
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                return file;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


        public void bind(MediaSite mediaSite) {
            Bitmap imageBitmap = MediaSite.decodeImageData(mediaSite.getImageData());
            if (imageBitmap != null) {
                imageView.setImageBitmap(imageBitmap);
            }

            // Gérer les événements de clic sur l'image
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        //MediaSite mediaSite = mediaSites.get(position);
                        Bitmap imageBitmap = MediaSite.decodeImageData(mediaSite.getImageData());
                        if (imageBitmap != null) {
                            File file = getFileByImageName(mediaSite.getImageName());
                            if (file != null) {
                                // Passer l'URI du fichier dans l'intent
                                Intent intent = new Intent(context, EnlargedImageActivity.class);
                                intent.putExtra("imageUri", Uri.fromFile(file));
                                intent.putExtra("imageName", mediaSite.getTouristicSiteName());
                                context.startActivity(intent);
                            } else {
                                File saveFile = saveBitmapToFile(context, imageBitmap, mediaSite.getImageName());
                                Intent intent = new Intent(context, EnlargedImageActivity.class);
                                intent.putExtra("imageUri", Uri.fromFile(saveFile));
                                intent.putExtra("imageName", mediaSite.getTouristicSiteName());
                                context.startActivity(intent);
                            }
                        }
                    }
                }
            });
        }
    }
}
