package bliss.blissrecruitmentapp.model;

import com.google.gson.annotations.SerializedName;

public class Status {
    @SerializedName("status")
    private String mStatus;



    public Status(String mStatus) {
        this.mStatus = mStatus;
    }


    public String getmStatus(){
        return mStatus;
    }

}
