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

}

