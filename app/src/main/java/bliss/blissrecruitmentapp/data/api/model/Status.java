package bliss.blissrecruitmentapp.data.api.model;

import com.google.gson.annotations.SerializedName;

public class Status {
    @SerializedName("status")
    private final String mStatus;



    public Status(String mStatus) {
        this.mStatus = mStatus;
    }


    public String getStatus(){
        return mStatus;
    }

}
