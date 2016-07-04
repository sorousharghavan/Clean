package com.arghami.www.clean;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.arghami.www.clean.View.PhotoView;
import com.arghami.www.clean.model.Photo;
import com.arghami.www.clean.model.PhotoAdapter;
import com.arghami.www.clean.presenter.PhotoPresenter;
import com.arghami.www.clean.presenter.ThumbnailPresenter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PhotoView {
    ProgressDialog progressDialog;
    RecyclerView listView;
    PhotoAdapter adapter;
    List<Photo> data = new ArrayList<>();
    PhotoPresenter presenter;
    ThumbnailPresenter thumbnailPresenter;

    @Override
    protected void onPause() {
        presenter.pause();
        thumbnailPresenter.pause();
        presenter = null;
        thumbnailPresenter = null;
        super.onPause();
    }

    @Override
    protected void onResume() {
        presenter = new PhotoPresenter(this);
        thumbnailPresenter = new ThumbnailPresenter(this);
        thumbnailPresenter.resume();
        presenter.resume();
        presenter.showMore(1);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (RecyclerView) findViewById(R.id.list_view);
        final GridLayoutManager manager = new GridLayoutManager(this, 3);
        listView.setLayoutManager(manager);
        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (manager.findLastVisibleItemPosition() == adapter.getItemCount() - 1) {
                    presenter.showMore(adapter.getItemCount() % 100 + 2);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        adapter = new PhotoAdapter(data, this);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void diplayProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoadMore(List<Photo> photo) {
        for (Photo p : photo)
            thumbnailPresenter.getThumbnail(p);
    }

    @Override
    public void onDisplayPhoto(Photo photo) {
        adapter.addData(photo);
    }
}
