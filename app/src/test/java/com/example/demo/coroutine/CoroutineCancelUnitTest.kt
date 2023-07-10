package com.example.demo.coroutine

import kotlinx.coroutines.*
import org.junit.Test

/**
 * Dispatchers.IO - disk 또는 Network I/O 에 최적화되어 Worker Thread 에서 동작
 * Dispatchers.Default - CPU 연산이 많이 필요한 JSON 파싱 혹은 긴 List Sorting 에 최적화
 * Dispatchers.Main - UI 작업
 */
class CoroutineCancelUnitTest {


    //runBlocking은 새로운 코루틴을 실행하고 완료되기 전까지 현재 스레드를 블로킹
    @Test
    fun `코루틴 취소 테스트1`() = runBlocking {

        /**
         * launch - 새로운 코루틴을 시작하지만 결과값을 전달하지 않을때 사용
         * async - 결과값을 리턴 할 수 있는 코루틴을 시작하는 경우 이 메소드를 사용
         * async{}.await() == withContext
         */
        val job = launch(Dispatchers.IO) {

            try {
                repeat(1_000_000) { number ->
                    withContext(Dispatchers.IO) {
                        println("number is $number")
                        println("isActive is $isActive")

                        //However, if a coroutine is working in a computation and does not check for cancellation, then it cannot be cancelled
                        if (isActive.not()) {
                            println("loop내부에서 isActive가 false인지를 체크해서 명시적으로 종료")
                            println("isActive is $isActive")
                            cancel()
                        }
                    }

                }
            } finally {
                //마지막으로 해야할 작없이 필요할때는 try - finally 구문을 이용
                //suspending function 을 포함할경우 Run non-cancellable block 사용
                withContext(NonCancellable) {
                    println("I'm running finally")
                    println("And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(1)
        println("코루틴 취소 요청")
        job.cancel()
        job.join()
        println("끝났다!")
    }

    /**
     * Exception 확인하기
     */
    @Test
    fun `코루틴 취소 테스트2`() = runBlocking {
        val job = Job()

        CoroutineScope(Dispatchers.IO).launch(job) {
            try {
                repeat(1_000_000) {
                    withContext(Dispatchers.IO) {
                        println("작업중............ $it")
                        if (isActive.not()) {
                            println("loop내부에서 isActive가 false인지를 체크해서 명시적으로 종료")
                            println("isActive is $isActive")
                            cancel()
                        }
                    }
                }
            } catch (e: Exception) {
                println("Exception is $e")
            } finally {
                println("I'm running finally")
            }
        }
        delay(50)
        job.cancelAndJoin()
        delay(500)
        println("Job canceled")
    }

    @Test
    fun `코루틴 취소 테스트3 사람들이 자주하는 실수`() = runBlocking {
        val job = launch {
            try {
                delay(500L)
            } catch (e: Exception) {
                //해결방법
//                if (e is CancellationException) {
//                    throw e
//                }
                e.printStackTrace()
            }
            //수행됨
            println("Coroutine 1 finished")
        }
        delay(300L)
        job.cancel()
    }

    @Test
    fun `코루틴 예외 전파 테스트`() = runBlocking {

        val job1= CoroutineScope(Dispatchers.IO).launch {
            childException("1번")
        }

        val job2= CoroutineScope(Dispatchers.IO).launch {
            childException("2번")
        }
        joinAll(job1,job2)
    }
    private suspend fun childException(scopeName: String) {
        delay(10)
        println("($scopeName) Hi I'm exception")
        throw IllegalStateException("Not supported yet")
    }
}