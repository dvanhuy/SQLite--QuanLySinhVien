package com.example.sqlitequanlysinhvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<SinhVien> itemList;

    public ListViewAdapter(Context context, int layout, List<SinhVien> itemList) {
        this.context = context;
        this.layout = layout;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView tensv,masv,tuoisv,lopsv;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder = new ViewHolder();
            //ánh xạ view
            holder.masv = view.findViewById(R.id.itemmasv);
            holder.tensv = view.findViewById(R.id.itemtensv);
            holder.tuoisv = view.findViewById(R.id.itemtuoisv);
            holder.lopsv = view.findViewById(R.id.itemlopsv);
            view.setTag(holder);
        }
        else {
            holder=(ViewHolder) view.getTag();
        }
        SinhVien sv = itemList.get(i);
        holder.masv.setText(sv.getMasv());
        holder.tensv.setText(sv.getTensv());
        holder.tuoisv.setText(String.valueOf(sv.getTuoi()));
        holder.lopsv.setText(sv.getLop());
        return view;
    }
}
