# YANews - Yet Another News
Проект для лекции по автоматизированному тестированию в Android 2
========================

Подготавливаем окружение
------------------------
Необходимо скачать и установить:
1. Oracle JDK ( для Linux подойдет и OpenJdk )
- для Windows и Mac Os [сайт Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html "Oracle")
- для Ubuntu [сайт help ubuntu](http://help.ubuntu.ru/wiki/java "java on ubuntu")
2. Android Studio [сайт google](https://developer.android.com/studio/index.html "Android Studio")
3. git [сайт git-scm](https://git-scm.com/download/ "git")

Скачиваем исходники
------------------------
В любую папку скачиваем исходники:

 `git clone https://github.com/timmalygin/YANews.git`

после того как проект будет скопирован, необходимо запустить Android Studio и открыть наш скачанный проект.

Запуск приложения
------------------------
Перед запуском нужно создать эмулятор. [как создать эмулятор](http://aristov-vasiliy.ru/knowledge/hello-world-v-android-studio/ustanovka-emulyatora-android.html "создание эмулятор"). в Ubuntu есть проблемы с эмулятором, для правильной работы необходимо отключить compiz. 
Запуск приложения: `Run->Run 'yanews'`

Дополнительная информация
------------------------
1. ["Самый Важный Сайт"](https://developer.android.com/)
2. ["Espresso docs"](https://google.github.io/android-testing-support-library/docs/espresso/)
3. ["Espresso samples"](https://github.com/googlesamples/android-testing)
4. ["Hamcrest Quick Reference"](www.marcphilipp.de/blog/2013/01/02/hamcrest-quick-reference/)
5. ["Espresso Cheat Sheet"](https://google.github.io/android-testing-support-library/docs/espresso/cheatsheet/)

Идентификаторы
* форма авторизации
* * **R.id.login_text** - EditText для ввода логина.
* * **R.id.password_text** - EditText для ввода пароля.
* * **R.id.login** - Button для входа. Если логин или пароль пустые, то недоступна ( enabled = false )
* * **R.id.demo** - кнопка анонимного входа ( демо )
* * **R.id.registration** - кнопка открытия формы регистрации нового пользователя
* Фрагмент Регистрации
* * **R.id.login** - EditText для ввода логина.
* * **R.id.password** - EditText для ввода пароля.
* * **R.id.visible_pwd** - кнопка показа/скрытия отображения пароля
* * **R.id.action_registration** - кнопка в меню для регистрации нового пользователя. Не доступна если пароль меньше 8 символов или логин имеет не верный формат.
