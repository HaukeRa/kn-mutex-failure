package sample

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

fun main() {
    val m = Mutex()
    GlobalScope.launch(Dispatchers.Default) {
        m.withLock {
            println("GlobalScope")
        }
    }
    runBlocking {
        repeat(100) {
            m.withLock {
                println("RunBlocking")
            }
        }
    }
}