package bliss.blissrecruitmentapp.model;

import com.google.gson.annotations.SerializedName;

public class Choice {

    @SerializedName("choice")
    private String choice;

    @SerializedName("votes")
    private int votes;

    public String getChoice() {
        return choice;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
