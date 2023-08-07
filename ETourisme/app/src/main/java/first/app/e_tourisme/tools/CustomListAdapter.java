package first.app.e_tourisme.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import first.app.e_tourisme.R;
import first.app.e_tourisme.model.MediaSite;
import first.app.e_tourisme.model.TouristicSite;

public class CustomListAdapter extends BaseAdapter {

    private List<TouristicSite> listeSiteTouristes;

    private List<TouristicSite> touristicSiteFiltered;
    private List<TouristicSite> init;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapter(Context context, List<TouristicSite> liste) {
        this.context = context;
        this.listeSiteTouristes = new ArrayList<>(liste);
        this.init = liste;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listeSiteTouristes.size();
    }

    @Override
    public Object getItem(int position) {
        return listeSiteTouristes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_tourisitic_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.entitledView = (TextView) convertView.findViewById(R.id.entitled);
            holder.placeView = (TextView) convertView.findViewById(R.id.place);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TouristicSite site = this.listeSiteTouristes.get(position);
        holder.entitledView.setText(site.getName());
        holder.placeView.setText(site.getPlace().getEntitled());
        Bitmap imageBitmap = MediaSite.decodeImageData(site.getImageData());
        if (imageBitmap != null) {
            holder.imageView.setImageBitmap(imageBitmap);
        } else {
            int imageId = this.getMipmapResIdByName("site");
            holder.imageView.setImageResource(imageId);
        }


        return convertView;
    }

    public void filter(String query) {
        listeSiteTouristes.clear();
        if (query.isEmpty()) {
            listeSiteTouristes.addAll(init);
        } else {
            for (TouristicSite site : init) {
                if (site.getName().toLowerCase().startsWith(query.toLowerCase())) {
                    listeSiteTouristes.add(site);
                }
            }
        }
        notifyDataSetChanged();
    }


    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName, "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }
    static class ViewHolder {
        ImageView imageView;
        TextView entitledView;
        TextView placeView;

    }
}


