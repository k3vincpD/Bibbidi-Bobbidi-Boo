package Logic;

import java.io.InputStreamReader;
public enum Files {
        DEFAULT_MAP,
        MAPA_UNO,
        MAPA_DOS,
        MAPA_TRES;

        public InputStreamReader file = null;

        public InputStreamReader getFile() {
            return this.file;
        }
    }

