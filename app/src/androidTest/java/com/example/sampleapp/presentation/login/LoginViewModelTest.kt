package com.example.sampleapp.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = LoginViewModel()
    }

    @Test
    fun testEmailLength() {
        viewModel.email.value = "test@email.com"
        val result = viewModel.getEmailLength()
        Assert.assertEquals(result, true)
    }

    @Test
    fun testPasswordLength() {
        viewModel.pwd.value = "password"
        val result = viewModel.getPwdLength()
        Assert.assertEquals(result, true)
    }


}