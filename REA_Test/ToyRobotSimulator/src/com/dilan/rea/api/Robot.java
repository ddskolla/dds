package com.dilan.rea.api;

/**
 * Robot class holds the X and Y positions of the Robot as well as the direction the Robot is facing.
 * This is a Singleton class to maintain a single instance of the Robot throughout the program.
 * @author Dilan
 *
 */
public class Robot {

	private int positionX;
	private int positionY;
	private Direction facing;

	private static Robot robo = null;

	private Robot() {

	}

	public static Robot getInstance() {

		if (robo == null) {
			robo = new Robot();
		}

		return robo;

	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public Direction getFacing() {
		return facing;
	}

	public void setFacing(Direction facing) {
		this.facing = facing;
	}

	/**
	 * This method return the current coordinates of the Robot and the direction of the robot.
	 * Its used to print the output for the REPORT command.
	 */
	
	public void reportCoordinates() {

		System.out.println("Output: " + this.positionX + "," + this.positionY+ "," + this.facing.toString());

	}

}
