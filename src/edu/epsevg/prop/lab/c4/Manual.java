package edu.epsevg.prop.lab.c4;

/**
 * Jugador Manual (sobre la UI)
 * @author Profe
 */

public class Manual implements Jugador{

    @Override
    public int moviment(Tauler t, int color) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String nom() {
        return "Manual";
    }
    
}