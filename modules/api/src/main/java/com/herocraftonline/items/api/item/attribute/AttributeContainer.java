/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.item.attribute;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Contains various methods to simplify working with attributes that could be nested in many different ways.
 *
 * @author Austin Payne
 */
public interface AttributeContainer {

    // Methods that only affect immediate attributes in the container

    /**
     * Checks if the container contains an attribute of a certain name.
     *
     * @param name the attribute's name
     * @return {@code true} if containing an attribute of the given name
     */
    boolean hasAttribute(String name);

    /**
     * Checks if the container contains an attribute of a certain type.
     *
     * @param type the attribute type
     * @return {@code true} if containing an attribute of the given type
     */
    boolean hasAttribute(Class<? extends Attribute> type);

    /**
     * Checks if the container contains an attribute matching a certain predicate.
     *
     * @param predicate the attribute predicate
     * @return {@code true} if containing an attribute that matches the predicate
     */
    boolean hasAttribute(Predicate<Attribute> predicate);

    /**
     * Gets an attribute of a certain name from the container.
     *
     * @param name the attribute's name
     * @return the attribute optional, present if containing an attribute of the given name
     */
    Optional<Attribute> getAttribute(String name);

    /**
     * Gets an attribute matching a certain predicate from the container.
     *
     * @param predicate the attribute predicate
     * @return the attribute optional, present if containing an attribute that matches the given predicate
     */
    Optional<Attribute> getAttribute(Predicate<Attribute> predicate);

    /**
     * Gets an attribute of a certain type from the container.
     *
     * @param type the attribute type
     * @return the attribute optional, present if containing an attribute of the given type
     */
    <T extends Attribute> Optional<T> getAttribute(Class<T> type);

    /**
     * Gets an attribute of a certain name and type from the container.
     *
     * @param type the attribute type
     * @param name the attribute's name
     * @return the attribute optional, present if containing an attribute of the given name and type
     */
    <T extends Attribute> Optional<T> getAttribute(Class<T> type, String name);

    /**
     * Gets an attribute matching a certain predicate and type from the container.
     *
     * @param type      the attribute type
     * @param predicate the attribute predicate
     * @return the attribute optional, present if containing an attribute that matches the given predicate and type
     */
    <T extends Attribute> Optional<T> getAttribute(Class<T> type, Predicate<T> predicate);

    /**
     * Gets a collection of the attributes currently in the container.
     *
     * @return the attributes in the container
     */
    Collection<Attribute> getAttributes();

    /**
     * Gets a collection of attributes matching a certain predicate currently in the container.
     *
     * @param predicate the attribute predicate
     * @return the attributes in the container that match the given predicate
     */
    Collection<Attribute> getAttributes(Predicate<Attribute> predicate);

    /**
     * Gets a collection of attributes of a certain type currently in the container.
     *
     * @param type the attribute type
     * @return the attributes in the container of the given type
     */
    <T extends Attribute> Collection<T> getAttributes(Class<T> type);

    /**
     * Gets a collection of attributes matching a certain predicate and type currently in the container.
     *
     * @param type      the attribute type
     * @param predicate the attribute predicate
     * @return the attributes in the container that match the given predicate and type
     */
    <T extends Attribute> Collection<T> getAttributes(Class<T> type, Predicate<T> predicate);

    /**
     * Performs a certain operation on the attributes currently in the container.
     *
     * @param action the action to perform
     */
    void forEach(Consumer<Attribute> action);

    /**
     * Performs a certain operation on the attributes matching a certain predicate currently in the container.
     *
     * @param predicate the attribute predicate
     * @param action    the action to perform
     */
    void forEach(Predicate<Attribute> predicate, Consumer<Attribute> action);

    /**
     * Performs a certain operation on the attributes of a certain type currently in the container.
     *
     * @param type   the attribute type
     * @param action the action to perform
     */
    <T extends Attribute> void forEach(Class<T> type, Consumer<T> action);

    /**
     * Performs a certain operation on the attributes matching a certain predicate and type currently in the container.
     *
     * @param type      the attribute type
     * @param predicate the attribute predicate
     * @param action    the action to perform
     */
    <T extends Attribute> void forEach(Class<T> type, Predicate<T> predicate, Consumer<T> action);

    // Methods that affect immediate attributes in the container as well as in nested containers

    /**
     * Checks if the container contains an attribute of a certain name.
     *
     * @param name the attribute's name
     * @return {@code true} if containing an attribute of the given name
     */
    boolean hasAttributeDeep(String name);

    /**
     * Checks if the container contains an attribute of a certain type.
     *
     * @param type the attribute type
     * @return {@code true} if containing an attribute of the given type
     */
    boolean hasAttributeDeep(Class<? extends Attribute> type);

    /**
     * Checks if the container contains an attribute matching a certain predicate.
     *
     * @param predicate the attribute predicate
     * @return {@code true} if containing an attribute that matches the predicate
     */
    boolean hasAttributeDeep(Predicate<Attribute> predicate);

    /**
     * Gets an attribute of a certain name from the container.
     *
     * @param name the attribute's name
     * @return the attribute optional, present if containing an attribute of the given name
     */
    Optional<Attribute> getAttributeDeep(String name);

    /**
     * Gets an attribute matching a certain predicate from the container.
     *
     * @param predicate the attribute predicate
     * @return the attribute optional, present if containing an attribute that matches the given predicate
     */
    Optional<Attribute> getAttributeDeep(Predicate<Attribute> predicate);

    /**
     * Gets an attribute of a certain type from the container.
     *
     * @param type the attribute type
     * @return the attribute optional, present if containing an attribute of the given type
     */
    <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type);

    /**
     * Gets an attribute of a certain name and type from the container.
     *
     * @param type the attribute type
     * @param name the attribute's name
     * @return the attribute optional, present if containing an attribute of the given name and type
     */
    <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type, String name);

    /**
     * Gets an attribute matching a certain predicate and type from the container.
     *
     * @param type      the attribute type
     * @param predicate the attribute predicate
     * @return the attribute optional, present if containing an attribute that matches the given predicate and type
     */
    <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type, Predicate<T> predicate);

    /**
     * Gets a collection of the attributes currently in the container.
     *
     * @return the attributes in the container
     */
    Collection<Attribute> getAttributesDeep();

    /**
     * Gets a collection of attributes matching a certain predicate currently in the container.
     *
     * @param predicate the attribute predicate
     * @return the attributes in the container that match the given predicate
     */
    Collection<Attribute> getAttributesDeep(Predicate<Attribute> predicate);

    /**
     * Gets a collection of attributes of a certain type currently in the container.
     *
     * @param type the attribute type
     * @return the attributes in the container of the given type
     */
    <T extends Attribute> Collection<T> getAttributesDeep(Class<T> type);

    /**
     * Gets a collection of attributes matching a certain predicate and type currently in the container.
     *
     * @param type      the attribute type
     * @param predicate the attribute predicate
     * @return the attributes in the container that match the given predicate and type
     */
    <T extends Attribute> Collection<T> getAttributesDeep(Class<T> type, Predicate<T> predicate);

    /**
     * Performs a certain operation on the attributes currently in the container.
     *
     * @param action the action to perform
     */
    void forEachDeep(Consumer<Attribute> action);

    /**
     * Performs a certain operation on the attributes matching a certain predicate currently in the container.
     *
     * @param predicate the attribute predicate
     * @param action    the action to perform
     */
    void forEachDeep(Predicate<Attribute> predicate, Consumer<Attribute> action);

    /**
     * Performs a certain operation on the attributes of a certain type currently in the container.
     *
     * @param type   the attribute type
     * @param action the action to perform
     */
    <T extends Attribute> void forEachDeep(Class<T> type, Consumer<T> action);

    /**
     * Performs a certain operation on the attributes matching a certain predicate and type currently in the container.
     *
     * @param type      the attribute type
     * @param predicate the attribute predicate
     * @param action    the action to perform
     */
    <T extends Attribute> void forEachDeep(Class<T> type, Predicate<T> predicate, Consumer<T> action);

}
