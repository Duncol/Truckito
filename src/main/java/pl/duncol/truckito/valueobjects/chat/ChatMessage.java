package pl.duncol.truckito.valueobjects.chat;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.duncol.truckito.domain.BaseEntity;
import pl.duncol.truckito.domain.user.employee.Employee;

@Entity
@Table(name = "chat_message")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessage extends BaseEntity<Long> {
	
	@Id
	private Long id;
	
	private String message;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "author_id")
	private Employee author;
	
	private Calendar timestamp;
	
	private ChatMessage(String message) {
		this.message = message;
	}
	
	public static ChatMessage of(String message) {
		ChatMessage chatMessage = new ChatMessage(message);
		chatMessage.timestamp = Calendar.getInstance();
		return chatMessage;
	}
}
