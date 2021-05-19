package bingr.websockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.List;

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

@ServerEndpoint("/websocket/rooms/{movieId}")
@Component
public class WebSocketMovieChat {
	
	private static Map < Session, String > sessionMovieIdMap = new Hashtable<>();
	private static Map < String , List<Session>> movieIdSessionMap = new Hashtable<>();
	
	private final Logger logger = LoggerFactory.getLogger(WebSocketMovieChat.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("movieId") String movieId) throws IOException {
		logger.info("Entered into Open");		
		
		if(movieIdSessionMap.get(movieId) == null){
			List<Session> sessionList = new ArrayList<>();
			
			movieIdSessionMap.put(movieId, sessionList);	    
		}

	    sessionMovieIdMap.put(session, movieId);
	    movieIdSessionMap.get(movieId).add(session);
		
	}
	
	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
			 
		logger.info("Entered into Message: Got Message:" + message);
		
		broadcast(message, session);
	}
	
	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");

	    String movieId = sessionMovieIdMap.get(session);
	    sessionMovieIdMap.remove(session);
		int idx = movieIdSessionMap.get(movieId).indexOf(session);
	    movieIdSessionMap.get(movieId).remove(idx);
	}
	
	@OnError
	public void onError(Session session, Throwable throwable) {
	    logger.info("Entered into Error");
	}
	
	private void broadcast(String message, Session session) {
		try {
			JSONObject jsonMessageObject = new JSONObject(message);
			String movieId = sessionMovieIdMap.get(session);
			List<Session> sessionsList = movieIdSessionMap.get(movieId);
			for(int i = 0; i < sessionsList.size(); i++){
				try {
					sessionsList.get(i).getBasicRemote().sendText(jsonMessageObject.getString("sender") + ": " +jsonMessageObject.getString("message"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (JSONException err) {
			logger.info("Exception: " + err.getMessage().toString());
		}
	}
}
