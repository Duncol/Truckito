package pl.duncol.truckito.web.components;

import java.io.Serializable;
import java.util.Collection;

import com.vaadin.ui.ComboBox;

import pl.duncol.truckito.domain.BaseEntity;
import pl.duncol.truckito.exceptions.CollectionNotSetException;

public class TruckitoComboBox<T extends BaseEntity<? extends Serializable>> extends ComboBox<T> {
	private static final long serialVersionUID = -8713660703705134783L;
	
	private Collection<T> items;

	public void addItem(T item) {
		try {
			if (this.items == null) {
				throw new CollectionNotSetException();
			}
			items.add(item);
			super.setItems(items);
		} catch (CollectionNotSetException ex) {
			ex.printStackTrace();
		}
	}

	public void removeItem(T item) {
		try {
			if (this.items == null) {
				throw new CollectionNotSetException();
			}
			items.remove(item);
			super.setItems(items);
		} catch (CollectionNotSetException ex) {
			ex.printStackTrace();
		}
	}

	public void updateItem(T item) {
		try {
			if (this.items == null) {
				throw new CollectionNotSetException();
			}
			items.remove(item);
			items.add(item);
			super.setItems(items);
		} catch (CollectionNotSetException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void setItems(Collection<T> items) {
		this.items = items;
		super.setItems(items);
	}
}
