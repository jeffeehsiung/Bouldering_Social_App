// Generated by view binder compiler. Do not edit!
package be.kuleuven.timetoclimb.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import be.kuleuven.timetoclimb.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CreateEventViewBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnEditEndTime;

  @NonNull
  public final Button btnEditStartTime;

  @NonNull
  public final TextView lblEndTime;

  @NonNull
  public final TextView lblSelectedDate;

  @NonNull
  public final TextView lblStartTime;

  @NonNull
  public final EditText txtDescription;

  @NonNull
  public final EditText txtTitle;

  private CreateEventViewBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnEditEndTime,
      @NonNull Button btnEditStartTime, @NonNull TextView lblEndTime,
      @NonNull TextView lblSelectedDate, @NonNull TextView lblStartTime,
      @NonNull EditText txtDescription, @NonNull EditText txtTitle) {
    this.rootView = rootView;
    this.btnEditEndTime = btnEditEndTime;
    this.btnEditStartTime = btnEditStartTime;
    this.lblEndTime = lblEndTime;
    this.lblSelectedDate = lblSelectedDate;
    this.lblStartTime = lblStartTime;
    this.txtDescription = txtDescription;
    this.txtTitle = txtTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CreateEventViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CreateEventViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.create_event_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CreateEventViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnEditEndTime;
      Button btnEditEndTime = ViewBindings.findChildViewById(rootView, id);
      if (btnEditEndTime == null) {
        break missingId;
      }

      id = R.id.btnEditStartTime;
      Button btnEditStartTime = ViewBindings.findChildViewById(rootView, id);
      if (btnEditStartTime == null) {
        break missingId;
      }

      id = R.id.lblEndTime;
      TextView lblEndTime = ViewBindings.findChildViewById(rootView, id);
      if (lblEndTime == null) {
        break missingId;
      }

      id = R.id.lblSelectedDate;
      TextView lblSelectedDate = ViewBindings.findChildViewById(rootView, id);
      if (lblSelectedDate == null) {
        break missingId;
      }

      id = R.id.lblStartTime;
      TextView lblStartTime = ViewBindings.findChildViewById(rootView, id);
      if (lblStartTime == null) {
        break missingId;
      }

      id = R.id.txtDescription;
      EditText txtDescription = ViewBindings.findChildViewById(rootView, id);
      if (txtDescription == null) {
        break missingId;
      }

      id = R.id.txtTitle;
      EditText txtTitle = ViewBindings.findChildViewById(rootView, id);
      if (txtTitle == null) {
        break missingId;
      }

      return new CreateEventViewBinding((ConstraintLayout) rootView, btnEditEndTime,
          btnEditStartTime, lblEndTime, lblSelectedDate, lblStartTime, txtDescription, txtTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
