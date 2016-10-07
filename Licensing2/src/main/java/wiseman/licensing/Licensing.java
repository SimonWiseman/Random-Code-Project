package wiseman.licensing;

import java.util.Scanner;

import wiseman.licensing.constants.LicensingConstants;
import wiseman.licensing.model.TaxiDriver;

/**
 * Licensing
 * @author Simon Wiseman
 */
public class Licensing {
	
	private static final String ANSI_CLEAR_SCREEN = "\u001b[2J";
	private static final String ANSI_CURSOR_HOME = "\u001b[H";
	
	/**
	 * private constructor
	 */
	private Licensing() {
	}
	
	/**
	 * main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		licensingMain();
	}
	
	/**
	 * Licensing main method
	 */
	static void licensingMain() {
		
		Scanner userInput = new Scanner(System.in);
		boolean appRunning = true;
		
		while (appRunning) {
	        clearScreenAndCursorHome();
	        
			System.out.println(LicensingConstants.TAXI_LICENSE_CALCULATOR);
			
			TaxiDriver driver = new TaxiDriver();
			
			driver.setNewDriver(getYesOrNoAnswer(LicensingConstants.NEW_DRIVER, userInput));
			
			if (driver.isNewDriver()) {
				driver.setOver18(isDriverOver18(userInput));
				if (driver.isOver18()) {
					driver.setDvlaLicensed(isDriverDvlaLicensed(userInput));
				}
				driver.setLicenseDurationInYears(LicensingConstants.NEW_DRIVER_1_YEAR_ONLY);
			} else {
				driver.setLicenseDurationInYears(getAnswer1To3(LicensingConstants.NUMBER_OF_YEARS, userInput));
			}
			
			if (!driver.isNewDriver() || (driver.isOver18() && driver.isDvlaLicensed())) {
				calculateAndDisplayFees(driver);
			}
			
			if (exitRequested(userInput)) {
				appRunning = false;
			}
		}
		System.out.println(LicensingConstants.YOU_ENTERED_X_AND_HAVE_NOW_EXITED_THIS_PROGRAM);
	}

	/**
	 * Clear screen and cursor home
	 */
	static void clearScreenAndCursorHome() {
		System.out.print(ANSI_CLEAR_SCREEN + ANSI_CURSOR_HOME);
		System.out.flush();
	}

	/**
	 * Is the driver over 18?
	 * 
	 * @param userInput
	 * @return
	 */
	static boolean isDriverOver18(final Scanner userInput) {
		boolean over18 = getYesOrNoAnswer(LicensingConstants.IS_THE_DRIVER_18_OR_OVER, userInput);
		if (!over18) {
			System.out.print(LicensingConstants.DRIVER_NOT_ELIGIBLE);
			System.out.println(LicensingConstants.DRIVER_MUST_BE_18_OR_OVER);
		}
		return over18;
	}

	/**
	 * Is the driver DVLA Licensed?
	 * 
	 * @param userInput
	 * @return
	 */
	static boolean isDriverDvlaLicensed(final Scanner userInput) {
		boolean dvlaLicense = getYesOrNoAnswer(LicensingConstants.DOES_THE_DRIVER_HOLD_A_FULL_DVLA_DRIVING_LICENSE, userInput);
		if (!dvlaLicense) {
			System.out.print(LicensingConstants.DRIVER_NOT_ELIGIBLE);
			System.out.println(LicensingConstants.DRIVER_MUST_HOLD_A_FULL_DVLA_DRIVING_LICENSE);
		}
		return dvlaLicense;
	}

	/**
	 * Get a yes or no reply
	 * 
	 * @param question
	 * @param userInput
	 * @return
	 */
	static boolean getYesOrNoAnswer(final String question, final Scanner userInput) {
		boolean validAnswer = false;
		boolean isAnswerYes = false;
		while (!validAnswer) {
			System.out.print(question);
			String inputText = userInput.nextLine().trim();
			
			if (LicensingConstants.ANSWER_Y.equalsIgnoreCase(inputText)) {
				validAnswer = true;
				isAnswerYes = true;
			} else if (LicensingConstants.ANSWER_N.equalsIgnoreCase(inputText)) {
				validAnswer = true;
				isAnswerYes = false;
			} else {
				System.out.println(LicensingConstants.ERROR_PLEASE_ENTER_Y_OR_N);
			}
		}
		return isAnswerYes;
	}

	/**
	 * Get the answer 1, 2 or 3
	 * 
	 * @param question
	 * @param userInput
	 * @return
	 */
	static int getAnswer1To3(final String question, final Scanner userInput) {
		int answer = 0;
		while (answer < 1 || answer > 3) {
			System.out.print(question);
			answer = getUserInputAsNumber(userInput);
			if (answer < 1 || answer > 3) {
				System.out.println(LicensingConstants.ERROR_PLEASE_ENTER_1_2_OR_3);
			}
		}
		return answer;
	}

	/**
	 * Get user input as number
	 * 
	 * @param userInput
	 * @return
	 */
	static int getUserInputAsNumber(final Scanner userInput) {
		int number;
		String inputText = userInput.nextLine().trim();
		try {
			number = Integer.parseInt(inputText);
		} catch (NumberFormatException e) {
			number = 0;
		}
		return number;
	}

	/**
	 * Calculate and display fees
	 * 
	 * @param driver
	 */
	static double calculateAndDisplayFees(final TaxiDriver driver) {
			
			double netTotalFee = 0;
			
			System.out.println();
			System.out.println(LicensingConstants.HORIZONTAL_LINE);
			
			if (driver.isNewDriver()) {
				System.out.printf(LicensingConstants.NEW_DRIVER_APPLICATION_FEE, LicensingConstants.AMOUNT_NEW_DRIVER_FEE);
				netTotalFee += LicensingConstants.AMOUNT_NEW_DRIVER_FEE;
			}
			
			driver.setGrossLicenseCost(calculateGrossLicenseCost(driver));
			
			netTotalFee += driver.getGrossLicenseCost();
			
			netTotalFee -= calculateDiscount(driver);
			
			System.out.println(LicensingConstants.HORIZONTAL_LINE);
			System.out.printf(LicensingConstants.TOTAL_NET_FEE, netTotalFee);
			System.out.println(LicensingConstants.HORIZONTAL_LINE);
			
			return netTotalFee;
	}

	/**
	 * Calculate gross license cost
	 * 
	 * @param driver
	 * @return
	 */
	static double calculateGrossLicenseCost(final TaxiDriver driver) {
		System.out.printf(LicensingConstants.LICENCE_COST_PER_YEAR, LicensingConstants.AMOUNT_LICENSE_COST_PER_YEAR);
		
		double grossLicenseCost = LicensingConstants.AMOUNT_LICENSE_COST_PER_YEAR * driver.getLicenseDurationInYears();
		System.out.printf(LicensingConstants.AMOUNT, grossLicenseCost);
		return grossLicenseCost;
	}

	/**
	 * Calculate discount
	 * 
	 * @param driver
	 * @return
	 */
	static double calculateDiscount(final TaxiDriver driver) {
		double discount = 0;
		if (driver.getLicenseDurationInYears() == 2) {
			discount = driver.getGrossLicenseCost()/20;
			System.out.printf(LicensingConstants.PERCENTAGE_DISCOUNT_FOR_2_YEARS, discount);
		} else if (driver.getLicenseDurationInYears() == 3) {
			discount = driver.getGrossLicenseCost()/10;
			System.out.printf(LicensingConstants.PERCENTAGE_DISCOUNT_FOR_3_YEARS, discount);
		}
		return discount;
	}

	/**
	 * Does the user wish to exit?
	 * 
	 * @param userInput
	 * @return
	 */
	static boolean exitRequested(final Scanner userInput) {
		boolean exitRequested = false;
		System.out.print(LicensingConstants.ENTER_X_TO_EXIT_OR_ANY_OTHER_KEY_TO_CONTINUE);
		if (LicensingConstants.ANSWER_X.equalsIgnoreCase(userInput.nextLine().trim())) {
			exitRequested = true;
		}
		return exitRequested;
	}
	
}