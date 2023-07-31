# Connecta 4
Implementació de l'algorisme minimax per jugar al joc Connecta4

Pràctica de l'assignatura Projecte de Programació on implementem l'algorisme minimax per jugar al joc Connecta4.

CR7: Classe que implementa l'algorisme MiniMax amb poda alfa-beta (alpha-beta prunning).

Per l'heurística, dues opcions:

    -> Taula de ponderació estàtica de les posicions. Aquest valor determina quantes combinacions de 4 fitxes consecutives es poden donar fent ús d’aquesta posició. 

    -> Per cada possible grup de 4 posicions, si es troben només fitxes nostres, sumem el valor 1 per cada fitxa nostra a la heurística. En cas contrari, si només n’hi ha fitxes del contrari, en restem el valor d’1. Quan hi ha fitxes d’ambdós jugadors sumem la diferència de fitxes

Pràctica realitzada per [David Martínez](https://github.com/Davichuelo) i Omar Briqa
