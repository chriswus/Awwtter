package me.shuobi_wu.awwtter.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

import me.shuobi_wu.awwtter.R;
import me.shuobi_wu.awwtter.helper.ImageAdapter;

/**
 * a custom fragment that supports the album (collection) view and activity
 * Temporary created on a 2x2 test view. Will migrate to a gridview.
 */

public class AlbumMenuFragment extends Fragment {

    //Constants
    public static final int NUM_PHOTOS = 20;

    public AlbumMenuFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GridView view = (GridView) inflater.inflate(R.layout.activity_album, container, false);
        view.setAdapter(new ImageAdapter(view.getContext()));
        return view;
    }




}
