package com.app3null.common_code.mvvm.common


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class LayoutResId(
    val value: Int
)