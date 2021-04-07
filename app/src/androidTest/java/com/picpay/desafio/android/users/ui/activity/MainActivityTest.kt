package com.picpay.desafio.android.users.ui.activity

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.users.repository.local.database.UserDataBase
import com.picpay.desafio.android.users.ui.activity.AndroidTestUtils.setupServerUrl
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test


class MainActivityTest {

    private val server = MockWebServer()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var dataBase: UserDataBase

    @Before
    fun setUp() {
        setupServerUrl(server)
        dataBase = Room.inMemoryDatabaseBuilder(context, UserDataBase::class.java).build()
        dataBase.clearAllTables()
    }

    @After
    fun tearDown() {
        server.shutdown()
        dataBase.clearAllTables()
    }

    @Test
    fun shouldDisplayTitle() {
        prepare {
            mockSucces(server)
        }
        execute {
            initActivityAndMoveToResumed()
        }
        validate {
            validateTitle()
        }
    }

    @Test
    fun shouldDisplayListItem() {
        prepare {
            mockSucces(server)
        }
        execute {
            initActivityAndMoveToResumed()
        }
        validate {
            validateRecycler()
        }
    }

    @Test
    fun shouldDisplayError() {
        prepare {
            mockError(server)
        }
        execute {
            initActivity()
        }
        validate {
            validateErrorMessage()
        }
    }
}