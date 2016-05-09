import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Mäng extends Application {
	int pildilaius = 600;
	int pildikõrgus = 600;
	static boolean onO = new Boolean(null); // alustab O;
	
	static void setO(boolean state){ // muudame mängija ära
		onO = !state;
	}
	
	public void start(Stage peaLava) throws Exception {
		Group juur = new Group();
		List<Ruut> ruudud = new ArrayList<Ruut>(); // et ruudud saaks tühjaks teha kui mäng uuesti algab
		Map<String, Integer> skoor = new HashMap<String, Integer>(); // skoori hoidmiseks mängu jooksul
		skoor.put("x", 0);
		skoor.put("o", 0);

// Küsime kes alustab
		List<String> valikud = new ArrayList<>();
		valikud.add("X");
		valikud.add("O");
		ChoiceDialog<String> dialog = new ChoiceDialog<>("X", valikud);
		dialog.setTitle("Trips-Traps-Trull");
		dialog.setHeaderText("Alustaja valimine");
		dialog.setContentText("Vali alustaja:");
		Optional<String> tulemus = dialog.showAndWait();
		if(tulemus.isPresent()){
			if(tulemus.get().equals("X")){
				onO = false;
			}
			else if(tulemus.get().equals("O")){
				onO = true;
			}
		}
		else if(!tulemus.isPresent()){
			System.exit(1);
		}
		
//küsime kas lugeda failist viimane tulemus ja alustada mängu uuesti
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Trips-Traps-Trull");
		alert.setHeaderText(null); // et aken ilusam oleks
		alert.setContentText("Kas soovid alustada uut mängu või jätkata eelmise seisuga?");
		ButtonType uus = new ButtonType("Uus mäng");
		ButtonType vana = new ButtonType("Jätka vanaga");
		ButtonType välju = new ButtonType("Välju mängust", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(uus, vana, välju);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == vana){
			try{
				DataInputStream dis = new DataInputStream(Files.newInputStream(Paths.get("viimane_mänguseis.txt")));
				
				@SuppressWarnings("deprecation")
				String rida = dis.readLine();
				String[] jupid = rida.split(" ");
				skoor.put("x", Integer.parseInt(jupid[1]));
				skoor.put("o", Integer.parseInt(jupid[3]));
				dis.close();
			}
			catch(Exception e){
				
			// kui faili pole olemas või on tühi (Kasutaja poolse valesti sisestuse erinditöötlus)
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("Algab uus mäng!");
				alert2.setHeaderText(null);
				alert2.setContentText("Viimast mänguseisu ei saanud lugeda!");
				alert2.showAndWait();
			}
		}
		else if(result.get() == uus){
			// lase tsükklil jätkuda
		}
		else{
			System.exit(2);
		}

// Luuakse mängulaud ja mäng algab------------------------------------------------------------------------
		peaLava.setTitle("Mänguseis: Mängija X: "+Integer.toString(skoor.get("x"))+ ", Mängija O: "+Integer.toString(skoor.get("o")));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {	
				Ruut ruut = new Ruut(200, 200, i*3+j);
				ruut.setLayoutX(j*200);
				ruut.setLayoutY(i*200);
				ruudud.add(ruut);
				juur.getChildren().add(ruut);
	
				ruut.setOnMouseClicked(new EventHandler<MouseEvent>(){
					public void handle(MouseEvent me){
						if(onO){
							if(ruut.vaba()){
								Tabel.kirjutaTabelisse(Integer.toString(ruut.getNr()+1), "o");
								//Tabel.näitaTabelit();
								setO(onO);
								ruut.joonistaO();
								peaLava.setTitle("Mänguseis: Mängija X: "+Integer.toString(skoor.get("x"))+ ", Mängija O: "+Integer.toString(skoor.get("o")));
								
								if(Tabel.kontrolliVõitu("o")){
									skoor.put("o", skoor.get("o")+1);
									//kirjutame skoori faili kujul: x (x-seis) o (o-seis)
									try {
										BufferedWriter bw = new BufferedWriter(Files.newBufferedWriter(Paths.get("viimane_mänguseis.txt")));
										bw.write("x "+ Integer.toString(skoor.get("x")) +" ");
										bw.write("o "+ Integer.toString(skoor.get("o")) +" ");
										bw.flush();
										bw.close();
									} catch (IOException e) {
										Alert alert3 = new Alert(AlertType.INFORMATION);
										alert3.setTitle(" ");
										alert3.setHeaderText(null);
										alert3.setContentText("Seisu kirjutamine faili ebaõnnestus O võidu juures!");
										alert3.showAndWait();
										}
								
									Alert alert = new Alert(AlertType.INFORMATION);
									alert.setTitle("Mängija O võitis!");
									alert.setHeaderText(null);
									alert.setContentText("Seis: X - " + Integer.toString(skoor.get("x")) + "; O - "+Integer.toString(skoor.get("o")) );
									alert.showAndWait();
									
									// puhastame tabeli ja teeme ruudud tühjaks uue mängu jaoks
									Tabel.puhasta();
									for (int k = 0; k < 9; k++) {
										ruudud.get(k).puhasta();
									}
									peaLava.setTitle("Mänguseis: Mängija X: "+Integer.toString(skoor.get("x"))+ ", Mängija O: "+Integer.toString(skoor.get("o")));
								}
							}
							else{
								peaLava.setTitle("Vali tühi ruut!");
							}					
						}
						else if(!onO){
							if(ruut.vaba()){
								Tabel.kirjutaTabelisse(Integer.toString(ruut.getNr()+1), "x");
								//Tabel.näitaTabelit();
								setO(onO);
								ruut.joonistaX();
								peaLava.setTitle("Mänguseis: Mängija X: "+Integer.toString(skoor.get("x"))+ ", Mängija O: "+Integer.toString(skoor.get("o")));
								if(Tabel.kontrolliVõitu("x")){
									skoor.put("x", skoor.get("x")+1);
									//kirjutame skoori faili kujul: x (x-seis) o (o-seis)
									try {
										BufferedWriter bw = new BufferedWriter(Files.newBufferedWriter(Paths.get("viimane_mänguseis.txt")));
										bw.write("x "+ Integer.toString(skoor.get("x")) +" ");
										bw.write("o "+ Integer.toString(skoor.get("o")) +" ");
										bw.flush();
										bw.close();
									} catch (IOException e) {
										Alert alert4 = new Alert(AlertType.INFORMATION);
										alert4.setTitle(" ");
										alert4.setHeaderText(null);
										alert4.setContentText("Seisu kirjutamine faili ebaõnnestus X võidu juures!");
										alert4.showAndWait();
										}
									
									Alert alert = new Alert(AlertType.INFORMATION);
									alert.setTitle("Mängija X võitis!");
									alert.setHeaderText(null);
									alert.setContentText("Seis: X - " + Integer.toString(skoor.get("x")) + "; O - "+Integer.toString(skoor.get("o")) );
									alert.showAndWait();
									
									// puhastame tabeli ja teeme ruudud tühjaks uue mängu jaoks
									Tabel.puhasta();
									for (int k = 0; k < 9; k++) {
										ruudud.get(k).puhasta();
									}
									peaLava.setTitle("Mänguseis: Mängija X: "+Integer.toString(skoor.get("x"))+ ", Mängija O: "+Integer.toString(skoor.get("o")));
								}
							}
							else{
								peaLava.setTitle("Vali tühi ruut!");
							}
						}	
					}
				});
			}	
		}
		
        Scene stseen1 = new Scene(juur);
        peaLava.setScene(stseen1);
        peaLava.setResizable(false);
        peaLava.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}