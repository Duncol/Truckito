package pl.duncol.truckito.web.chat;

import java.util.LinkedList;
import java.util.List;

import org.jboss.logging.Logger;

import com.vaadin.ui.UI;

import pl.duncol.truckito.valueobjects.chat.ChatMessage;

//@NormalUIScoped
public class ChatMessageReciever implements ChatMessageListener {

	private Logger log = Logger.getLogger(ChatMessageReciever.class);

//	@Inject
	private ChatBox chatBox;

	private UI ui;

	private List<ChatMessage> undeliveredMessages;

	private Runnable broadcastAction;

//	@Inject
	public ChatMessageReciever(UI ui, ChatBox chatBox) {
		this.ui = ui;
		this.chatBox = chatBox;
		this.undeliveredMessages = new LinkedList<>();
	}

	@Override
	public void receiveBroadcast(ChatMessage message) {
		ui.access(() -> append(message));
	}

	private void append(ChatMessage message) {
		this.chatBox.appendMessage(message);
		ui.push();
	}
	
	private void appendAndRemove(ChatMessage message) {
		this.chatBox.appendMessage(message);
		undeliveredMessages.remove(message);
	}
}
