package com.dilan.rea.util;

import com.dilan.rea.api.Command;
import com.dilan.rea.api.Direction;
import com.dilan.rea.api.Robot;
import com.dilan.rea.dto.InputDataDto;

/**
 * Grid class is used to validate the commands issued to the Robot and to
 * perform those tasks.
 * 
 * @author Dilan
 */
public class Grid {

	private int[][] gridMap;
	private int gridSize;
	//Used to check if a correct PLACE command has been issued
	private boolean robotStatus = false;

	public Grid(int gridSize) {

		gridMap = new int[gridSize][gridSize];
		this.gridSize = gridSize;

	}

	/**
	 * Validates the PLACE command and calls the validate methods for MOVE,LEFT&RIGHT commands.
	 * @param inputDataDto - DTO with the input data from the file.
	 * @return - Live robot Object.
	 */
	public Robot validateAndPerformCommands(InputDataDto inputDataDto) {

		Robot robot = Robot.getInstance();

		if (inputDataDto.getCommand().equals(Command.PLACE)) {

			if (!(inputDataDto.getPositionX() >= 0 && inputDataDto
					.getPositionX() <= 5)
					|| (inputDataDto.getPositionY() >= 0 && inputDataDto
							.getPositionY() <= 5)) {

				gridMap[inputDataDto.getPositionX()][inputDataDto
						.getPositionY()] = 1;
				robotStatus = true;
				robot.setPositionX(inputDataDto.getPositionX());
				robot.setPositionY(inputDataDto.getPositionY());
				robot.setFacing(inputDataDto.getFacing());
			}

		} else if (robotStatus
				&& inputDataDto.getCommand().equals(Command.MOVE)) {

			validateMoveCommand(inputDataDto, robot);

			robot.setPositionX(inputDataDto.getPositionX());
			robot.setPositionY(inputDataDto.getPositionY());

		} else if (robotStatus
				&& inputDataDto.getCommand().equals(Command.LEFT)) {

			performLeftCommand(inputDataDto, robot);

		} else if (robotStatus
				&& inputDataDto.getCommand().equals(Command.RIGHT)) {

			performRightCommand(inputDataDto, robot);

		}

		return robot;

	}

	/**
	 * Validates if the Move command can be carried out without the Robot falling off the grid.
	 * @param inputDataDto - DTO with the input data from the file.
	 * @param robot - Live Robot object.
	 */
	private void validateMoveCommand(InputDataDto inputDataDto, Robot robot) {

		int[] currentCoordinates = searchElementInGrid(1);
		int currentX = currentCoordinates[0];
		int currentY = currentCoordinates[1];
		int newX;
		int newY;

		if (robot.getFacing().equals(Direction.EAST)) {

			newX = currentX + 1;
			newY = currentY;
			try {
				gridMap[newX][newY] = 1;
				gridMap[currentX][currentY] = 0;

				inputDataDto.setPositionX(newX);
				inputDataDto.setPositionY(newY);
			} catch (Exception e) {
				// e.printStackTrace();
				inputDataDto.setPositionX(currentX);
				inputDataDto.setPositionY(currentY);
			}

		} else if (robot.getFacing().equals(Direction.NORTH)) {

			newX = currentX;
			newY = currentY + 1;
			try {
				gridMap[newX][newY] = 1;
				gridMap[currentX][currentY] = 0;

				inputDataDto.setPositionX(newX);
				inputDataDto.setPositionY(newY);
			} catch (Exception e) {
				// e.printStackTrace();
				inputDataDto.setPositionX(currentX);
				inputDataDto.setPositionY(currentY);
			}

		} else if (robot.getFacing().equals(Direction.WEST)) {

			newX = currentX - 1;
			newY = currentY;
			try {
				gridMap[newX][newY] = 1;
				gridMap[currentX][currentY] = 0;

				inputDataDto.setPositionX(newX);
				inputDataDto.setPositionY(newY);
			} catch (Exception e) {
				// e.printStackTrace();
				inputDataDto.setPositionX(currentX);
				inputDataDto.setPositionY(currentY);
			}

		} else if (robot.getFacing().equals(Direction.SOUTH)) {

			newX = currentX;
			newY = currentY - 1;
			try {
				gridMap[newX][newY] = 1;
				gridMap[currentX][currentY] = 0;

				inputDataDto.setPositionX(newX);
				inputDataDto.setPositionY(newY);
			} catch (Exception e) {
				// e.printStackTrace();
				inputDataDto.setPositionX(currentX);
				inputDataDto.setPositionY(currentY);
			}
		}

	}

	/**
	 * Performs the Left turn command based on the direction the Robot is facing.
	 * @param inputDataDto - DTO with the input data from the file.
	 * @param robot - Live Robot object.
	 */
	private void performLeftCommand(InputDataDto inputDataDto, Robot robot) {

		if (robot.getFacing().equals(Direction.EAST)) {

			robot.setFacing(Direction.NORTH);

		} else if (robot.getFacing().equals(Direction.NORTH)) {

			robot.setFacing(Direction.WEST);

		} else if (robot.getFacing().equals(Direction.WEST)) {

			robot.setFacing(Direction.SOUTH);

		} else if (robot.getFacing().equals(Direction.SOUTH)) {

			robot.setFacing(Direction.EAST);
		}
	}

	/**
	 * Performs the Right turn command based on the direction the Robot is facing.
	 * @param inputDataDto - DTO with the input data from the file.
	 * @param robot - Live Robot object.
	 */
	private void performRightCommand(InputDataDto inputDataDto, Robot robot) {

		if (robot.getFacing().equals(Direction.EAST)) {

			robot.setFacing(Direction.SOUTH);

		} else if (robot.getFacing().equals(Direction.NORTH)) {

			robot.setFacing(Direction.EAST);

		} else if (robot.getFacing().equals(Direction.WEST)) {

			robot.setFacing(Direction.NORTH);

		} else if (robot.getFacing().equals(Direction.SOUTH)) {

			robot.setFacing(Direction.WEST);
		}
	}

	/**
	 * Used to get the current coordinates of the robot in the gridMap.
	 * @param element - search element("1" used as a marker for the Robot location)
	 * @return - current coordinates of the robot.
	 */
	private int[] searchElementInGrid(int element) {

		int[] currentCoordinates = null;

		for (int x = 0; x < gridSize; x++) {

			for (int y = 0; y < gridSize; y++) {

				if (gridMap[x][y] == element) {

					currentCoordinates = new int[2];
					currentCoordinates[0] = x;
					currentCoordinates[1] = y;

				}
			}
		}

		return currentCoordinates;

	}

	public boolean isRobotStatus() {
		return robotStatus;
	}

	public void setRobotStatus(boolean robotStatus) {
		this.robotStatus = robotStatus;
	}
	
	public int[][] getGridMap() {
		return gridMap;
	}

	public void setGridMap(int[][] gridMap) {
		this.gridMap = gridMap;
	}

}
