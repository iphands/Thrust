package org.ahands.game;

public class Pew {
	private float x, y, ySpeed, xSpeed;

	public Pew(float x, float y, float angle, float shipSpeedX, float shipSpeedY, boolean flipped, int speedTick,
			float angleRand, float speedRand) {
		angle += (float) ((Math.random() * angleRand) - (angleRand / 2f));
		final float speedRandInternal = (float) (Math.random() / speedRand) + 1;

		if (!flipped) {
			this.ySpeed = (((float) speedTick * (float) Math.cos(Math.toRadians(angle))) * speedRandInternal);
			this.xSpeed = (((float) speedTick * (float) -Math.sin(Math.toRadians(angle))) * speedRandInternal);
		} else {
			this.ySpeed = (((float) speedTick * (float) -Math.cos(Math.toRadians(angle))) * speedRandInternal);
			this.xSpeed = (((float) speedTick * (float) Math.sin(Math.toRadians(angle))) * speedRandInternal);
			this.xSpeed += ((Math.random() * 3) - 1.5);
			this.ySpeed += ((Math.random() * 3) - 1.5);
		}

		this.xSpeed += shipSpeedX;
		this.ySpeed += shipSpeedY;
		this.x = x;
		this.y = y;
	}

	public void reverseX() {
		xSpeed *= -1;
		// ySpeed *= -1;
	}

	public void update() {
		x += xSpeed;
		y += ySpeed;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void randomizeAngle(int seed) {

	}

	public void randomizeSpeed(int seed) {
		this.xSpeed += (float) ((Math.random() * seed) - (seed / 2f));
		this.ySpeed += (float) ((Math.random() * seed) - (seed / 2f));
	}

	public void reverseY() {
		ySpeed *= -1;
	}
}
