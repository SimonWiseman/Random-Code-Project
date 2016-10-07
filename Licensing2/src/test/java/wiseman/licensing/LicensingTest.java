package wiseman.licensing;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import wiseman.licensing.constants.LicensingConstants;
import wiseman.licensing.model.TaxiDriver;

/**
 * Licensing Test
 * @author Simon Wiseman
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Scanner.class)
public class LicensingTest {

	private ByteArrayInputStream inputContent;
	private ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
	
	private Scanner scanner;
	
	private TaxiDriver driver;

	@Before
	public void setUp() {
		// Reassign input stream
		System.setIn(inputContent);
		// Reassign output stream
		System.setOut(new PrintStream(outputContent));
		
		driver = new TaxiDriver();
	}
	
	@After
	public void resetInputAndOutputStreams() {
		// Reset input stream
		System.setIn(System.in);
		// Reset output stream
		System.setOut(null);
	}
/*
	@Test
	public void licensingMain_WhenNewDriver_ThenLicenseDurationIs1Year() throws Exception {
		Scanner mockScanner = PowerMockito.mock(Scanner.class);
//		PowerMockito.when(Licensing.getYesOrNoAnswer(Mockito.anyString(), mockScanner)).thenReturn(true);
//		PowerMockito.when(mockScanner.nextLine().trim()).thenReturn("Y");
		
		
		inputContent = new ByteArrayInputStream("Y".getBytes());
		mockScanner = new Scanner(inputContent);
//		driver.setNewDriver(Licensing.getYesOrNoAnswer(LicensingConstants.NEW_DRIVER, scanner));
		PowerMockito.when(mockScanner.nextLine().trim()).thenReturn("Y");

		
		Licensing.licensingMain(scanner, true);
		Assert.assertEquals(1, driver.getLicenseDurationInYears());
	}
	*/
	@Test
	public void isDriverOver18_WhenEnteredTextIsY_ThenReturnTrue() {
		inputContent = new ByteArrayInputStream("Y".getBytes());
		scanner = new Scanner(inputContent);
		boolean response = Licensing.isDriverOver18(scanner);
		Assert.assertTrue("User entered 'Y' to over 18, but returned false", response);
	}
	
	@Test
	public void isDriverOver18_WhenEnteredTextIsN_ThenReturnFalse() {
		inputContent = new ByteArrayInputStream("N".getBytes());
		scanner = new Scanner(inputContent);
		boolean response = Licensing.isDriverOver18(scanner);
		Assert.assertFalse("User entered 'N' to over 18, but returned true", response);
	}
	
	@Test
	public void isDriverDvlaLicensed_WhenEnteredTextIsY_ThenReturnTrue() {
		inputContent = new ByteArrayInputStream("Y".getBytes());
		scanner = new Scanner(inputContent);
		boolean response = Licensing.isDriverDvlaLicensed(scanner);
		Assert.assertTrue("User entered 'Y' to DVLA licensed, but returned false", response);
	}
	
	@Test
	public void isDriverDvlaLicensed_WhenEnteredTextIsN_ThenReturnFalse() {
		inputContent = new ByteArrayInputStream("N".getBytes());
		scanner = new Scanner(inputContent);
		boolean response = Licensing.isDriverDvlaLicensed(scanner);
		Assert.assertFalse("User entered 'N' to DVLA licensed, but returned true", response);
	}
	
	@Test
	public void getYesOrNoAnswer_WhenEnteredTextIsY_ThenReturnTrue() {
		inputContent = new ByteArrayInputStream("Y".getBytes());
		scanner = new Scanner(inputContent);
		boolean response = Licensing.getYesOrNoAnswer(LicensingConstants.NEW_DRIVER, scanner);
		Assert.assertTrue("User entered 'Y' to question, but returned false", response);
	}
	
	@Test
	public void getYesOrNoAnswer_WhenEnteredTextIsN_ThenReturnFalse() {
		inputContent = new ByteArrayInputStream("N".getBytes());
		scanner = new Scanner(inputContent);
		boolean response = Licensing.getYesOrNoAnswer(LicensingConstants.NEW_DRIVER, scanner);
		Assert.assertFalse("User entered 'N' to question, but returned true", response);
	}
	
	@Test
	public void getAnswer1To3_WhenEnteredTextIsNumberBetween1And3_ThenReturnNumberAsInteger() {
		inputContent = new ByteArrayInputStream("2".getBytes());
		scanner = new Scanner(inputContent);
		int number = Licensing.getAnswer1To3(LicensingConstants.NUMBER_OF_YEARS, scanner);
		Assert.assertEquals("User entered '2', but returned another integer", 2, number);
	}
	
	@Test
	public void getUserInputAsNumber_WhenEnteredTextIsNotNumber_ThenReturnZero() {
		inputContent = new ByteArrayInputStream("random text".getBytes());
		scanner = new Scanner(inputContent);
		int number = Licensing.getUserInputAsNumber(scanner);
		Assert.assertEquals("User entered 'text', but did not return 0", 0, number);
	}
	
	@Test
	public void getUserInputAsNumber_WhenEnteredTextIsNumber_ThenReturnNumberAsInteger() {
		inputContent = new ByteArrayInputStream("925".getBytes());
		scanner = new Scanner(inputContent);
		int number = Licensing.getUserInputAsNumber(scanner);
		Assert.assertEquals("User entered '925', but returned another integer", 925, number);
	}
	
	@Test
	public void calculateAndDisplayFees_WhenNewDriver_ThenNetFeeIs1YearPlusNewDriverFee() {
		driver.setNewDriver(true);
		driver.setLicenseDurationInYears(LicensingConstants.NEW_DRIVER_1_YEAR_ONLY);
		double netTotalFee = Licensing.calculateAndDisplayFees(driver);
		Assert.assertEquals("Total net fee for new driver is incorrect", LicensingConstants.AMOUNT_LICENSE_COST_PER_YEAR+LicensingConstants.AMOUNT_NEW_DRIVER_FEE, netTotalFee, 0);
	}
	
	@Test
	public void calculateAndDisplayFees_WhenLicenseRenewalIs1Year_ThenNetFeeIs1Year() {
		driver.setNewDriver(false);
		driver.setLicenseDurationInYears(1);
		double netTotalFee = Licensing.calculateAndDisplayFees(driver);
		Assert.assertEquals("Total net fee for 1 year renewal is incorrect", LicensingConstants.AMOUNT_LICENSE_COST_PER_YEAR, netTotalFee, 0);
	}
	
	@Test
	public void calculateAndDisplayFees_WhenLicenseRenewalIs2Years_ThenNetFeeIs2YearsMinus5Percent() {
		driver.setNewDriver(false);
		driver.setLicenseDurationInYears(2);
		double netTotalFee = Licensing.calculateAndDisplayFees(driver);
		Assert.assertEquals("Total net fee for 2 year renewal is incorrect", 2*LicensingConstants.AMOUNT_LICENSE_COST_PER_YEAR*.95, netTotalFee, 0);
	}
	
	@Test
	public void calculateAndDisplayFees_WhenLicenseRenewalIs3Years_ThenNetFeeIs3YearsMinus10Percent() {
		driver.setNewDriver(false);
		driver.setLicenseDurationInYears(3);
		double netTotalFee = Licensing.calculateAndDisplayFees(driver);
		Assert.assertEquals("Total net fee for 3 year renewal is incorrect", 3*LicensingConstants.AMOUNT_LICENSE_COST_PER_YEAR*.9, netTotalFee, 0);
	}
	
	@Test
	public void calculateGrossLicenseCost_WhenLicenseDurationIs1Year_ThenGrossLicenseCostIsYearlyCost() {
		driver.setLicenseDurationInYears(1);
		double grossLicenseCost = Licensing.calculateGrossLicenseCost(driver);
		Assert.assertEquals("Gross license cost for 1 year is incorrect", LicensingConstants.AMOUNT_LICENSE_COST_PER_YEAR, grossLicenseCost, 0);
	}
	
	@Test
	public void calculateGrossLicenseCost_WhenLicenseDurationIs2Years_ThenGrossLicenseCostIs2xYearlyCost() {
		driver.setLicenseDurationInYears(2);
		double grossLicenseCost = Licensing.calculateGrossLicenseCost(driver);
		Assert.assertEquals("Gross license cost for 2 years is incorrect", 2*LicensingConstants.AMOUNT_LICENSE_COST_PER_YEAR, grossLicenseCost, 0);
	}
	
	@Test
	public void calculateGrossLicenseCost_WhenLicenseDurationIs3Years_ThenGrossLicenseCostIs3xYearlyCost() {
		driver.setLicenseDurationInYears(3);
		double grossLicenseCost = Licensing.calculateGrossLicenseCost(driver);
		Assert.assertEquals("Gross license cost for 3 years is incorrect", 3*LicensingConstants.AMOUNT_LICENSE_COST_PER_YEAR, grossLicenseCost, 0);
	}
	
	@Test
	public void calculateDiscount_WhenLicenseDurationIs1Year_ThenDiscountIsZero() {
		driver.setLicenseDurationInYears(1);
		driver.setGrossLicenseCost(100);
		double discount = Licensing.calculateDiscount(driver);
		Assert.assertEquals("License is for 1 year, but discount is not 0", 0, discount, 0);
	}
	
	@Test
	public void calculateDiscount_WhenLicenseDurationIs2Years_ThenDiscountIs5PercentOfGrossAmount() {
		driver.setLicenseDurationInYears(2);
		driver.setGrossLicenseCost(100);
		double discount = Licensing.calculateDiscount(driver);
		Assert.assertEquals("License is for 2 years, but discount is not 5 percent", 5, discount, 0);
	}
	
	@Test
	public void calculateDiscount_WhenLicenseDurationIs3Years_ThenDiscountIs10PercentOfGrossAmount() {
		driver.setLicenseDurationInYears(3);
		driver.setGrossLicenseCost(100);
		double discount = Licensing.calculateDiscount(driver);
		Assert.assertEquals("License is for 3 years, but discount is not 10 percent", 10, discount, 0);
	}
	
	@Test
	public void calculateDiscount_WhenLicenseDurationIsInvalidNumberOfYears_ThenDiscountIsZero() {
		driver.setLicenseDurationInYears(4);
		driver.setGrossLicenseCost(100);
		double discount = Licensing.calculateDiscount(driver);
		Assert.assertEquals("License is for invalid number of years, but discount is not 0", 0, discount, 0);
	}
	
	@Test
	public void exitRequested_WhenEnteredTextIsX_ThenReturnTrue() {
		inputContent = new ByteArrayInputStream("X".getBytes());
		scanner = new Scanner(inputContent);
		boolean exitRequested = Licensing.exitRequested(scanner);
		Assert.assertTrue("User entered 'X' to exit, but returned false", exitRequested);
	}
	
	@Test
	public void exitRequested_WhenEnteredTextIsNotX_ThenReturnFalse() {
		inputContent = new ByteArrayInputStream("E".getBytes());
		scanner = new Scanner(inputContent);
		boolean exitRequested = Licensing.exitRequested(scanner);
		Assert.assertFalse("User entered something other than 'X', but returned true", exitRequested);
	}
	
}
