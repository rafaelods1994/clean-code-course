package com.c.refactoring.lock;

import java.util.Date;
import java.util.List;

public class UserLoginChecker {

	private static final int MAXIMUM_LOCK_PERIOD_IN_MS = 60 * 60 * 1000;

	/**
	 * {@inheritDoc}.
	 */
	public Lock isUserAllowedToLogin(long id, String status, boolean isFirstScreen, User userTryingToLogin,
			List existingLocks) {

		Lock lockAccess = new Lock();

		Object[] existingLock = (Object[]) existingLocks.get(0);
		String userIdWithLock = (String) existingLock[0];
		Date lockTimestamp = (Date) existingLock[1];

		if (null == existingLocks || 0 >= existingLocks.size()) {
			return createWriteLock(lockAccess);
		}

		if (null == userIdWithLock) {
			return lockAccess;
		}

		if (userIdWithLock.equalsIgnoreCase(userTryingToLogin.getUserId())) {
			return createWriteLock(lockAccess);
		}

		long timeElapseSinceLock = new Date().getTime() - lockTimestamp.getTime();

		if (isFirstScreen && timeElapseSinceLock > MAXIMUM_LOCK_PERIOD_IN_MS) {
			return createWriteLock(lockAccess);
		}

		return createReadLockWithMessage(lockAccess, buildMessage(userIdWithLock));

	}

	private String buildMessage(String userId) {
		return Constants.LOCK_TEXT.replaceAll("@@USER@@", userId);
	}

	private Lock createReadLockWithMessage(Lock lck, String showUserMessage) {
		lck.setRead(true);
		lck.setLockReason(showUserMessage);
		return lck;
	}

	private Lock createWriteLock(Lock lck) {
		lck.setRead(false);
		return lck;
	}
}