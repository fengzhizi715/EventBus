package com.safframework.eventbus

import android.util.Log
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 *
 * @FileName:
 *          com.safframework.eventbus.EventBus
 * @author: Tony Shen
 * @date: 2019-08-24 23:28
 * @version: V1.0 <描述当前版本功能>
 */
object EventBus: CoroutineScope {

    private val TAG = "EventBus"

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext = Dispatchers.Default + job

    fun <T> registerEvent(
        contextName: String,
        eventDispatcher: CoroutineDispatcher,
        eventClass: Class<T>,
        eventCallback: (T) -> Unit
    ) {
        val pipeList = if (contextList.containsKey(contextName)) {
            contextList[contextName]!!
        } else {
            val eventPipe = mutableMapOf<Class<*>, EventData<*>>()
            contextList[contextName] = eventPipe
            eventPipe
        }
        val eventData = EventData(this, eventDispatcher, eventCallback)
        pipeList[eventClass] = eventData
    }


    fun send(event: Any, delaySend: Long = 0) {
        if (delaySend > 0) {
            launch {
                delay(delaySend)
                send(event)
            }
        } else {
            send(event)
        }
    }

    fun unregisterAllEvents() {
        unregisterAllEvent()
    }

    fun unregisterByContext(contextName: String) {

        Log.i(TAG,"unregisterByContext(context: $contextName)")

        val cloneContextList = mutableMapOf<String, MutableMap<Class<*>, EventData<*>>>()
        cloneContextList.putAll(contextList)
        val pipesInContext = cloneContextList.filter { it.key == contextName }
        for ((_, pipe) in pipesInContext) {
            pipe.values.forEach { it.cancel() }
            pipe.clear()
        }
        contextList.remove(contextName)
    }

    private val contextList = mutableMapOf<String, MutableMap<Class<*>, EventData<*>>>()

    private fun send(event: Any) {
        val cloneContextList = mutableMapOf<String, MutableMap<Class<*>, EventData<*>>>()
        cloneContextList.putAll(contextList)
        for ((_, pipe) in cloneContextList) {
            pipe.keys.firstOrNull { it == event.javaClass || it == event.javaClass.superclass }
                .let { key ->
                    pipe[key]?.send(event)
                }
        }
    }

    private fun unregisterAllEvent() {

        Log.i(TAG,"unregisterAllEvent()")
        coroutineContext.cancelChildren()
        for ((_, pipe) in contextList) {
            pipe.values.forEach { it.cancel() }
            pipe.clear()
        }
        contextList.clear()
    }
}