# PlyReader

PlyReader est une application Java de modélisation et de manipulation d'objets 3D.

## Lancement

Exécuter l'archive appliH1.jar, ou dans un terminal exécuter la commande

```bash
java -jar appliH1.jar
```

## Utilisation

### Choix de bibliothèque
L'application se lance avec une bibliothèque de fichiers .ply par défaut, mais il est possible d'en ouvrir une personnelle depuis l'application. Il suffit d'ouvrir le menu et de sélectionner les fichiers voulus. Ils s'affichent alors sur la gauche de l'écran, en vert s'ils sont valides et utilisables par l'application, en rouge s'il sont d'une quelconque façon invalides à la lecture.

### Affichage d'un objet 3D
Il suffit de cliquer sur le modèle voulu dans la bibliothèque, s'il erreur il y a, elle sera affichée dans une fenêtre modale à l'originale (nécessaire de la fermer avant de continuer).
L'objet s'affiche alors au centre de l'écran.

### Manipulation d'un objet 3D
- Il est possible de déplacer l'objet sur l'écran en utilisant les boutons ↑↓→← sur la droite de l'écran.
- Le zoom et le dézoom sur l'objet se font avec la molette de la souris.
- Les rotations de l'objet peuvent se faire soit avec les sliders présents sur la droite, soit avec les touches X, Y et Z du clavier, qui effectuent respectivement une rotation d'un angle fixe sur les axes horizontaux, verticaux et en profondeur.

## Auteurs et contributions
### Groupe H1 : DUHEM Alexis, DELOBEL Jeremy, OBRY Thomas, BONNET Tanguy
- Alexis : interfaces, FXML et Controllers Java
- Jeremy : lecture des fichiers, gestion des erreurs, documentation Java
- Thomas : lecture des fichiers, gestion des erreurs, documentation Java, tests unitaires
- Tanguy : partie interactive, ouverture de fichiers, calculs matriciels centrés sur l'objet

De plus, chacun a travaillé sur une partie des calculs matriciels et la manipulation des objets 3D (translations, rotations, homothéties, zooms...).

Nous avons choisi de travailler en pair programming à cause de la distance : l'un travaillait directement sur le code en partageant son écran, et l'autre travaillait à partir du partage d'écran, faisait des recherches, aidait à la conception... Ce qui peut expliquer le faible nombre de commits et la contribution d'apparence faible de Jeremy et Tanguy, car dans notre cas c'était Thomas et Alexis qui partageaient leur écran, et donc qui effectuaient la plupart des commits.

Cependant chacun a autant contribué à l'avancement du projet, qui a débuté assez tard, car Thomas et Alexis faisaient face à des problèmes liés à Gitlab, il nous a donc fallu tout reconfigurer sur Github, et nous avons également eu des problèmes de configuration de Maven, notamment dûs à la distance.