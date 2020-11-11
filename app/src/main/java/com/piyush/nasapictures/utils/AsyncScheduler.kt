package com.piyush.nasapictures.utils

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors




interface Scheduler {
    fun execute(task: () -> Unit)
}

/**
 * Runs tasks in a [ExecutorService] with a fixed thread of pools
 */
object DefaultScheduler : Scheduler {

    private var delegate: Scheduler = AsyncScheduler


    //Sets a new scheduler, null to revert to the async one
    fun setDelegate(newDelegate: Scheduler?) {
        delegate = newDelegate ?: AsyncScheduler
    }

    override fun execute(task: () -> Unit) {
        delegate.execute(task)
    }
}




object AsyncScheduler : Scheduler {

//    private  val NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors()
    private  val NUMBER_OF_THREADS = 4

    private val executorService: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

     override fun execute(task: () -> Unit) {
        executorService.execute(task)
    }
}

object SyncScheduler : Scheduler {
    override fun execute(task: () -> Unit) {
        task()
    }
}