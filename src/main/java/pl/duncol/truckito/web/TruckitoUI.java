package pl.duncol.truckito.web;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.jboss.logging.Logger;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.server.VaadinCDIServlet;
import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Position;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import lombok.NoArgsConstructor;
import pl.duncol.truckito.web.chat.ChatBroadcaster;
import pl.duncol.truckito.web.chat.ChatMessageListener;
import pl.duncol.truckito.web.login.LoginListener;
import pl.duncol.truckito.web.login.LoginPage;
import pl.duncol.truckito.web.login.access.AccessControl;
import pl.duncol.truckito.web.main.MainPage;

@CDIUI("")
@Theme("truckito")
@SuppressWarnings("serial")
@Push(PushMode.MANUAL)
@NoArgsConstructor
//@JavaScript("https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css")
//@JavaScript("https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js")
//@PreserveOnRefresh
public class TruckitoUI extends UI {

	private Logger log = Logger.getLogger(TruckitoUI.class);

	@Inject
	private CDIViewProvider cdiViewProvider;

	@Inject
	private AccessControl auth;
	
	private ChatMessageListener chatMessageListener;

	@Override
	protected void init(VaadinRequest request) {
		log.info("New user: " + request.getRemoteAddr());
		getPage().setTitle("Truckito");
		Responsive.makeResponsive(this);
		setLocale(request.getLocale());
		
		if (!auth.isUserSignedIn())  {
			setContent(new LoginPage(auth, new LoginListener() {

				@Override
				public void loginSuccessful() {
//					registerChatMessageReciever();
					showMainView();
				}
			}));
		} else {
			showMainView();
		}
	}

	public void showMainView() {
		MainPage mainPage = new MainPage(auth, cdiViewProvider);
		setContent(mainPage);
	}
	
	public ChatMessageListener getChatMessageListener() {
		return this.chatMessageListener;
	}
	
	public static void reloadPage() {
		Page.getCurrent().reload();
	}
	
	public static void navigateTo(String viewName) {
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
	
    @Override
    public void detach() {
        ChatBroadcaster.unregister(getChatMessageListener());
        super.detach();
    }

	@WebServlet(
			value = "/*", 
			name = "", 
			asyncSupported = true
	)
	public static class Servlet extends VaadinCDIServlet {
		@Override
		protected void servletInitialized() throws ServletException {
			super.servletInitialized();
			getService().addSessionInitListener(new SessionInitListener() {

				@Override
				public void sessionInit(SessionInitEvent event) {
					event.getSession().addBootstrapListener(new BootstrapListener() {
						@Override
						public void modifyBootstrapPage(BootstrapPageResponse response) {
							response.getDocument().head().append("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css\" "
									+ "integrity=\"sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ\" crossorigin=\"anonymous\">");
						}

						@Override
						public void modifyBootstrapFragment(BootstrapFragmentResponse response) {

						}
					});
				}
			});
		}
	}
}
