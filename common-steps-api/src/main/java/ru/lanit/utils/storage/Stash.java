package ru.lanit.utils.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lanit.exceptions.StashException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Collections.unmodifiableMap;

@SuppressWarnings("unchecked")
public enum Stash {
    STASH;

    private final Logger LOG = LogManager.getLogger(Stash.class);
    private ThreadLocal<HashMap<String, Object>> innerStash = ThreadLocal.withInitial(HashMap::new);

    /**
     * Сохранение значения по ключу.
     * Если такой ключ уже существует, произойдет замена старой пары значений на новую.
     *
     * @param key   - ключ для сохранения
     * @param value - значение
     */
    public void put(String key, Object value) {
        if (innerStash.get().containsKey(key)) {
            LOG.info("Значение {} в хранилище будет изменено с {} на {}", key, innerStash.get().get(key), value);
        } else {
            LOG.info("По ключу {} в хранилище сохранено значение {}", key, value);
        }
        innerStash.get().put(key, value);
    }

    /**
     * Сохранение не null значения по ключу.
     *
     * @param key   - ключ для сохранения
     * @param value - значение
     * @throws ru.lanit.exceptions.StashException - при попытке положить null
     */
    public void putNotNull(String key, Object value) throws StashException {
        if (value == null)
            throw new StashException("Попытка положить в хранилище null по ключу " + key);
        put(key, value);
    }

    /**
     * Получить значение по ключу.
     *
     * @param key - ключ
     * @return - значение типа Object, если значение не найдено возвращает null
     */
    public Object get(String key) {
        if (!innerStash.get().containsKey(key)) {
            LOG.info("Хранилище не содержит ключа {}", key);
            return null;
        }
        Object value = innerStash.get().get(key);
        LOG.info("По ключу {} извлечено значение {}", key, value);
        return value;
    }

    /**
     * Возвращает объект приводимого класса,
     * если это возможно, иначе возникает ошибка.
     *
     * @param key - ключ
     * @param <T> - класс, объект которого желаем получить
     * @return - объект приводимого класса или null,
     * если значение по ключу не найдено
     * @throws ClassCastException - при попытке получить объект одного класса,
     *                            а по ключу сохранен объект другого класса,
     *                            подробнее см. ссылку наверху класса
     */
    public <T> T getAs(String key) {
        return (T) get(key);
    }

    /**
     * Аналогично методу #getAs(String key),
     * но если значение по ключу не найдено не будет возвращать null,
     * а выкинет NullPointerException
     *
     * @param key - ключ
     * @param <T> - класс, объект которого желаем получить
     * @return - объект приводимого класса
     * @throws ClassCastException   - при попытке получить объект одного класса,
     *                              а по ключу сохранен объект другого класса,
     *                              подробнее см. ссылку наверху класса
     * @throws NullPointerException - если значение по ключу не найдено
     */
    public <T> T getNotNullAs(String key) {
        Object val = get(key);
        Objects.requireNonNull(val);
        return (T) val;
    }

    /**
     * Возвращает строковое представление полученного по ключу значения.
     *
     * @param key - ключ
     * @return - строковое представление значения или null
     */
    public String getAsString(String key) {
        return String.valueOf(get(key));
    }

    /**
     * Возвращает объект (типа Object) полученный по ключу.
     * Если значение по ключу не найдено и равно null, возвращает дефолтный объект.
     *
     * @param key      - ключ
     * @param defValue - объект, возвращаемый по дефолту
     * @return - объект по ключу или дефолтный объект
     */
    public Object getOrDefault(String key, Object defValue) {
        Object value = get(key);
        if (value == null && !innerStash.get().containsKey(key))
            return defValue;
        return value;
    }

    /**
     * Ожидает найти в хранилище строковое значение.
     *
     * @param key - ключ
     * @return - строковое значение или переданный ключ,
     * в случае если данного ключа нет в стеше и полученное значение равно null
     * @throws ClassCastException - при попытке получить объект одного класса,
     *                            а по ключу сохранен объект другого класса,
     *                            подробнее см. ссылку наверху класса
     */
    public String getOrDefault(String key) {
        String value = getAs(key);
        if (value == null && !innerStash.get().containsKey(key))
            return key;
        return value;
    }

    /**
     * Возвращает объект приводимого класса, иначе возникает ошибка.
     * Если значение по ключу не найдено и равно null, возвращает дефолтный объект.
     *
     * @param key      - ключ
     * @param defValue - объект, возвращаемый по дефолту
     * @param <T>      - класс, объект которого желаем получить
     * @return - объект по ключу или дефолтный объект
     * @throws ClassCastException - при попытке получить объект одного класса,
     *                            а по ключу сохранен объект другого класса,
     *                            подробнее см. ссылку наверху класса
     */
    public <T> T getAsOrDefault(String key, T defValue) {
        return (T) getOrDefault(key, defValue);
    }

    /**
     * Возвращает строковое представление объекта, полученного по ключу.
     * Если значение по ключу не найдено и равно null, возвращает дефолтную строку.
     *
     * @param key      - ключ
     * @param defValue - строка, возвращаемая по дефолту
     * @return - строка по ключу или дефолтная строка
     */
    public String getAsStringOrDefault(String key, String defValue) {
        return String.valueOf(getOrDefault(key, defValue));
    }

    /**
     * Метод для получения всех значений из хранилища.
     *
     * @return - неизменяемая Map
     * @throws UnsupportedOperationException - при попытке изменения
     */
    public Map<String, Object> getAllValues() {
        return unmodifiableMap(innerStash.get());
    }
}
