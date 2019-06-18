package redone.game.content;

import java.util.Calendar;
import java.util.GregorianCalendar;

import redone.game.players.Client;
import redone.util.Misc;

/**
 * Handles the BankPin on Bank's
 * 
 * @author Michael
 * @author Ian / Core
 * @author Linus
 * @author Genesis
 */

public class BankPin {

	public int recovery_Delay = 3;

	private final Client client;

	public BankPin(Client client) {
		this.client = client;
	}

	public int allowTimer = 2000000;

	public int dateExpired() {
		return client.pinDeleteDateRequested = dateRequested() + recovery_Delay;
	}

	public int dateRequested() {
		Calendar cal = new GregorianCalendar();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		return client.pinDeleteDateRequested = year * 10000 + month * 100 + day;
	}

	public void closeBankPin() {
		firstPin = secondPin = thirdPin = fourthPin = client.playerBankPin = 0;
		falseButtons();
		client.getPlayerAssistant().removeAllWindows();
	}

	public void pinSettingFrames() {
		int pinSendFrames[] = { 15075, 15080, 15110, 15171, 15076, 15176,
				15104, 15082, 15079 };
		for (int j = 0; j < 9; j++) {
			client.getPlayerAssistant().sendFrame126("", pinSendFrames[j]);
		}
		client.getPlayerAssistant().sendFrame126("Welcome to our bank", 15038);
		client.getPlayerAssistant().sendFrame126("recovery system.", 15039);
		client.getPlayerAssistant().sendFrame126("Remember, it's important",
				15040);
		client.getPlayerAssistant()
				.sendFrame126("to change your recovery", 15041);
		client.getPlayerAssistant().sendFrame126("pin and password", 15042);
		client.getPlayerAssistant().sendFrame126("every 1-3 months", 15043);
		if (!client.hasBankpin) {
			client.getPlayerAssistant().sendFrame126("Set a Bank Pin", 15078);
			client.getPlayerAssistant().sendFrame126("No PIN Set", 15105);
		} else {
			client.getPlayerAssistant().sendFrame126("Delete your PIN", 15078);
			if (client.requestPinDelete) {
				client.getPlayerAssistant().sendFrame126("Pending delete", 15105);
			} else {
				client.getPlayerAssistant().sendFrame126("Has Bank PIN", 15105);
			}
		}
		client.getPlayerAssistant().sendFrame126(recovery_Delay + " days", 15107);
	}

	public void bankPinSettings() {
		pinSettingFrames();
		client.getPlayerAssistant().showInterface(14924);
	}

	private int resetBankNumbers() {
		return firstPin = secondPin = thirdPin = fourthPin = client.playerBankPin = client.firstPin = client.secondPin = client.thirdPin = client.fourthPin = -1;
	}

	public boolean resetBankPin() {
		resetBankNumbers();
		falseButtons();
		client.getPlayerAssistant().closeAllWindows();
		return client.hasBankpin = false;
	}

	public void bankPinEnter(int button) {
		if (allowTimer > 0 && allowTimer <= 300000) {
			int time = allowTimer / 6000;
			if (time >= 2) {
				client.getActionSender()
						.sendMessage(
								"Please wait "
										+ time
										+ " minutes before attempting your bank pin again.");
			} else if (time == 1) {
				client.getActionSender()
						.sendMessage(
								"Please wait "
										+ time
										+ " minute before attempting your bank pin again.");
			} else if (time <= 0) {
				client.getActionSender()
						.sendMessage(
								"Please wait less "
										+ "than a minute before attempting your bank pin again.");
			}
			return;
		}
		sendPins();
		if (!client.firstPinEnter) {
			handleButtonOne(button);
		} else if (!client.secondPinEnter) {
			handleButtonTwo(button);
		} else if (!client.thirdPinEnter) {
			handleButtonThree(button);
		} else if (!client.fourthPinEnter) {
			handleButtonFour(button);
		}
	}

	public void openPin() {
		if (client.enterdBankpin) {
			client.getPlayerAssistant().openUpBank();
			return;
		}
		randomizeNumbers();
		client.getPlayerAssistant().sendFrame126("First click the FIRST digit",
				15313);
		client.getPlayerAssistant().sendFrame126("", 14923);
		int pinSendFrames[] = { 14913, 14914, 14915, 14916 };
		for (int j = 0; j < 4; j++) {
			client.getPlayerAssistant().sendFrame126("?", pinSendFrames[j]);
		}
		client.getPlayerAssistant().showInterface(7424);
		sendPins();
	}

	private void sendPins() {
		if (client.enterdBankpin) {
			client.getPlayerAssistant().openUpBank();
			return;
		}
		for (int i = 0; i < getBankPins().length; i++) {
			client.getPlayerAssistant().sendFrame126("" + getBankPins()[i],
					stringIds[i]);
		}
	}

	private void handleButtonOne(int button) {
		client.getPlayerAssistant().sendFrame126("Now click the SECOND digit",
				15313);
		client.getPlayerAssistant().sendFrame126("*", 14913);
		for (int i = 0; i < getActionButtons().length; i++) {
			if (getActionButtons()[i] == button) {
				firstPin = getBankPins()[i];
			}
		}
		client.firstPinEnter = true;
		randomizeNumbers();
	}

	private void handleButtonTwo(int button) {
		client.getPlayerAssistant().sendFrame126("Now click the THIRD digit",
				15313);
		client.getPlayerAssistant().sendFrame126("*", 14914);
		for (int i = 0; i < getActionButtons().length; i++) {
			if (getActionButtons()[i] == button) {
				secondPin = getBankPins()[i];
			}
		}
		client.secondPinEnter = true;
		randomizeNumbers();
	}

	private void handleButtonThree(int button) {
		client.getPlayerAssistant().sendFrame126("Now click the LAST digit",
				15313);
		client.getPlayerAssistant().sendFrame126("*", 14915);
		for (int i = 0; i < getActionButtons().length; i++) {
			if (getActionButtons()[i] == button) {
				thirdPin = getBankPins()[i];
			}
		}
		client.thirdPinEnter = true;
		randomizeNumbers();
	}

	private boolean falseButtons() {
		return client.fourthPinEnter = client.thirdPinEnter = client.secondPinEnter = client.firstPinEnter = false;
	}

	private void handleButtonFour(int button) {
		if (client.enterdBankpin) {
			client.getPlayerAssistant().openUpBank();
			return;
		}
		client.getPlayerAssistant().sendFrame126("*", 14916);
		for (int i = 0; i < getActionButtons().length; i++) {
			if (getActionButtons()[i] == button) {
				fourthPin = getBankPins()[i];
			}
		}
		client.fourthPinEnter = true;
		if (!client.hasBankpin) {
			client.firstPin = client.bankPin1 = firstPin;
			client.secondPin = client.bankPin2 = secondPin;
			client.thirdPin = client.bankPin3 = thirdPin;
			client.fourthPin = client.bankPin4 = fourthPin;
			client.hasBankpin = client.enterdBankpin = true;
			client.getActionSender().sendMessage(
					"You have just created a bank pin.");
			client.getActionSender().sendMessage(
					"Your new Bank PIN is: " + firstPin + " - " + secondPin
							+ " - " + thirdPin + " - " + fourthPin);
			client.saveCharacter = true;
		}

		int one = firstPin, two = secondPin, three = thirdPin, four = fourthPin;
		if (client.bankPin1 == one && client.bankPin2 == two
				&& client.bankPin3 == three && client.bankPin4 == four) {
			falseButtons();
			client.getPlayerAssistant().removeAllWindows();
			client.enterdBankpin = true;
			client.playerBankPin = 15000;
			client.getPlayerAssistant().openUpBank();

		} else {
			client.attemptsRemaining--;
			if (client.attemptsRemaining <= 0) {
				allowTimer = 30000;
			}
			if (client.attemptsRemaining == -1) {
				client.attemptsRemaining = 3;
				allowTimer = 2000000;
			}
			if (client.attemptsRemaining > 1) {
				client.getActionSender().sendMessage(
						"Invalid pin. You have " + client.attemptsRemaining
								+ " attempts remaining.");
			} else if (client.attemptsRemaining == 1) {
				client.getActionSender().sendMessage(
						"Invalid pin. You have " + client.attemptsRemaining
								+ " attempt remaining.");
			} else if (client.attemptsRemaining <= 0) {
				client.getActionSender()
						.sendMessage(
								"Invalid pin. You must wait 5 minutes before attempting again.");
			}
			client.getPlayerAssistant().removeAllWindows();
			falseButtons();
		}
	}

	private void randomizeNumbers() {
		int i = Misc.random(4);
		if (i == client.lastPinSettings) {
			i = client.lastPinSettings == 0 ? client.lastPinSettings
					: client.lastPinSettings;
		}
		switch (i) {
		case 0:
			bankPins[0] = 1;
			bankPins[1] = 7;
			bankPins[2] = 0;
			bankPins[3] = 8;
			bankPins[4] = 4;
			bankPins[5] = 6;
			bankPins[6] = 5;
			bankPins[7] = 9;
			bankPins[8] = 3;
			bankPins[9] = 2;
			break;

		case 1:
			bankPins[0] = 5;
			bankPins[1] = 4;
			bankPins[2] = 3;
			bankPins[3] = 7;
			bankPins[4] = 8;
			bankPins[5] = 6;
			bankPins[6] = 9;
			bankPins[7] = 2;
			bankPins[8] = 1;
			bankPins[9] = 0;
			break;

		case 2:
			bankPins[0] = 4;
			bankPins[1] = 7;
			bankPins[2] = 6;
			bankPins[3] = 5;
			bankPins[4] = 2;
			bankPins[5] = 3;
			bankPins[6] = 1;
			bankPins[7] = 8;
			bankPins[8] = 9;
			bankPins[9] = 0;
			break;

		case 3:
			bankPins[0] = 9;
			bankPins[1] = 4;
			bankPins[2] = 2;
			bankPins[3] = 7;
			bankPins[4] = 8;
			bankPins[5] = 6;
			bankPins[6] = 0;
			bankPins[7] = 3;
			bankPins[8] = 1;
			bankPins[9] = 5;
			break;
		}
		client.lastPinSettings = i;
		sendPins();
	}

	private final int bankPins[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	private final int stringIds[] = { 14883, 14884, 14885, 14886, 14887, 14888,
			14889, 14890, 14891, 14892 };
	private final int actionButtons[] = { 58025, 58026, 58027, 58028, 58029,
			58030, 58031, 58032, 58033, 58034 };

	private int[] getBankPins() {
		return bankPins;
	}

	private int[] getActionButtons() {
		return actionButtons;
	}

	private int firstPin;
	private int secondPin;
	private int thirdPin;
	private int fourthPin;

	@Override
	public String toString() {
		return "BankPin{" + "recovery_Delay=" + recovery_Delay + ", client="
				+ client + ", allowTimer=" + allowTimer + ", bankPins="
				+ bankPins + ", stringIds=" + stringIds + ", actionButtons="
				+ actionButtons + ", firstPin=" + firstPin + ", secondPin="
				+ secondPin + ", thirdPin=" + thirdPin + ", fourthPin="
				+ fourthPin + '}';
	}
}
