package me.shuobi_wu.awwtter.fragment;

import android.app.FragmentTransaction;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import me.shuobi_wu.awwtter.R;
import me.shuobi_wu.awwtter.activity.AlbumMenuActivity;

/**
 * a custom fragment that displays a list of available menu items
 */
public class MenuFragment extends Fragment {

    //Constants
    private static final String FOOD_MENU = "food menu";
    private static final String TOY_MENU = "toy menu";
    private static final String ALBUM_MENU = "album menu";
    private static final String SETTINGS_MENU = "settings menu";

    public MenuFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_menu, container, false);
        Button food = (Button) view.findViewById(R.id.food_button);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(FOOD_MENU);
            }
        });

        Button toy = (Button) view.findViewById(R.id.toy_button);
        toy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(TOY_MENU);
            }
        });

        Button album = (Button) view.findViewById(R.id.album_button);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(ALBUM_MENU);
            }
        });

        Button settings = (Button) view.findViewById(R.id.settings_button);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(SETTINGS_MENU);
            }
        });
        return view;
    }

    /**
     * helper method to open a designated menu
     */
    private void open(String menu) {
        Intent intent = null;
        if (menu != null && menu != "") {
            Fragment frag = null;
            switch(menu) {
                case FOOD_MENU:
                    frag = new FoodMenuFragment();
                    break;
                case TOY_MENU:
                    frag = new ToyMenuFragment();
                    break;
                case ALBUM_MENU:
                    frag = new AlbumMenuFragment();
                    intent = new Intent(getActivity(), AlbumMenuActivity.class);
                    break;
                case SETTINGS_MENU:
                    frag = new SettingsMenuFragment();
                    break;
                default:
                    break;
            }
            if (intent != null) {
                startActivity(intent);
            }
            //TODO: fix this
            else if (frag != null) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.map_view, frag, menu);
                transaction.addToBackStack(menu);
                transaction.commit();
                Log.e("menu", "opening "+menu);
            }

        }
    }

}
