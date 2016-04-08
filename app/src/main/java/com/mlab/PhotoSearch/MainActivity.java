package com.mlab.PhotoSearch;

import java.util.ArrayList;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
//                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
//                intent.putExtra("title", item.getTitle());
//                intent.putExtra("image", item.getImage());
//
//                //Start details activity
//                startActivity(intent);
            }
        });
    }

    /**
     * gridview에 담기 위한 데이터
     */
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        ArrayList<String> thumbsIDs = new ArrayList<String>();
        getThumbInfo(thumbsIDs);

        for(int i = 0; i < thumbsIDs.size(); i++) {
            imageItems.add(new ImageItem(thumbsIDs.get(i), "Image#" + i, ""));
        }

        return imageItems;
    }

    /**
     * 앨범 사진들에 대한 썸네일 이미지 정보 수집
     */
    private void getThumbInfo(ArrayList<String> thumbsIDs) {
        final String[] columns = { MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.IMAGE_ID };
        final String orderBy = MediaStore.Images.Thumbnails.IMAGE_ID;
        final Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        Cursor imagecursor = getContentResolver().query(uri, columns, null, null, orderBy);

        if(imagecursor != null && imagecursor.moveToFirst()) {
            String date;
            String thumbsID;
            int imageColumIndex = imagecursor.getColumnIndexOrThrow(columns[0]);
            do {
                thumbsID = imagecursor.getString(imageColumIndex);
//                date = imagecursor.getString(imageColumIndexDate);
                if(thumbsID.isEmpty() != true) {
                    thumbsIDs.add(thumbsID);
//                    thumbsDates.add(date);
                }
            } while (imagecursor.moveToNext());
        }

        imagecursor.close();
    }

    private String getMediaDateAdd(String imageID) {
        final String[] columns = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_ADDED };
        final String orderBy = MediaStore.Images.Media._ID;
        final Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor imagecursor = getContentResolver().query(uri, columns, null, null, orderBy);

        String mediaDate = "";
        if(imagecursor != null && imagecursor.moveToFirst()) {
            String mediaID;
            int imageColumIndexID = imagecursor.getColumnIndexOrThrow(columns[0]);
            int imageColumIndexDate = imagecursor.getColumnIndexOrThrow(columns[1]);
            do {
                mediaID = imagecursor.getString(imageColumIndexID);
                if(mediaID.isEmpty() != true && mediaID.compareTo(imageID) == 0) {
                    mediaDate = imagecursor.getString(imageColumIndexDate);
                    break;
                }
            } while (imagecursor.moveToNext());
        }

        imagecursor.close();

        return mediaDate;
    }
}