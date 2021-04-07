package com.picpay.desafio.android.users.usecase.exception

import com.picpay.desafio.android.users.usecase.constants.UserUseCaseConstants.EMPTY_LIST_MESSAGE

class UserEmptyException(override val message: String? = EMPTY_LIST_MESSAGE) : Exception()