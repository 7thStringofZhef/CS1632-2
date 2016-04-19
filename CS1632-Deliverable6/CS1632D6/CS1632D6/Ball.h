#pragma once
#include <SDL.h>
#include "Brick.h"

//A simple circle
struct Circle {
	int x, y;
	int radius;
};

struct Point {
	int cX;
	int cY;
};


class Ball {
public:
	static const int BALL_RADIUS = 10;
	static const int INITIAL_VELOCITY = 5; //Start the ball with some speed
	static const int MAX_BREAKOUT_SHIFT_DEGREES = 20; //Without special breakout physics, the ball would only bounds up and down. How far can it shift based on point of impact?

	Ball(int x, int y, SDL_Rect bounds);

	void move(Brick(&grid)[25][10], SDL_Rect& player);
	void render(SDL_Renderer* renderer);

	Circle getPosition();
	double getXVel();
	double getYVel();

private:
	Circle position;
	SDL_Rect bounds;
	double xVel;
	double yVel;
	void handleCollisions(Brick(&grid)[25][10], SDL_Rect& player); //Ball is responsible for handling collisions, since it is primary 
	bool handleCollision(SDL_Rect& rect);
	Point findClosestPoint(SDL_Rect& rect);
	bool checkCollision(SDL_Rect& rect, Point point);
	double distanceSquared(int x1, int y1, int x2, int y2);
	double shiftDegrees(SDL_Rect& rect, Point point);
};