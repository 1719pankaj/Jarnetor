package com.example.jarnetor.vo

class Resource<T>(val status: Status, val body: T?, val message: String?) {
    companion object {

        fun <T> success(body: T?): Resource<T> = Resource(Status.SUCCESS, body, null)

        fun <T> error(msg: String?, body: T?): Resource<T> = Resource(Status.ERROR, body, msg)

        fun <T> loading(data: T?): Resource<T> = Resource(Status.LOADING, data, null)
    }
}