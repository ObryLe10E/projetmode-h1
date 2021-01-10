package modele;

import java.util.Collections;

public class RepereUtils{
	/**
	 * Permet de centrer la figure
	 */
	public static void center(Repere repere) {
		repere.translation2(-repere.averageX(), -repere.averageY(), -repere.averageZ()); 
	}

	/**
	 * Effectue une translation du modèle en stockant l'offSet de x et y
	 * 
	 * @param ratioX Ratio à ajouter à la coordonée homogène X de chaque point du
	 *               repère
	 * @param ratioY Ratio à ajouter à la coordonée homogène Y de chaque point du
	 *               repère
	 */
	public static void translation(Repere repere, double ratioX, double ratioY) {
		repere.xOffSet += ratioX;
		repere.yOffSet += ratioY;
		repere.translation2(ratioX, ratioY, 0);
		notifyObservers(repere);
	}
	
	/**
	 *  Effectue une translation basique
	 * @param ratioX 
	 * @param ratioY
	 * @param ratioZ
	 */
	public static void translation2(Repere repere, double ratioX, double ratioY, double ratioZ) {
		for (Point p : repere.getPointsList()) 
			p.translation(new Vecteur(ratioX,ratioY,ratioZ));
	}
	
	/**
	 * Effectue une rotation du modèle autour de l'axe des abscisses X
	 * 
	 * @param grad Angle autour duquel calculer la matrice de rotation
	 */	
	public static void rotateX(Repere repere, Double grad) {
		repere.translation2(-repere.xOffSet, -repere.yOffSet, 0.0);
		for (Point p : repere.getPointsList())
			p.rotationX(grad);
		repere.translation2(repere.xOffSet, repere.yOffSet, 0.0);
		notifyObservers(repere);
	}

	/**
	 * Effectue une rotation du modèle autour de l'axe des abscisses Y
	 * 
	 * @param grad Angle autour duquel calculer la matrice de rotation
	 */
	public static void rotateY(Repere repere, Double grad) {
		repere.translation2(-repere.xOffSet, -repere.yOffSet, 0.0);
		for (Point p : repere.getPointsList())
			p.rotationY(grad);
		repere.translation2(repere.xOffSet, repere.yOffSet, 0.0);
		notifyObservers(repere);
	}

	/**
	 * Effectue une rotation du modèle autour de l'axe des abscisses Z
	 * 
	 * @param grad Angle autour duquel calculer la matrice de rotation
	 */
	public static void rotateZ(Repere repere, Double grad) {
		repere.translation2(-repere.xOffSet,-repere.yOffSet , 0.0);
		for (Point p : repere.getPointsList())
			p.rotationZ(grad);
		repere.translation2(repere.xOffSet,repere.yOffSet , 0.0);
		notifyObservers(repere);
	}

	/**
	 * Effectue un zoom sur le modèle
	 * 
	 * @param scale Ratio à partir duquel calculer la matrice d'homothétie
	 */
	public static void scaling(Repere repere, double scale) {
		repere.translation2(-repere.xOffSet, -repere.yOffSet,0);
		for (Point p : repere.getPointsList()) 
			p.homothetie(scale);
		repere.translation2(repere.xOffSet, repere.yOffSet,0);
		notifyObservers(repere);
	}

	/**
	 * Trie les faces du repère selon leur coordonée Z moyenne
	 * voir si on peut utiliser list.sort en implémentant comparable/comparator
	 */
	public static void sortFaces(Repere repere) {
		Collections.sort(repere.getFacesList());
	}
	
	public static void notifyObservers(Repere repere) {
		repere.notifyObservers();
	}
}