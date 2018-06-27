package pl.duncol.truckito.web.components;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Window;

public class TruckitoWindow extends Window {

	private static final long serialVersionUID = -266096496800469928L;

	public TruckitoWindow() {
		super();
		setModal(true);
		addCloseShortcut(KeyCode.ESCAPE, null);
	}
}
