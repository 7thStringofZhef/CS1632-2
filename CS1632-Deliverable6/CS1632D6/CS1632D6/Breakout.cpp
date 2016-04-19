#include <SDL.h>
#include <stdio.h>
#include "Ball.h"
#include "Player.h"
#include "Brick.h"
#include "Timer.h"
#include "Breakout.h"

#ifdef main
#undef main
#endif

//Global constants specific to game
static const int WINDOW_HEIGHT = 480;
static const int WINDOW_WIDTH = 640;
static const SDL_Rect PLAYER_BOUNDS = { 0, 380, 640, 100}; //x, y, w, h
static const SDL_Rect BALL_BOUNDS = { 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT };

//Global constants to be used by functions
SDL_Window* gWindow;
SDL_Renderer* gRenderer;

//Grid of bricks to hit with ball
Brick grid[25][10];

//Function prototypes
bool init();
void close();
bool renderBricksAndCheckVictory();
void populateBrickGrid();

//Main game loop
int main(int argc, char* args[]) {
	if (!init()) {
		return 0;
	}
	
	//Game loop end
	bool quit = false;

	//User input
	SDL_Event e;

	//Set up pieces
	populateBrickGrid();
	Player player(PLAYER_BOUNDS);
	Ball ball(WINDOW_WIDTH/2, 300, BALL_BOUNDS);

	//Main game loop
	while (!quit) {
		//Poll event handler until empty. Input
		while (SDL_PollEvent(&e) != 0) {
			if (e.type == SDL_QUIT) {
				quit = true;
			}
			//Handle events here
			player.handleEvent(e);
		}

		//Movement and collision
		player.move(); //move player
		ball.move(grid, player.getRect()); //Move ball and handle collisions

		//Rendering
		//Clear screen
		SDL_SetRenderDrawColor(gRenderer, 0xFF, 0xFF, 0xFF, 0xFF);
		SDL_RenderClear(gRenderer);

		quit = renderBricksAndCheckVictory();
		player.render(gRenderer);
		ball.render(gRenderer);

		//Actually display
		SDL_RenderPresent(gRenderer);
	}
	close();

	return 0;
}

//Initialize SDL's renderer and such
bool init() {
	//Initialize video and audio
	if (SDL_Init(SDL_INIT_VIDEO) < 0)
	{
		printf("SDL could not initialize! SDL_Error: %s\n", SDL_GetError());
		return false;
	}

	//Create the window
	gWindow = SDL_CreateWindow("Basic Breakout Clone", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, WINDOW_WIDTH, WINDOW_HEIGHT, SDL_WINDOW_SHOWN);
	if (gWindow == NULL)
	{
		printf("Window could not be created! SDL Error: %s\n", SDL_GetError());
		return false;
	}

	//Set texture filtering to linear. Not necessary, so don't close if not done.
	if (!SDL_SetHint(SDL_HINT_RENDER_SCALE_QUALITY, "1"))
	{
		printf("Warning: Linear texture filtering not enabled!");
	}

	//Create the renderer, making sure to use VSYNC.
	gRenderer = SDL_CreateRenderer(gWindow, -1, SDL_RENDERER_ACCELERATED | SDL_RENDERER_PRESENTVSYNC);
	if (gRenderer == NULL)
	{
		printf("Renderer could not be created! SDL Error: %s\n", SDL_GetError());
		return false;
	}

	//Set the renderer to fill the scren white
	SDL_SetRenderDrawColor(gRenderer, 0xFF, 0xFF, 0xFF, 0xFF);
	return true;
}

//Clear up leftovers from initialization
void close() {
	SDL_DestroyRenderer(gRenderer);
	SDL_DestroyWindow(gWindow);
	gWindow = NULL;
	gRenderer = NULL;

	SDL_Quit();
}

//The player has won if there are no active bricks on screen
bool renderBricksAndCheckVictory() {
	bool victory = true;
	for (int i = 0; i < 25; i++) {
		for (int j = 0; j < 10; j++) {
			if (grid[i][j].isActive()) {
				victory = false;
				grid[i][j].render(gRenderer);
			}
		}
	}
	return victory;
}

//Fill whole grid with bricks
void populateBrickGrid() {
	int startingX = (WINDOW_WIDTH / 2) - (12 * Brick::BRICK_WIDTH);
	int startingY = 40;
	for (int i = 0; i < 25; i++) {
		for (int j = 0; j < 10; j++) {
			grid[i][j].setX(startingX + i * Brick::BRICK_WIDTH);
			grid[i][j].setY(startingY + j * Brick::BRICK_HEIGHT);
		}
	}
}