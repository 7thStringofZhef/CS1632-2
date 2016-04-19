#include "Ball.h"
#include "Brick.h"
#include <SDL.h>
#include <cmath>

//Create a ball centered at given pixel, with initial velocity of 5 directly down
Ball::Ball(int x, int y, SDL_Rect inBounds) {
	position.x = x;
	position.y = y;
	position.radius = BALL_RADIUS;
	bounds = inBounds;
	xVel = 0;
	yVel = INITIAL_VELOCITY;
}

//Move the ball and check for collisions
void Ball::move(Brick (&grid)[25][10], SDL_Rect& player) {
	position.x += round(xVel);
	position.y += round(yVel);
	//Check for bounds collisions before objects
	if ((position.x - position.radius) < bounds.x || (position.x + position.radius) > (bounds.x + bounds.w)) {
		position.x -= round(xVel);
		xVel = -xVel; //Hit side of screen, reverse
	}
	if ((position.y - position.radius) < bounds.y || (position.y + position.radius) > (bounds.y + bounds.h)) {
		position.y -= round(yVel);
		yVel = -yVel; //Hit bottom/top of screen, reverse
	}
	handleCollisions(grid, player);
}

//Destroys brick hit. 
void Ball::handleCollisions(Brick(&grid)[25][10], SDL_Rect& player) {
	//Check player first
	if (handleCollision(player)) {
		return;
	}
	//Check over grid until I hit one. Destory it and return
	for (int i = 0; i < 25; i++) {
		for (int j = 0; j < 10; j++) {
			if (grid[i][j].isActive()) {
				if (handleCollision(grid[i][j].getRect())) {
					grid[i][j].destroy();
					return;
				}
			}
		}
	}
}

//Check if collides with rect. If so, adjust ball velocities appropriately, return true
bool Ball::handleCollision(SDL_Rect& rect) {
	Point point = findClosestPoint(rect);
	if (!checkCollision(rect, point)) {
		return false;
	}
	position.x -= round(xVel);
	position.y -= round(yVel);
	double degreesToShift = shiftDegrees(rect, point); //Extra degrees to shift
	double vel = INITIAL_VELOCITY;
	//Which side did I hit to check which direction to reverse?
	bool side = false;
	//If the closest x point is on right/left side of rectangle, it was side collision
	if (point.cX == (rect.x + rect.w) || point.cX == rect.x) {
		side = true;
	}
	if (side) {
		xVel = -xVel;
		xVel = cos(degreesToShift)*vel;
		yVel = sin(degreesToShift)*vel;
	}
	else {
		yVel = -yVel;
		xVel = sin(degreesToShift)*vel;
		yVel = cos(degreesToShift)*vel;
	}
	return true;
}

//Did the ball hit this rect?
bool Ball::checkCollision(SDL_Rect& rect, Point point) {
	//If the distance to the closest point on the rect is less than the distance covered by radius, collision
	if (distanceSquared(position.x, position.y, point.cX, point.cY) < position.radius*position.radius) {
		return true;
	}
	return false;
}

//Shift ball's trajectory some number of degrees based on where it impacted on the rect
double Ball::shiftDegrees(SDL_Rect& rect, Point point) {
	double degrees = MAX_BREAKOUT_SHIFT_DEGREES;
	//Side or bottom/top collision?
	bool side = false;
	//If the closest x point is on right/left side of rectangle, it was side collision
	if (point.cX == (rect.x + rect.w) || point.cX == rect.x) {
		side = true;
	}

	//Either way, normalize the length of the impact side from -1 to 1, find coordinate in relation to that
	double normalized;
	if (side) {
		normalized = ((double)point.cY / (double)rect.h)*2; //from 0 to 2
		normalized -= 1;
	}
	else {
		normalized = ((double)point.cX / (double)rect.w) * 2; //from 0 to 2
		normalized -= 1;
	}
	return degrees * normalized;
}

//Find closest point on rect to ball
Point Ball::findClosestPoint(SDL_Rect& rect) {
	int cX, cY; //Find closest point on rectangle to circle
	if (position.x < rect.x) {
		cX = rect.x; //Left side
	}
	else if (position.x > rect.x + rect.w) {
		cX = rect.x + rect.w; //right side
	}
	else {
		cX = position.x; //Same as x position of circle
	}
	if (position.y < rect.y) {
		cY = rect.y; //Top side
	}
	else if (position.y > rect.y + rect.h) {
		cY = rect.y + rect.h; //Bottom side
	}
	else {
		cY = position.y; //Same as y position of circle
	}
	Point point;
	point.cX = cX;
	point.cY = cY;
	return point;
}

//Distance squared between two points
double Ball::distanceSquared(int x1, int y1, int x2, int y2) {
	int deltaX = x2 - x1;
	int deltaY = y2 - y1;
	return deltaX*deltaX + deltaY*deltaY;
}

//Render using Bresenham's midpoint algorithm. Ball is red.
void Ball::render(SDL_Renderer* renderer) {
	SDL_SetRenderDrawColor(renderer, 0xFF, 0x00, 0x00, 0xFF);
	int x0 = position.x;
	int y0 = position.y;
	int bX = position.radius;
	int bY = 0;
	int decisionOver2 = 1-bX;
	while (bY <= bX) {
		SDL_RenderDrawLine(renderer, x0 - bX, y0 + bY, x0 + bX, y0 + bY);
		SDL_RenderDrawLine(renderer, x0 - bX, y0 - bY, x0 + bX, y0 - bY);
		SDL_RenderDrawLine(renderer, x0 + bX, y0 - bY, x0 + bX, y0 + bY);
		SDL_RenderDrawLine(renderer, x0 - bX, y0 - bY, x0 - bX, y0 + bY);
		bY++;
		if (decisionOver2 <= 0) {
			decisionOver2 += 2 * bY + 1;
		}
		else {
			bX--;
			decisionOver2 += 2 * (bY - bX) + 1;
		}
	}
}

Circle Ball::getPosition() {
	return position;
}

double Ball::getXVel() {
	return xVel;
}

double Ball::getYVel() {
	return yVel;
}