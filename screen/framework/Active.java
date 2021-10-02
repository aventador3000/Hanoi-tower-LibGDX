package ru.kow.honoigame.screen.framework;
/**
 * Активный объект
 * Может сталкиваться с другими.
 */


public interface Active {
    /**
     * возвращает успех влияния
     *  @return true, если объекты прореагировали
     */
    boolean react(DrawableObject obj);
}