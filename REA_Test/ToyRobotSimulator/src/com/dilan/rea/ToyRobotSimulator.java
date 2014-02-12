package com.dilan.rea;

import com.dilan.rea.api.Command;
import com.dilan.rea.api.Robot;
import com.dilan.rea.dto.InputDataDto;
import com.dilan.rea.exception.InvalidInputDataException;
import com.dilan.rea.util.Grid;
import com.dilan.rea.util.InputDataValidator;
import com.dilan.rea.util.InputReader;

public class ToyRobotSimulator {

	public static void main(String[] args) {

		//Reads commands from a file.
		InputReader inputReader = new InputReader();
		//Validates the data on the file for formatting/data issues.
		InputDataValidator dataValidator = new InputDataValidator();
		//Does command validation and performs actions.
		Grid grid = new Grid(5);

		try {
			
			String inputString = inputReader
					.readInputFile("C:\\Users\\Dilan\\workspace\\ToyRobotSimulator\\InputData1.txt");

			String[] commands = inputString.split(":");
			
			//Iterating through the command array and performing the tasks.
			for (int a = 0; a < commands.length; a++) {
				
				//Perform input validations.
				InputDataDto inputDataDto = dataValidator
						.validateInputString(commands[a]);

				//Validate the commands and perform tasks.
				Robot robot = grid.validateAndPerformCommands(inputDataDto);

				//Handle the report command
				if (grid.isRobotStatus()
						&& inputDataDto.getCommand().equals(Command.REPORT)) {

					robot.reportCoordinates();

				}
			}

		} catch (InvalidInputDataException e) {
			
			e.printStackTrace();
		}

	}
}
