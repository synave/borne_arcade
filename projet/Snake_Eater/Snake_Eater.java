import MG2D.Fenetre;
import MG2D.Clavier;
import MG2D.geometrie.*;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Font;

public class Snake_Eater {

    
    // Attributs //

    final static int largeur = 1280;
    final static int hauteur = 1024;
    static Fenetre f = new Fenetre ( "Snake Eater | 0 pomme",largeur, hauteur);
    //static FenetrePleinEcran f = new Fenetre ( "Snake Eater | 0 pomme");

    private static ClavierBorneArcade clavier;
    // Géometrie //
    private static Texture background = new Texture ( "img/background.jpg", new Point ( 0, 0 ), 1280 , 1024 );
    private static Point a = new Point ( 400, 300 );
    private static Carre joueur = new Carre ( new Couleur ( 255, 200, 200 ), a, 10,true );
    // Game Over & Statistique //
    private static Font calibri = new Font("Calibri", Font.TYPE1_FONT, 40);
    private static Rectangle haut = new Rectangle(Couleur.NOIR, new Point(0,0), 1280, 150, true);
    private static Rectangle gauche = new Rectangle(Couleur.NOIR, new Point(0,0), 150, 1024, true);
    private static Rectangle droite = new Rectangle(Couleur.NOIR, new Point(1130,0), 150, 1024, true);
    private static Rectangle bas = new Rectangle(Couleur.NOIR, new Point(0,874), 1280, 150, true);
    private static Texte gameover = new Texte (
					       Couleur.ROUGE,
					       new String ("Game Over !"),
					       calibri,
					       f.getMilieu()
					       );

    private static Texte statistique = new Texte (
						  Couleur.ROUGE,
						  new String ("Vous avez mangé 0 pomme."),
						  calibri,
						  new Point ( f.getMilieu().getX(), f.getMilieu().getY() + 50 )
						  );

    private static Texte commentaire = new Texte (
						  Couleur.ROUGE,
						  new String (" "),
						  calibri,
						  new Point ( f.getMilieu().getX(), f.getMilieu().getY() + 100 )
						  );

    // Main //
    public static void main ( String [] args ) {

	f.setVisible(true);
  	clavier = new ClavierBorneArcade();
  	f.addKeyListener ( clavier );
  	
  	f.setBackground ( Couleur.NOIR );
  	f.ajouter ( background );
	f.ajouter( haut );
	f.ajouter( gauche );
	f.ajouter( droite );
	f.ajouter( bas );
  	Serpent s = new Serpent ( f, joueur );
  	Nourriture n = new Nourriture ( f );
  	int vitesse = 60;
  	int compteur = 0;
  	while ( s.getJouer() ) {
	    try {
		Thread.sleep ( vitesse );
	    }
	    catch ( Exception e ) {
		System.out.println ( e );
	    }
	    s.mouvement ( clavier );
	    s.intersection ( n.getPomme() );
	    n.jeu();
	    if ( vitesse > 50 && ( s.getNb() - s.getNb() % 10 ) != compteur ) {
    		vitesse -= 5;
    		compteur = s.getNb() - s.getNb() % 10;
	    }
	    if ( s.getNb() < 2 )
    		f.setTitle ("Snake Eater | " + s.getNb() + " pomme");

	    else
		f.setTitle ("Snake Eater | " + s.getNb() + " pommes");

	    f.rafraichir();
	}
	f.supprimer ( background );
	s.effacer();
	n.effacer();
	f.rafraichir();
	if ( s.getNb() < 2 )
	    statistique.setTexte ( "Vous avez mangé " + s.getNb() + " pomme.");
	else
	    statistique.setTexte ( "Vous avez mangé " + s.getNb() + " pommes.");
	f.ajouter ( gameover );
	f.ajouter ( statistique );
	if ( s.getNb() == 0 )
	    commentaire.setTexte ( "Sérieusement ?!");

	if ( s.getNb() == 1 )
	    commentaire.setTexte ( "Snake looser ;) !" );

	if ( s.getNb() >= 2 )
	    commentaire.setTexte ( "Pas mal, jeune Snakewan." );

	if ( s.getNb() >= 10 )
	    commentaire.setTexte ( "Tu veux qu'on se tire l'oreille ?" );

	if ( s.getNb() >= 50 )
	    commentaire.setTexte ( "Snake, tu va mourir !" );

	f.ajouter ( commentaire );
	try {
	    Thread.sleep(3000);
	    System.exit(5);
	}catch(Exception e){e.getMessage();};
    }
    
    
}
