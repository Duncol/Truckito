package pl.duncol.truckito.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class BaseEntity<ID extends Serializable> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private ID id;
}
