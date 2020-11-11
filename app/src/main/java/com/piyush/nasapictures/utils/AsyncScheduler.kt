package com.piyush.nasapictures.utils


import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors




interface Scheduler {

    fun execute(task: () -> Unit)

    fun postToMainThread(task: () -> Unit)
}

/**
 * Runs tasks in a [ExecutorService] with a fixed thread of pools
 */


object DefaultScheduler : Scheduler {

    private var delegate: Scheduler = AsyncScheduler


    /**
     * Sets the new delegate scheduler, null to revert to the default async one.
     */
    fun setDelegate(newDelegate: Scheduler?) {
        delegate = newDelegate ?: AsyncScheduler
    }

    override fun execute(task: () -> Unit) {
        delegate.execute(task)
    }

    override fun postToMainThread(task: () -> Unit) {
        delegate.postToMainThread(task)
    }

//    override fun postDelayedToMainThread(delay: Long, task: () -> Unit) {
//        delegate.postDelayedToMainThread(delay, task)
//    }
}




 internal object AsyncScheduler : Scheduler {

    private  val NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors()

    private val executorService: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

     override fun execute(task: () -> Unit) {
        executorService.execute(task)
    }

     override fun postToMainThread(task: () -> Unit) {
         if (isMainThread()) {
             task()
         } else {
             val mainThreadHandler = Handler(Looper.getMainLooper())
             mainThreadHandler.post(task)
         }
     }

     private fun isMainThread(): Boolean {
         return Looper.getMainLooper().thread === Thread.currentThread()
     }

}

object SyncScheduler : Scheduler {
    private val postDelayedTasks = mutableListOf<() -> Unit>()

    override fun execute(task: () -> Unit) {
        task()
    }

    override fun postToMainThread(task: () -> Unit) {
        task()
    }
}