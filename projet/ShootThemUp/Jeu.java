import MG2D.*;
import MG2D.geometrie.*;
import MG2D.Clavier;
import java.util.ArrayList;
import java.util.Random; 
import java.awt.Font;
import java.util.Date;
import java.io.File;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;


class Jeu{

    
    /* Attribut */
    private Joueur jou;
    private Texture fond1;
    private Texture fond2;
    private Rectangle barreNoir;
    private Font font;


    private ArrayList<Ennemi> tabEnn;//Tableau pour les ennemis
    private ArrayList<Tir> tabTirJou;//Tableau pour les tirs ennemis
    private ArrayList<Tir> tabTirEnn;//Tableau pour les tirs joueurs
    private ArrayList<Bonus> tabBonus;//Tableau pour les bonus
    private ArrayList<Texture> tabAnimationIntersection;//Tableau pour les intersections tir joueur/ennemi

    private Fenetre fen;
    //private FenetrePleinEcran fen;
    private ClavierBorneArcade cla;
    private int score;
    //private Texture scoreAffichage[];
    private Texte scoreAffichage;
    private Texture nombreVie[];
    private Texte affichageNombreVieJoueur;
    private Random r;
	

    /* Constructeur */
    public Jeu(){
	font = null;
	try{
	    File in = new File("font.ttf");
	    font = font.createFont(Font.TRUETYPE_FONT, in);
	    font = font.deriveFont(32.0f);
	}catch (Exception e) {
	    System.out.println(e.getMessage());
	}
	
	
	jou = new Joueur(new Texture("./img/player/player1/1.png", new Point(0,360)),3,0,0,0,0,0);
	tabEnn = new ArrayList<Ennemi>();
	tabTirJou = new ArrayList<Tir>();
	tabTirEnn = new ArrayList<Tir>();
	tabBonus = new ArrayList<Bonus>();
	tabAnimationIntersection = new ArrayList<Texture>();

	fen = new Fenetre("JAVA SPACE", 1280, 1024);
	//fen = new Fenetre("JAVA SPACE");

	cla = new ClavierBorneArcade();
	fen.addKeyListener(cla);
	
	fen.setVisible(true);
    
	fen.setAffichageFPS(true);
	fen.setAffichageNbPrimitives(true);
	
	fond1 = new Texture("./img/background/1.png", new Point(0, 0), 1280, 1024);
	fond2 = new Texture("./img/background/2.png", new Point(1280, 0), 1280, 1024);
	
	barreNoir = new Rectangle(Couleur.NOIR, new Point(0,0), 1280, 1024, true);
	fen.ajouter(barreNoir);

	fen.ajouter(fond1);
	fen.ajouter(fond2);
	fen.ajouter(jou.getTex());

	scoreAffichage = new Texte(Couleur.BLANC,"0",new Font("", Font.BOLD, 20), new Point(50,900));
	scoreAffichage.setPolice(font);
	fen.ajouter(scoreAffichage);
	
	/*scoreAffichage=new Texture[9];//Tableau pour l'affichage du score 
	  scoreAffichage[0]=new Texture("./img/score/0.png", new Point(10,691));
	  scoreAffichage[1]=new Texture("./img/score/0.png", new Point(42,691));
	  scoreAffichage[2]=new Texture("./img/score/0.png", new Point(74,691));
	  scoreAffichage[3]=new Texture("./img/score/0.png", new Point(106,691));
	  scoreAffichage[4]=new Texture("./img/score/0.png", new Point(138,691));
	  scoreAffichage[5]=new Texture("./img/score/0.png", new Point(170,691));
	  scoreAffichage[6]=new Texture("./img/score/0.png", new Point(202,691));
	  scoreAffichage[7]=new Texture("./img/score/0.png", new Point(234,691));
	  scoreAffichage[8]=new Texture("./img/score/0.png", new Point(266,691));*/

	/*for(int i=0; i < scoreAffichage.length; i++){
	  fen.ajouter(scoreAffichage[i]);	
	  }*/
	
	Texture hudVaisseau;
	hudVaisseau=new Texture("./img/life/1.png", new Point(1151, 892));
	fen.ajouter(hudVaisseau);//Symbole du vaisseau pour indiquer la vie du joueur
	affichageNombreVieJoueur=new Texte(Couleur.BLANC,"3",new Font("", Font.BOLD, 20), new Point(1210, 900));
	affichageNombreVieJoueur.setPolice(font);
	fen.ajouter(affichageNombreVieJoueur);

	r = new Random();

	fen.rafraichir();
    }


    /* Méthode */
    public void avancerUnPasDeTemps(){
	int randomApparition;
	int randomTirEnn;
	int randomBonus;
	int randomApparitionBonus;
	int randomTrajectoire;
	int randomPositionxyTrajectoire;
	int zoneApparitionEnnemi=0;

	/*------------------------------*/
	/*----------BACKGROUND----------*/
	/*------------------------------*/
	
	/*-----TRANSLATION_DES_FONDS----*/
	if(fond1.getB().getX()<=0){
	    fond1.translater(2560,0);
	}else if(fond2.getB().getX()<=0){
	    fond2.translater(2560,0);
	}
	else{
	    fond1.translater(-1,0);
	    fond2.translater(-1,0);
	}

	/*-------------------------*/
	/*----------BONUS----------*/
	/*-------------------------*/

	/*-----APPARITION_BONUS-----*/
	randomApparitionBonus = r.nextInt(1000)+1;
	if(randomApparitionBonus==1){
	    randomBonus = r.nextInt(4)+1;
	    randomPositionxyTrajectoire = r.nextInt(720)+152;
	    switch(randomBonus)
		{
		case 1: //TIR DOUBLE
		    tabBonus.add(new Bonus(new Texture("./img/bonus/1.png", new Point(1280,randomPositionxyTrajectoire)),1,1000));
		    fen.ajouter(tabBonus.get(tabBonus.size()-1).getTex());
		    break;
		case 2: //TIR TRIPLE
		    tabBonus.add(new Bonus(new Texture("./img/bonus/2.png", new Point(1280,randomPositionxyTrajectoire)),2,1000));
		    fen.ajouter(tabBonus.get(tabBonus.size()-1).getTex());
		    break;
		case 3: //TIR EVENTAIL
		    tabBonus.add(new Bonus(new Texture("./img/bonus/3.png", new Point(1280,randomPositionxyTrajectoire)),3,1000));
		    fen.ajouter(tabBonus.get(tabBonus.size()-1).getTex());
		    break;
		case 4: //VIE SUPPLEMENTAIRE
		    tabBonus.add(new Bonus(new Texture("./img/bonus/4.png", new Point(1280,randomPositionxyTrajectoire)),4,0));
		    fen.ajouter(tabBonus.get(tabBonus.size()-1).getTex());
		    break;

		default: // DEFAUT
		}
	}
	
	/*-----TRANSLATION_BONUS/INTERSECTION_BONUS-----*/
	for(int i=tabBonus.size()-1; i>0; i--){
	    tabBonus.get(i).getTex().translater(-10,0);
	    if(tabBonus.get(i).getTex().getB().getX()<0){//BONUS SORT DE L'ECRAN
		fen.supprimer(tabBonus.get(i).getTex());
		tabBonus.remove(i);
	    }
	    else if(jou.intersection(tabBonus.get(i))==true){//JOUEUR TOUCHE UN BONUS
		if(tabBonus.get(i).getNumBonus()==4){
		    if(jou.getVie()<3){//VIE DU JOUEUR INFERIEUR A TROIS MISE A JOUR DU NOMBRE DE VIE
			jou.setVie(jou.getVie()+1);
			//affichageNombreVieJoueur.setImg("./img/score/"+jou.getVie()+".png");
			affichageNombreVieJoueur.setTexte(""+jou.getVie());
		    }
		}else{//CAS OU LE BONUS CHANGE LE TIR DU JOUEUR
		    jou.setTir(tabBonus.get(i).getNumBonus());
		    jou.setTempBonus(tabBonus.get(i).getDuree());
		}
		fen.supprimer(tabBonus.get(i).getTex());
		tabBonus.remove(i);
	    }
	}


	/*--------------------------*/
	/*----------JOUEUR----------*/
	/*--------------------------*/
	
	/*-----TEMPORISATION_BONUS_DU_JOUEUR-----*/
	if(jou.getTempBonus()>0){//BONUS DE TIR EN COURS ON ENLEVE UN A LA DUREE 
	    jou.setTempBonus(jou.getTempBonus()-1);
	}
	if(jou.getTempBonus()==0 && jou.getTir()>0){
	    jou.setTir(0);
	}

	/*-----DEPLACEMENT_JOUEUR-----*/
	jou.bougerJoueur(cla,0,1280,-152,872,12,12,jou.getTex().getLargeur(),jou.getTex().getHauteur());
	

	/*-----------------------*/
	/*----------TIR----------*/
	/*-----------------------*/

	/*-----EFFACEMENT_ANIMATION_TIR-----*/
	for(int i=tabAnimationIntersection.size()-1; i>=0; i--){//ENELEVE TOUTE LES TEXTURES D'IMPACT 
	    fen.supprimer(tabAnimationIntersection.get(i));
	    tabAnimationIntersection.remove(i);
	}
	
	/*-----TRANSLATION_TIR_ENNEMIE-----*/
	for(int i=tabTirEnn.size()-1; i>=0; i--){
	    switch(tabTirEnn.get(i).getOrientation())//TRANSLATION EN FONCTION DE L'ORIENATION DU TIR
		{
		case "centre"://TIR DROIT
		    tabTirEnn.get(i).getTex().translater(-1*tabTirEnn.get(i).getVit(),0);
		    break;
		case "gauche": //TIR GAUCHE->EVENTAIL
		    tabTirEnn.get(i).getTex().translater(-1*tabTirEnn.get(i).getVit(),2);
		    break;
		case "droite": //TIR DROITE->EVENTAIL
		    tabTirEnn.get(i).getTex().translater(-1*tabTirEnn.get(i).getVit(),-2);
		    break;
		default: // DEFAUT
		    
		}
	    if(tabTirEnn.get(i).getTex().getB().getX()<0){// SUPPRESION SI LE TIR SORT DE L'ECRAN
		fen.supprimer(tabTirEnn.get(i).getTex());
		tabTirEnn.remove(i);
	    }
	}

	/*-----APPARITION_TIR_ENNEMIE-----*/
 	for(int i=tabEnn.size()-1; i>0; i--){
	    randomTirEnn = r.nextInt(100)+1;
	    if(randomTirEnn==1){//RANDOM SI =1 ALORS L'ENNEMIE TIR
		randomTirEnn = r.nextInt(4);//CHOIX DU TIR
		switch(randomTirEnn)
		    {
		    case 1: //TIR DOUBLE
			tabTirEnn.add(new Tir(new Texture("./img/laser/ennemie1/1.png", new Point(tabEnn.get(i).getTex().getB().getX(),tabEnn.get(i).getTex().getB().getY()-23)),1,20,"centre"));
			fen.ajouter(tabTirEnn.get(tabTirEnn.size()-1).getTex());
			tabTirEnn.add(new Tir(new Texture("./img/laser/ennemie1/1.png", new Point(tabEnn.get(i).getTex().getB().getX(),tabEnn.get(i).getTex().getA().getY()+10)),1,20,"centre"));
			fen.ajouter(tabTirEnn.get(tabTirEnn.size()-1).getTex());
			break;
		    case 2: //TIR TRIPLE
			tabTirEnn.add(new Tir(new Texture("./img/laser/ennemie1/1.png", new Point(tabEnn.get(i).getTex().getB().getX(),tabEnn.get(i).getTex().getB().getY()-19)),1,20,"centre"));
			fen.ajouter(tabTirEnn.get(tabTirEnn.size()-1).getTex());
			tabTirEnn.add(new Tir(new Texture("./img/laser/ennemie1/1.png", new Point(tabEnn.get(i).getTex().getB().getX(),tabEnn.get(i).getTex().getA().getY()+10)),1,20,"centre"));
			fen.ajouter(tabTirEnn.get(tabTirEnn.size()-1).getTex());
			tabTirEnn.add(new Tir(new Texture("./img/laser/ennemie1/1.png", new Point(tabEnn.get(i).getTex().getB().getX(),tabEnn.get(i).getTex().getB().getY()-(jou.getTex().getHauteur()/2))),1,20,"centre"));
			tabTirEnn.get(tabTirEnn.size()-1).getTex().translater(0,-tabTirEnn.get(tabTirEnn.size()-1).getTex().getHauteur()/2);
			fen.ajouter(tabTirEnn.get(tabTirEnn.size()-1).getTex());
			break;
		    case 3: //TIR EVENTAIL
			tabTirEnn.add(new Tir(new Texture("./img/laser/ennemie1/1.png", new Point(tabEnn.get(i).getTex().getB().getX(),tabEnn.get(i).getTex().getB().getY()-39)),1,20,"gauche"));
			fen.ajouter(tabTirEnn.get(tabTirEnn.size()-1).getTex());
			tabTirEnn.add(new Tir(new Texture("./img/laser/ennemie1/1.png", new Point(tabEnn.get(i).getTex().getB().getX(),tabEnn.get(i).getTex().getA().getY()+30)),1,20,"droite"));
			fen.ajouter(tabTirEnn.get(tabTirEnn.size()-1).getTex());
			tabTirEnn.add(new Tir(new Texture("./img/laser/ennemie1/1.png", new Point(tabEnn.get(i).getTex().getB().getX(),tabEnn.get(i).getTex().getB().getY()-(jou.getTex().getHauteur()/2))),1,20,"centre"));
			tabTirEnn.get(tabTirEnn.size()-1).getTex().translater(0,-tabTirEnn.get(tabTirEnn.size()-1).getTex().getHauteur()/2);
			fen.ajouter(tabTirEnn.get(tabTirEnn.size()-1).getTex());
			break;

		    default: //TIR PAR DEFAUT
			tabTirEnn.add(new Tir(new Texture("./img/laser/ennemie1/1.png", new Point(tabEnn.get(i).getTex().getB().getX(),tabEnn.get(i).getTex().getB().getY()-(jou.getTex().getHauteur()/2))),1,20,"centre"));
			tabTirEnn.get(tabTirEnn.size()-1).getTex().translater(0,-tabTirEnn.get(tabTirEnn.size()-1).getTex().getHauteur()/2);
			fen.ajouter(tabTirEnn.get(tabTirEnn.size()-1).getTex());
		    }
	    }
	}

	/*-----TRANSLATION_TIR_JOUEUR-----*/
	for(int i=tabTirJou.size()-1; i>=0; i--){
	    switch(tabTirJou.get(i).getOrientation())//TRANSLATION EN FONCTION DE L'o
		{
		case "centre"://TIR DROIT
		    tabTirJou.get(i).getTex().translater(1*tabTirJou.get(i).getVit(),0);
		    break;
		case "gauche": //TIR GAUCHE->EVENTAIL
		    tabTirJou.get(i).getTex().translater(1*tabTirJou.get(i).getVit(),2);
		    break;
		case "droite": //TIR DROITE->EVENTAIL
		    tabTirJou.get(i).getTex().translater(1*tabTirJou.get(i).getVit(),-2);
		    break;
		default: //BONUS PAR DEFAUT
		    tabTirJou.get(i).getTex().translater(1*tabTirJou.get(i).getVit(),0);
		}
	    if(tabTirJou.get(i).getTex().getB().getX()>1280){ 
		fen.supprimer(tabTirJou.get(i).getTex());
		tabTirJou.remove(i);
		if(i==0){
		    i=0;
		}
		else
		    {
			i=i-1;
		    }
	    }
	    else
		{
		    for(int j=tabEnn.size()-1; j>=0; j--){
			if(tabTirJou.get(i).intersection(tabEnn.get(j))==true){//SI UN TIR DU JOUEUR TOUCHE UN ENNEMIE
			    tabAnimationIntersection.add(new Texture("./img/intersection/touchey.png", new Point(tabTirJou.get(i).getTex().getA().getX()+50,tabTirJou.get(i).getTex().getA().getY())));
			    fen.ajouter(tabAnimationIntersection.get(tabAnimationIntersection.size()-1));
			    fen.supprimer(tabEnn.get(j).getTex());
			    fen.supprimer(tabTirJou.get(i).getTex());
			    tabEnn.remove(j);
			    tabTirJou.remove(i);
			    score=score+25;//INCREMENTATION DU SCORE
			    break;
			}
		    }
		}
	}

	/*-----APPARITION_TIR_JOUEUR-----*/
	if(cla.getBoutonJ1AEnfoncee() && jou.getTempTir()==0){
	    switch(jou.getTir())//TIR EN FONCTION DU TIR DEFINI DANS LE JOUEUR
		{
		case 1: //TIR DOUBLE
		    tabTirJou.add(new Tir(new Texture("./img/laser/player1/"+jou.getTir()+".png", new Point(jou.getTex().getB().getX(),jou.getTex().getB().getY()-23)),1,20,"centre"));
		    fen.ajouter(tabTirJou.get(tabTirJou.size()-1).getTex());
		    tabTirJou.add(new Tir(new Texture("./img/laser/player1/"+jou.getTir()+".png", new Point(jou.getTex().getB().getX(),jou.getTex().getA().getY()+10)),1,20,"centre"));
		    fen.ajouter(tabTirJou.get(tabTirJou.size()-1).getTex());
		    jou.setTempTir(10);
		    break;
		case 2: //TIR TRIPLE
		    tabTirJou.add(new Tir(new Texture("./img/laser/player1/"+jou.getTir()+".png", new Point(jou.getTex().getB().getX(),jou.getTex().getB().getY()-19)),1,20,"centre"));
		    fen.ajouter(tabTirJou.get(tabTirJou.size()-1).getTex());
		    tabTirJou.add(new Tir(new Texture("./img/laser/player1/"+jou.getTir()+".png", new Point(jou.getTex().getB().getX(),jou.getTex().getA().getY()+10)),1,20,"centre"));
		    fen.ajouter(tabTirJou.get(tabTirJou.size()-1).getTex());
		    tabTirJou.add(new Tir(new Texture("./img/laser/player1/"+jou.getTir()+".png", new Point(jou.getTex().getB().getX(),jou.getTex().getB().getY()-(jou.getTex().getHauteur()/2))),jou.getTir(),20,"centre"));
		    tabTirJou.get(tabTirJou.size()-1).getTex().translater(0,-tabTirJou.get(tabTirJou.size()-1).getTex().getHauteur()/2);
		    fen.ajouter(tabTirJou.get(tabTirJou.size()-1).getTex());
		    jou.setTempTir(10);
		    break;
		case 3: //TIR EVENTAIL
		    tabTirJou.add(new Tir(new Texture("./img/laser/player1/"+jou.getTir()+".png", new Point(jou.getTex().getB().getX(),jou.getTex().getB().getY()-39)),1,20,"gauche"));
		    fen.ajouter(tabTirJou.get(tabTirJou.size()-1).getTex());
		    tabTirJou.add(new Tir(new Texture("./img/laser/player1/"+jou.getTir()+".png", new Point(jou.getTex().getB().getX(),jou.getTex().getA().getY()+30)),1,20,"droite"));
		    fen.ajouter(tabTirJou.get(tabTirJou.size()-1).getTex());
		    tabTirJou.add(new Tir(new Texture("./img/laser/player1/"+jou.getTir()+".png", new Point(jou.getTex().getB().getX(),jou.getTex().getB().getY()-(jou.getTex().getHauteur()/2))),jou.getTir(),20,"centre"));
		    tabTirJou.get(tabTirJou.size()-1).getTex().translater(0,-tabTirJou.get(tabTirJou.size()-1).getTex().getHauteur()/2);
		    fen.ajouter(tabTirJou.get(tabTirJou.size()-1).getTex());
		    jou.setTempTir(10);
		    break;

		default: //TIR PAR DEFAUT
		    tabTirJou.add(new Tir(new Texture("./img/laser/player1/"+jou.getTir()+".png", new Point(jou.getTex().getB().getX(),jou.getTex().getB().getY()-(jou.getTex().getHauteur()/2))),jou.getTir(),20,"centre"));
		    tabTirJou.get(tabTirJou.size()-1).getTex().translater(0,-tabTirJou.get(tabTirJou.size()-1).getTex().getHauteur()/2);
		    fen.ajouter(tabTirJou.get(tabTirJou.size()-1).getTex());
		    jou.setTempTir(10);
		}
	}
	else if(jou.getTempTir()>0){//DECREMENTATION DU COMPTEUR D'ATTENTE POUR TIRER
	    jou.setTempTir(jou.getTempTir()-1);
	}


	/*---------------------------*/
	/*----------ENNEMIE----------*/
	/*---------------------------*/

	/*-----TRANSLATION_ENNEMIE-----*/
	for(int i=tabEnn.size()-1; i>=0; i--){
	    switch(tabEnn.get(i).getTraj())
		{
		case 1: //Translation en ligne, puis virement à gauche/droite
		    if(tabEnn.get(i).getTex().getA().getX()>500){
			tabEnn.get(i).getTex().translater(-tabEnn.get(i).getVit(),0);
		    }
		    else
			{
			    if(tabEnn.get(i).getTex().getA().getY()>(680/2)){
				tabEnn.get(i).getTex().translater(-tabEnn.get(i).getVit(),+(tabEnn.get(i).getVit())/2);
			    }
			    else
				{
				    tabEnn.get(i).getTex().translater(-tabEnn.get(i).getVit(),-(tabEnn.get(i).getVit())/2);
				}
			}
		    break;
		case 2: //Translation haut->bas
		    tabEnn.get(i).getTex().translater(0,-tabEnn.get(i).getVit());
		    break;
		case 3: //Translation bas->haut
		    tabEnn.get(i).getTex().translater(0,+tabEnn.get(i).getVit());
		    break;

		default: //Translation en ligne par défault
		    tabEnn.get(i).getTex().translater(-tabEnn.get(i).getVit(),0);
		}
	    if(tabEnn.get(i).getTex().getB().getX()<0){//SUPPRESION SI UN ENNEMIE ATTEIND LE BORD GAUCHE DE LECRAN
		fen.supprimer(tabEnn.get(i).getTex());
		tabEnn.remove(i);
	    }
	    else if(tabEnn.get(i).getTex().getB().getY()<0-700 && tabEnn.get(i).getTraj()==2){//SUPPRESION SI UN ENNEMIE VENANT DU HAUT DE L'ECRAN ATTEIND LE BAS DE LECRAN
		fen.supprimer(tabEnn.get(i).getTex());
		tabEnn.remove(i);
	    }
	    else if(tabEnn.get(i).getTex().getA().getY()>720+700 && tabEnn.get(i).getTraj()==3){//SUPPRESION SI UN ENNEMIE VENANT DU BAS DE L'ECRAN ATTEIND LE HAUT DE LECRAN
		fen.supprimer(tabEnn.get(i).getTex());
		tabEnn.remove(i);
	    }	
	    else if(tabEnn.get(i).getTex().getB().getX()>1200){//ZONE D'ATTENTE POUR EVITER DE FAIRE APPARAITRE DES ENNEMIES EN BOUCLE
		zoneApparitionEnnemi=1;
	    }
	}
	
	/*-----APPARITION_ENNEMI-----*/
	randomApparition = r.nextInt(40)+1;
	if(randomApparition==1 && zoneApparitionEnnemi==0){
	    randomTrajectoire = r.nextInt(8);
	    switch(randomTrajectoire)//RANDOM POUR DEFINIR LA TRAJECTOIRE DE(s) ENNEMIE(s)
		{
		case 1: //Apparition en ligne, puis virement à gauche/droite
		    randomPositionxyTrajectoire = r.nextInt((720/2)-94);
		    for(int i=0; i<2; i++){
			tabEnn.add(new Ennemi(new Texture("./img/ennemie/ennemie1/1.png", new Point(1280+i*(84+10),((720/2)+randomPositionxyTrajectoire))),8,1,randomTrajectoire));
			fen.ajouter(tabEnn.get(tabEnn.size()-1).getTex());
			tabEnn.add(new Ennemi(new Texture("./img/ennemie/ennemie1/1.png", new Point(1280+i*(84+10),((720/2)-randomPositionxyTrajectoire)-94)),8,1,randomTrajectoire));
			fen.ajouter(tabEnn.get(tabEnn.size()-1).getTex());
		    }
		    break;
		case 2: //Apparition haut->bas
		    randomPositionxyTrajectoire = r.nextInt((1280/2)-100);
		    for(int i=0; i<4; i++){
			tabEnn.add(new Ennemi(new Texture("./img/ennemie/ennemie1/1.png", new Point(1280-randomPositionxyTrajectoire,720+i*(93+10))),8,1,randomTrajectoire));
			fen.ajouter(tabEnn.get(tabEnn.size()-1).getTex());
		    }
		    break;
		case 3: //Apparition bas->haut
		    randomPositionxyTrajectoire = r.nextInt((1280/2)-100);
		    for(int i=0; i<4; i++){
			tabEnn.add(new Ennemi(new Texture("./img/ennemie/ennemie1/1.png", new Point(1280-randomPositionxyTrajectoire,0-i*(93+10))),8,1,randomTrajectoire));
			fen.ajouter(tabEnn.get(tabEnn.size()-1).getTex());
		    }
		    break;
		default: //Apparition en ligne par défault
		    randomPositionxyTrajectoire = r.nextInt(680-94);
		    tabEnn.add(new Ennemi(new Texture("./img/ennemie/ennemie1/1.png", new Point(1280,randomPositionxyTrajectoire)),8,1,-1));
		    fen.ajouter(tabEnn.get(tabEnn.size()-1).getTex());
		}
	   
	}

	
	/*-------------------------*/
	/*----------SCORE----------*/
	/*-------------------------*/

	/*-----MISE_A_JOUR_AFFICHAGE _SCORE-----*/
	scoreAffichage.setTexte(""+score);
	/*int s=score;
	  int i = 8;
					
	  while(s!=0){				
	  int mod=s%10;
	  s/=10;				
	  scoreAffichage[i].setImg("./img/score/"+mod+".png");
	  i--;
	  }*/


	fen.rafraichir();

    }


    public boolean fin(){//METHODE QUI VERIFIE LES DIFFERENTES COLISIONS DANS LE JEU
	boolean finJeu=false;
	boolean verifIntersection=false;
	Font policeGameOver;
	Texture gameOver;

	if(jou.getTempVie()==0){//SI LE JOUEUR NA PAS ETAIT EN COLLISION RECEMMENT
	    for(int i=tabEnn.size()-1; i>0 ; i--){
		if(jou.intersection(tabEnn.get(i))==true){//COLLISION AVEC UN ENNEMIE
		    jou.setVie(jou.getVie()-1);
		    //affichageNombreVieJoueur.setImg("./img/score/"+jou.getVie()+".png");
		    affichageNombreVieJoueur.setTexte(""+jou.getVie());
		    jou.setTempVie(100);//TIMER ANTI-COLLISION
		    jou.getTex().setImg("./img/life/damage/"+jou.getVie()+".png");
		    break;
		}
	    }
	    
            if(jou.getTempVie() != 100){//SI DEJA COLLISION AVEC ENNEMIE PAS DE VERIFICATION DES TIR ENNEMIE
		for(int i=tabTirEnn.size()-1; i>0 ; i--){
		    if(jou.intersection(tabTirEnn.get(i))==true){//COLLISION AVEC UN TIR ENNEMIE
			fen.supprimer(tabTirEnn.get(i).getTex());
			tabTirEnn.remove(i);
			jou.setVie(jou.getVie()-1);
			//affichageNombreVieJoueur.setImg("./img/score/"+jou.getVie()+".png");
			affichageNombreVieJoueur.setTexte(""+jou.getVie());
			jou.setTempVie(100);//TIMER ANTI-COLLISION
			jou.getTex().setImg("./img/life/damage/"+jou.getVie()+".png");
			break;
		    }
		}
	    }

	    if(jou.getVie()==0){//SI LE JOUEUR N'A PLUS DE VIE
		gameOver = new Texture("./img/background/3.png",new Point(640-89,360+89));
		fen.ajouter(gameOver);
		finJeu=true;
	    
	    }
	}
	else{//DECREMENTATION DU TIMER ANTI-COLLISION
	    jou.setTempVie(jou.getTempVie()-1);
	}
	
	return finJeu;
    } 
	
	
}
