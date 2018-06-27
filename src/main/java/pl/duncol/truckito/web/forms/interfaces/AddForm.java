package pl.duncol.truckito.web.forms.interfaces;

import java.io.Serializable;

import com.vaadin.data.ValidationException;
import com.vaadin.ui.Button.ClickListener;

import pl.duncol.truckito.domain.BaseEntity;
import pl.duncol.truckito.web.forms.TruckitoForm;

public abstract class AddForm<O extends BaseEntity<? extends Serializable>> extends TruckitoForm<O> {
	public abstract O extractObject() throws ValidationException;
	public abstract void setButtonAction(ClickListener action);
	public abstract void enableSubmitButton();
}
