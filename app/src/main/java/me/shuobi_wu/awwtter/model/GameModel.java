package me.shuobi_wu.awwtter.model;

import android.os.Parcelable;
import android.os.Parcel;

/**
 * A Game model that stores all the relevant data user generated and modified
 */

public class GameModel implements Parcelable {
    protected GameModel(Parcel in) {
    }

    public static final Creator<GameModel> CREATOR = new Creator<GameModel>() {
        @Override
        public GameModel createFromParcel(Parcel in) {
            return new GameModel(in);
        }

        @Override
        public GameModel[] newArray(int size) {
            return new GameModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
