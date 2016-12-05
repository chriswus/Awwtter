package me.shuobi_wu.awwtter.model;

import java.util.HashMap;

/**
 * A class that specifies the type of a item on the map
 */

public class ItemType {
    public static final int FOOD = 0;
    public static final int TOY  = 1;

    protected static String[] mTypeNames = {
            "FOOD", "TOY" };

    protected static HashMap<String, Integer> mNameDict =
            new HashMap<String, Integer>(mTypeNames.length);

    static {
        for (int i = 0; i< mTypeNames.length; i++) {
            mNameDict.put(mTypeNames[i], i);
        }
    }

    public static int indexFromName(String typeName) {
        if (typeName == null) return -1;
        Integer result = mNameDict.get(typeName);
        if (result == null) return -1;
        return result;
    }

    public static String nameFromIndex(int indx) {
        if (!isValid(indx)) return null;
        else return mTypeNames[indx];
    }

    public static boolean isValid(int indx) {
        return indx < 0 || indx >= mTypeNames.length;
    }

}
