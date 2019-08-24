package com.safframework.eventbus

import android.util.Log
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentHashMap
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

    private val contextMap = ConcurrentHashMap<String, MutableMap<Class<*>, EventData<*>>>()

    @JvmStatic
    fun <T> registerEvent(
        contextName: String,
        eventDispatcher: CoroutineDispatcher,
        eventClass: Class<T>,
        eventCallback: (T) -> Unit
    ) {
        val eventDataMap = if (contextMap.containsKey(contextName)) {
            contextMap[contextName]!!
        } else {
            val eventDataMap = mutableMapOf<Class<*>, EventData<*>>()
            contextMap[contextName] = eventDataMap
            eventDataMap
        }

        eventDataMap[eventClass] = EventData(this, eventDispatcher, eventCallback)
    }

    @JvmStatic
    fun send(event: Any, delayTime: Long = 0) {

        if (delayTime > 0) {
            launch {
                delay(delayTime)
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
        for ((_, eventDataMap) in contextMap) {
            eventDataMap.values.forEach {
                it.cancel()
            }
            eventDataMap.clear()
        }
        contextMap.clear()
    }

    @JvmStatic
    fun unregisterByContext(contextName: String) {

        Log.i(TAG,"$contextName")

        val cloneContexMap = ConcurrentHashMap<String, MutableMap<Class<*>, EventData<*>>>()
        cloneContexMap.putAll(contextMap)
        val map = cloneContexMap.filter { it.key == contextName }
        for ((_, eventDataMap) in map) {
            eventDataMap.values.forEach {
                it.cancel()
            }
            eventDataMap.clear()
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