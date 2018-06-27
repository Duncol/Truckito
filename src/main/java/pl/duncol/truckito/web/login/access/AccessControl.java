package pl.duncol.truckito.web.login.access;

import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.vaadin.server.VaadinSession;

import lombok.Getter;
import lombok.Setter;
import pl.duncol.truckito.security.UserHashedPasswordDAO;
import pl.duncol.truckito.security.hash.HashService;

// MOCK
@ApplicationScoped
@Getter
@Setter
public class AccessControl {
	
	private static final String CURRENT_USER_SESSION_ATTRIBUTE_KEY = "user";
	
	private Logger log = Logger.getLogger(AccessControl.class);
	
    private String username;
    private String password;

    @Inject
    private HashService hashService;
    
    @Inject
    private UserHashedPasswordDAO dao;
    
    public AccessControl() {
    	
    }

    public boolean authenticate(String userName, String password){
//    	UserHashedPassword hashPass = dao.getHashSaltFor(userName);
//        if(hashService.validate(password, hashPass.getHashedPass(), hashPass.getSalt())){
//            return true;
//        }
//        return false;
    	if (Objects.equals(userName, "Duncol") && Objects.equals(password, "masterkey")) {
    		return true;
    	}
    	return false;
    }
   
	public void registerUser(String userName) {
		VaadinSession.getCurrent().setAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY, userName);
	}
	
	public void unregisterCurrentUser() {
		if (!isUserSignedIn()) {			
			log.info("Unregister user: User is already signed off");
		}
		VaadinSession.getCurrent().setAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY, null);
	}
	
	public boolean isUserSignedIn() {
		return VaadinSession.getCurrent().getAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY) != null ? true : false;
	}
	
	public boolean isUserInRole(String role) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getCurrentUser() {
		return (String) VaadinSession.getCurrent().getAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY);
	}
}