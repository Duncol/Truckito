package pl.duncol.truckito.domain.document;

import javax.persistence.MappedSuperclass;

import pl.duncol.truckito.domain.BaseEntity;

@MappedSuperclass
public abstract class Document extends BaseEntity<Long> {

}