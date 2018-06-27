package bliss.blissrecruitmentapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.AnimRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import bliss.blissrecruitmentapp.R;

public class Utils {
    public static final String API_BASE_URL = "https://private-bbbe9-blissrecruitmentapi.apiary-mock.com";
    public static final String APP_BASE_LINK = "blissrecruitment://";
    public static final String APP_FILTER_PARAM = "question_filter";
    public static final String APP_QUESTION_PARAM = "question_id";

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm == null)
            return false;

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    public static void hideFocusKeyboard(Context context,View view){
        InputMethodManager imm =  (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm !=null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void runViewAnimation(Context context, View view, @AnimRes int animationId) {
        Animation animation = AnimationUtils.loadAnimation(context, animationId);
        animation.reset();

        view.clearAnimation();
        view.startAnimation(animation);
    }

    public static void showInfoSnackBar(View parentView, int contentStringId, int color){
        Snackbar snackSuccess = Snackbar.make(parentView, contentStringId, Snackbar.LENGTH_SHORT);
        snackSuccess.getView().setBackgroundColor(color);
        snackSuccess.getView().bringToFront();

        snackSuccess.show();
    }

}
