package me.zt.illusion.test

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlin.coroutines.Continuation

class Coroutines {

    fun load(url: String) {
        println(Thread.currentThread())
        CoroutineScope(Dispatchers.Main).launch {
            println("launch ${Thread.currentThread()}")
            val result = doLoad(url)
            val deferred = async { doLoad(url) }
            println("launch result $result, ${Thread.currentThread()}")
        }
    }

    private suspend fun doLoad(url: String) = withContext(Dispatchers.Default) {
        println("doLoad  ${Thread.currentThread()}")
        delay(2000)
        println("doLoad2  ${Thread.currentThread()}")
        return@withContext "result : $url"
    }
}