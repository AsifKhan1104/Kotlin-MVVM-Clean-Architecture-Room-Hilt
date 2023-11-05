package com.example.practiceapp1.cleanarchitecture.util

/**
 * Created by Asif Khan on 03/11/23.
 */
sealed class DataHandler<T>(
    val data: T? = null,
    val msg: String? = null
) {
    class SUCCESS<T>(data: T?) : DataHandler<T>(data)
    class FAILURE<T>(data: T? = null, msg: String?) : DataHandler<T>(data, msg)
    class LOADING<T> : DataHandler<T>()
}
