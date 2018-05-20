package com.osaf.root.moviepedia.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {

    private String mPosterPath;
    private String mReleaseDate;
    private String mTitle;
    private String mVote;
    private String mOverview;

    public Movies() {

    }

    public Movies(String title, String releaseDate, String posterPath,
                  String vote, String overview) {
        this.mPosterPath = posterPath;
        this.mReleaseDate = releaseDate;
        this.mTitle = title;
        this.mOverview = overview;
        this.mVote = vote;
    }

    public void setPosterpath(String posterPath) {
        this.mPosterPath = posterPath;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    public void setVote(String vote) {
        this.mVote = vote;
    }

    public void setOverview(String overview) {
        this.mOverview = overview;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getVote() {
        return mVote +"/10";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.mPosterPath);
        dest.writeString(this.mVote);
        dest.writeString(this.mOverview);
    }

    protected Movies(Parcel in) {
        this.mTitle = in.readString();
        this.mReleaseDate = in.readString();
        this.mPosterPath = in.readString();
        this.mVote = in.readString();
        this.mOverview = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
