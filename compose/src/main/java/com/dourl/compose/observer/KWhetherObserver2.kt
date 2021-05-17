package com.dourl.compose.observer

/**
 * 订阅者 （数据的处理者）
 */
class KWhetherObserver2(var subject: KSubject) : KObserver {
    var temperature:Float = 0.0f

    init {
        subject.registerObserver(this)
    }

    override fun update(temp: Float) {
        this.temperature = temp;
        display()
    }

    /**
     *
     */
    fun display(){
        print("展示在大屏2的温度为： ${temperature}")
    }
}