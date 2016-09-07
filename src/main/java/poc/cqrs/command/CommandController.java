package poc.cqrs.command;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface CommandController {

	/**
	 * Déclenchée lorsqu'il y a un POST sur une commande.
	 * L'objet Command est reconstruit à partir des données et est envoyé sur le bus des commandes.
	 *  
	 * @param commandPath Le chemin de la commande. Permet de reconstituer l'instance de {@link Command}.
	 * @param commandData Les données. Le mapping se fait en JSON.
	 * 
	 * @return {@link HttpStatus#CREATED} si tout se passe bien, {@link HttpStatus#BAD_REQUEST} si le handler lève une {@link InvalidCommandException}.
	 */
	ResponseEntity<?> post(String commandPath, Map<String, ?> commandData);

}