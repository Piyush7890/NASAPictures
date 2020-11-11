package com.piyush.nasapictures

import com.piyush.nasapictures.utils.DefaultScheduler
import com.piyush.nasapictures.utils.SyncScheduler
import org.junit.rules.TestWatcher
import org.junit.runner.Description

//
class SyncTaskExecutorRule : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)
        DefaultScheduler.setDelegate(SyncScheduler)
    }

    override fun finished(description: Description?) {
        super.finished(description)
//        SyncScheduler.clearScheduledPostdelayedTasks()
        DefaultScheduler.setDelegate(null)
    }
}