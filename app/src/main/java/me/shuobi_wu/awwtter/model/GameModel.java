package me.shuobi_wu.awwtter.model;

import android.os.Parcelable;
import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * A Game model that stores all the relevant data user generated and modified
 * Need to use this to communicate with menu fragments
 */

//TODO: implement more custom data write and read methods
public class GameModel implements Parcelable {

    private int mClamCount;
    private int mEnergyCount;
    private List<Integer> mFoods;
    private List<Integer> mToys;
    private List<Boolean> mAlbum;

    public GameModel() {
        mClamCount = 0;
        mEnergyCount = 0;
        mFoods = new ArrayList<>();
        mToys = new ArrayList<>();
        mAlbum = new ArrayList<>();
    }

    public GameModel(int clam, int energy,
                     List<Integer> foods, List<Integer> toys, List<Boolean> album) {
        mClamCount = clam;
        mEnergyCount = energy;
        mFoods = foods;
        mToys = toys;
        mAlbum = album;
    }

    protected GameModel(Parcel in) {
        mClamCount = in.readInt();
        mEnergyCount = in.readInt();
        mFoods = new ArrayList<>();
        in.readList(mFoods, null);
        mToys = new ArrayList<>();
        in.readList(mToys, null);
        mAlbum = new ArrayList<>();
        in.readList(mAlbum, null);
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
        dest.writeInt(mClamCount);
        dest.writeInt(mEnergyCount);
        dest.writeList(mFoods);
        dest.writeList(mToys);
        dest.writeList(mAlbum);
    }

    public int getClamCount() {
        return mClamCount;
    }

    public void setClamCount(int clams) {
        this.mClamCount = clams;
    }

    public void addClam(int clams) {
        this.mClamCount += clams;
    }

    public int getEnergyCount() {
        return mEnergyCount;
    }

    public void setEnergyCount(int energy) {
        this.mEnergyCount = energy;
    }

    public void addEnergy(int energy) {
        this.mEnergyCount += energy;
    }

    public List<Integer> getFoods() {
        return mFoods;
    }

    public void setFoods(List<Integer> foods) {
        this.mFoods = foods;
    }

    /**
     * method to get the count of a certain type of food.
     * Refer to FoodType for the available types of food
     * @return
     */
    public int getFoodAt(int index) {
        if (mFoods != null && index >= 0 && index < mFoods.size()) {
            return mFoods.get(index);
        }
        return -1;
    }

    public List<Integer> getToys() {
        return mToys;
    }

    public void setToys(List<Integer> toys) {
        this.mToys = toys;
    }

    /**
     * method to get the count of a certain type of toy.
     * Refer to ToyType for the available types of toy
     * @return
     */
    public int getToyAt(int index) {
        if (mToys != null && index >= 0 && index < mToys.size()) {
            return mToys.get(index);
        }
        return -1;
    }

    public List<Boolean> getAlbum() {
        return mAlbum;
    }

    public void setAlbum(List<Boolean> album) {
        this.mAlbum = album;
    }

    /**
     * method to get a certain photo.
     * Refer to PhotoType for the available types of photo
     * @return
     */
    public boolean getPhotoAt(int index) {
        if (mAlbum != null && index >= 0 && index < mAlbum.size()) {
            return mAlbum.get(index);
        }
        return false;
    }
}
