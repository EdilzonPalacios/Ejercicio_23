package hn.uth.tareafoto;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DataItem> dataList;

    public CustomAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
    }

    public void addData(byte[] imageBytes, String description) {
        dataList.add(new DataItem(imageBytes, description));
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        DataItem dataItem = dataList.get(position);

        // Convertir el array de bytes a Bitmap
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(dataItem.getImageBytes(), 0, dataItem.getImageBytes().length);
        imageView.setImageBitmap(imageBitmap);

        textView.setText(dataItem.getDescription());

        return convertView;
    }


    // Clase para almacenar los datos de cada elemento en la lista
    private static class DataItem {
        private byte[] imageBytes;
        private String description;

        public DataItem(byte[] imageBytes, String description) {
            this.imageBytes = imageBytes;
            this.description = description;
        }

        public byte[] getImageBytes() {
            return imageBytes;
        }

        public String getDescription() {
            return description;
        }
    }
}

