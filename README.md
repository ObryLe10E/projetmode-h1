# PlyReader

PlyReader est une application Java de modélisation et de manipulation d'objets 3D.

## Lancement

Exécuter l'archive appliH1.jar, ou dans un terminal exécuter la commande

```bash
java -jar appliH1.jar
```

## Utilisation

### Choix de bibliothèque
Cliquer sur Fichier > Open. Sélectionner les fichiers voulus. Ils s'affichent alors sur la gauche de l'écran, en vert s'ils sont valides et utilisables par l'application, en rouge s'il sont d'une quelconque façon invalides à la lecture.

**Note** : L'application se lance d'ordinaire avec une bibliothèque par défaut (mise en commentaire pour permettre à l'archive Jar de s'exécuter sans erreur), cependant l'archive Jar ne permettait pas d'inclure dans notre code des chemins, relatifs ou non. Le lancement via l'archive Jar ne permet donc pas d'inclure de bibliothèque par défaut. Il suffit de décommenter les lignes 93 et 94 de MainViewController et lancer le projet via Maven pour l'avoir.

### Affichage d'un objet 3D
Il suffit de cliquer sur le modèle voulu dans la bibliothèque, si erreur il y a, elle sera affichée dans une fenêtre modale à l'originale (nécessaire de la fermer avant de continuer).
L'objet s'affiche alors au centre de l'écran.

### Manipulation d'un objet 3D
- Il est possible de déplacer l'objet sur l'écran en utilisant les boutons ↑↓→← sur la droite de l'écran, ou glissant-déposant avec le clic gauche de la souris.
- Le zoom et le dézoom sur l'objet se font avec la molette de la souris.
- Les rotations de l'objet peuvent se faire soit avec les sliders présents sur la droite, soit avec les touches X, Y et Z du clavier, qui effectuent respectivement une rotation d'un angle fixe sur les axes horizontaux, verticaux et en profondeur, soit en glissant-déposant avec le clic droit de la souris dans la direction voulue.
- La source de lumière peut être déplacée depuis la gauche vers la droite grâce au slider "Rotation de la lumière".

### Paramètres
- Il est possible de désactiver ou réactiver la source de lumière grâce au bouton 💡
- Le bouton ❑ permet quant à lui de désactiver l'ombre du modèle.
- Les boutons □ et ▰ permettent respectivement d'afficher le modèle en mode "fil de fer" (seulement les arêtes) ou d'afficher/effacer les arêtes
- Le bouton + permet de dupliquer le modèle et de l'afficher dans une seconde fenêtre dépendante de la principale (c'est-à-dire qui tournera et se déplacera comme la principale), et où les 4 options précédentes sont possibles.
- Le bouton ? affiche une fenêtre d'aide et le ℹ une fenêtre d'informations sur l'application.

## Auteurs et contributions
### Groupe H1 : DUHEM Alexis, DELOBEL Jeremy, OBRY Thomas, BONNET Tanguy
- Alexis : interfaces, FXML et Contrôleurs Java, tests unitaires et documentation
- Jeremy : lecture des fichiers, gestion des erreurs, éclairage et ombrage
- Thomas : lecture des fichiers, gestion des erreurs, architecture du code (MVC, clean code etc...)
- Tanguy : tests unitaires et centrages de la figure

De plus, chacun a travaillé sur une partie des calculs matriciels et la manipulation des objets 3D (translations, rotations, homothéties, zooms...).

Pour ce deuxième rendu, chacun a travaillé sur une partie spécifique du projet et des pré-requis au livrable:

- Alexis a continué de travailler sur la partie JavaFX, les contrôleurs et l'IHM et les vues en général. Il a également écrit la documentation et réalisé une partie des tests unitaires.

- Pour le premier rendu, le projet fonctionnait sans matrices, cependant pour implémenter les fonctionnalités de ce 2nd livrable, leur utilisation s'imposait, Jeremy et Thomas se sont donc occupés de réimplémenter la partie modèle en utilisant des matrices et les calculs vectoriels... Thomas s'est ensuite chargé de convertir tout le projet en modèle MVC et Jeremy a implémenté la coloration, l'éclairage et l'ombrage du modèle. Par manque de temps il n'a pas pu ajouter le lissage.

- Tanguy a réalisé une bonne partie des tests unitaires et le centrage de la figure au milieu du panel d'affichage. Il a également effectué la transition d'un Group à un Canvas, qui optimisait les performances, qui seraient quasi-nulles en cumulant l'ombrage, l'éclairage etc...

L'application dans sa globalité est selon nous plutôt satisfaisante étant données les conditions dans laquelle elle a été développée, le seul soucis que nous notons est que les performances sont réduites si le modèle ouvert est volumineux, et nous n'avons pas trouvé de solution à ce problème. 

En dépit de cela le travail au sein de l'équipe a été très fluide, les informations circulaient bien et nous n'avons rencontré aucun problème majeur lié à l'organisation ou le groupe en lui-même.