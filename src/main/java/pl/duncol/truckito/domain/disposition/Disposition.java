package pl.duncol.truckito.domain.disposition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pl.duncol.truckito.domain.BaseEntity;
import pl.duncol.truckito.domain.document.CMRDocument;
import pl.duncol.truckito.domain.user.employee.driver.Driver;
import pl.duncol.truckito.domain.vehicle.Trailer;
import pl.duncol.truckito.domain.vehicle.Truck;
import pl.duncol.truckito.valueobjects.Comment;
import pl.duncol.truckito.valueobjects.id.Dossier;

@Entity
@Table(name = "disp_dispositions")
@Getter
@Setter
//@AttributeOverride(name = "id", column = @Column(name = "ID"))
public class Disposition extends BaseEntity<Long> {
	
    @Embedded
    private Dossier dossierNumber;
    
    @Column(name = "create_date")
    private Calendar createDate = Calendar.getInstance();
    
    @Column(name = "is_active")
    private boolean isActive;
    
    @ManyToMany(cascade = { }, fetch = FetchType.EAGER) // TODO LAZY
    @JoinTable(
        name = "dispositions_drivers", 
        joinColumns = { @JoinColumn(name = "dossier", nullable = false, updatable = false) }, 
        inverseJoinColumns = { @JoinColumn(name = "driver_id", nullable = false, updatable = false) })
    private Set<Driver> drivers = new LinkedHashSet<>();
    
    @ManyToMany(cascade = { }, fetch = FetchType.EAGER) // TODO LAZY
    @JoinTable(
    		name = "dispositions_trucks", 
    		joinColumns = { @JoinColumn(name = "dossier", nullable = false, updatable = false) }, 
    		inverseJoinColumns = { @JoinColumn(name = "truck_id", nullable = false, updatable = false) })
    private Set<Truck> trucks = new LinkedHashSet<>();
    
    @ManyToMany(cascade = { }, fetch = FetchType.EAGER) // TODO LAZY
    @JoinTable(
    		name = "dispositions_trailers", 
    		joinColumns = { @JoinColumn(name = "dossier", nullable = false, updatable = false) }, 
    		inverseJoinColumns = { @JoinColumn(name = "trailer_id", nullable = false, updatable = false) })
    private Set<Trailer> trailers = new LinkedHashSet<>();
    
    @OneToMany(mappedBy="disposition", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER) // TODO LAZY
    private Set<CMRDocument> cmrDocuments = new HashSet<>();
    
    @ElementCollection(fetch = FetchType.LAZY)
    @JoinTable(name = "disp_comments", joinColumns = @JoinColumn(referencedColumnName = "id", name = "disposition_id"))
    private List<Comment> comments = new ArrayList<>();
    
    
    public void addDriver(Driver driver) {
    	drivers.add(driver);
    }
    public Driver getLastDriver() throws NoSuchElementException {
    	return drivers.stream().findFirst().get();
    }
    
    
    public void addTruck(Truck truck) {
    	trucks.add(truck);
    }
    public Truck getLastTruck() throws NoSuchElementException {
    	return trucks.stream().findFirst().get();
    }
    
    
    public void addTrailer(Trailer trailer) {
    	trailers.add(trailer);
    }
    public Trailer getLastTrailer() throws NoSuchElementException {
    	return trailers.stream().findFirst().get();
    }
    
    
    public void addCMRDocument(CMRDocument cmr) {
    	cmrDocuments.add(cmr);
    	cmr.setDisposition(this);
    }
    public void removeCMRDocument(CMRDocument cmr) {
    	cmrDocuments.remove(cmr);
    	cmr.setDisposition(null);
    }
    
    public String getConcatenatedComments() {
    	StringBuilder sb = new StringBuilder();
    	comments.forEach(c -> {
    		sb.append(c + "\n");
    	});
    	return sb.toString();
    }
    
    public void addComment(Comment comment) {
    	comments.add(comment);
    }
    
    public void addComment(String text) {
    	Comment comment = Comment.of(text);
    	comments.add(comment);
    }
    
    public int getCMRcount() {
    	return cmrDocuments.size();
    }
    
    public String getCreateDateForDisplay() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    	return sdf.format(createDate.getTime());
    }
}