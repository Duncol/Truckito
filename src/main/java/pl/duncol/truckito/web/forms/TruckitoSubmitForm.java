package pl.duncol.truckito.web.forms;

import java.io.Serializable;
import java.util.function.Consumer;

import com.vaadin.data.ValidationException;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

import pl.duncol.truckito.domain.BaseEntity;
import pl.duncol.truckito.utils.AsyncThread;

public abstract class TruckitoSubmitForm<T extends BaseEntity<? extends Serializable>> extends TruckitoForm<T> {
	private static final long serialVersionUID = -9086144839979342775L;

	protected Button submitButton;
	protected Consumer<T> callback;

	protected Button forgeSubmitButton(String title) {
		Button button = new Button(title);
		button.setDisableOnClick(true);
		button.addClickListener(e -> {
			preSubmit();
			UI current = UI.getCurrent();
			try {
				completeObject();
//				clearFields();
				current.access(new AsyncThread(() -> {
					UI.setCurrent(current);
					T object = saveObject();
					if (callback != null && object != null) {
						callback.accept(object);
					}
					current.push();
				}));
			} catch (ValidationException ex) {
				ex.printStackTrace();
			} finally {
				button.setEnabled(true);
			}
		});

		return button;
	}

	public void setCallback(Consumer<T> callback) {
		this.callback = callback;
	}

	protected abstract void preSubmit();

	protected abstract void completeObject() throws ValidationException;

	public abstract void clearFields();

	protected abstract T saveObject();

//	public abstract void setObjectForEdit(T object);
}
