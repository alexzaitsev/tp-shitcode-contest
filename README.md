### Реализация задания к [олимпиде Типичного программиста](https://tproger.ru/events/shitcode-contest/) 

Нужно написать самый тупой код, который выводит время, больше текущего на 23 часа.
Не знаю, надо ли учитывать дату, но я решил, что надо.

Мой основной язык - Java, поэтому я выбрал его. Java позиционируется как кроссплатформенный язык и поэтому грех не пользоваться предоставляемыми плюшками. Но поскольку основная задача - грешить, значит будем грешить :) 
Итак, первый этап - это получение текущего времени и даты. И делать мы это будем, наплевав на кроссплатформ, а именно, вызывая команды для соответствующей оси.
Для Windows это "wmic os get LocalDateTime", которая возвращает дату и время всегда в одном формате, в не зависимости от локали, тогда как "time /T" и "date /T" могут иметь разные форматы вывода. Можно было бы воспользоваться и ими, но тогда написание кода и тестирование заняло бы слишком много времени :) 
Для Linux это команда "date". Здесь я использую форматирование, чтобы выходная строка была аналогична результату виндовой команды. Но это не индусс-way, здесь надо получать строку как есть и парсить ее отдельно.
Итак, выполнив платформозависимую команду, мы получаем строку (для виндовс еще и определение нужной строки сделано с явным игнорированием метода contains класса String), которая содержит необходимые нам данные. Если же ОС не виндовс и не линукс - придется все же воспользоваться встроенным в java классом Calendar.
Далее эту строку нужно распарсить. Здесь мы опять максимально привязываемся к магическим числам и забываем про String.substring(...).

Как-то сложновато для простого определения даты и времени? Это только начало.

Далее идет собственно вычисление новой даты и времени.
Мы прибавляем 23 к часам и смотрим, если это число больше возможного - нам нужно перескочить на следующий день. Мы это делаем и теперь нужно проверить, надо ли нам перескочить на следующий месяц.
Для этого нужно узнать кол-во дней в текущем месяце. А если это будет еще и февраль, то нужно узнать не высокосный ли сейчас год. Опять забыв, что в Java есть метод для определения высокосного года (да и для кол-ва дней в месяце тоже есть..), мы пишем условие, которое говорит, что выкососный год должен делиться на 4 без остатка и не делиться на 100 или же делиться на 4, на 100 и на 400 без остатка. 
Стоп.
Что-то слишком просто, давайте подумаем, как написать это потупее. Для этого создаем массив LEAP_YEARS, который содержит высокосные годы от 1800 до 2400. Должно хватить, врядли этот код, написанный в белой горячке, будет запущен позже. (Про раньше давайте умолчим. Про перевод часов тоже:))
Итак, мы снова ушли от универсального решения к проверенному индусскому коду. Далее простым сравнением выясняем, сколько же дней в текущем месяце. 
Если выясняется, что нам нужно перепрыгнуть на следующий месяц - делаем это, и, если необходимо, прибавляем год (вот нечего делать кому-то, в Новый год запускать эту программу).

Затем выводим тяжело добытое итоговое значение.

Я не использовал тяжелые формы индусского кода типа сравнений:  
```
boolean value = true;
if (value.toString().length() == 4 &&
value.toString().charAt(0) == 't' &&
value.toString().charAt(1) == 'r' &&
value.toString().charAt(2) == 'u' &&
value.toString().charAt(3) == 'e') {}
```  
с целью всё же оставить этот код читаемым. Да и врядли такое приятно читать :)

Программу можно усложнить, вынеся высокосные года в отдельный текстовый файл. Тогда его еще надо было бы прочитать.
Также дату и время можно получать по HTTP запросу, но это, я предполагаю, реализовало большинство. Выполнение платформозависимой команды мне показалось более тупым :)

Спасибо за внимание, надеюсь мой код вас повеселил.
