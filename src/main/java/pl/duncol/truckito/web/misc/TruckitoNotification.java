package pl.duncol.truckito.web.misc;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TruckitoNotification {

	private String title;
	private String description;
	private Type type;
	private int duration = 2000;
	private Position position = Position.BOTTOM_RIGHT;
	
	public static TruckitoNotification builder() {
		TruckitoNotification wrapper = new TruckitoNotification();
		return wrapper;
	}

	public TruckitoNotification setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public TruckitoNotification setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public TruckitoNotification setType(Type type) {
		this.type = type;
		return this;
	}
	
	public TruckitoNotification setDuration(int duration) {
		this.duration = duration;
		return this;
	}
	
	public TruckitoNotification setPosition(Position position) {
		this.position = position;
		return this;
	}

	public void show() {
		if (title != null) {
			Notification notification;
			if (type != null) {
				notification = new Notification(title, type);
			} else {
				notification = new Notification(title);
			}
			if (description != null) {
				notification.setDescription(description);
			}

			if (duration > 0) {
				notification.setDelayMsec(duration);
			}
			if (position != null) {
				notification.setPosition(Position.BOTTOM_RIGHT);
			}
			notification.show(Page.getCurrent());
		}
	}
}