package com.dourl.compose.observer


fun main(args: Array<String>) {
    var subject: KWhetherSubject = KWhetherSubject()
    var observer: KWhetherObserver = KWhetherObserver(subject);
    var observer2: KWhetherObserver2 = KWhetherObserver2(subject);
    subject.setTemperature(29f)
}

