package pl.duncol.truckito.web.main.notepad;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.jboss.logging.Logger;

import com.mysql.fabric.xmlrpc.base.Array;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.web.TruckitoUI;
import pl.duncol.truckito.web.misc.TruckitoNotification;

public class TruckitoNotepad extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private static TruckitoNotepad instance;
	
	private Logger log = Logger.getLogger(TruckitoNotepad.class);
	
	private Toolkit toolkit;
	private Clipboard clipboard;
	
	private Button head;
	
	private VerticalLayout body;
	
	private HorizontalLayout optionsRow;
	private Label autoCopyLabel;
	private CheckBox autoCopyCheckbox;
	private TextArea textArea;
	
	private Registration tfRegistration;
	
	public TruckitoNotepad() {
		
		setSizeFull();
		setMargin(false);
		addStyleName("notepad");
		
		prepareClipboard();
		
		head = new Button();
		head.setSizeFull();
		head.setCaption("Notatnik");
		head.addClickListener(e -> {
			if (body.isVisible()) {
				body.setVisible(false);				
			} else {
				body.setVisible(true);
			}
		});
		head.setClickShortcut(KeyCode.N, ModifierKey.ALT);
		
		addComponent(head);
		
		body = new VerticalLayout();
		body.setMargin(false);
		body.setSpacing(false);
		body.setVisible(false);
		addComponent(body);
		
		textArea = new TextArea();
		textArea.setSizeFull();
		textArea.setWidth(100f, Unit.PERCENTAGE);
		
		optionsRow = new HorizontalLayout();
		autoCopyLabel = new Label("Włącz kopiowanie automatyczne");
		autoCopyCheckbox = new CheckBox();
		autoCopyCheckbox.addValueChangeListener(e -> {
			setNotepadAutocopy(e.getValue());
		});
		
		optionsRow.addComponent(autoCopyLabel);
		optionsRow.addComponent(autoCopyCheckbox);
		
		body.addComponent(optionsRow);
		body.addComponent(textArea);
	}
	
	// cdi?
	public static TruckitoNotepad getInstance() {
		if (instance == null) {
			instance = new TruckitoNotepad();
		}
		return instance;
	}
	
	private void prepareClipboard() {
		toolkit = Toolkit.getDefaultToolkit();
		clipboard = toolkit.getSystemClipboard();
	}
	
	private void setNotepadAutocopy(boolean isEnabled) {
		if (isEnabled) {
			tfRegistration = textArea.addValueChangeListener(e -> {
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						StringSelection s = new StringSelection(textArea.getValue());
						clipboard.setContents(s, s);
						TruckitoNotification.builder().setTitle("Skopiowano notatnik").show();
					}
				}, 2000);	
			});
		} else {
			if (tfRegistration != null) {
				tfRegistration.remove();
				TruckitoNotification.builder().setTitle("Wyłączono autokopiowanie").show();
			}
		}
	}
}