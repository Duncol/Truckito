package pl.duncol.truckito.web.components.providers.interfaces;

import java.io.Serializable;

import pl.duncol.truckito.domain.BaseEntity;

public interface ViewingContainerProvider<O extends BaseEntity<? extends Serializable>> extends ObjectContainerProvider<O> {
	void setObject(O object);
}
