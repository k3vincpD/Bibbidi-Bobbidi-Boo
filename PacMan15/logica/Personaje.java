package logica;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Clase que define atributos y comportamientos comunes tanto del jugador como de los fantasmas.
 * Contiene métodos que permiten modelar el movimiento de sus diversas especializaciones. Así mismo
 * permite su ejecución dentro de un Thread
 */
public abstract class Personaje implements Runnable, Serializable {
    private List<Integer> valoresBloque = Arrays.asList(1, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27);
    protected int posiciónX, posiciónY, velocidad, tiempoDeEspera, contador;

    protected Laberinto laberinto;
    protected Tecla dirección;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    protected String direcciónImagen;
    public boolean hilo = true;
    protected boolean estáEsperando = true;
    protected boolean pausado = false;


    @Override
    public void run() {
        while (hilo) {
            if (!pausado) {
                actualizar();
                verificarTiempo();
            } else {
                estaPausado();
                verificarTiempo();
            }
        }
    }

    public static void verificarTiempo() {
        double drawinterval = 1000000000 / Nivel.getFPS(); //60 frames por seundo
        double nextDrawTime = System.nanoTime() + drawinterval; //intervalo de sistema en nanosegundos
        try {
            double tiempoRestante = (nextDrawTime - System.nanoTime()) / 1000000;
            if (tiempoRestante < 0) {
                tiempoRestante = 0;
            }
            Thread.sleep((long) tiempoRestante);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract boolean estaPausado();

    public abstract void actualizar();

    /**
     * Devuelve un boolean que define si el personaje colisiona con un muro del
     * laberinto
     *
     * @return
     */
    public boolean estaColisionando(Tecla tecla) {
        switch (tecla) {
            case ARRIBA:
                return valoresBloque.contains(laberinto.getValorMapa((posiciónY-velocidad)/24, obtenerColumnaLaberinto()));
            case ABAJO:
                return valoresBloque.contains(laberinto.getValorMapa(obtenerFilaLaberinto() + 1, obtenerColumnaLaberinto()));
            case IZQUIERDA:
                return valoresBloque.contains(laberinto.getValorMapa(obtenerFilaLaberinto(), (posiciónX-velocidad)/24));
            case DERECHA:
                return valoresBloque.contains(laberinto.getValorMapa(obtenerFilaLaberinto(), obtenerColumnaLaberinto() + 1));
            default:
                return false;
        }
    }

    /**
     * Devuelve true si el cambio de dirección de la entidad no entra en conflicto con la alineación
     * de los bloques (cambio de dirección que descuadra a la entidad en relación con los bloques)
     * o con un muro, si alguno no se cumple retorna false
     */
    public boolean sePuedeMover(Tecla teclaNueva) {
        if (!estaAlineado()) {
            return false;
        }
        return !estaColisionando(teclaNueva);
    }

    public boolean esDireccionContraria(Tecla teclaActual, Tecla teclaNueva) {
        return teclaActual == Tecla.ABAJO && teclaNueva == Tecla.ARRIBA ||
                teclaActual == Tecla.ARRIBA && teclaNueva == Tecla.ABAJO ||
                teclaActual == Tecla.DERECHA && teclaNueva == Tecla.IZQUIERDA ||
                teclaActual == Tecla.IZQUIERDA && teclaNueva == Tecla.DERECHA;
    }

    /**
     * Mueve al personaje en el laberinto, si colisiona el personaje mantiene su posición
     *
     * @param tecla     : Dirección del movimiento
     * @param velocidad : Magnitud del desplazamiento del personaje (debe ser un multiplo del tamaño de entidades
     *                  manejado en un nivel)
     */
    public void mover(Tecla tecla, int velocidad) {
        if (Objects.equals(tecla, Tecla.ARRIBA) && !estaColisionando(tecla)) {
            direcciónImagen = "arriba";
            posiciónY -= velocidad;
        } else if (Objects.equals(tecla, Tecla.ABAJO) && !estaColisionando(tecla)) {
            direcciónImagen = "abajo";
            posiciónY += velocidad;

        } else if (Objects.equals(tecla, Tecla.DERECHA) && !estaColisionando(tecla)) {
            direcciónImagen = "derecha";
            posiciónX += velocidad;

        } else if (Objects.equals(tecla, Tecla.IZQUIERDA) && !estaColisionando(tecla)) {
            direcciónImagen = "izquierda";
            posiciónX -= velocidad;
        }
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }

    public boolean estaAlineado() {
        return posiciónX % Nivel.getTamañoEntidades() == 0
                && posiciónY % Nivel.getTamañoEntidades() == 0;
    }

    /**
     * Devuelve un boolean que define si el personaje se encuentra en un teletransporte, en tal caso,
     * cambia su posición al otro lado de este
     *
     * @return
     */
    public boolean puedeTeletransportarse() {
        if (laberinto.getValorMapa(obtenerFilaLaberinto(), (posiciónX + 22) / 24) == 6) {
            posiciónX = laberinto.getTpDerechoCol() * 24 - 12;
            posiciónY = laberinto.getTpDerechoFil() * 24;
            return true;
        }
        if (laberinto.getValorMapa(obtenerFilaLaberinto(), obtenerColumnaLaberinto()) == 5) {
            this.posiciónX = laberinto.getTpIzquierdoCol() * 24 + 12;
            this.posiciónY = laberinto.getTpIzquierdoFil() * 24;
            return true;
        }
        return false;
    }

    public boolean seAcabóElTiempo(int tiempoDeEspera) {
        contador++;
        if (contador == tiempoDeEspera) {
            estáEsperando = false;
            contador = 0;
            return true;
        }
        return false;
    }

    public int obtenerColumnaLaberinto() {
        return posiciónX / Nivel.getTamañoEntidades();
    }

    public int obtenerFilaLaberinto() {
        return posiciónY / Nivel.getTamañoEntidades();
    }

    public int calcularTiempo(int segundos) {
        return Nivel.getFPS() * segundos;
    }

    public int getPosiciónX() {
        return this.posiciónX;
    }

    public int getPosiciónY() {
        return this.posiciónY;
    }

    public String getDirecciónImagen() {
        return direcciónImagen;
    }

    public Laberinto getLaberinto() {
        return laberinto;
    }

    public boolean isEstáEsperando() {
        return estáEsperando;
    }

    public boolean isPausado() {
        return pausado;
    }
}

