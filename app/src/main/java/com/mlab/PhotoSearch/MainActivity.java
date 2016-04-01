package com.mlab.PhotoSearch;

import java.util.ArrayList;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends ActionBarActivity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                //Create intent
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("image", item.getImage());

                //Start details activity
                startActivity(intent);
            }
        });
    }

    /**
     * gridview에 담기 위한 데이터
     */
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        ArrayList<String> thumbsIDs;
        getThumbInfo(thumbsIDs);

        for(itn i = 0; i < thumbsIDs.size(); i++) {
            imageItems.add(new ImageItem(thumbsIDs.get(i), "Image#" + i, ""));
        }


//        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
//        for (int i = 0; i < imgs.length(); i++) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
//            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }

    /**
     * 앨범 사진들에 대한 썸네일 이미지 정보 수집
     */
    private void getThumbInfo(ArrayList<String> thumbsIDs) {
        String[] projection = { MediaStore.Images.Thumbnails._ID };
        Cursor imageCursor = managedQuery(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                projection, null, null, MediaStore.Images.Thumbnails.IMAGE_ID + "");

        if(imageCursor != null && imageCursor.moveToFirst()) {
            String date;
            String thumbsID;
            int imageColumIndex = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
            do {
                thumbsID = imageCursor.getString(imageColumIndex);
                if(thumbsID.isEmpty() != true) {
                    thumbsIDs.add(thumbsID);
//                    thumbsDates.add(date);
                }
            } while (imageCursor.moveToNext())
        }

        imageCursor.close();

        return;
    }

}