package be.kuleuven.timetoclimb.subActivity;

import android.net.Uri;

public interface postCreateView {
    void setRouteIDError(String error);

    void setClimbingHallError(String error);

    void setGradeError(String error);

    void setDiscriptionError(String error);

    String getRouteIDText();

    String getClimbingHallText();

    String getGradeText();

    String getDescriptionText();

    void requestImageViewFocus();

    void onPostSavedSuccess();

    Uri getImageUri();
}
