package me.shuobi_wu.awwtter.model;

import me.shuobi_wu.awwtter.R;

/**
 * A class that holds all the constants.
 */

public class ImageCollections {

    public static Integer[] ALBUM_IDS = {
            R.drawable.otter_orig, R.drawable.otter_clap,
            R.drawable.otter_confident, R.drawable.otter_cups,
            R.drawable.otter_cute, R.drawable.otter_indifferent,
            R.drawable.otter_milk, R.drawable.otter_shy,
            R.drawable.otter_super_cute, R.drawable.otter_surprise,
            R.drawable.otter_wtf, R.drawable.otter_mom,
            R.drawable.unknown, R.drawable.unknown,
            R.drawable.unknown, R.drawable.unknown,
            R.drawable.unknown, R.drawable.unknown
    };

    public static Integer[] TOY_IDS = {
            R.drawable.unknown, R.drawable.unknown,
            R.drawable.unknown, R.drawable.unknown,
            R.drawable.unknown, R.drawable.unknown,
            R.drawable.unknown, R.drawable.unknown,
            R.drawable.unknown, R.drawable.unknown,
            R.drawable.unknown, R.drawable.unknown
    };

    public static Integer[] FOOD_IDS = {
            R.drawable.unknown, R.drawable.unknown,
            R.drawable.unknown, R.drawable.unknown,
            R.drawable.unknown, R.drawable.unknown,
            R.drawable.unknown, R.drawable.unknown,
            R.drawable.unknown, R.drawable.unknown,
            R.drawable.unknown, R.drawable.unknown
    };

    public static Integer[] getAlbumIds() {
        return ALBUM_IDS;
    }

    public static void setAlbumIds(Integer[] albumIds) {
        ALBUM_IDS = albumIds;
    }

    public static Integer[] getToyIds() {
        return TOY_IDS;
    }

    public static void setToyIds(Integer[] toyIds) {
        TOY_IDS = toyIds;
    }

    public static Integer[] getFoodIds() {
        return FOOD_IDS;
    }

    public static void setFoodIds(Integer[] foodIds) {
        FOOD_IDS = foodIds;
    }
}
