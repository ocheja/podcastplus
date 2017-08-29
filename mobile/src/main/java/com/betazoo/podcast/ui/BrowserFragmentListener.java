package com.betazoo.podcast.ui;

import android.os.Bundle;

/**
 * Created by thetaeo on 22/08/2017.
 */


public interface BrowserFragmentListener {
    void onMediaItemSelected(String mediaID, int browsingID, Bundle bundle);
    void setToolbarTitle(CharSequence title);
}
