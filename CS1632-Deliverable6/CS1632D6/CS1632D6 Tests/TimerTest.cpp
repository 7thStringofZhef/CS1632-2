#pragma once
#include "stdafx.h"
#include "CppUnitTest.h"
#include "..\CS1632D6\Timer.h"
#include <stdlib.h>

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace CS1632D6Tests
{		
	TEST_CLASS(TimerTest)
	{
	public:
		
		//Test timer's only constructor
		TEST_METHOD(TimerConstructorTest)
		{
			Timer timer;
			Assert::IsFalse(timer.isPaused());
			Assert::IsFalse(timer.isStarted());
			Uint32 temp = 0;
			Assert::AreEqual(timer.getTicks(), temp);
		}

		//Test that I can start timer
		TEST_METHOD(TimerStartTest) {
			Timer timer;
			timer.start();
			Assert::IsTrue(timer.isStarted());
			Assert::IsFalse(timer.isPaused());
			_sleep(200);
			Uint32 temp = 0;
			Assert::AreNotEqual(timer.getTicks(), temp);
		}

		//Test that I can pause and unpause timer when started
		TEST_METHOD(TimerPauseTest) {
			Timer timer;
			timer.start();
			timer.pause();
			Assert::IsTrue(timer.isPaused());
			Uint32 temp = timer.getTicks();
			_sleep(200);
			Assert::AreEqual(temp, timer.getTicks());
			timer.unpause();
			_sleep(200);
			Assert::AreNotEqual(temp, timer.getTicks());
		}

		//Test that I can't pause timer when not started
		TEST_METHOD(TimerPauseNotStartedTest) {
			Timer timer;
			timer.pause();
			Assert::IsFalse(timer.isPaused());
		}

		//Test that I can stop timer
		TEST_METHOD(TimerStopTest) {
			Timer timer;
			timer.start();
			_sleep(200);
			timer.stop();
			Assert::IsFalse(timer.isPaused());
			Assert::IsFalse(timer.isStarted());
			Uint32 temp = 0;
			Assert::AreEqual(timer.getTicks(), temp);
		}

	};
}