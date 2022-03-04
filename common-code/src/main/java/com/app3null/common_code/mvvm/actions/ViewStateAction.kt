package com.app3null.common_code.mvvm.actions

interface ViewStateAction<VIEW_STATE> {
    fun newState(oldState: VIEW_STATE): VIEW_STATE
}