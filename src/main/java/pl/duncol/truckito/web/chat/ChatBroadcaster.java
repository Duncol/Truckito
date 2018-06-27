package pl.duncol.truckito.web.chat;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.logging.Logger;

import com.vaadin.ui.UI;

import pl.duncol.truckito.valueobjects.chat.ChatMessage;

public class ChatBroadcaster implements Serializable {

	private static final long serialVersionUID = 8646609426550566418L;

	private static Logger log = Logger.getLogger(ChatBroadcaster.class);
	
	static ExecutorService executorService = Executors.newSingleThreadExecutor();
	
    private static LinkedList<ChatMessageListener> listeners = new LinkedList<>();

    
    
    public static synchronized void register(ChatMessageListener listener) {
    	listeners.add(listener);
    }

    public static synchronized void unregister(ChatMessageListener listener) {
        listeners.remove(listener);
    }

    public static synchronized void broadcast(final ChatMessage message) {
		for (final ChatMessageListener listener : listeners)
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					log.infof("Broadcasting message: %s", message.getMessage());
					listener.receiveBroadcast(message);
				}
			});
    }
}
