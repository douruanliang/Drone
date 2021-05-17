package com.dourl.compose.observer


/**
 *  天气主题 (数据来源)
 */
class KWhetherSubject : KSubject {

    var temperature : Float = 0.0f

    var arrayList = mutableListOf<KObserver>()
    override fun registerObserver(observer: KObserver) {
        arrayList.add(observer)
    }

    override fun removeObserver(observer: KObserver) {
        arrayList.remove(observer)
    }

    override fun notification() {
        for (observer in arrayList ){
            print("------>")
            observer.update(temperature)
        }
    }

    @JvmName("setTemperature1")
    fun  setTemperature(temperature : Float){
        this.temperature = temperature;
        notification();
    }
}