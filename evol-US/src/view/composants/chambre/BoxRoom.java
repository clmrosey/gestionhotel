package view.composants.chambre;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import model.beans.Chambre1;
import model.beans.EtatChambre;
import view.ViewInterfaceConstante;
import view.composants.popup.Popup;

public class BoxRoom extends VBox {
	
	private Chambre1 chambre;
	private static String url = "/ressources/img/occupe.png";

	public BoxRoom() {
		// TODO Auto-generated constructor stub
	}

	public BoxRoom(double spacing) {
		super(spacing);
	}
	
	public BoxRoom(Chambre1 chambre) {
		this(5.0);
		this.chambre = chambre;
		this.getStyleClass().add("box-room-shadow");
		if (chambre.getEtat() == EtatChambre.LIBRE) {
			this.setStyle("-fx-background-color: #4CAF50;");
		}else {
			this.setStyle("-fx-background-color: #F25333;");
		}
		
		Image img = new Image(url);
		ImageView imageView = new ImageView(img);
		Label roomLabel = new Label("Chambre N° : "+chambre.getIdChambre());
		Label fumeur = new Label("Fumeur : NON");
		Label baignoire = new Label("Baignoire : OUI");
		
		this.getChildren().addAll(imageView, roomLabel, fumeur, baignoire);
		//Ajouter l'evenement qui se produira au clique
		this.addEvent();
	}
	
	public void addEvent() {
		this.setOnMouseClicked((event) -> {
			
			//On recup�re la BoxRoom sur laquelle l'on a cliqu�
			BoxRoom room = (BoxRoom) event.getSource();
			//On affecte cette chambre � la variable statique afin de pouvoir la r�cup�rer dans la Popup
			ListRoom.roomSelected = room.chambre;
			System.out.println(room);
			
			if (ListRoom.roomSelected.getEtat() == EtatChambre.LIBRE) {
				Scene scene = Popup.loadScene(ViewInterfaceConstante.POPUP_ROOT_VIEW, 1);
				//System.out.println(event.getSource());
				Popup popup = new Popup(scene);
				popup.setTitle("My modal window");
				popup.initOwner((((Node) event.getSource()).getScene()).getWindow());
				
				//Definir une action � la fermeture de la popup
				popup.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent event) {
						System.out.println("Mettre la variable statique de chambre à NULL");
						ListRoom.roomSelected = null;
					}
				});
				popup.showAndWait();
			}
			
		});
		
		//Changer le curseur de la souris au survol d'une chambre BoxRoom
		this.setOnMouseEntered(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				setCursor(Cursor.HAND);
			}
		});
		
	}
	
	@Override
	public String toString() {
		return this.chambre+"";
	}

}
