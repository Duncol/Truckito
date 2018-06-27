package pl.duncol.truckito.web.forms;

import java.io.Serializable;

import com.vaadin.data.Binder;
import com.vaadin.ui.*;
import org.vaadin.jouni.animator.Animator;
import org.vaadin.jouni.dom.client.Css;

import pl.duncol.truckito.domain.BaseEntity;
import pl.duncol.truckito.exceptions.ValidationException;

public abstract class TruckitoForm<O extends BaseEntity<? extends Serializable>> extends FormLayout {
	private static final long serialVersionUID = 2867693475220175037L;

	protected VerticalLayout mainLayout;
	protected Label header;
	protected O object;
	protected Binder<O> binder;
//	protected Button submitButton;

	protected void prepareMainLayout() {
		mainLayout = new VerticalLayout();
		mainLayout.addStyleName("p-0");
		addComponent(mainLayout);
	}

	protected void prepareHeader(String title) {
		header = new Label(title);
		header.addStyleName("form-header");
		mainLayout.addComponent(header);
	}

	public void setHeader(String title) {
		header.setValue(title);
	}

	HorizontalLayout createFullSizeRow() {
		HorizontalLayout row = new HorizontalLayout();
		row.setSizeFull();
		return row;
	}

	VerticalLayout createFullSizeColumn() {
		VerticalLayout column = new VerticalLayout();
		column.setSizeFull();
		return column;
	}

	static void fancinizeInput(Label header, TextField input) {
		header.addStyleName("opacity-0");
		input.setPlaceholder(header.getValue() + "...");
		input.setSizeFull();
		input.addFocusListener(e -> {
			input.setPlaceholder("");
			Animator.animate(header, new Css().opacity(1)).duration(500);
		});
		input.addBlurListener(e -> {
			if (input.isEmpty()) {
				input.setPlaceholder(header.getValue() + "...");
				Animator.animate(header, new Css().opacity(0)).duration(500);
			}
		});
	}

	static void fancinizeInput(Label header, ComboBox<?> input) {
		header.addStyleName("opacity-0");
		input.setPlaceholder(header.getValue() + "...");
		input.setSizeFull();
		input.addFocusListener(e -> {
			input.setPlaceholder("");
			Animator.animate(header, new Css().opacity(1)).duration(500);
		});
		input.addBlurListener(e -> {
			input.setPlaceholder(header.getValue() + "...");
			Animator.animate(header, new Css().opacity(0)).duration(500);
		});
	}

	protected Button forgeSubmitButton(String title) {
		Button button = new Button(title);
		button.setDisableOnClick(true);
//		button.addClickListener(e -> {
//			preSubmit();
//			UI current = UI.getCurrent();
//			try {
//				completeObject();
////				clearFields();
//				current.access(new AsyncThread(() -> {
//					UI.setCurrent(current);
//					T object = saveObject();
//					if (callback != null && object != null) {
//						callback.accept(object);
//					}
//					current.push();
//				}));
//			} catch (ValidationException ex) {
//				ex.printStackTrace();
//			} finally {
//				button.setEnabled(true);
//			}
//		});

		return button;
	}
}
