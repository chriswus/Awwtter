package me.shuobi_wu.awwtter.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.GridView;

import me.shuobi_wu.awwtter.R;
import me.shuobi_wu.awwtter.helper.ImageAdapter;

/**
 * a class that describes the activity in album menu
 */
public class ToyMenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_toy);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
    }
}
