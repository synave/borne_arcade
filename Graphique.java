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

    //private final Fenetre f;
    private final FenetrePleinEcran f;
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

	//f = new Fenetre("_Menu Borne D'arcade_",TAILLEX,TAILLEY);
	f = new FenetrePleinEcran("_Menu Borne D'arcade_");
	f.setVisible(true);
	clavier = new ClavierBorneArcade();
	f.addKeyListener(clavier);
	f.getP().addKeyListener(clavier);
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
	f.ajouter(new Ligne(Couleur.NOIR,new Point(670,360), new Point(1250,360)));
	f.ajouter(new Ligne(Couleur.NOIR,new Point(670,190), new Point(1250,190)));
	f.ajouter(bd.getHighscore());
    }

    public void selectionJeu(){
		
	if(bs.selection(clavier)){
	    
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
	}else{
	    Texture fondBlancTransparent = new Texture("./img/blancTransparent.png", new Point(0,0));
	    Rectangle boutonNon = new Rectangle(Couleur.ROUGE, new Point(340, 600), 200, 100, true);
	    Rectangle boutonOui = new Rectangle(Couleur.VERT, new Point(740, 600), 200, 100, true);
	    Texte message = new Texte(Couleur.NOIR, "Voulez vous vraiment quitter ?", new Font("Calibri", Font.TYPE1_FONT, 40), new Point(640, 800));
	    Texte non = new Texte(Couleur.NOIR, "NON", new Font("Calibri", Font.TYPE1_FONT, 20), new Point(440, 650));
	    Texte oui = new Texte(Couleur.NOIR, "OUI", new Font("Calibri", Font.TYPE1_FONT, 20), new Point(840, 650));
	    Rectangle rectSelection = new Rectangle(Couleur.BLEU, new Point(330,590),220,120, true);
	    f.ajouter(fondBlancTransparent);
	    f.ajouter(message);
	    f.ajouter(rectSelection);
	    f.ajouter(boutonNon);
	    f.ajouter(boutonOui);
	    f.ajouter(non);
	    f.ajouter(oui);
	    int selectionSur = 0;
	    
	    boolean sortie=false;
	    while(!sortie){
		try{
		    Thread.sleep(10);
		}catch(Exception e){}
		if(clavier.getJoyJ1DroiteEnfoncee())
		    selectionSur=1;
		if(clavier.getJoyJ1GaucheEnfoncee())
		    selectionSur=0;
		
		if(selectionSur==0){
		    rectSelection.setA(new Point(330,590));
		    rectSelection.setB(new Point(550,710));
		}
		else{
		    rectSelection.setB(new Point(950,710));
		    rectSelection.setA(new Point(730,590));
		    
		}
		if(clavier.getBoutonJ1ATape()){
		    if(selectionSur==0){
			f.supprimer(fondBlancTransparent);
			f.supprimer(message);
			f.supprimer(rectSelection);
			f.supprimer(boutonNon);
			f.supprimer(boutonOui);
			f.supprimer(non);
			f.supprimer(oui);
			sortie=true;
		    }
		    else{
			System.exit(0);
		    }
		}
		f.rafraichir();
	    }
	}
    }
}
