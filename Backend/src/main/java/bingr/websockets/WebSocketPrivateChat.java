package bingr.websockets;

import java.io.IOException;

import java.util.Hashtable;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.json.JSONException;
import org.json.JSONObject;

@ServerEndpoint("/websocket/{emailId}")
@Component
public class WebSocketPrivateChat {

	private static Map < Session, String > sessionEmailIdMap = new Hashtable < > ();
	private static Map < String, Session > emailIdSessionMap = new Hashtable < > ();

	private final Logger logger = LoggerFactory.getLogger(WebSocketPrivateChat.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("emailId") String emailId) throws IOException {
		logger.info("Entered into Open");

	    sessionEmailIdMap.put(session, emailId);
	    emailIdSessionMap.put(emailId, session);

	    String message = "User:" + emailId + " has Joined the Chat";

	    sendMessageToParticularUser(emailId, message);
	}

	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
		String emailId = sessionEmailIdMap.get(session); 
			 
		logger.info("Entered into Message: Got Message:" + message);
		
		sendMessageToParticularUser(emailId, message);
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");

	    String emailId = sessionEmailIdMap.get(session);
	    sessionEmailIdMap.remove(session);
	    emailIdSessionMap.remove(emailId);

	    String message = emailId + " disconnected";

	    sendMessageToParticularUser(emailId, message);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
	    logger.info("Entered into Error");
	}

	// email ID = sender Id, message = data
	private void sendMessageToParticularUser(String emailId, String message) {
		try {
		     JSONObject jsonMessageObject = new JSONObject(message);
			 if(emailIdSessionMap.get(jsonMessageObject.getString("receiver")) != null){
				emailIdSessionMap.get(jsonMessageObject.getString("receiver")).getBasicRemote().sendText(emailId + ":" + jsonMessageObject.getString("message"));
				emailIdSessionMap.get(jsonMessageObject.getString("sender")).getBasicRemote().sendText(emailId + ":" + jsonMessageObject.getString("message"));
			 }
			 else{
				 emailIdSessionMap.get(jsonMessageObject.getString("sender")).getBasicRemote().sendText("Seems like no one else is in the chat! Try again later.");
			 }
		} catch (Exception err){
			logger.info("Exception: " + err.getMessage().toString());
		}
	}
	
	private void broadcast(String message) {
		try {
			JSONObject jsonMessageObject = new JSONObject(message);
			sessionEmailIdMap.forEach((session, emailId) -> {
			      try {
			        session.getBasicRemote().sendText(jsonMessageObject.getString("message"));
			      } catch (IOException e) {
			        logger.info("Exception: " + e.getMessage().toString());
			        e.printStackTrace();
			      }
			   });
			
		} catch (JSONException err) {
			logger.info("Exception: " + err.getMessage().toString());
		}
	}
}
