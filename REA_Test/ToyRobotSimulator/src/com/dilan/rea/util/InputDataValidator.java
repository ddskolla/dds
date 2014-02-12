package com.dilan.rea.util;

import com.dilan.rea.api.Command;
import com.dilan.rea.api.Direction;
import com.dilan.rea.dto.InputDataDto;
import com.dilan.rea.exception.InvalidInputDataException;

/**
 * Validates the data read from the file.
 * @author Dilan
 *
 */
public class InputDataValidator {

	public InputDataDto validateInputString(String inputString)
			throws InvalidInputDataException {

		InputDataDto inputData = new InputDataDto();
		inputString = inputString.trim();
		String[] inputArray = inputString.split(" ");

		if (inputArray[0].equalsIgnoreCase(Command.PLACE.toString())) {

			validateCoordinates(inputArray[1], inputData);
			inputData.setCommand(Command.PLACE);

		} else if (inputArray[0].equalsIgnoreCase(Command.MOVE.toString())) {

			inputData.setCommand(Command.MOVE);

		} else if (inputArray[0].equalsIgnoreCase(Command.LEFT.toString())) {

			inputData.setCommand(Command.LEFT);

		} else if (inputArray[0].equalsIgnoreCase(Command.RIGHT.toString())) {

			inputData.setCommand(Command.RIGHT);

		} else if (inputArray[0].equalsIgnoreCase(Command.REPORT.toString())) {
			
			inputData.setCommand(Command.REPORT);
			
		} else {

			throw new InvalidInputDataException(
					"Commands are malformed, Please check and try again");

		}

		return inputData;

	}

	private void validateCoordinates(String coordinates, InputDataDto inputData)
			throws InvalidInputDataException {

		String[] coordinateArray = coordinates.split(",");
		if (coordinateArray.length != 3) {
			throw new InvalidInputDataException(
					"Coordinates entered with the PLACE command are malformed, Please check and try again");
		} else if ((coordinateArray[2].equalsIgnoreCase(Direction.NORTH
				.toString()))
				|| (coordinateArray[2].equalsIgnoreCase(Direction.EAST
						.toString()))
				|| (coordinateArray[2].equalsIgnoreCase(Direction.SOUTH
						.toString()))
				|| (coordinateArray[2].equalsIgnoreCase(Direction.WEST
						.toString()))) {

			populateInputData(coordinateArray, inputData);
		} else {
			throw new InvalidInputDataException(
					"The facing direction entered with the PLACE command is incorrect, Please check and try again");
		}

	}

	private void populateInputData(String[] coordinateArray,
			InputDataDto inputData) {

		inputData.setPositionX(Integer.parseInt(coordinateArray[0]));
		inputData.setPositionY(Integer.parseInt(coordinateArray[1]));

		if (coordinateArray[2].equalsIgnoreCase(Direction.NORTH.toString())) {
			inputData.setFacing(Direction.NORTH);
		} else if (coordinateArray[2].equalsIgnoreCase(Direction.EAST
				.toString())) {
			inputData.setFacing(Direction.EAST);
		} else if (coordinateArray[2].equalsIgnoreCase(Direction.SOUTH
				.toString())) {
			inputData.setFacing(Direction.SOUTH);
		} else if (coordinateArray[2].equalsIgnoreCase(Direction.WEST
				.toString())) {
			inputData.setFacing(Direction.WEST);
		}

	}
}
