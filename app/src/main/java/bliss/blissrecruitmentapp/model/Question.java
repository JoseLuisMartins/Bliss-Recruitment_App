package bliss.blissrecruitmentapp.model;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Question {

    @SerializedName("id")
    private int id;

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

    public int getId() {
        return id;
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

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.get().load(url).into(view);
    }
}

