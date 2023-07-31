package edu.epsevg.prop.lab.c4;

/**
 *
 * @author Omar Briqa i David Martinez
 * 
 * Classe que implementa una IA amb l'algorisme minimax que juga al Conecta4
 */

public class CR7 implements Jugador, IAuto {
    
    private String nom;
    
    private boolean Heuristica = false;
    private int jugadesExplorades  = 0;
    private int PROFUNDITAT_MAXIMA = 8;
    private int tornContrari = 0, tornCR7 = 0;
    final static int VICTORIA = 100000, DERROTA = -100000;
    final static int[] files    = {0, 1, 2, 3, 4, 5, 6, 7};
    final static int[] columnes = {0, 1, 2, 3, 4, 5, 6, 7};
    
    final static int[][] taulaPuntuacio = {
                                            {3, 4, 5, 7, 7, 5, 4, 3},
                                            {4, 6, 8,10,10, 8, 6, 4},
                                            {5, 8,11,13,13,11, 8, 5},
                                            {7,10,13,16,16,13,10, 7},
                                            {7,10,13,16,16,13,10, 7},
                                            {5, 8,11,13,13,11, 8, 5},
                                            {4, 6, 8,10,10, 8, 6, 4},
                                            {3, 4, 5, 7, 7, 5, 4, 3} };
    
    /***
     * Constructora de la classe CR7
     * @param prof Profunditat máxima que utilitza l'algorisme MiniMax
     * @param tipusH Heurística implementada, es pot canviar assignant true o false
     */
    public CR7(int prof, boolean tipusH){
        nom = "CR7";
        Heuristica = tipusH;
        jugadesExplorades  = 0;
        PROFUNDITAT_MAXIMA = prof;
    }
    
    @Override
    public int moviment(Tauler t, int color) {
        
        tornCR7 = color; tornContrari = tornCR7 * (-1);

        int columna = miniMax(t);
        System.out.println("Nombre d'estats explorats: " + jugadesExplorades);
                
        return columna;
    }
    
    @Override
    public String nom(){
        return nom;
    }
    
    /***
     * Funció que implementa l'algorisme MiniMax
     * @param T Taulell que representa la partida del Conecta4
     * @return Columna on tirar segons l'alogirsme MiniMax
     */
    private int miniMax(Tauler T){
        
        int cActual = Integer.MIN_VALUE, colTirar = 0;
        int alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;
        
        for(int col : columnes){
            if(T.movpossible(col)){
                Tauler nouT = new Tauler(T);
                nouT.afegeix(col, tornCR7);
                int fH = MinValor(nouT, col, 1, alpha, beta);
                if (fH > cActual){
                    cActual = fH; colTirar = col; 
                }
            }
        }
        
        return colTirar;
    }
    
    /***
     * Funció que retorna la heurística máxima de tots els estats següents possibles a l'estat indicat en el Tauler T
     * @param T Taulell que representa la partida del Conecta4
     * @param colTirada Columna on s'ha tirat l'última fitxa
     * @param prof Profunditat máxima que utilitza l'algorisme MiniMax
     * @param alpha Paràmetre alfa que s'utiliza en la poda alfa-beta
     * @param beta Paràmetre alfa que s'utiliza en la poda alfa-beta
     * @return Heurística máxima de tots els estats següents possibles a l'estat indicat en el Tauler T
     */
    private int MaxValor(Tauler T, int colTirada, int prof, int alpha, int beta){
        
        int cActual = Integer.MIN_VALUE;
        
        if (fiPartida(T, colTirada))
            cActual = costFinalPartida(T, colTirada);
        
        else if (prof == PROFUNDITAT_MAXIMA || !T.espotmoure())
            cActual = fHeuristica(T, Heuristica);
        
        else{
            for(int col : columnes){
                Tauler nouT = new Tauler(T); 
                if(nouT.movpossible(col)){
                    nouT.afegeix(col, tornCR7);
                    int fHMIN = MinValor(nouT, col, prof+1, alpha, beta);
                    cActual = Math.max(cActual, fHMIN); alpha = Math.max(cActual, alpha);
                    if(alpha >= beta) break;
                }
            }
        }
        
        return cActual;
    }
    
    /***
     * Funció que retorna la heurística mínima de tots els estats següents possibles a l'estat indicat en el Tauler T
     * @param T Taulell que representa la partida del Conecta4
     * @param colTirada Columna on s'ha tirat l'última fitxa
     * @param prof Profunditat máxima que utilitza l'algorisme MiniMax
     * @param alpha Paràmetre alfa que s'utiliza en la poda alfa-beta
     * @param beta Paràmetre alfa que s'utiliza en la poda alfa-beta
     * @return Heurística mínima de tots els estats següents possibles a l'estat indicat en el Tauler T
     */
    private int MinValor(Tauler T, int colTirada, int prof, int alpha, int beta){
        
        int cActual = Integer.MAX_VALUE;
                
        if (fiPartida(T, colTirada))
            cActual = costFinalPartida(T, colTirada);
        
        else if (prof == PROFUNDITAT_MAXIMA || !T.espotmoure())
            cActual = fHeuristica(T, Heuristica);
        
        else{
            for(int col : columnes){
                Tauler nouT = new Tauler(T); 
                if(nouT.movpossible(col)){
                    nouT.afegeix(col, tornContrari);
                    int fHMAX = MaxValor(nouT, col, prof+1, alpha, beta);
                    cActual = Math.min(cActual, fHMAX); beta = Math.min(cActual, beta);
                    if(alpha >= beta) break;
                }
            }
        }
        
        return cActual;
    }

    /***
     * Funció que retorna el valor heurístic del taulell T, positiu en cas d'anar guanyant i negatiu en cas contrari.
     * @param T Taulell que representa la partida del Conecta4
     * @param funcioH Heurística implementada, es pot canviar assignant true o false
     * @return Retorna el valor heurístic del taulell T
     */
    private int fHeuristica(Tauler T, boolean funcioH) {
        
        int h = 0;
        
        if(funcioH){
            for(int fil : files){
                for(int col : columnes){
                    if (T.getColor(fil, col) == tornCR7)
                        h += taulaPuntuacio[fil][col];
                    else if (T.getColor(fil, col) == tornContrari)
                        h -= taulaPuntuacio[fil][col];
                }
            }  
        }
        else{
                       
            for(int fil : files){
               for(int i = 0; i <= 4; i++){
                   int puntsFavor = 0, puntsContra = 0;
                   for(int j = 0; j < 4; j++){
                       if(T.getColor(fil, i+j) == tornCR7)
                           puntsFavor++;
                       else if(T.getColor(fil, i+j) == tornContrari)
                           puntsContra++;
                   }
                   if(puntsFavor > 0 && puntsContra == 0) h += puntsFavor;
                   else if(puntsFavor == 0 && puntsContra > 0) h -= puntsFavor;
                   else {
                        h += puntsFavor;
                        h -= puntsContra;
                   }
               }
            }
            
            for(int col : columnes){
               for(int i = 0; i <= 4; i++){
                   int puntsFavor = 0, puntsContra = 0;
                   for(int j = 0; j < 4; j++){
                       if(T.getColor(i+j, col) == tornCR7)
                           puntsFavor++;
                       else if(T.getColor(i+j, col) == tornContrari)
                           puntsContra++;
                   }
                   if(puntsFavor > 0 && puntsContra == 0) h += puntsFavor;
                   else if(puntsFavor == 0 && puntsContra > 0) h -= puntsFavor;
                   else {
                        h += puntsFavor;
                        h -= puntsContra;
                   }
               }
            }
            
            // diagonal sota-esquerra -> centre
            for(int fil = 0; fil < 5; fil++){
                for(int col = 0; col < 5; col++){
                    int puntsFavor = 0, puntsContra = 0;
                    for(int pos = 0; pos < 4; pos++){
                        if(T.getColor(fil+pos, col+pos) == tornCR7)
                           puntsFavor++;
                        else if(T.getColor(fil+pos, col+pos) == tornContrari)
                           puntsContra++;
                    }
                    if(puntsFavor > 0 && puntsContra == 0) h += puntsFavor;
                    else if(puntsFavor == 0 && puntsContra > 0) h -= puntsFavor;
                    else {
                        h += puntsFavor;
                        h -= puntsContra;
                    }
                }
            }
            
            // diagonal sota-dreta -> centre
            for(int fil = 0; fil < 5; fil++){
                for(int col = 7; col > 2; col--){
                    int puntsFavor = 0, puntsContra = 0;
                    for(int pos = 0; pos < 4; pos++){
                        if(T.getColor(fil+pos, col-pos) == tornCR7)
                           puntsFavor++;
                        else if(T.getColor(fil+pos, col-pos) == tornContrari)
                           puntsContra++;
                    }
                    if(puntsFavor > 0 && puntsContra == 0) h += puntsFavor;
                    else if(puntsFavor == 0 && puntsContra > 0) h -= puntsFavor;
                    else {
                        h += puntsFavor;
                        h -= puntsContra;
                    }
                }
            }
            
        }
        
        jugadesExplorades = jugadesExplorades + 1;
        return h;
    }
      
    /***
     * Funció que determina si hi ha un guanyador a la partida
     * @param T Taulell que representa la partida del Conecta4
     * @param colTirada Columna on s'ha tirat l'última fitxa
     * @return Retorna cert en cas que hagi guanyat un jugador
     */
    private boolean fiPartida(Tauler T, int colTirada){
        return (T.solucio(colTirada, tornCR7) || T.solucio(colTirada, tornContrari));
    }
    
    /***
     * Funció que retorna un valor heurístic pels estats de victoria o derrota
     * @param T Taulell que representa la partida del Conecta4
     * @param colTirada Columna on s'ha tirat l'última fitxa
     * @return Cost final de la partida
     */
    private int costFinalPartida(Tauler T, int colTirada){
        return (T.solucio(colTirada, tornCR7) ? VICTORIA : DERROTA);
    }
    
}