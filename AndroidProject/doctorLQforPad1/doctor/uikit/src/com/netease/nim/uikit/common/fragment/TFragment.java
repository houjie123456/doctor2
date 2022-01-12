package com.netease.nim.uikit.common.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.netease.nim.uikit.business.contact.ContactsFragment;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.util.log.LogUtil;

public abstract class TFragment extends Fragment {
    private static final Handler handler = new Handler();
    public FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    final String FRAGMENT_TAG = "fragment";
    private int containerId;

    private boolean destroyed;

    protected final boolean isDestroyed() {
        return destroyed;
    }

    public int getContainerId() {
        return containerId;
    }

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LogUtil.ui("fragment: " + getClass().getSimpleName() + " onActivityCreated()");

        destroyed = false;
    }

    public void onDestroy() {
        super.onDestroy();

        LogUtil.ui("fragment: " + getClass().getSimpleName() + " onDestroy()");

        destroyed = true;
    }

    protected final Handler getHandler() {
        return handler;
    }

    protected final void postRunnable(final Runnable runnable) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // validate
                // TODO use getActivity ?
                if (!isAdded()) {
                    return;
                }

                // run
                runnable.run();
            }
        });
    }

    protected final void postDelayed(final Runnable runnable, long delay) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // validate
                // TODO use getActivity ?
                if (!isAdded()) {
                    return;
                }

                // run
                runnable.run();
            }
        }, delay);
    }

    protected void showKeyboard(boolean isShow) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        if (isShow) {
            if (activity.getCurrentFocus() == null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                imm.showSoftInput(activity.getCurrentFocus(), 0);
            }
        } else {
            if (activity.getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }
    }

    protected void hideKeyboard(View view) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        imm.hideSoftInputFromWindow(
                view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected <T extends View> T findView(int resId) {
        return (T) (getView().findViewById(resId));
    }

    protected void setToolBar(int toolbarId, int titleId, int logoId) {
        if (getActivity() != null && getActivity() instanceof UI) {
            ((UI) getActivity()).setToolBar(toolbarId, titleId, logoId);
        }
    }

    protected void setTitle(int titleId) {
        if (getActivity() != null && getActivity() instanceof UI) {
            getActivity().setTitle(titleId);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Fragment f = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
//        if (f == null) {
//            f = new ContactsFragment();
//            f.setTargetFragment(f, 0);
//            //重新添加fragment
//            fragmentTransaction.add(f, FRAGMENT_TAG).commit();
//        }
//        f.onActivityResult(requestCode, resultCode, data);
    }
}
