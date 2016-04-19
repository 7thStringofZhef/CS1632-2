#include "stdafx.h"
#include "CppUnitTest.h"
#include "..\CS1632D6\Ball.h"
#include <SDL.h>

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace CS1632D6Tests
{
	TEST_CLASS(BallTest)
	{
	public:

		//Test ball constructor
		TEST_METHOD(BallConstructorTest1)
		{
			SDL_Rect bounds = { 0,0,640,480 };
			Ball ball(100, 100, bounds);
			Assert::AreEqual(ball.getPosition().x, 100);
			Assert::AreEqual(ball.getPosition().y, 100);
			Assert::AreEqual(ball.getPosition().radius, Ball::BALL_RADIUS);
			Assert::AreEqual(ball.getXVel(), 0.0);
			int temp = ball.getYVel();
			Assert::AreEqual(temp, Ball::INITIAL_VELOCITY);
		}

		//Test ball basic movement. Should move 5 pixels down
		TEST_METHOD(BallMoveTest) {
			SDL_Rect bounds = { 0,0,640,480 };
			Brick grid[25][10];
			SDL_Rect playerRect = { 540, 460, 100, 20 };
			Ball ball(100, 100, bounds);
			ball.move(grid, playerRect);
			Assert::AreEqual(ball.getPosition().x, 100);
			Assert::AreEqual(ball.getPosition().y, 105);
		}

		//Test ball collision with player. Should keep same position, have a negative y velocity and nonzero x because it's hitting off center
		TEST_METHOD(BallMoveColPlayerTest) {
			SDL_Rect bounds = { 0,0,640,480 };
			Brick grid[25][10];
			SDL_Rect playerRect = { 540, 460, 100, 20 };
			Ball ball(590, 450, bounds);
			ball.move(grid, playerRect);
			Assert::IsTrue(ball.getYVel() < 0);
			Assert::IsFalse(ball.getXVel() == 0);
			Assert::AreEqual(ball.getPosition().x, 590);
			Assert::AreEqual(ball.getPosition().y, 450);
		}

		//Test ball collisions with grid object. Should keep same position, have a negative y velocity and nonzero x
		TEST_METHOD(BallMoveColBrickTest) {
			SDL_Rect bounds = { 0,0,640,480 };
			Brick grid[25][10];
			grid[0][0].setX(90);
			grid[0][0].setY(110);
			SDL_Rect playerRect = { 540, 460, 100, 20 };
			Ball ball(100, 100, bounds);
			ball.move(grid, playerRect);
			Assert::IsTrue(ball.getYVel() < 0);
			Assert::IsFalse(ball.getXVel() == 0);
			Assert::AreEqual(ball.getPosition().x, 100);
			Assert::AreEqual(ball.getPosition().y, 100);
		}

	};
}