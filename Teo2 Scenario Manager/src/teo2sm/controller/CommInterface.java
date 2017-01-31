package teo2sm.controller;

import java.util.HashMap;

public interface CommInterface {
	
	public void openConnection();
	
	public void closeConnection();
	
	/*blocco funzioni comandi bluetooth*/
	/**
	 * Teo stays idle till the child interact with him
	 * @return an int representing the communication constant of the type of interaction received
	 */
	public int waitChildInteraction();
	
	/**
	 * Send Teo the RFID tag of the object we want the child to bring to him
	 * @param tag the RFID tag of the wanted object
	 * @return the RFID tag of the read object or null if a timeout has expired
	 */
	public String waitRfidObject();
	
	/**
	 * Set Teo mood (face led matrix and led strips)
	 * @param code an int representing the communication constant of the wanted mood
	 */
	public void setTeoMood(String code);
	
	/*pensavo che magari, giusto per renderlo meno statico, quando il bambino sbaglia l'oggetto potremmo far tremare Teo
	 * o in generale fargli fare dei piccoli movimenti "sul posto" in altri casi
	 */
	/**
	 * Send Teo a command to move
	 * @param code an int representing the communication constant of the wanted movement
	 */
	public void sendTeoMovement(String code);
}
