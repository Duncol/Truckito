package pl.duncol.truckito.web.chat;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.vaadin.cdi.NormalUIScoped;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.dao.ChatMessageDAO;
import pl.duncol.truckito.valueobjects.chat.ChatMessage;

@NormalUIScoped
public class ChatBox extends VerticalLayout {

	private static final long serialVersionUID = 2892574300458329535L;
	
	private Logger log = Logger.getLogger(ChatBox.class);
	
	private Panel messages;
//	private TruckitoGrid<ChatMessage> messageGrid;
	
	private Button loadPreviousBtn;
	private int messageOffset;
	
	private VerticalLayout messagesLayout;
	private TextField inputTF;
	private Button sendButton;
	
	@Inject
	private ChatMessageDAO dao;
	
	private ChatMessageListener chatMessageListener;

	private int messageBufforLimit = 8;
	
	@PostConstruct
	private void postConstruct() {
		log.debug("ChatBox.postConstruct");
		setSizeFull();
    	setMargin(false);
    	
    	messageOffset = 0;
    	
    	createChatMessageReciever();
    	registerChatMessageReciever();
    	
    	prepareMessagePanel();
    	
//    	addComponent(messageGrid);
    	
    	HorizontalLayout inputHL = forgeMessageInputRow();
    	addComponent(inputHL);
    	
    	loadMessages();
	}

	private HorizontalLayout forgeMessageInputRow() {
		HorizontalLayout inputHL = new HorizontalLayout();
    	inputHL.setSizeFull();
    	
    	inputTF = forgeInputArea();
    	sendButton = forgeSendButton();
    	
    	inputHL.addComponent(inputTF);
    	inputHL.addComponent(sendButton);
    	
    	inputHL.setExpandRatio(inputTF, 100);
    	
    	return inputHL;
	}

	private Button forgeSendButton() {
		Button button = new Button("Wyślij");
		button.setClickShortcut(KeyCode.ENTER);
		button.addClickListener(e -> {
    		ChatMessage message = buildMessage();
    		inputTF.clear();
    		dao.create(message);
    		log.info("Message created");
    		ChatBroadcaster.broadcast(message);
    	});
    	
    	return button;
	}

	private TextField forgeInputArea() {
		TextField inputTF = new TextField();
    	inputTF.setSizeFull();
    	return inputTF;
	}
	
	private void prepareMessagePanel() { // TODO
//		messageGrid = new TruckitoGrid<>();
//		messageGrid.addColumn(ChatMessage::getMessage);
//		messageGrid.setDataProvider(
//			    (sortOrders, offset, limit) ->
//			            dao.findAll(offset, limit).stream(),
//			    () -> dao.findAll().size()
//			);
//		
		messages = new Panel();
    	messages.addStyleName("chatbox");
    	messages.setSizeFull();
    	messagesLayout = new VerticalLayout();
    	
    	loadPreviousBtn = forgePreviousBtn();
    	addComponent(loadPreviousBtn);
    	
    	messages.setContent(messagesLayout);
    	
    	addComponent(messages);
	}

	private Button forgePreviousBtn() {
		Button b = new Button("Wcześniejsze");
		b.setSizeFull();
		b.addClickListener(this::loadPreviousMessages);
		return b;
	}
	
	private void loadPreviousMessages(Button.ClickEvent listener) {
		List<ChatMessage> messages = dao.findLatest(messageOffset, messageBufforLimit);
		messageOffset += messageBufforLimit;
		log.infof("Loaded additional %d messages", messages.size());

		UI current = UI.getCurrent();
		current.access(() -> {
			messages.forEach(this::appendMessage);
			current.push();
		});
	}

	@Override
	public void detach() {
		ChatBroadcaster.unregister(chatMessageListener);
		super.detach();
	}
	
	@Override
	public void attach() {
		registerChatMessageReciever();
    	super.attach();
	}
	
	private void createChatMessageReciever() {
		chatMessageListener = new ChatMessageReciever(UI.getCurrent(), this);				
	}
	
	private void registerChatMessageReciever() {
		ChatBroadcaster.register(chatMessageListener);
	}
	
	private ChatMessage buildMessage() {
		String message = inputTF.getValue();
		ChatMessage chatMessage = ChatMessage.of(message);
		return chatMessage;
	}
	
	private void loadMessages() {
		List<ChatMessage> messages = dao.findLatest(0, messageBufforLimit);
		log.infof("Found %d messages", messages.size());

		UI current = UI.getCurrent();
		current.access(() -> {
			messages.forEach(this::appendMessage);
			current.push();
		});
	}
	
	protected void appendMessage(ChatMessage message) {
		if (checkBufferLimit()) {
			removeOldest();
		}
		Label line = forgeLine(message);
		messagesLayout.addComponent(line);
		log.infof("Appending message: %s", message.getMessage());
	}
	
	protected void appendMessage(int index, ChatMessage message) {
		if (checkBufferLimit()) {
			removeOldest();
		}
		Label line = forgeLine(message);
		messagesLayout.addComponent(line, index);
		log.infof("Appending message: %s", message.getMessage());
	}
	
	private boolean checkBufferLimit() {
		return messagesLayout.getComponentCount() >= (messageBufforLimit + (messageBufforLimit*messageOffset));
	}
	
	private void removeOldest() {
		Component first = messagesLayout.getComponent(0);
		messagesLayout.removeComponent(first);
	}
	
	private Label forgeLine(ChatMessage message) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		StringBuilder sb = new StringBuilder();
		sb.append(sdf.format(message.getTimestamp().getTime()));
		sb.append(" - ");
		sb.append(message.getAuthor());
		sb.append(": ");
		sb.append(message.getMessage());
		return new Label(sb.toString());
	}
}
