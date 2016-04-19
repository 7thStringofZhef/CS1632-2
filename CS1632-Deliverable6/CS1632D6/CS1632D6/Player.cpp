#include "Player.h"
#include <SDL.h>

//Create a player object in the middle of the established bounds
Player::Player(SDL_Rect inBounds) {
	bounds = inBounds;
	rect.x = inBounds.x + (inBounds.w / 2) - (PLAYER_WIDTH / 2);
	rect.y = inBounds.y + (inBounds.h / 2) - (PLAYER_HEIGHT / 2);
	rect.w = PLAYER_WIDTH;
	rect.h = PLAYER_HEIGHT;
	xVel = 0;
	yVel = 0;
}

//Render player as a black rectangle at current position
void Player::render(SDL_Renderer* renderer) {
	SDL_SetRenderDrawColor(renderer, 0x00, 0x00, 0x00, 0xFF);
	SDL_RenderDrawRect(renderer, &rect);
}

SDL_Rect Player::getRect() {
	return rect;
}

void Player::setRect(SDL_Rect newRect) {
	rect = newRect;
}

//Handle player input
void Player::handleEvent(SDL_Event& e) {
	//If the player presses an arrow key, go that way
	if (e.type == SDL_KEYDOWN && e.key.repeat == 0) {
		switch (e.key.keysym.sym) {
			case SDLK_UP: yVel -= PLAYER_VELOCITY; break;
			case SDLK_DOWN: yVel += PLAYER_VELOCITY; break;
			case SDLK_LEFT: xVel -= PLAYER_VELOCITY; break;
			case SDLK_RIGHT: xVel += PLAYER_VELOCITY; break;
		}
	}
	//When they let go, stop going that way
	else if (e.type == SDL_KEYUP && e.key.repeat == 0)
	{
		switch (e.key.keysym.sym)
		{
			case SDLK_UP: yVel += PLAYER_VELOCITY; break;
			case SDLK_DOWN: yVel -= PLAYER_VELOCITY; break;
			case SDLK_LEFT: xVel += PLAYER_VELOCITY; break;
			case SDLK_RIGHT: xVel -= PLAYER_VELOCITY; break;
		}
	}
}

//Handle movement. Don't let player leave the bounds
void Player::move() {
	//Check against x bounds.
	if (((rect.x + rect.w + xVel) <= (bounds.x + bounds.w)) && ((rect.x+xVel) >= bounds.x)) {
		rect.x += xVel;
	}
	//Check against y bounds
	if (((rect.y + rect.h + yVel) <= (bounds.y + bounds.h)) && ((rect.y + yVel) >= bounds.y)) {
		rect.y += yVel;
	}
}