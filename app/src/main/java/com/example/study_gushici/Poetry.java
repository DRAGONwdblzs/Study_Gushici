package com.example.study_gushici;

import android.os.Parcel;
import android.os.Parcelable;

public class Poetry implements Parcelable {
    private String title;
    private String author;
    private String content;
    private String dynasty;

    public Poetry(String title, String author, String content, String dynasty) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.dynasty = dynasty;
    }

    protected Poetry(Parcel in) {
        title = in.readString();
        author = in.readString();
        content = in.readString();
        dynasty = in.readString();
    }

    public static final Creator<Poetry> CREATOR = new Creator<Poetry>() {
        @Override
        public Poetry createFromParcel(Parcel in) {
            return new Poetry(in);
        }

        @Override
        public Poetry[] newArray(int size) {
            return new Poetry[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(dynasty);
    }
}