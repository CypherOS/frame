package com.android.systemui.ambientmusic;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.systemui.R;
import com.android.systemui.ambientmusic.AmbientIndicationInflateListener;
import com.android.systemui.AutoReinflateContainer;
import com.android.systemui.doze.DozeReceiver;
import com.android.systemui.statusbar.phone.StatusBar;

public class AmbientIndicationContainer extends AutoReinflateContainer implements DozeReceiver {
	
    private View mAmbientIndication;
    private boolean mDozing;
    private ImageView mIcon;
    private StatusBar mStatusBar;
    private TextView mText;
    private Context mContext;
	
	private String mTrackName;
	private String mArtistName;

    public AmbientIndicationContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mContext = context;
    }

    public void hideIndication() {
        setIndication(null, null);
    }

    public void initializeView(StatusBar statusBar) {
        mStatusBar = statusBar;
        addInflateListener(new AmbientIndicationInflateListener(this));
    }

    public void updateAmbientIndicationView(View view) {
        mAmbientIndication = findViewById(R.id.ambient_indication);
        mText = (TextView)findViewById(R.id.ambient_indication_text);
        mIcon = (ImageView)findViewById(R.id.ambient_indication_icon);
        setIndication(mTrackName, mArtistName);
    }

    @Override
    public void setDozing(boolean dozing) {
        mDozing = dozing;
    }

    public void setIndication(String trackName, String artistName) {
		String trackByArtist = String.format(mContext.getResources().getString(R.string.ambient_play_track_information),
                            trackName, artistName)
        mText.setText(trackByArtist);
        mTrackName = trackName;
		mArtistName = artistName;
        mAmbientIndication.setClickable(false);
        boolean infoAvaillable = TextUtils.isEmpty(trackByArtist);
        if (infoAvaillable) {
            mAmbientIndication.setVisibility(View.INVISIBLE);
        } else {
            mAmbientIndication.setVisibility(View.VISIBLE);
        }
    }
}