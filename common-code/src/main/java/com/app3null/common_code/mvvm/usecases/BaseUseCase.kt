package com.app3null.common_code.mvvm.usecases

interface BaseUseCase<ARG_TYPE, RETURN_TYPE> {
    fun start(arg: ARG_TYPE? = null): RETURN_TYPE
}