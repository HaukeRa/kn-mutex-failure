package sample

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.sync.withPermit

private suspend fun Semaphore.lock() = this.acquire()
private fun Semaphore.unlock() = this.release()

private suspend fun <T> Semaphore.withLock(action: () -> T): T = this.withPermit(action)

// Fails
private fun makeMutex() = Mutex()

// Works
//private fun makeMutex() = Semaphore(1)

const val NUM = 1_000_000
fun main() {
    val m = makeMutex()

    runBlocking {
        repeat(NUM) {
            GlobalScope.launch(Dispatchers.Default) {
                m.withLock {
                    println("GlobalScope")
                }
            }
            m.withLock {
                println("RunBlocking")
            }
        }
    }
}