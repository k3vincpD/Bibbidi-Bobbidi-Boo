package logica;

/**
 * Especialización de Fantasma que tras llegar a su esquina alterna
 * entre un movimiento aleatorio y uno que persigue al jugador, dicho
 * intervalo es de 7 segundos
 */
public class Inky extends Fantasma {
    private int contador2;

    public Inky(int velocidad, Laberinto mapa, Jugador jugador) {
        super(velocidad, mapa, jugador, 2);
        this.contador2 = 0;
        this.spawnColumna = laberinto.getLinkyPuntoCol();
        this.spawnFila = laberinto.getLinkyPuntoFil();
    }


    @Override
    public void moverPorPersonalidad() {
        if (!puedeTeletransportarse()) {
            if (estaAlineado() && this.contador2 < 25) {
                direcciónAnterior = dirección;
                dirección = obtenerDirecciónRandom(direcciónAnterior);
                this.contador2++;
            } else if (estaAlineado() && contador2 >= 25) {
                direcciónAnterior = dirección;
                dirección = obtenerMejorDireccion(jugador.obtenerColumnaLaberinto(), jugador.obtenerFilaLaberinto());
                this.contador2++;
            }
            if (this.contador2 == 60) {
                this.contador2 = 0;
            }
        }
        mover(dirección, velocidad);
    }
}


