package pl.duncol.truckito.web.components.providers.interfaces;

import java.io.Serializable;

import pl.duncol.truckito.domain.BaseEntity;

public interface ObjectContainerProvider<O extends BaseEntity<? extends Serializable>> {
	void buildContainer();
	void close();
	O getObject();
	void releaseContainer();
}
