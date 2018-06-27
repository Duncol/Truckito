package pl.duncol.truckito.domain.disposition;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

//@Entity
//@Table(name = "task_load")
@Getter
@Setter
public class Load extends Task {
	
//	@OneToMany
//	@JoinColumn(name="task_unload_id")
	private List<Unload> unloads;
}
