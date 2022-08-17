package com.chan.navigation

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
class Event<out T>(private val content: T) {
    private val consumedScopes = HashSet<String>()

    fun isConsumed(scope: String = "") = consumedScopes.contains(scope)

    @MainThread
    fun consume(scope: String = ""): T? {
        return if (isConsumed(scope)) {
            null
        } else {
            consumedScopes.add(scope)
            content
        }
    }

    fun peek(): T = content
}

fun <T> LiveData<Event<T>>.observeEvent(lifecycleOwner: LifecycleOwner, scope: String = "", observer: Observer<T>) {
    observe(lifecycleOwner) { event ->
        event?.consume(scope)?.let { observer.onChanged(it) }
    }
}

fun MutableLiveData<Event<Unit>>.emit() = postValue(Event(Unit))
fun <T> MutableLiveData<Event<T>>.emit(value: T) = postValue(Event(value))
