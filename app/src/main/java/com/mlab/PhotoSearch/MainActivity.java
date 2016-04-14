package com.mlab.PhotoSearch;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData(""));
        gridView.setAdapter(gridAdapter);

        Button buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextSearch = (EditText) findViewById(R.id.editTextSearch);
                String searchWord = editTextSearch.getText().toString();
                gridAdapter.setData(getData(searchWord));
            }
        });


//        searchview = (SearchView) findViewById(R.id.searchView);
//        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                searchWord = query;
//                gridAdapter.setData(getData());
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                searchWord = newText;
//                gridAdapter.setData(getData());
//                return true;
//            }
//        });

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
    private ArrayList<ImageItem> getData(String searchWord) {
        final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
        getThumbInfo(imageItems);

        if(searchWord != "") {
            final ArrayList<ImageItem> findItems = new ArrayList<ImageItem>();
            for(int i = 0; i < imageItems.size(); ++i) {
                ImageItem item = imageItems.get(i);
                if(item.getDate().indexOf(searchWord) != -1) {
                    findItems.add(item);
                }
            }

            return findItems;
        }

        return imageItems;
    }

    /**
     * MediaStore 정보 얻기
     */
    private void getThumbInfo(ArrayList<ImageItem> imageItems) {
        final String[] columns = { MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.IMAGE_ID };
        final String orderBy = MediaStore.Images.Thumbnails._ID;
        final Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        Cursor imagecursor = getContentResolver().query(uri, columns, null, null, orderBy);

        if(imagecursor != null && imagecursor.moveToFirst()) {
            String thumbsID;
            String mediaID;
            String date;
            int imageColumIdxThumbsID = imagecursor.getColumnIndexOrThrow(columns[0]);
            int imageColimIdxMediaID = imagecursor.getColumnIndexOrThrow(columns[1]);
            do {
                thumbsID = imagecursor.getString(imageColumIdxThumbsID);
                mediaID = imagecursor.getString(imageColimIdxMediaID);
                date = getMediaDateAdd(mediaID);
                if(thumbsID.isEmpty() != true) {
                    imageItems.add(new ImageItem(mediaID, thumbsID, date));
                }
            } while (imagecursor.moveToNext());
        }

        imagecursor.close();
    }

    private String getMediaDateAdd(String imageID) {
        final String[] columns = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_TAKEN };
        final String orderBy = MediaStore.Images.Media._ID;
        final Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor imagecursor = getContentResolver().query(uri, columns, null, null, orderBy);

        String mediaDate = "";
        if(imagecursor != null && imagecursor.moveToFirst()) {
            String mediaID = "";
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