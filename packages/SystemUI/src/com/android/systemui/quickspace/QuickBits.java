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
 * limitations under the License.
 */

package com.android.systemui.quickspace;

import android.content.IntentFilter;

public class QuickBits {

    public static IntentFilter getPackageIntentInfo(String pkg, String... action) {
        IntentFilter filter = new IntentFilter();
        for (String addAction : action) {
            filter.addAction(addAction);
        }
        filter.addDataScheme("package");
        filter.addDataSchemeSpecificPart(pkg, 0);
        return filter;
    }
}