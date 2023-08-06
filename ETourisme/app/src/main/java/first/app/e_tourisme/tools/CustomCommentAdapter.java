package first.app.e_tourisme.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import first.app.e_tourisme.R;
import first.app.e_tourisme.model.Commentaire;

public class CustomCommentAdapter extends BaseAdapter {

    private List<Commentaire> listeCommentaire;

    private LayoutInflater layoutInflater;

    private Context context;

    public CustomCommentAdapter(Context context, List<Commentaire> liste) {
        this.context = context;
        this.listeCommentaire = liste;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listeCommentaire.size();
    }

    @Override
    public Object getItem(int position) {
        return listeCommentaire.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_list_comment, null);
            holder = new ViewHolder();
            holder.usernameText = (TextView) convertView.findViewById(R.id.userComment);
            holder.dateCommentText = (TextView) convertView.findViewById(R.id.dateComment);
            holder.contentText = (TextView) convertView.findViewById(R.id.contentComment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Commentaire coms = this.listeCommentaire.get(position);
        holder.usernameText.setText(coms.getUserName()+" "+coms.getUserSurname());
        holder.dateCommentText.setText(coms.getDateOfComment().toString());
        holder.contentText.setText(coms.getContent());

        return convertView;
    }

    static class ViewHolder {
        TextView usernameText;

        TextView dateCommentText;

        TextView contentText;
    }
}
