# Клиент проекта под Android.
---
## Архитектура
Архитектура построена на базе чёткого разделения проекта на уровни. В качестве вводных для данного подхода необходимо ознакомиться со следующими материалами:
- [The Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Architecting Android…The clean way?](http://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/)
- [Architecting Android…The evolution](http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/)
- [Евгений Мацюк — Пишем тестируемый код](https://youtu.be/AlxMGxs2QnM)
- [Заблуждения Clean Architecture](https://habrahabr.ru/company/mobileup/blog/335382/)

### Передача данных
Основным средством передачи данных в системе являются Rx-потоки (на базе [RxJava2](https://github.com/ReactiveX/RxJava)) (см. http://reactivex.io, https://github.com/ReactiveX/RxJava).
При этом в системе присутствуют следующие схемы взаимодействия через потоки: постоянные (без завершения) (например валидация форм или отметки о доступности сети/сервера) и завершаемые (передача данных списка/фактов). При этом используются следующие методы:
- `getXXXHotStream` – получить бесконечный hot stream для организации подписки (при этом подписка/отписка осуществляется вызывающей стороной)
- `createXXXStream` – получить конечный `Observable` (при этом при получении `onComplete` возможна некоторая дополнительная обработка полученных данных)
- при этом в случае если при получении данных используются только закэшированные данные, то используется метод `createLocalXXXStream`
- при этом в случае если при получении данных используются только удалённые данные, то используется метод `createRemoteXXXStream`
- при этом в случае если при получении данных происходит кэширование успешно полученных данных, то используется метод `createRemoteCachedCachedXXXStream`

### Политика получения данных (актуальных и локальных)
Получение данных (в большинстве случаев) организуется таким образом:
- (уровень "data") формируется поток данных из БД (закэшированные данные)
- (уровень "data") формируется поток данных с сети (актуальные данные)
- (уровень "data") к потоку данных из сети подключается слушатель для обновления через транзакцию данных в БД (т.е. при успешном обновлении -- обновляется кэш)
- (уровень "data") итоговый поток объединяется таким образом, что при поступлении данных из сети (на текущий момент таймаут не проверяется) данные из кэша не берутся и наоборот
- уровень "ui" и "business' просто работают с данными - прозрачно пробрасывая исключения в "ui" (presenter)

_При этом обновление данных оформляется аналогичным образом_
Хорошее пояснениние использованной методики (в технических деталях) тут: http://stackoverflow.com/a/36118469

## Порядок инициализации компонентов
- **Activity/Fragment**:
 - создаётся Android (non-singletone, w/ scope)
 - инициализируется в методах `onCreate()` (с завершением в `onViewCreated()` для `Fragment`)
 - Presenter внутри присваивается ч/з Dagger2
 - инициализация Presenter внутри осуществляется в указанных методах (`onCreate()`,с завершением в `onViewCreated()` для `Fragment`)
- **Presenter**:
 - создаётся через Dagger2 (non-singletone, w/ scope)
 - инициализируется в методе `initialize()`, вызываемой View
 - View присваивается самим View, ч/з `bindView()`
 - Interactor внутри присваивается/присваивается ч/з Dagger2
 - создание связи Interactor->Presenter выполняется в методе `initialize()`
- **Interactor**:
 - создаётся через Dagger2 (singletone, w/o scope)
 - инициализируется через Dagger2
 - Repository внутри присваивается/инициализируется ч/з Dagger2
- **Repository**:
 - создаётся через Dagger2 (singletone, w/o scope)
 - инициализируется через Dagger2

## Объекты для обмена данных
- на текущий момент используются 2 типа сущностей `data+ui`/`business`
- конвертация осуществляется в статических методах самих объектов
- основное правило конвертации в более низкий уровень объекты должные передаваться уже сконвыертированными


---
## Тестирование
Основными библиотека для тестирования являются
- [AssertJ](http://joel-costigliola.github.io/assertj/)
- [Robolectric](http://robolectric.org/)
- [Mockito](http://site.mockito.org/)
- [Espresso](http://google.github.io/android-testing-support-library/docs/espresso/index.html)

---
## Тезисно о прототипе:
- бесконечная лента новостей
просмотра новости (естественно….)
- ленты новостей от разных изданий
- категоризация каждой новости на отношений к одному или нескольким разделам (группировка по ключевым словам на сервере)
- ключевые тэги по каждой новости
- поиск новостей по тексту
- геолокация по указанному городу
- настройка источников по местоположению или ручная

#### "Браузер источников" (SourcesBrowser)
* **Местоположение:**  activity по-умолчанию
* **Путь:** activity по-умолчанию
* **Источник данных:** БД (кэш) и REST API (http://domain/articles)
* **Особенности:**
 - первоначальная иницилизация интерфейсов и настроек пользователя
 - скроллинг с выбором источников данных

#### «Лента новостей» (NewsTape)
* **Местоположение:**  один из фрагментов в SourceBrowser's viewPager
* **Путь:** фрагмент по-умолчанию
* **Источник данных:** БД (кэш) и REST API (http://domain/articles)
* **Особенности:**
 - Перелистывание влево и вправо позволяет переключаться на следующие источники
 - Обновление свайпом
 - Дозагрузка данных с использованием эмуляции бесконечного списка
 - Возможны внедрения рекламы и viewPager вместо одного из элементов списка

####  "Браузер новости" (NewsBrowser)
* **Местоположение:** Переход из ленты при клике
* **Путь:** Лента -> клик по записи (контейнер записей)
* **Источник данных:**
* **Особенности:**
 - Перелистывание влево и вправо позволяет переключаться на следующие/предыдущие новости в ленте текущего источника (source)

####  «Просмотрщик новости» (NewsWatcher)
* **Местоположение:** Каждое перелистанная страница в браузере новостей
* **Путь:** Лента -> клик по записи (сама запись)
* **Источник данных:**
* **Особенности:**
- Пока только оотображение содержания новости

####  TBD: «Выбор категорий/настроек/доп. информации» (CategorySelector)
* **Местоположение:** SlidePaneLayout слева от ленты (по-умолчанию скрыта)
* **Путь:** Ленты новостей -> слиде с края экрана
**Источник данных:** Пока предопределнные значения
* **Особенности:**
 - В списке имеется поле для поиска, позволяющее осуществлять поиск новостей
 - В списке на текущий момент предопределённые значения
 - Клик на записях открывает соответствующие фрагменты или открывает ленту с фильтром по категории

####  TBD: «Выбор местоположения» (LocationSelector)
* **Местоположение:** Выбор местоположения из настроек
* **Путь:** Ленты новостей -> слиде с края экрана -> выбор пункта местоположения
* **Источник данных:**
* **Особенности:**
 - Отфильтрация на клиенте по мере ввода символов
 - Сохранение в настройках приложения
