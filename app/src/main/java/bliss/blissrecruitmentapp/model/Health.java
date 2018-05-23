package bliss.blissrecruitmentapp.model;

import com.google.gson.annotations.SerializedName;

public class Health {
    @SerializedName("status")
    private String mStatus;


    public Health(String mStatus) {
        this.mStatus = mStatus;
    }


    public String getmStatus(){
        return mStatus;
    }
}
