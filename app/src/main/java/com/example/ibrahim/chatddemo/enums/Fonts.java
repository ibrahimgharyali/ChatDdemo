package com.example.ibrahim.chatddemo.enums;

import android.support.annotation.NonNull;

/**
 * By katepratik on 27/2/17.
 */

public enum Fonts {
    LobsterRegular {
        @NonNull
        @Override
        public String toString() {
            return "fonts/LobsterRegular.ttf";
        }
    },
    MontserratRegular {
        @NonNull
        @Override
        public String toString() {
            return "fonts/MontserratRegular.otf";
        }
    },
    MontserratLight {
        @NonNull
        @Override
        public String toString() {
            return "fonts/MontserratLight.otf";
        }
    }
}
