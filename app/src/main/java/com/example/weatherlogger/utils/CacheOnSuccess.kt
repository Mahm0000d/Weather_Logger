package com.example.weatherlogger.utils

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CacheOnSuccess<T: Any>(
    private val onErrorFallback: (suspend () -> T)? = null,
    private val block: suspend () -> T
) {
    private val mutex =  Mutex()
    @Volatile
    private var deferred: Deferred<T>? = null
    suspend fun getOrAwait(): T {
        return supervisorScope {
            val currentDeferred = mutex.withLock {
                deferred?.let { return@withLock it }

                async {
                    // Note: mutex is not held in this async block
                    block()
                }.also {
                    // Note: mutex is held here
                    deferred = it
                }
            }

            // await the result, with our custom error handling
            currentDeferred.safeAwait()
        }
    }

    private suspend fun Deferred<T>.safeAwait(): T {
        try {
            // Note: this call to await will always throw if this coroutine is cancelled
            return await()
        } catch (throwable: Throwable) {
            // If deferred still points to `this` instance of Deferred, clear it because we don't
            // want to cache errors
            mutex.withLock {
                if (deferred == this) {
                    deferred = null
                }
            }

            // never consume cancellation
            if (throwable is CancellationException) {
                throw throwable
            }

            // return fallback if provided
            onErrorFallback?.let { fallback -> return fallback() }

            // if we get here the error fallback didn't provide a fallback result, so throw the
            // exception to the caller
            throw throwable
        }
    }

}