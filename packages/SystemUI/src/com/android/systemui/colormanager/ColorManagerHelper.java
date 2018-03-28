/*
 * Copyright (C) 2018 CypherOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.systemui.colormanager;

import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.os.RemoteException;
import android.provider.Settings;

/**
 * Helper class for Color Manager that works as a bridge
 * to get/set aoscp overlays.
 */
public class ColorManagerHelper {

    public static final String TAG = "ColorManagerHelper";

    public static final String[] BLACK_THEME = {
            "co.aoscp.theme.black",
            "co.aoscp.theme.settings.black",
    };
    public static final String[] DARK_THEME = {
            "co.aoscp.theme.dark",
            "co.aoscp.theme.settings.dark",
    };

    // Package names for accents
    private static final String ACCENT_DEFAULT = "default";
    private static final String ACCENT_DEEP_PURPLE = "co.aoscp.accent.deeppurple";
    private static final String ACCENT_INDIGO = "co.aoscp.accent.indigo";
    private static final String ACCENT_PINK = "co.aoscp.accent.pink";
    private static final String ACCENT_PURPLE = "co.aoscp.accent.purple";
    private static final String ACCENT_RED = "co.aoscp.accent.red";
    private static final String ACCENT_SKY_BLUE = "co.aoscp.accent.skyblue";
    private static final String ACCENT_TEAL = "co.aoscp.accent.teal";
    private static final String ACCENT_WHITE = "co.aoscp.accent.white";
    private static final String ACCENT_YELLOW = "co.aoscp.accent.yellow";

    private static final String[] ACCENTS = {
            ACCENT_DEFAULT,
            ACCENT_DEEP_PURPLE,
            ACCENT_INDIGO,
            ACCENT_PINK,
            ACCENT_PURPLE,
            ACCENT_RED,
            ACCENT_SKY_BLUE,
            ACCENT_TEAL,
            ACCENT_WHITE,
            ACCENT_YELLOW,
    };

    public static boolean isUsingDarkTheme(IOverlayManager om, int userId) {
        OverlayInfo themeInfo = null;
        try {
            themeInfo = om.getOverlayInfo("co.aoscp.theme.dark",
                    userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return themeInfo != null && themeInfo.isEnabled();
    }

    public static boolean isUsingBlackTheme(IOverlayManager om, int userId) {
        OverlayInfo themeInfo = null;
        try {
            themeInfo = om.getOverlayInfo("co.aoscp.theme.black",
                    userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return themeInfo != null && themeInfo.isEnabled();
    }

    public static void restoreDefaultAccent(IOverlayManager om, int userId) {
        for (int i = 1; i < ACCENTS.length; i++) {
            String accents = ACCENTS[i];
            try {
                om.setEnabled(accents, false, userId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateAccent(IOverlayManager om, int userId, int userAccentSetting) throws RemoteException {
        switch (userAccentSetting) {
            case 0:
                restoreDefaultAccent(om, userId);
                break;
            case 1:
                om.setEnabledExclusive(ACCENT_DEEP_PURPLE, true, userId);
                break;
            case 2:
                om.setEnabledExclusive(ACCENT_INDIGO, true, userId);
                break;
            case 3:
                om.setEnabledExclusive(ACCENT_PINK, true, userId);
                break;
            case 4:
                om.setEnabledExclusive(ACCENT_PURPLE, true, userId);
                break;
            case 5:
                om.setEnabledExclusive(ACCENT_RED, true, userId);
                break;
            case 6:
                om.setEnabledExclusive(ACCENT_SKY_BLUE, true, userId);
                break;
            case 7:
                om.setEnabledExclusive(ACCENT_TEAL, true, userId);
                break;
            case 8:
                om.setEnabledExclusive(ACCENT_WHITE, true, userId);
                break;
            case 9:
                om.setEnabledExclusive(ACCENT_YELLOW, true, userId);
                break;
        }
    }
}
