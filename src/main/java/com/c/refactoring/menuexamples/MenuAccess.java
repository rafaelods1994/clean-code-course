package com.c.refactoring.menuexamples;

import java.util.Arrays;
import java.util.List;

public class MenuAccess {

	public void setAuthorizationsInEachMenus(List<MenuItem> menuItemsList, Role[] roles) {

		if (roles == null) {
			return;
		}

		menuItemsList.forEach(menuItem -> {

			if (hasRole(roles, menuItem.getReadAccessRole())) {
				menuItem.setAccess(Constants.READ);
				menuItem.setVisible(true);

			}
			if (hasRole(roles, menuItem.getWriteAccessRole())) {
				menuItem.setAccess(Constants.WRITE);
				menuItem.setVisible(true);
			}

		});

	}

	private boolean hasRole(Role[] roles, String typeAccessRole) {

		return Arrays.stream(roles).anyMatch(role -> role.getName().equals(typeAccessRole));

	}

}
