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

package com.payment.ayushdigitils.vo


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */

sealed class Resource<out T : Any?> {
    data class Success<out T : Any?>(val data: T?) : Resource<T>()
    data class Error<out T : Any?>(val exception: Exception, val data: T?) : Resource<T>()

    // data class Loading<out T : Any?>(val data: T = null) : Resource<Nothing>()
    data class Loading<out T : Any?>(val data: T?) : Resource<T>()
}