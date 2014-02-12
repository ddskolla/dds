package com.dilan.rea.dto;

import com.dilan.rea.api.Command;
import com.dilan.rea.api.Direction;

/**
 * Used as a data transfer object to pass around the input data during the execution of the program.
 * @author Dilan
 *
 */
public class InputDataDto {

	private int positionX;
	private int positionY;
	private Command command;
	private Direction facing;

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
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

}
