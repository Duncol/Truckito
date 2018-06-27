package pl.duncol.truckito.web.login;

import org.jboss.logging.Logger;
import org.vaadin.jouni.animator.Animator;
import org.vaadin.jouni.dom.client.Css;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.web.login.access.AccessControl;
import pl.duncol.truckito.web.misc.TruckitoNotification;
import pl.duncol.truckito.web.misc.WojtasWrzutaProvider;

public class LoginPage extends VerticalLayout {
	
	private Logger log = Logger.getLogger(LoginPage.class);
	
	private static final long serialVersionUID = 1L;
	
	private TextField username;
	private PasswordField password;
	
	private AccessControl auth;
	
	private LoginListener loginListener;
	
	public LoginPage(AccessControl auth, LoginListener listener) {
		addStyleName("login-panel");
		
		this.auth = auth;
		this.loginListener = listener;
		
		Panel panel = new Panel("Panel logowania");
		panel.setSizeUndefined();
		
		Animator.animate(this, new Css()
				.opacity(1)
				.translateY("100px"))
			.delay(500)
			.duration(1000);

		FormLayout content = new FormLayout();
		username = new TextField("Użytkownik");
		username.focus();
		content.addComponent(username);
		password = new PasswordField("Hasło");
		content.addComponent(password);
		
		Button loginButton = new Button("Zaloguj");
		loginButton.setDisableOnClick(true);
		loginButton.addClickListener(e -> {
            try {
                login();
            } finally {
                loginButton.setEnabled(true);
            }
		});
		loginButton.setClickShortcut(KeyCode.ENTER);
		content.addComponent(loginButton);
		content.setSizeUndefined();
		content.setMargin(true);
		panel.setContent(content);
		
		// DLA BEKI, DO WYWALENIA :D
//		List<String> wrzuty = Arrays.asList("chapie dzide", 
//				"siorpie pisiora", 
//				"mlaszcze pytonga", 
//				"pierze w rzece", 
//				"myśli, że android to nazwa makaronu");
//		Random random = new Random();
//		String wrzuta = wrzuty.get(random.nextInt(wrzuty.size()));
		WojtasWrzutaProvider provider = new WojtasWrzutaProvider();
		String wrzuta = provider.getWrzuta();
		Label wojtek = new Label();
		wojtek.addStyleName("wojtas");
		Animator.animate(wojtek, new Css()
				.translateY("600px"))
			.delay(3500)
			.duration(1500);
		wojtek.setValue("Czy wiesz, że... Wojtas " + wrzuta + "?");
		addComponent(wojtek);
		setComponentAlignment(wojtek, Alignment.MIDDLE_CENTER);
		
		addComponent(panel);
		
		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
	}
	
    private void login() {
    	String username = this.username.getValue();
    	String password = this.password.getValue();
        if (auth.authenticate(username, password)) {
        	auth.registerUser(username);
        	log.info("User " + username + " has been successfuly logged!");
            loginListener.loginSuccessful();
        } else {
        	TruckitoNotification.builder()
        		.setTitle("Błąd logowania")
        		.setDescription("Niepoprawny login i/lub hasło")
        		.setType(Notification.Type.ERROR_MESSAGE)
        		.show();
            this.username.focus();
        }
    }
}