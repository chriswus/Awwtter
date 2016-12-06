package me.shuobi_wu.awwtter.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.GridView;

import me.shuobi_wu.awwtter.R;
import me.shuobi_wu.awwtter.helper.ImageAdapter;

/**
 * a class that describes the activity in settings menu
 */
public class SettingsMenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_settings);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
    }
}
