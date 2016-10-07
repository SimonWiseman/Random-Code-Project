package wiseman.licensing.model;

/**
 * Taxi Driver Application
 * @author Simon Wiseman
 */
public class TaxiDriver {

	private boolean newDriver;
	private boolean over18;
	private boolean dvlaLicensed;
	private int licenseDurationInYears;
	private double grossLicenseCost;

	public boolean isNewDriver() {
		return newDriver;
	}

	public void setNewDriver(boolean newDriver) {
		this.newDriver = newDriver;
	}

	public boolean isOver18() {
		return over18;
	}

	public void setOver18(boolean over18) {
		this.over18 = over18;
	}

	public boolean isDvlaLicensed() {
		return dvlaLicensed;
	}

	public void setDvlaLicensed(boolean dvlaLicensed) {
		this.dvlaLicensed = dvlaLicensed;
	}

	public int getLicenseDurationInYears() {
		return licenseDurationInYears;
	}

	public void setLicenseDurationInYears(int licenseDurationInYears) {
		this.licenseDurationInYears = licenseDurationInYears;
	}

	public double getGrossLicenseCost() {
		return grossLicenseCost;
	}

	public void setGrossLicenseCost(double grossLicenseCost) {
		this.grossLicenseCost = grossLicenseCost;
	}

}
