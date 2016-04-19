/*This source code copyrighted by Lazy Foo' Productions (2004-2015)
and may not be redistributed without written permission.*/

//Using SDL and standard IO
#include <SDL.h>
#include <SDL_image.h>
//#include <SDL_ttf.h>
#include <stdio.h>
#include <string>

//Screen dimension constants
const int SCREEN_WIDTH = 640;
const int SCREEN_HEIGHT = 480;

struct Circle {
	int x, y;
	int r;
};
//Texture wrapper class
class LTexture {
public:
	LTexture();

	~LTexture();

	bool loadFromFile(std::string path);

#ifdef _SDL_TTF_H
	bool loadFromRenderedText(std::string textureText, SDL_Color textColor);
#endif
	void free();

	void setColor(Uint8 red, Uint8 green, Uint8 blue);

	void setBlendMode(SDL_BlendMode blending);

	void setAlpha(Uint8 alpha);

	void render(int x, int y, SDL_Rect* clip = NULL, double angle = 0.0, SDL_Point* center = NULL, SDL_RendererFlip flip = SDL_FLIP_NONE);

	int getWidth();
	int getHeight();

private:
	SDL_Texture* mTexture;
	int mWidth;
	int mHeight;
};

class LTimer {
public:
	LTimer();

	void start();
	void stop();
	void pause();
	void unpause();

	Uint32 getTicks();

	bool isStarted();
	bool isPaused();

private:
	Uint32 mStartTicks;
	Uint32 mPausedTicks;
	bool mPaused;
	bool mStarted;
};

class Dot {
public:
	static const int DOT_WIDTH = 20;
	static const int DOT_HEIGHT = 20;

	static const int DOT_VEL = 1; //Max velocity of dot

	Dot(int x, int y);

	void handleEvent(SDL_Event& e);

	void move(SDL_Rect& square, Circle& circle);
	void render();

	Circle& getCollider();
private:
	int mPosX, mPosY;
	int mVelX, mVelY;
	Circle mCollider;
	void shiftColliders();
};

//Functions
bool init();
bool loadMedia();
void close();
bool checkCollision(Circle &a, Circle&b);
bool checkCollision(Circle &a, SDL_Rect &b);
double distanceSquared(int x1, int y1, int x2, int y2);
bool checkCollision(SDL_Rect a, SDL_Rect b); //Box Collision detector

											 //Globals
SDL_Window* gWindow = NULL;
SDL_Renderer* gRenderer = NULL; //Current surface in window

LTexture gDotTexture;



//LTexture functions
LTexture::LTexture() {
	mTexture = NULL;
	mWidth = 0;
	mHeight = 0;
}

LTexture::~LTexture() {
	free();
}

bool LTexture::loadFromFile(std::string path) {
	free();
	SDL_Texture* newTexture = NULL;
	SDL_Surface* loadedSurface = IMG_Load(path.c_str());
	if (loadedSurface == NULL) {
		printf("Unable to load image %s! SDL_Image Error: %s\n", path.c_str(), IMG_GetError());
	}
	else {
		SDL_SetColorKey(loadedSurface, SDL_TRUE, SDL_MapRGB(loadedSurface->format, 0, 0xFF, 0xFF));
		newTexture = SDL_CreateTextureFromSurface(gRenderer, loadedSurface);
		if (newTexture == NULL) {
			printf("Unable to create texture from %s! SDL Error: %s\n", path.c_str(), SDL_GetError());
		}
		else {
			mWidth = loadedSurface->w;
			mHeight = loadedSurface->h;
		}
		SDL_FreeSurface(loadedSurface);
	}
	mTexture = newTexture;
	return mTexture != NULL;
}
#ifdef _SDL_TTF_H
bool LTexture::loadFromRenderedText(std::string textureText, SDL_Color textColor) {
	free();

	SDL_Surface* textSurface = TTF_RenderText_Shaded(gFont, textureText.c_str(), textColor, { 0xff,0xff,0xff });
	if (textSurface == NULL) {
		printf("Unable to render text surface! SDL_ttf Error: %s\n", TTF_GetError());
	}
	else {
		mTexture = SDL_CreateTextureFromSurface(gRenderer, textSurface);
		if (mTexture == NULL) {
			printf("Unable to create texture from rendered text! SDL Error: %s\n", SDL_GetError());
		}
		else {
			mWidth = textSurface->w;
			mHeight = textSurface->h;
		}
		SDL_FreeSurface(textSurface);
	}
	return mTexture != NULL;
}
#endif

void LTexture::free() {
	if (mTexture != NULL) {
		SDL_DestroyTexture(mTexture);
		mTexture = NULL;
		mWidth = 0;
		mHeight = 0;
	}
}

void LTexture::setColor(Uint8 red, Uint8 green, Uint8 blue) {
	//Modulate texture
	SDL_SetTextureColorMod(mTexture, red, green, blue);
}

void LTexture::setBlendMode(SDL_BlendMode blending) {
	//Set blending functon
	SDL_SetTextureBlendMode(mTexture, blending);
}

void LTexture::setAlpha(Uint8 alpha) {
	//Modulate texture alpha
	SDL_SetTextureAlphaMod(mTexture, alpha);
}

void LTexture::render(int x, int y, SDL_Rect* clip, double angle, SDL_Point* center, SDL_RendererFlip flip) {
	SDL_Rect renderQuad = { x, y, mWidth, mHeight };
	if (clip != NULL) {
		renderQuad.w = clip->w;
		renderQuad.h = clip->h;
	}
	SDL_RenderCopyEx(gRenderer, mTexture, clip, &renderQuad, angle, center, flip);
}

int LTexture::getWidth() {
	return mWidth;
}

int LTexture::getHeight() {
	return mHeight;
}

LTimer::LTimer() {
	mStartTicks = 0;
	mPausedTicks = 0;
	mPaused = false;
	mStarted = false;
}

void LTimer::start() {
	mStarted = true;
	mPaused = false;
	mStartTicks = SDL_GetTicks();
	mPausedTicks = 0;
}

void LTimer::stop() {
	mStarted = false;
	mPaused = false;
	mStartTicks = 0;
	mPausedTicks = 0;
}

void LTimer::pause() {
	if (mStarted&&!mPaused) {
		mPaused = true;
		mPausedTicks = SDL_GetTicks() - mStartTicks;
		mStartTicks = 0;
	}
}

void LTimer::unpause() {
	if (mStarted&&mPaused) {
		mPaused = false;
		mStartTicks = SDL_GetTicks() - mPausedTicks;
		mPausedTicks = 0;
	}
}

Uint32 LTimer::getTicks() {
	Uint32 time = 0;
	if (mStarted) {
		if (mPaused) {
			return mPausedTicks; //Number of ticks when timer was paused
		}
		else {
			return SDL_GetTicks() - mStartTicks;
		}
	}
	return time;
}

bool LTimer::isStarted() {
	return mStarted;
}

bool LTimer::isPaused() {
	return mPaused && mStarted;
}

Dot::Dot(int x, int y) {
	mPosX = x;
	mPosY = y;

	//Collision circle size
	mCollider.r = DOT_WIDTH / 2;

	mVelX = 0;
	mVelY = 0;

	//Move collider relative to circle
	shiftColliders();
}

void Dot::handleEvent(SDL_Event& e) {
	if (e.type == SDL_KEYDOWN && e.key.repeat == 0) {
		switch (e.key.keysym.sym) {
		case SDLK_UP: mVelY -= DOT_VEL; break;
		case SDLK_DOWN: mVelY += DOT_VEL; break;
		case SDLK_LEFT: mVelX -= DOT_VEL; break;
		case SDLK_RIGHT: mVelX += DOT_VEL; break;
		}
	}
	//Undo velocity
	else if (e.type == SDL_KEYUP && e.key.repeat == 0)
	{
		switch (e.key.keysym.sym)
		{
		case SDLK_UP: mVelY += DOT_VEL; break;
		case SDLK_DOWN: mVelY -= DOT_VEL; break;
		case SDLK_LEFT: mVelX += DOT_VEL; break;
		case SDLK_RIGHT: mVelX -= DOT_VEL; break;
		}
	}
}

void Dot::move(SDL_Rect& square, Circle& circle) {
	mPosX += mVelX;
	shiftColliders(); //Move the collision circle with the dot

	mCollider.x = mPosX;
	if ((mPosX - mCollider.r<0) || (mPosX + mCollider.r > SCREEN_WIDTH) || checkCollision(mCollider, square) || checkCollision(mCollider, circle)) {
		mPosX -= mVelX;
		shiftColliders();
	}
	mPosY += mVelY;
	mCollider.y = mPosY;
	shiftColliders(); //Same as above

	if ((mPosY - mCollider.r<0) || (mPosY + mCollider.r > SCREEN_HEIGHT) || checkCollision(mCollider, square) || checkCollision(mCollider, circle)) {
		mPosY -= mVelY;
		mCollider.y = mPosY;
		shiftColliders();
	}
}

void Dot::render() {
	gDotTexture.render(mPosX, mPosY);
}

Circle& Dot::getCollider() {
	return mCollider;
}

void Dot::shiftColliders() {
	mCollider.x = mPosX;
	mCollider.y = mPosY;
}

bool init()
{
	if (SDL_Init(SDL_INIT_VIDEO | SDL_INIT_AUDIO) < 0)
	{
		printf("SDL could not initialize! SDL_Error: %s\n", SDL_GetError());
		return false;
	}
	gWindow = SDL_CreateWindow("SDL Tutorial", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, SCREEN_WIDTH, SCREEN_HEIGHT, SDL_WINDOW_SHOWN);
	if (gWindow == NULL)
	{
		printf("Window could not be created! SDL Error: %s\n", SDL_GetError());
		return false;
	}
	//Set texture filtering to linear
	if (!SDL_SetHint(SDL_HINT_RENDER_SCALE_QUALITY, "1"))
	{
		printf("Warning: Linear texture filtering not enabled!");
	}

	//Create renderer. Added vsync
	gRenderer = SDL_CreateRenderer(gWindow, -1, SDL_RENDERER_ACCELERATED | SDL_RENDERER_PRESENTVSYNC);
	if (gRenderer == NULL)
	{
		printf("Renderer could not be created! SDL Error: %s\n", SDL_GetError());
		return false;
	}

	//Set renderer draw color
	SDL_SetRenderDrawColor(gRenderer, 0xFF, 0xFF, 0xFF, 0xFF);

	//Initialize PNG loading
	int imgFlags = IMG_INIT_PNG;
	if (!(IMG_Init(imgFlags)&imgFlags))
	{
		printf("SDL_Image could not initialize! Image Error: %s\n", IMG_GetError());
		return false;
	}
	return true;
}

bool loadMedia()
{
	if (!gDotTexture.loadFromFile("dot.bmp")) {
		printf("Failed to load dot texture!\n");
		return false;
	}

	return true;
}

void close()
{
	gDotTexture.free();


	SDL_DestroyRenderer(gRenderer);
	SDL_DestroyWindow(gWindow);
	gWindow = NULL;
	gRenderer = NULL;

	IMG_Quit();
	SDL_Quit();
}

bool checkCollision(Circle& a, Circle& b) {
	int totalRadiusSquared = a.r + b.r;
	totalRadiusSquared = totalRadiusSquared*totalRadiusSquared; //Square it
	if (distanceSquared(a.x, a.y, b.x, b.y) < totalRadiusSquared) {
		return true;
	}
	return false;
}

bool checkCollision(Circle& a, SDL_Rect& b) {
	int cX, cY;
	//Find the sides of the rectangle to check against
	if (a.x < b.x) {
		cX = b.x;
	}
	else if (a.x > b.x + b.w) {
		cX = b.x + b.w;
	}
	else {
		cX = a.x;
	}

	if (a.y < b.y) {
		cY = b.y;
	}
	else if (a.y > b.y + b.h) {
		cY = b.y + b.h;
	}
	else {
		cY = a.y;
	}

	if (distanceSquared(a.x, a.y, cX, cY) < a.r * a.r) {
		return true;
	}
	return false;
}

double distanceSquared(int x1, int y1, int x2, int y2) {
	int deltaX = x2 - x1;
	int deltaY = y2 - y1;
	return deltaX * deltaX + deltaY* deltaY;
}


int main(int argc, char* args[])
{
	if (!init())
		printf("Failed to initialize\n");
	else
	{
		if (!loadMedia())
			printf("Failed to load media\n");
		else
		{
			//Game loop check
			bool quit = false;

			//Event handler
			SDL_Event e;

			Dot dot(Dot::DOT_WIDTH / 2, Dot::DOT_HEIGHT / 2);
			Dot otherDot(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4);

			//Set the wall
			SDL_Rect wall;
			wall.x = 300;
			wall.y = 40;
			wall.w = 40;
			wall.h = 400;

			//While app is running
			while (!quit)
			{
				//Poll event handler until empty
				while (SDL_PollEvent(&e) != 0) {
					if (e.type == SDL_QUIT) {
						quit = true;
					}
					dot.handleEvent(e);
				}

				dot.move(wall, otherDot.getCollider());

				//Clear screen
				SDL_SetRenderDrawColor(gRenderer, 0xFF, 0xFF, 0xFF, 0xFF);
				SDL_RenderClear(gRenderer);

				//Render wall
				SDL_SetRenderDrawColor(gRenderer, 0x00, 0x00, 0x00, 0xFF);
				SDL_RenderDrawRect(gRenderer, &wall);

				dot.render();
				otherDot.render();

				//Update screen
				SDL_RenderPresent(gRenderer);
			}
		}
	}
	close();

	return 0;
}