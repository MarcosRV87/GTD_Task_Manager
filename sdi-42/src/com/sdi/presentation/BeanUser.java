package com.sdi.presentation;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sdi.dto.User;

@ManagedBean(name = "user")
@SessionScoped
public class BeanUser extends User implements Serializable {
	private static final long serialVersionUID = 55556L;

	private String repassword ="";
	
	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public BeanUser() {
		iniciaUser(null);
	}

	// Este método es necesario para copiar el usuario a editar cuando
	// se pincha el enlace Editar en la vista listado.xhtml. Podría sustituirse
	// por un método editar en BeanUsers.
	public void setUser(User user) {
		setId(user.getId());
		setEmail(user.getEmail());
		setIsAdmin(user.getIsAdmin());
		setLogin(user.getLogin());
		setPassword(user.getPassword());
		setStatus(user.getStatus());
	}

	// Iniciamos los datos del alumno con los valores por defecto
	// extraídos del archivo de propiedades correspondiente
	public void iniciaUser(ActionEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");
		setId(null);
		setEmail(bundle.getString("valorDefectoCorreo"));
//		setIsAdmin(bundle.getString("valorDefectoUserAdmin"));
		setLogin(bundle.getString("valorDefectoLogin"));
//		setPassword(bundle.getString("valorDefectoPassword"));
//		setStatus(bundle.getString("valorDefectoStatus"));
	}

}
