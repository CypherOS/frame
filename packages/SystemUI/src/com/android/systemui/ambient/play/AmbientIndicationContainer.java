/*
 * Copyright (C) 2018 CypherOS
 * Copyright (C) 2018 PixelExperience
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program;
 * if not, see <http://www.gnu.org/licenses>.
 */
package com.android.systemui.ambient.play;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aoscp.support.lottie.LottieAnimationView;

import com.android.systemui.AutoReinflateContainer;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.StatusBar;

public class AmbientIndicationContainer extends AutoReinflateContainer {
    private View mAmbientIndication;
    private boolean mDozing;
	private boolean mManualMode = false;
    private LottieAnimationView mIcon;
    private CharSequence mIndication;
    private StatusBar mStatusBar;
    private TextView mText;
    private Context mContext;

    private String mSong;
    private String mArtist;

    public AmbientIndicationContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mContext = context;
    }

    public void hideIndication() {
        if (mAmbientIndication != null){
            mAmbientIndication.setVisibility(View.GONE);
        }
    }

    public void showIndication() {
        if (mAmbientIndication != null && mSong != null && mArtist != null){
            mAmbientIndication.setVisibility(View.VISIBLE);
            mAmbientIndication.setClickable(false);
            mText.setText(String.format(mContext.getResources().getString(
                    com.android.internal.R.string.ambient_recognition_information), mSong, mArtist));
            mIcon.setAnimation(R.raw.ambient_music_note);
			mIcon.loop(false);
            mIcon.playAnimation();
        }
    }

	public void showManualIndication() {
		mAmbientIndication.setVisibility(View.VISIBLE);
		mAmbientIndication.setClickable(true);
		mIcon.setAnimation(R.raw.ambient_music_manual_mode);
		mIcon.loop(false);
		mIcon.playAnimation();
		mIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mIcon.setAnimation(R.raw.ambient_music_manual_mode_start);
				mIcon.loop(false);
				mIcon.playAnimation();
				mStatusBar.startManualRecognition();
			}
		});
	}

	public void onRecognizing() {
		mIcon.setAnimation(R.raw.ambient_music_manual_mode_listen);
		mIcon.loop(true);
		mIcon.playAnimation();
	}
	
	public void onRecognized() {
		mRecognized = true;
		mIcon.setAnimation(R.raw.ambient_music_manual_mode);
		mIcon.loop(false);
		mIcon.playAnimation();
		if (mAmbientIndication != null && mSong != null && mArtist != null) {
			mText.setText(String.format(mContext.getResources().getString(
				    com.android.internal.R.string.ambient_recognition_information), mSong, mArtist));
		}
	}

    public void initializeView(StatusBar statusBar) {
        mStatusBar = statusBar;
        addInflateListener(new AmbientIndicationInflateListener(this));
    }

    public void updateAmbientIndicationView(View view) {
        mAmbientIndication = findViewById(R.id.ambient_indication);
        mText = (TextView) findViewById(R.id.ambient_indication_text);
        mIcon = (LottieAnimationView) findViewById(R.id.ambient_indication_icon);
        setIndication(mSong, mArtist, mManualMode);
    }

    public void setIndication(String song, String artist, boolean manualMode) {
        mSong = song;
        mArtist = artist;
		mManualMode = manualMode;
		if (manualMode) {
			if (mSong == null || mArtist == null) {
				mText.setVisibility(View.GONE);
			}
			return;
		}

        if (mSong == null || mArtist == null) {
            hideIndication();
        }
    }

    private class AmbientIndicationInflateListener implements AutoReinflateContainer.InflateListener {
        private Object mContainer;

        public AmbientIndicationInflateListener(Object object) {
            mContainer = object;
        }

        private void setAmbientIndicationView(View view) {
            ((AmbientIndicationContainer) mContainer).updateAmbientIndicationView(view);
        }

        @Override
        public void onInflated(View view) {
            setAmbientIndicationView(view);
        }
    }
}
