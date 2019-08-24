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

    private val contextMap = mutableMapOf<String, MutableMap<Class<*>, EventData<*>>>()

    @JvmStatic
    fun <T> registerEvent(
        contextName: String,
        eventDispatcher: CoroutineDispatcher,
        eventClass: Class<T>,
        eventCallback: (T) -> Unit
    ) {
        val pipeList = if (contextMap.containsKey(contextName)) {
            contextMap[contextName]!!
        } else {
            val eventPipe = mutableMapOf<Class<*>, EventData<*>>()
            contextMap[contextName] = eventPipe
            eventPipe
        }
        val eventData = EventData(this, eventDispatcher, eventCallback)
        pipeList[eventClass] = eventData
    }

    @JvmStatic
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

    @JvmStatic
    fun unregisterAllEvents() {

        Log.i(TAG,"unregisterAllEvents()")
        coroutineContext.cancelChildren()
        for ((_, pipe) in contextMap) {
            pipe.values.forEach { it.cancel() }
            pipe.clear()
        }
        contextMap.clear()
    }

    @JvmStatic
    fun unregisterByContext(contextName: String) {

        Log.i(TAG,"unregisterByContext(context: $contextName)")

        val cloneContextList = mutableMapOf<String, MutableMap<Class<*>, EventData<*>>>()
        cloneContextList.putAll(contextMap)
        val pipesInContext = cloneContextList.filter { it.key == contextName }
        for ((_, pipe) in pipesInContext) {
            pipe.values.forEach { it.cancel() }
            pipe.clear()
        }
        contextMap.remove(contextName)
    }

    private fun send(event: Any) {
        val cloneContextList = mutableMapOf<String, MutableMap<Class<*>, EventData<*>>>()
        cloneContextList.putAll(contextMap)
        for ((_, pipe) in cloneContextList) {
            pipe.keys.firstOrNull { it == event.javaClass || it == event.javaClass.superclass }
                .let { key ->
                    pipe[key]?.send(event)
                }
        }
    }
}