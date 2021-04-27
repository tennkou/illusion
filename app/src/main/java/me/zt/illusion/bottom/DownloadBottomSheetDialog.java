package me.zt.illusion.bottom;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import me.zt.illusion.R;


/**
 * DownloadBottomSheetDialog
 * @see com.google.android.material.bottomsheet.BottomSheetDialog
 */
public class DownloadBottomSheetDialog extends AppCompatDialog {

  private BottomSheetBehavior<FrameLayout> behavior;

  private boolean cancelable = true;

  public DownloadBottomSheetDialog(@NonNull Context context) {
    this(context, 0);
  }

  public DownloadBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
    super(context, getThemeResId(context, theme));
  }

  protected DownloadBottomSheetDialog(
          @NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
    super(context, cancelable, cancelListener);
    this.cancelable = cancelable;
  }

  @Override
  public void setContentView(@LayoutRes int layoutResId) {
    super.setContentView(wrapInBottomSheet(layoutResId, null, null));
  }

  @Override
  public void setContentView(View view) {
    super.setContentView(wrapInBottomSheet(0, view, null));
  }

  @Override
  public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(wrapInBottomSheet(0, view, params));
  }

  @Override
  public void setCancelable(boolean cancelable) {
    super.setCancelable(cancelable);
    if (this.cancelable != cancelable) {
      this.cancelable = cancelable;
      if (behavior != null) {
        behavior.setHideable(cancelable);
      }
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (behavior != null) {
      behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
  }

  @Override
  public void setCanceledOnTouchOutside(boolean cancel) {
    super.setCanceledOnTouchOutside(cancel);
    if (cancel && !cancelable) {
      cancelable = true;
    }
  }

  private View wrapInBottomSheet(int layoutResId, View view, ViewGroup.LayoutParams params) {
    FrameLayout container =
        (FrameLayout) View.inflate(getContext(), R.layout.download_design_bottom_sheet_dialog, null);
    CoordinatorLayout coordinator = (CoordinatorLayout) container.findViewById(R.id.coordinator);
    if (layoutResId != 0 && view == null) {
      view = getLayoutInflater().inflate(layoutResId, coordinator, false);
    }
    FrameLayout bottomSheet = (FrameLayout) coordinator.findViewById(R.id.design_bottom_sheet);
    behavior = BottomSheetBehavior.from(bottomSheet);
//    behavior.setBottomSheetCallback(bottomSheetCallback);
    behavior.setHideable(cancelable);
    if (params == null) {
      bottomSheet.addView(view);
    } else {
      bottomSheet.addView(view, params);
    }

    // Handle accessibility events
    ViewCompat.setAccessibilityDelegate(
        bottomSheet,
        new AccessibilityDelegateCompat() {
          @Override
          public void onInitializeAccessibilityNodeInfo(
                  View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            if (cancelable) {
              info.addAction(AccessibilityNodeInfoCompat.ACTION_DISMISS);
              info.setDismissable(true);
            } else {
              info.setDismissable(false);
            }
          }

          @Override
          public boolean performAccessibilityAction(View host, int action, Bundle args) {
            if (action == AccessibilityNodeInfoCompat.ACTION_DISMISS && cancelable) {
              cancel();
              return true;
            }
            return super.performAccessibilityAction(host, action, args);
          }
        });
    bottomSheet.setOnTouchListener(
        new View.OnTouchListener() {
          @Override
          public boolean onTouch(View view, MotionEvent event) {
            // Consume the event and prevent it from falling through
            return true;
          }
        });
    return container;
  }

  private static int getThemeResId(Context context, int themeId) {
    if (themeId == 0) {
      // If the provided theme is 0, then retrieve the dialogTheme from our theme
      TypedValue outValue = new TypedValue();
      if (context.getTheme().resolveAttribute(R.attr.bottomSheetDialogTheme, outValue, true)) {
        themeId = outValue.resourceId;
      } else {
        // bottomSheetDialogTheme is not provided; we default to our light theme
        themeId = R.style.Theme_Design_Light_BottomSheetDialog;
      }
    }
    return themeId;
  }

}