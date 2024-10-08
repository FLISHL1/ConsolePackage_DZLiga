# Программа для погрузки посылок в грузовики

## Описание

Программа предназначена для загрузки грузовиков посылками из текстового файла с использованием двух вариантов алгоритмов погрузки. Программа запускается через консоль и позволяет пользователю выбрать тип загрузки посылок в грузовики, а также проверить загруженные посылки из файла JSON.

### Варианты погрузки:
1. **Максимальная погрузка** – посылки распределяются по грузовикам с максимальным заполнением, при этом пользователь указывает максимальное количество грузовиков.
2. **Равномерная погрузка** – посылки распределяются равномерно по объему на указанное пользователем количество грузовиков.

## Функции программы

Программа поддерживает следующие функции:

1. **Погрузка грузовиков посылками из файла .txt**:
    - Пользователь вводит команду в консоль для вызова функции
    - Для указания пути до файла нужно использовать флаги `-f or --filePath`
    - Для указания размерности грузовиков нужно использовать фалаги `-s or --sizeTrucks`
    - Размерность указывается через пробел в формате `{width}x{height}`, где `width` это ширина грузовик,
      а ```height``` высота грузовик
      - Есть две команды для двух алгоритмов погрущки:
          - **Равномерная погрузка**.
            ```shell
                loader-truck-uniform-file -f test.txt -s 6x6 1x3 5x5 12x12
            ```
          - **Максимальная погрузка**.
            ```shell
            loader-truck-maximal-file -f test.txt -s 6x6 1x3 5x5 12x12
            ```
    - Результат погрузки сохраняется в файл `.json` по пути прописанному в `application.property` в `util.json.truck.write.file`.
   
2. **Погрузка грузовиков посылками из сохраненных типов**:
    - Пользователь вводит команду в консоль для вызова функции
    - Для указания имен коробок нужно использовать флаги `-b or --boxes`
    - Имена коробок указываются через пробел
    - Файл `.json` откуда берутся типы коробок указываются в `application.property` в `util.json.box.file`
    - Для указания размерности грузовиков нужно использовать фалаги `-s or --sizeTrucks`
    - Размерность указывается через пробел в формате `{width}x{height}`, где `width` это ширина грузовик,
      а ```height``` высота грузовик
        - Есть две команды для двух алгоритмов погрущки:
            - **Равномерная погрузка**.
              ```shell
                  loader-truck-uniform-box -b test test test -s 6x6 1x3 5x5 12x12
              ```
            - **Максимальная погрузка**.
              ```shell
              loader-truck-maximal-box -b test test test -s 6x6 1x3 5x5 12x12
              ```
    - Результат погрузки сохраняется в файл `.json` по пути прописанному в `application.property` в `util.json.truck.write.file`.
   
3. **Определение количества посылок в загруженных грузовиках из файла .json**:
    - Пользователь вводит команду в консоль для вызова функции
    - Для указания пути до файла нужно использовать флаги `-f or --filePath`
    - Команда для вызова счета:
      ```shell
                count-box-in-trucks -f testTruck.json
      ```

4. **Работа с типа посылок**
   - Пользователь вводит команду в консоль для вызова функции
   - Команды:
     - box-add: Сохраняет тип коробки
       - Для указания названия нужно использовать флаг `-n or --name`
       - Для указания формы нужно исопльзовать флаг `-s or --space`
       - Формат указания формы такой 1,1,1,1 1 1,1,1
       - Вместо единичек можно подставить какой нибудь другой символ
       ```shell
        box-add -n newBox -s !,!,! !,!,! !,!,!,!,!
       ```
     - box-all: Выводит все сохраненные типы коробок
       ```shell
        box-all
       ```
     - box-update: Обновляет тип коробки
       - Для указания названия коробки для обнавления нужно использовать флаг `-n or --name` обязательный флаг
       - Для указания нового имени типа коробки нужно использовать флаг `--newName` не обязательный
       - Для указания формы нужно исопльзовать флаг `-s or --space` не обязательный
       - Формат указания формы такой 1,1,1,1 1 1,1,1
       - Вместо единичек можно подставить какой нибудь другой символ
       - Для указания нового символа заполнения коробки нужно использовать флаг `--charSpace or -c` не обязательный
       ```shell
        box-update -n test --newName testNew -s !,!,! !,!,! !,!,!,!,! -c %
       ```
     - box: Выводит тип коробки по имени
       - Для указания названия коробки для обнавления нужно использовать флаг `-n or --name`
       ```shell
       box -n test
       ```
     - box-remove: Удаляет тип коробки по имени
       - Для указания названия коробки для обнавления нужно использовать флаг `-n or --name`
       ```shell
       box-remove -n test
       ```

## Формат файлов

### Пример текстового файла с посылками (формат `.txt`):
```txt
1

22

333

4444
```
Каждая строка представляет собой посылку, пустая строчка означает начало новой посылки и конец старой, а её длина в символах указывает на объем посылки.

### Пример JSON файла с загруженными грузовиками (формат `.json`):
```json
[
   {
      "trunk": {
         "HEIGHT": 6,
         "WIDTH": 6,
         "boxes": [
            {"space": [[1]]}
         ],
         "space": [
            [1, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0]
         ]
      }
   }
]
```

## Запуск программы
1. **Погрузка грузовиков:**
- Ввести функцию для погрузки и указать нужные флаги.
- После завершения погрузки результат будет сохранен в формате .json и выведен в консоль.

2. **Проверка количества посылок в загруженных грузовиках:**

- Ввести функцию для погрузки и указать нужные флаги.
- Программа выведет количество и объем каждой посылки, загруженной в каждый грузовик.
## Исключения и ошибки
- **Некорректное имя файла (```UserInputException```)**
  + Если введенное имя файла не соответствует формату .txt или .json, программа выведет сообщение об ошибке и запросит корректное имя.
- **Некорректные данные в файле**
   + Если в текстовом файле содержатся неверные данные, программа завершится с соответствующим сообщением об ошибке.
- **Недостаточное количество грузовиков при максимальной погрузки (```LoadingCapacityExceededException```)**
  + Если заданное количество поссылок не помещается в заданном количестве грузовиков
- **Не помещается поссылка ни в один из грузовиков при равномерной погрузке (```LoadingCapacityExceededException```)**
- **Тип посылки не найден (```BoxNotFoundExeption```)**
- **Коробка не помещается в заданных координатах багажника (```BoxDoesNotFitExeption```)**
- **Дублирование имени коробки (```IdentityNameBoxException```)**
- **Ошибка чтения json файла (```ReadJsonException```)**
- **Ошибка записи в json файла (```WriteJsonException```)**
