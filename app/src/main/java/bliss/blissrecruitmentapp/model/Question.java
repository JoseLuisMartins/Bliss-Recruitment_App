package bliss.blissrecruitmentapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Question {

    @SerializedName("id")
    private int mId;

    @SerializedName("question")
    private String question;

    @SerializedName("image_url")
    private String image_url;

    @SerializedName("thumb_url")
    private String thumb_url;

    @SerializedName("published_at")
    private String published_at;

    @SerializedName("choices")
    private ArrayList<Choice> choices;

    public int getmId() {
        return mId;
    }

    public String getQuestion() {
        return question;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public String getPublished_at() {
        return published_at;
    }

    public ArrayList<Choice> getChoices() {
        return choices;
    }

    @Override
    public String toString() {
        return "Question{" +
                "mId=" + mId +
                ", question='" + question + '\'' +
                ", image_url='" + image_url + '\'' +
                ", thumb_url='" + thumb_url + '\'' +
                ", published_at='" + published_at + '\'' +
                ", choices=" + choices +
                '}';
    }
}

