package br.com.aio.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Created by elton on 25/09/2017.
 */

public class SpinnerActionsHeader extends Spinner {

    private boolean dropDownMenuShown = false;

    public interface OnSpinnerEventsListener {

        /**
         * Callback triggered when the spinner was opened.
         */
        void onSpinnerOpened(Spinner spinner);

        /**
         * Callback triggered when the spinner was closed.
         */
        void onSpinnerClosed(Spinner spinner);

    }

    private OnSpinnerEventsListener mListener;

    @Override
    public void setPopupBackgroundResource(@DrawableRes int resId) {
        super.setPopupBackgroundResource(resId);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    public void setDropDownMenuShown(boolean dropDownMenuShown) {
        this.dropDownMenuShown = dropDownMenuShown;
    }

    public SpinnerActionsHeader(Context context)
    { super(context); }

    public SpinnerActionsHeader(Context context, AttributeSet attrs)
    { super(context, attrs); }

    public SpinnerActionsHeader(Context context, AttributeSet attrs, int defStyle)
    { super(context, attrs, defStyle); }

    @Override
    public boolean performClick() {
        setDropDownMenuShown(true);
        if (mListener != null) {
            mListener.onSpinnerOpened(this);
        }
        return super.performClick();
    }

    public void setSpinnerEventsListener(
            OnSpinnerEventsListener onSpinnerEventsListener) {
        mListener = onSpinnerEventsListener;
    }

    public void performClosedEvent() {
        dropDownMenuShown = false;
        if (mListener != null) {
            mListener.onSpinnerClosed(this);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (this.isActivated() && dropDownMenuShown && hasWindowFocus) {
            performClosedEvent();
        }
    }

}
