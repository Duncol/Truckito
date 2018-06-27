package pl.duncol.truckito.valueobjects;

import java.util.Calendar;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class Comment {
	
	private Calendar timestamp;
	private String text;
	
	private Comment(String text) {
		this.timestamp = Calendar.getInstance();
		this.text = text;
	}
	
	public static Comment of(String comment) {
		return new Comment(comment);
	}
}
