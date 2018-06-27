package pl.duncol.truckito.web.forms.interfaces;

import java.io.Serializable;

import pl.duncol.truckito.domain.BaseEntity;

public interface ViewForm<O extends BaseEntity<? extends Serializable>> {
	void setObject(O object);
}
