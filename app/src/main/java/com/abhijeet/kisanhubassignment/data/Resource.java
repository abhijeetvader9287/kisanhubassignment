/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abhijeet.kisanhubassignment.data;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.abhijeet.kisanhubassignment.data.Status.ERROR;
import static com.abhijeet.kisanhubassignment.data.Status.LOADING;
import static com.abhijeet.kisanhubassignment.data.Status.SUCCESS;
/**
 * The type Resource.
 *
 * @param <T> the type parameter
 */
public class Resource<T> {
    /**
     * The Status.
     */
    @NonNull
    public final Status status;
    /**
     * The Data.
     */
    @Nullable
    public final T data;

    private Resource(@NonNull Status status, @Nullable T data) {
        this.status = status;
        this.data = data;
    }

    /**
     * Success resource.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the resource
     */
    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data);
    }

    /**
     * Error resource.
     *
     * @param <T>  the type parameter
     * @param msg  the msg
     * @param data the data
     * @return the resource
     */
    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data);
    }

    /**
     * Loading resource.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the resource
     */
    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data);
    }
}
