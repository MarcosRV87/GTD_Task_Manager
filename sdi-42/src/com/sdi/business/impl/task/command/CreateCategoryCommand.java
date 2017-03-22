package com.sdi.business.impl.task.command;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.command.Command;
import com.sdi.business.impl.util.CategoryCheck;
import com.sdi.dto.Category;
import com.sdi.infrastructure.Factories;

public class CreateCategoryCommand implements Command<Long> {

	private Category category;

	public CreateCategoryCommand(Category category) {
		this.category = category;
	}

	@Override
	public Long execute() throws BusinessException {
		CategoryCheck.nameIsNotNull( category );
		CategoryCheck.nameIsNotEmpty( category );
		CategoryCheck.userIsNotNull( category );
		CategoryCheck.isValidUser( category );
		CategoryCheck.isUniqueName( category );
		CategoryCheck.isNotForAdminUser( category );
		
		return Factories.persistence.getCategoryDao().save( category );
	}

}
