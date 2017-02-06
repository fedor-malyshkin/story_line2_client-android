# Клиент проекта под Android.

## Архитектура
Архитектура построена на базе чёткого разделения проекта на уровни. В качестве вводных для данного подхода необходимо ознакомиться со следующими материалами:
- [The Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Architecting Android…The clean way?](http://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/)
- [Architecting Android…The evolution](http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/)
- [Евгений Мацюк — Пишем тестируемый код](https://youtu.be/AlxMGxs2QnM)

## Передача данных
В качестве основного метода взаимодействия между уровнями и внутри используется реактивный подход на базе RxJava (см. http://reactivex.io, https://github.com/ReactiveX/RxJava)



## Тестирование
Основными библиотека для тестирования являются
- [AssertJ](http://joel-costigliola.github.io/assertj/)
- [Robolectric](http://robolectric.org/)
- [Mockito](http://site.mockito.org/)
- [Espresso](http://google.github.io/android-testing-support-library/docs/espresso/index.html)
