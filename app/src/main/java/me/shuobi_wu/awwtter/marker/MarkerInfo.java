package me.shuobi_wu.awwtter.marker;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * An ADT class that stores infos about a marker on the map
 */

public class MarkerInfo {

    private int mValue = 0;
    private int mCategory = -1;
    private LatLng mLocation = null;
    private String mTag = "";

    private double mFrequency = 0;

    public MarkerInfo() {

    }

    public MarkerInfo(int value, int category, LatLng location, String tag, int x, int y,
                      int w, int h, Bitmap img) {
        this.mValue = value;
        this.mCategory = category;
        this.mLocation = location;
        this.mTag = tag;
        this.mX = x;
        this.mY = y;
        this.mWidth = w;
        this.mHeight = h;
        this.mImage = img;
    }

    public MarkerInfo(int value, int category, LatLng location, String tag, int x, int y,
                      int w, int h) {
        this.mValue = value;
        this.mCategory = category;
        this.mLocation = location;
        this.mTag = tag;
        this.mX = x;
        this.mY = y;
        this.mWidth = w;
        this.mHeight = h;
    }

    protected float mX = 0;
    public float getX() {return mX;}
    public void setX(float xv) {mX = xv; /*xx add damage here */}

    protected float mY = 0;
    public float getY() {return mY;}
    public void setY(float yv) {mY = yv;}

    protected float mWidth = 13;
    public float getW() {return mWidth;}
    public void setW(float wv) {mWidth = wv;}

    protected float mHeight = 17;
    public float getH() {return mHeight;}
    public void setH(float hv) {mHeight = hv;}

    protected Bitmap mImage = null;

    /**
     *
     * @return the image that should be shown. If null, no image should be displayed
     */
    public Bitmap getImage() {
        return mImage;
    }

    /**
     * @param newImage the image that is set, which could be null if it is not supposed to be visible
     */
    public void setImage(Bitmap newImage) {
        mImage = newImage;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int mValue) {
        this.mValue = mValue;
    }

    public int getCategory() {
        return mCategory;
    }

    public void setCategory(int mCategory) {
        this.mCategory = mCategory;
    }

    public LatLng getLocation() {
        return mLocation;
    }

    public void setLocation(LatLng mLocation) {
        this.mLocation = mLocation;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String mTag) {
        this.mTag = mTag;
    }

    public double getFrequency() {
        return mFrequency;
    }

    public void setFrequency(double mFrequency) {
        this.mFrequency = mFrequency;
    }
}
