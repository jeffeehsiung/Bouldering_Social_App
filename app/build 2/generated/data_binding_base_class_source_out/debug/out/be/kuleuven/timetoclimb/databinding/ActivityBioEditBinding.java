// Generated by view binder compiler. Do not edit!
package be.kuleuven.timetoclimb.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public final class ActivityBioEditBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView Back;

  @NonNull
  public final Button btnUpdate;

  @NonNull
  public final TextView lblEditBio;

  @NonNull
  public final EditText txtEditBioField;

  private ActivityBioEditBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView Back,
      @NonNull Button btnUpdate, @NonNull TextView lblEditBio, @NonNull EditText txtEditBioField) {
    this.rootView = rootView;
    this.Back = Back;
    this.btnUpdate = btnUpdate;
    this.lblEditBio = lblEditBio;
    this.txtEditBioField = txtEditBioField;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityBioEditBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityBioEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_bio_edit, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityBioEditBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Back;
      ImageView Back = ViewBindings.findChildViewById(rootView, id);
      if (Back == null) {
        break missingId;
      }

      id = R.id.btnUpdate;
      Button btnUpdate = ViewBindings.findChildViewById(rootView, id);
      if (btnUpdate == null) {
        break missingId;
      }

      id = R.id.lblEditBio;
      TextView lblEditBio = ViewBindings.findChildViewById(rootView, id);
      if (lblEditBio == null) {
        break missingId;
      }

      id = R.id.txtEditBioField;
      EditText txtEditBioField = ViewBindings.findChildViewById(rootView, id);
      if (txtEditBioField == null) {
        break missingId;
      }

      return new ActivityBioEditBinding((ConstraintLayout) rootView, Back, btnUpdate, lblEditBio,
          txtEditBioField);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
