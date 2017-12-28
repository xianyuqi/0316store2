package com.example.administrator.a0109store;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.util.List;

/**
 * Created by Administrator
 * on 2017/1/12.
 */

public class GoodsAdapter extends BaseAdapter {
    private List<Goods> goodses;
    private Context context;

    public GoodsAdapter(Context context, List<Goods> goodses) {
        super();
        this.context = context;
        this.goodses = goodses;


    }


    @Override
    public int getCount() {
        return goodses.size();
    }

    @Override
    public Object getItem(int position) {
        return goodses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_item_query, parent, false);
        }
        ImageView imageView= (ImageView) convertView.findViewById(R.id.item_image);
        TextView name= (TextView) convertView.findViewById(R.id.item_name);
        TextView num= (TextView) convertView.findViewById(R.id.item_num);
        TextView outprice= (TextView) convertView.findViewById(R.id.item_outprice);
        TextView quantity= (TextView) convertView.findViewById(R.id.item_quantity);
        TextView remark= (TextView) convertView.findViewById(R.id.item_remark);
        TextView codebar= (TextView) convertView.findViewById(R.id.item_codebar);

        if (goodses.get(position).getImagesrc()!=null){
        /*Bitmap bitmap= BitmapFactory.decodeFile(goodses.get(position).getImagesrc());
        imageView.setImageBitmap(bitmap);*/
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.nopicture)
                    .showImageOnFail(R.drawable.nopicture2)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();


            String imagePath = goodses.get(position).getImagesrc();
            String imageUrl = ImageDownloader.Scheme.FILE.wrap(imagePath);
            ImageLoader.getInstance().displayImage(imageUrl,imageView,options);

        }
        else
        {
            imageView.setImageResource(R.drawable.nopicture);
        }

       /* name.setText("名称："+goodses.get(position).getName());*/
        name.setText(" "+goodses.get(position).getName());
        num.setText("货号："+goodses.get(position).getNum());
        outprice.setText("售价："+goodses.get(position).getOutprice());
        quantity.setText("数量："+goodses.get(position).getQuantity());
        remark.setText("备注:"+goodses.get(position).getRemark());
        codebar.setText("条形码"+goodses.get(position).getCodebar());



        return convertView;

    }
}
