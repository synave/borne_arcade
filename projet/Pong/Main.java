
public class Main {
    



    public static void main ( String [] args ){
	//à régler en fonction de l'ordinateur utilisé
	int vitesse = 10;
	Pong p = new Pong();
	
	while ( true ) { 
	    try {
		Thread.sleep ( vitesse );
	    }		
	    catch ( Exception e ) {		
		System.out.println ( e );
	    }
	    if(Pong.nbRebond == 1){
		if(vitesse > 5){
		    vitesse = vitesse - 1;
		}
	    }
	    if(!Pong.demarrer){
		vitesse = 10;
	    }
	    p.maj();
	}
	
    }//main




}//class Main
