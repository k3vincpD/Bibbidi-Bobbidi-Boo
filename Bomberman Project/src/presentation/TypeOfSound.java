package presentation;

public enum TypeOfSound {
    MENU(0),
    DEATH(1),
    GAME(2),
    START_GAME(3),
    POWER_UP(4);

    private final int soundIndex;

    TypeOfSound(int soundIndex) {
        this.soundIndex = soundIndex;
    }

    public int getSoundIndex() {
        return soundIndex;
    }
}

