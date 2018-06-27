package pl.duncol.truckito.web.components.providers.interfaces;

import java.io.Serializable;
import java.util.function.Consumer;

import pl.duncol.truckito.domain.BaseEntity;

public interface AddingContainerProvider<O extends BaseEntity<? extends Serializable>> extends ObjectContainerProvider<O> {
	void setCallback(Consumer<O> callback);
}