package pl.duncol.truckito.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pl.duncol.truckito.domain.BaseEntity;
import pl.duncol.truckito.domain.user.User;

@Entity
@Table(name = "config_entry")
@Getter
@Setter
public class ConfigEntry extends BaseEntity<Long> {

	@ManyToOne
	@JoinColumn
	private User user;
	
	@Column(name = "entry_key")
	private String entryKey;
	
	@Column(name = "entry_value")
	private String entryValue;
}
