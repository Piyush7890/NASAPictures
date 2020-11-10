package com.piyush.nasapictures.utils


import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors



/**
 * Runs tasks in a [ExecutorService] with a fixed thread of pools
 */
 object AsyncScheduler  {

    private  val NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors()

    private val executorService: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

     fun execute(task: () -> Unit) {
        executorService.execute(task)
    }

}
