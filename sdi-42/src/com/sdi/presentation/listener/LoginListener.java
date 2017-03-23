package com.sdi.presentation.listener;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.sdi.dto.User;

public class LoginListener implements PhaseListener {

	private User user;

	@Override
	public void beforePhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext fc = event.getFacesContext();
		String view = fc.getViewRoot().getViewId();
		user = (User) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("LOGGEDIN_USER");

		// Check to see if they are on the login page.
		if (view.contains("index")) {
			// processing can continue
			return;
		}
		if (notLoggedIn()) {
			NavigationHandler nh = fc.getApplication().getNavigationHandler();
			nh.handleNavigation(fc, null, "/index.xhtml");
		}
	}

	private boolean notLoggedIn() {
		return (user != null) ? false : true;
	}
}
