package presentation;

import logic.Key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyDetector implements KeyListener {
    public Key key;

    @Override
    public void keyTyped(KeyEvent e) {
        int code = e.getKeyChar();
        if (code == Key.DOWN.getCode()) {
            key = Key.DOWN;
        } else if (code == Key.UP.getCode()) {
            key = Key.UP;
        } else if (code == Key.LEFT.getCode()) {
            key = Key.LEFT;
        } else if (code == Key.RIGHT.getCode()) {
            key = Key.RIGHT;
        } else if (code == Key.PAUSE.getCode()) {
            key = Key.PAUSE;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP -> key = Key.UP;
            case KeyEvent.VK_DOWN -> key = Key.DOWN;
            case KeyEvent.VK_LEFT -> key = Key.LEFT;
            case KeyEvent.VK_RIGHT -> key = Key.RIGHT;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}