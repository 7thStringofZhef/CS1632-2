#include "Brick.h"

Brick::Brick(int x, int y) {
	rect.x = x;
	rect.y = y;
	rect.w = BRICK_WIDTH;
	rect.h = BRICK_HEIGHT;
	active = true;
}

Brick::Brick() {
	rect.x = 0;
	rect.y = 0;
	rect.w = BRICK_WIDTH;
	rect.h = BRICK_HEIGHT;
	active = true;
}

//What is this brick's position
SDL_Rect Brick::getRect() {
	return rect;
}

//Is the brick still on the board?
bool Brick::isActive() {
	return active;
}

void Brick::setX(int x) {
	rect.x = x;
}

void Brick::setY(int y) {
	rect.y = y;
}

//Render the brick in green
void Brick::render(SDL_Renderer* renderer) {
	SDL_SetRenderDrawColor(renderer, 0x00, 0xFF, 0x00, 0xFF);
	SDL_RenderFillRect(renderer, &rect);
	SDL_SetRenderDrawColor(renderer, 0x00, 0x00, 0x00, 0xFF);
	SDL_RenderDrawRect(renderer, &rect);
}

void Brick::destroy() {
	active = false;
}
