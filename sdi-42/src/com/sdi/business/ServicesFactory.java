package com.sdi.business;


public interface ServicesFactory {

	AdminService getAdminService();

	UserService getUserService();

	TaskService getTaskService();
	
}
