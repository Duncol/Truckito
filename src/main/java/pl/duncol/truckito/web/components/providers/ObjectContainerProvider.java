package pl.duncol.truckito.web.components.providers;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import lombok.Getter;
import pl.duncol.truckito.domain.BaseEntity;
import pl.duncol.truckito.web.components.providers.interfaces.AddingContainerProvider;
import pl.duncol.truckito.web.components.providers.interfaces.EditingContainerProvider;
import pl.duncol.truckito.web.components.providers.interfaces.ViewingContainerProvider;

@ApplicationScoped
@Getter
public class ObjectContainerProvider<T extends BaseEntity<? extends Serializable>> {

	@Inject
	private AddingContainerProvider<T> adder;

	@Inject
	private EditingContainerProvider<T> editor;

	@Inject
	private ViewingContainerProvider<T> viewer;
}
