Ensemble de jeux open source et libre de droit exécutable depuis un menu type Borne d'arcade.

Procédure à suivre pour la création de votre borne d'arcade. Contrainte matérielle :
- raspberry pi model 3 de préférence
- écran 4:3 de résolution 1280x1024
- Pour borne 2 joueurs, joystick et 6 boutons par joueur + d'autres boutons inutilisés pour le moment.

I - Création physique de la borne
Créez votre borne d'arcade en prenant soin lors du raccord des joysticks et boutons sur votre encodeur clavier que :
- le joystick du joueur 1 envoie les touches flèches haut/bas/gauche/droite
- le joystick du joueur 2 envoie les touches o/l/k/m
- les boutons du joueur 1 envoient les touches f/g/h (boutons du bas) et r/t/y (boutons du haut)
- les boutons du joueur 2 envoient les touches q/s/d (boutons du bas) et a/z/e (boutons du haut)

Si vous ne les avez pas raccordé correctement, pas de panique, on palliera ce problème après.

Les boutons sont nommés A B C pour les boutons du bas et X Y Z pour les boutons du haut.

II - Installation du système d'exploitation
Installez raspbian sur votre raspberry

III - Installation des outils
Installez le jdk de java. Dans un terminal :
> sudo apt-get update
> sudo apt-get install openjdk-8-jdk

Installez git. Toujours dans le même terminal
> sudo apt-get install git

Créez un répertoire git :
> cd ~
> mkdir git
> cd git

On va ensuite télécharger la bibliothèque MG2D et la partie logicielle ici présente. Si vous l'avez déjà téléchargée, vous déplacerez le répertoire dans le répertoire git précédemment créé. Le répertoire doit s'appeler ***borne_arcade***
> git clone https://github.com/synave/MG2D.git
> git clone https://github.com/synave/borne_arcade.git

IV - SEULEMENT SI les boutons ne sont pas raccordés correctement sur els bonnes touches de l'encodeur clavier
Il vous faudra créer une nouveau fichier de configuration des touches de votre clavier.Vous pouvez prendre exemple sur le fichier qui s'appelle ***borne*** qui se trouve dans ce répertoire. Ce fichier sera à placer dans le répertoire
***/usr/share/X11/xkb/symbols/***
> sudo mv borne /usr/share/X11/xkb/symbols/

Modifiez ensuite le fichier de configuration du clavier par défaut.
> sudo nano /etc/default/keyboard
Modifiez la ligne
XKBLAYOUT="fr" (ou autre chose comme "us")
par
XKBLAYOUT="borne"

Enregistrez, quittez.

V - Lancez la logiciel au démarrage de la borne
> mv borne.desktop ~/.config/autostart/

Redémarrez et normalement, lors du démarrage, un terminal va s'ouvrir et quelques secondes (10-15 secondes), l'interface de la borne va se lancer. Des informations concernant les opérations en cours sont affichées dans le terminal. SOyez patient.

Sélectionnez le jeu avec haut/bas du jostick du joueur 1 et lancez le jeu avec le bouton A du joueur 1.
Quittez le logiciel avec le bouton Z du joueur 1. Une demande de confirmation s'affichera. Validez oui ou non avec le bouton A du joueur 1.

Si vous quittez le menu, vous reviendrez sur le terminal. Attendez 30 secondes pour une extinction totale de la machine.