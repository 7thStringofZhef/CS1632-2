#pragma once
#include <SDL.h>

class Player {
public:
	static const int PLAYER_WIDTH = 100;
	static const int PLAYER_HEIGHT = 20;
	static const int PLAYER_VELOCITY = 5;

	Player(SDL_Rect bounds);

	void render(SDL_Renderer* renderer);

	SDL_Rect getRect();
	void setRect(SDL_Rect newRect);

	void handleEvent(SDL_Event& e);
	void move();

	int xVel;
	int yVel;
private:
	SDL_Rect rect;
	SDL_Rect bounds;
};