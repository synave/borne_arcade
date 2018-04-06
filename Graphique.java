import java.awt.Font;
import java.io.IOException;
import java.nio.file.*;
import javax.swing.*;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;


import MG2D.geometrie.*;
import MG2D.*;

public class Graphique {

    private final Fenetre f;
    //private final FenetrePleinEcran f;
    private int TAILLEX;
    private int TAILLEY;
    private int i;
    private ClavierBorneArcade clavier;
    private BoiteSelection bs;
    private BoiteImage bi;
    private BoiteDescription bd;
    public static Bouton[] tableau;
    private Pointeur pointeur;
    Font font;
    Font fontSelect;



    public Graphique(){

	TAILLEX = 1280;
	TAILLEY = 1024;

	font = null;
	try{
	    File in = new File("fonts/font.ttf");
	    font = font.createFont(Font.TRUETYPE_FONT, in);
	    font = font.deriveFont(32.0f);
	}catch (Exception e) {
	    System.err.println(e.getMessage());
	}

	f = new Fenetre("_Menu Borne D'arcade_",TAILLEX,TAILLEY);
	//f = new FenetrePleinEcran("_Menu Borne D'arcade_");
	f.setVisible(true);
	clavier = new ClavierBorneArcade();
	f.addKeyListener(clavier);
	tableau = new Bouton[5];
	Bouton.remplirBouton();
	pointeur = new Pointeur();
	bs = new BoiteSelection(new Rectangle(Couleur .GRIS_CLAIR, new Point(0, 0), new Point(640, TAILLEY), true), pointeur);
	f.ajouter(bs.getRectangle());
	//System.out.println(tableau[pointeur.getValue()].getChemin());
	bi = new BoiteImage(new Rectangle(Couleur .GRIS_FONCE, new Point(640, 512), new Point(TAILLEX, TAILLEY), true), new String(tableau[pointeur.getValue()].getChemin()));
	f.ajouter(bi.getRectangle());
	bd = new BoiteDescription(new Rectangle(Couleur .GRIS, new Point(640, 0), new Point(TAILLEX, 512), true));
	bd.lireFichier(tableau[pointeur.getValue()].getChemin());
	//f.ajouter(bd.getRectangle());

	Texture fond = new Texture("img/fond3.png", new Point(0, 0), TAILLEX, TAILLEY);
	f.ajouter(fond);
	//ajout apres fond car bug graphique sinon
	f.ajouter(bi.getImage());
	for(int i = 0 ; i < bd.getMessage().length ; i++){
	    f.ajouter(bd.getMessage()[i]);
	}
	//f.ajouter(bd.getMessage());
	f.ajouter(pointeur.getTriangleGauche());
	//f.ajouter(pointeur.getTriangleDroite());
	for(int i = 0 ; i < tableau.length ; i++){
	    f.ajouter(tableau[i].getTexture());
	}
	f.ajouter(pointeur.getRectangleCentre());
	for(int i = 0 ; i < tableau.length ; i++){
	    f.ajouter(tableau[i].getTexte());
	    tableau[i].getTexte().setPolice(font);
	}
	//add texture
	for(int i = 0 ; i < bd.getBouton().length ; i++){
	    f.ajouter(bd.getBouton()[i]);
	}
	f.ajouter(bd.getJoystick());
	//add texte
	for(int i = 0 ; i < bd.gettBouton().length ; i++){
	    f.ajouter(bd.gettBouton()[i]);
	}
	f.ajouter(bd.gettJoystick());
    }

    public void selectionJeu(){
		
	bs.selection(clavier);

	bi.setImage(tableau[pointeur.getValue()].getChemin());

	fontSelect = null;
	try{
	    File in = new File("fonts/font.ttf");
	    fontSelect = fontSelect.createFont(Font.TRUETYPE_FONT, in);
	    fontSelect = fontSelect.deriveFont(48.0f);
	}catch (Exception e) {
	    System.err.println(e.getMessage());
	}

	if(!tableau[pointeur.getValue()].getTexte().getPolice().equals(fontSelect)){
	    tableau[pointeur.getValue()].getTexte().setPolice(fontSelect);
	}
		
			
		
	//clignotement texte selectionne
		
		
			
	i=0;
	while(i<1){
	    try {
		Thread.sleep(100);
		f.supprimer(tableau[pointeur.getValue()].getTexte());

		Thread.sleep(250);
		f.ajouter(tableau[pointeur.getValue()].getTexte());

	    }
	    catch (Exception e) {
		System.err.println(e.getMessage());
	    }
	    i++;
	}
		

	tableau[pointeur.getValue()].getTexte().setPolice(font);

	bd.lireFichier(tableau[pointeur.getValue()].getChemin());
	bd.lireBouton(tableau[pointeur.getValue()].getChemin());
	//System.out.println(tableau[pointeur.getValue()].getChemin());
	//bd.setMessage(tableau[pointeur.getValue()].getNom());
		
	pointeur.lancerJeu(clavier);

	f.rafraichir();
    }
}
