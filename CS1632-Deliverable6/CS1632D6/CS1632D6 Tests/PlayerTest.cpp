#include "stdafx.h"
#include "CppUnitTest.h"
#include "..\CS1632D6\Player.h"
#include <SDL.h>

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace CS1632D6Tests
{
	TEST_CLASS(PlayerTest)
	{
	public:

		//Test the constructor with some screen bounds
		TEST_METHOD(PlayerConstructorTest)
		{
			SDL_Rect bounds = { 0,0,640,480 };
			Player player(bounds);
			Assert::AreEqual(player.getRect().x, bounds.x + (bounds.w / 2) - (Player::PLAYER_WIDTH / 2));
			Assert::AreEqual(player.getRect().y, bounds.y + (bounds.h / 2) - (Player::PLAYER_HEIGHT / 2));
			Assert::AreEqual(player.getRect().h, Player::PLAYER_HEIGHT);
			Assert::AreEqual(player.getRect().w, Player::PLAYER_WIDTH);
			Assert::AreEqual(player.xVel, 0);
			Assert::AreEqual(player.yVel, 0);
		}

		//Test the event handler with an up key press and release
		TEST_METHOD(PlayerHandleUpArrowPressAndReleaseTest)
		{
			SDL_Rect bounds = { 0,0,640,480 };
			Player player(bounds);
			SDL_Event e;
			e.type = SDL_KEYDOWN;
			e.key.repeat = 0;
			e.key.keysym.sym = SDLK_UP;
			player.handleEvent(e);
			Assert::AreEqual(player.yVel, -Player::PLAYER_VELOCITY);
			Assert::AreEqual(player.xVel, 0);
			e.type = SDL_KEYUP;
			player.handleEvent(e);
			Assert::AreEqual(player.yVel, 0);
			Assert::AreEqual(player.xVel, 0);
		}

		//Test the event handler with an "a" key press and release
		TEST_METHOD(PlayerHandleAPressAndReleaseTest)
		{
			SDL_Rect bounds = { 0,0,640,480 };
			Player player(bounds);
			SDL_Event e;
			e.type = SDL_KEYDOWN;
			e.key.repeat = 0;
			e.key.keysym.sym = SDLK_a;
			player.handleEvent(e);
			Assert::AreEqual(player.yVel, 0);
			Assert::AreEqual(player.xVel, 0);
			e.type = SDL_KEYUP;
			player.handleEvent(e);
			Assert::AreEqual(player.yVel, 0);
			Assert::AreEqual(player.xVel, 0);
		}

		//Move the player while within bounds
		TEST_METHOD(PlayerMoveWithinBoundsTest) {
			SDL_Rect bounds = { 0,0,640,480 };
			Player player(bounds);
			int originalX = player.getRect().x;
			SDL_Event e;
			e.type = SDL_KEYDOWN;
			e.key.repeat = 0;
			e.key.keysym.sym = SDLK_RIGHT;
			player.handleEvent(e);
			player.move();
			Assert::AreEqual(player.getRect().x, originalX + 5);
		}

		//Test that bounds stop player movement
		TEST_METHOD(PlayerMoveAgainstBoundsTest) {
			SDL_Rect bounds = { 0,0,640,480 };
			Player player(bounds);
			SDL_Rect newPositon = { 640 - Player::PLAYER_WIDTH, 480 - Player::PLAYER_HEIGHT, Player::PLAYER_WIDTH, Player::PLAYER_HEIGHT };
			player.setRect(newPositon);
			int originalX = player.getRect().x;
			SDL_Event e;
			e.type = SDL_KEYDOWN;
			e.key.repeat = 0;
			e.key.keysym.sym = SDLK_RIGHT;
			player.handleEvent(e);
			player.move();
			Assert::AreEqual(player.getRect().x, originalX);
		}


		//Test that Player cannot move when out of bounds
		TEST_METHOD(PlayerMoveOutsideBoundsTest) {
			SDL_Rect bounds = { 0,0,640,480 };
			Player player(bounds);
			SDL_Rect newPositon = { 680, 500, Player::PLAYER_WIDTH, Player::PLAYER_HEIGHT };
			player.setRect(newPositon);
			int originalX = player.getRect().x;
			SDL_Event e;
			e.type = SDL_KEYDOWN;
			e.key.repeat = 0;
			e.key.keysym.sym = SDLK_RIGHT;
			player.handleEvent(e);
			player.move();
			Assert::AreEqual(player.getRect().x, originalX);
		}
	};
}