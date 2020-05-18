package com.app3null.common_code.exceptions

class ServerSideException constructor(val code: Int, override val message: String) : Exception(message)