#pragma once
#include <SDL.h>

class Brick {
public:
	static const int BRICK_WIDTH = 20;
	static const int BRICK_HEIGHT = 10;

	Brick(int x, int y);
	Brick();
	SDL_Rect getRect();
	void setX(int x);
	void setY(int y);
	bool isActive();

	void render(SDL_Renderer* renderer);
	void destroy();
private:
	SDL_Rect rect;
	bool active;
};