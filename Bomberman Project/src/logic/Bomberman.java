package logic;

public class Bomberman implements Cloneable {
    private Area[] areas;

    public Bomberman(Area... areas) {
        this.areas = areas;
        linkAreas();
    }

    private void linkAreas() {
        if (areas.length == 1) {
            return;
        }
        for (int i = 0; i < areas.length; i++) {
            if (i + 1 < areas.length) {
                areas[i].setNextArea(areas[i + 1]);
            }
        }
    }

    public Area getArea(int position) {
        if (position < 0 || position >= areas.length) {
            return null;
        }
        return areas[position];
    }

    public void resetAreas() {
        Area[] newAreas = new Area[areas.length];
        for (int i = 0; i < areas.length; i++) {
            Area area = areas[i];
            newAreas[i] = new Area(area.getDifficulty(), area.getMapDirection());
        }
        areas = newAreas;
    }

    public Area[] getAreas() {
        return this.areas;
    }

}

