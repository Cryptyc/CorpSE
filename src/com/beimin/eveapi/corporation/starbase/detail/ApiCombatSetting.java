package com.beimin.eveapi.corporation.starbase.detail;

public class ApiCombatSetting {
	private boolean enabled;
	private int standing;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getStanding() {
		return standing;
	}

	public void setStanding(int standing) {
		this.standing = standing;
	}
}
