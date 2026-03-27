package com.je.gui;

import java.io.InputStream;

public sealed interface Theme permits Theme.Day, Theme.Night, Theme.Custom {
    InputStream configStream();

    record Day() implements Theme {
        @Override
        public InputStream configStream() {
            return getClass().getResourceAsStream("/day.style");
        }
    }

    record Night() implements Theme {
        @Override
        public InputStream configStream() {
            return getClass().getResourceAsStream("/night.style");
        }
    }

    final class Custom implements Theme {
        private final InputStream mStream;

        public Custom(InputStream stream) {
            mStream = stream;
        }

        @Override
        public InputStream configStream() {
            return mStream;
        }
    }
}
