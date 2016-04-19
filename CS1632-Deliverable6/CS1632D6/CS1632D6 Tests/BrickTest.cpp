#include "stdafx.h"
#include "CppUnitTest.h"
#include "..\CS1632D6\Brick.h"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace CS1632D6Tests
{
	TEST_CLASS(BrickTest)
	{
	public:

		//Test brick constructor default values
		TEST_METHOD(BrickDefaultConstructorTest)
		{
			Brick brick;
			Assert::AreEqual(brick.getRect().x, 0);
			Assert::AreEqual(brick.getRect().y, 0);
			Assert::AreEqual(brick.getRect().w, Brick::BRICK_WIDTH);
			Assert::AreEqual(brick.getRect().h, Brick::BRICK_HEIGHT);
			Assert::IsTrue(brick.isActive());
		}

		//Test brick constructor with 2 arguments
		TEST_METHOD(Brick2ArgConstructorTest)
		{
			Brick brick(100,100);
			Assert::AreEqual(brick.getRect().x, 100);
			Assert::AreEqual(brick.getRect().y, 100);
			Assert::AreEqual(brick.getRect().w, Brick::BRICK_WIDTH);
			Assert::AreEqual(brick.getRect().h, Brick::BRICK_HEIGHT);
			Assert::IsTrue(brick.isActive());
		}

		//Test brick's setX on a common number
		TEST_METHOD(BrickSetXBasicTest)
		{
			Brick brick;
			brick.setX(29);
			Assert::AreEqual(brick.getRect().x, 29);
		}

		//Test whether setX sets properly with a negative number
		TEST_METHOD(BrickSetXNegativeTest)
		{
			Brick brick;
			brick.setX(-3);
			Assert::AreEqual(brick.getRect().x, -3);
		}

		//Test whether setY sets properly on a common number
		TEST_METHOD(BrickSetYBasicTest)
		{
			Brick brick;
			brick.setY(29);
			Assert::AreEqual(brick.getRect().y, 29);
		}

		//Test whether setY sets properly on a negative number
		TEST_METHOD(BrickSetYNegativeTest)
		{
			Brick brick;
			brick.setY(-3);
			Assert::AreEqual(brick.getRect().y, -3);
		}

		//Test whether an active brick returns true
		TEST_METHOD(BrickIsActiveTrueTest) {
			Brick brick;
			Assert::IsTrue(brick.isActive());
		}

		//Test whether a brick can be destroyed
		TEST_METHOD(BrickDestroyAndIsActiveFalseTest) {
			Brick brick;
			brick.destroy();
			Assert::IsFalse(brick.isActive());
		}
	};
}