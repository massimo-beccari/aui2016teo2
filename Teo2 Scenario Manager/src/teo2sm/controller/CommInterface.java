package teo2sm.controller;

import java.sql.Time;

public interface CommInterface {
	/*blocco di funzioni per proiettore, casse, altoparlante bluetooth*/
	/*le funzioni ritornano un oggetto di tipo Time con il tempo in cui la riproduzione è o è stata interrotta
	 * non so se sia una buona idea, poi vedremo*/
	public Time playVideo();
	
	public Time pauseVideo();
	
	public Time playStory();
	
	public Time pauseStory();
	
	public Time playMusic();
	
	public Time pauseMusic();
	
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
	public String waitRfidObject(String tag);
	
	/**
	 * Set Teo mood (face led matrix and led strips)
	 * @param code an int representing the communication constant of the wanted mood
	 */
	public void setTeoMood(int code);
	
	/*pensavo che magari, giusto per renderlo meno statico, quando il bambino sbaglia l'oggetto potremmo far tremare Teo
	 * o in generale fargli fare dei piccoli movimenti "sul posto" in altri casi
	 */
	/**
	 * Send Teo a command to move
	 * @param code an int representing the communication constant of the wanted movement
	 */
	public void sendTeoMovement(int code);
}
