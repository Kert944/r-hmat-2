import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Ruut extends StackPane{
	int nr;
	Text märk = new Text("");
	
	public Ruut(int laius,int kõrgus,int nr){
		this.nr = nr;
		Rectangle piir = new Rectangle(laius, kõrgus);
        piir.setFill(null);
        piir.setStroke(Color.WHITE);
        setAlignment(Pos.CENTER);
        
        addEventFilter(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                    	piir.setFill(Color.GRAY.brighter());
                    	event.consume();
                    }
                });
		setOnMouseExited(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent me){
				piir.setFill(null);
			}
		});
		
		getChildren().addAll(piir, märk);
	}
	
	public void joonistaX(){
		märk.setFont(Font.font(84));
		märk.setFill(Color.GRAY.darker().darker());
		märk.setText("X");
	}
	
	public void joonistaO(){
		märk.setFont(Font.font(84));
		märk.setFill(Color.GRAY.darker().darker());
		märk.setText("O");
	}
	public void puhasta(){
		märk.setText("");
	}
	
	public int getNr() {
		return nr;
	}
	
	public boolean vaba(){
		if(märk.getText().equals("")){
			return true;
		}
		else{
			return false;
		}
	}
	
}