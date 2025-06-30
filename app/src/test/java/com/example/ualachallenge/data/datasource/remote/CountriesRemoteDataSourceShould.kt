package com.example.ualachallenge.data.datasource.remote

import com.example.ualachallenge.core.MockWebServerRule
import com.example.ualachallenge.core.assertThatEquals
import com.example.ualachallenge.core.assertThatIsInstanceOf
import com.example.ualachallenge.data.datasource.exception.DataException
import com.example.ualachallenge.data.datasource.remote.retrofit.CountriesServiceRetrofit
import com.example.ualachallenge.fakedata.EXPECT_COUNTRY_ENDPOINT
import com.example.ualachallenge.fakedata.givenCountriesResponseFakeData
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CountriesRemoteDataSourceShould {

    @get:Rule
    val webServerRule = MockWebServerRule()

    private lateinit var countriesRemoteDataSource: CountriesRemoteDataSource

    @Before
    fun setup() {
        countriesRemoteDataSource = CountriesRemoteDataSourceImpl(
            webServerRule.mockRetrofit().create(CountriesServiceRetrofit::class.java)
        )
    }

    @Test
    fun `Get CountriesResponse data when fetchCountries is success`(): Unit = runTest {
        val countriesResponse = givenCountriesResponseFakeData()
        webServerRule.loadMockResponse(fileName = "countriesResponse.json")

        val result = countriesRemoteDataSource.fetchCountries().lastOrNull()

        webServerRule.assertRequestMethod(
            path = EXPECT_COUNTRY_ENDPOINT,
            method = MockWebServerRule.GET
        )
        assertThatEquals(result?.getOrNull(), countriesResponse)
    }

    @Test
    fun `Get CountriesException data when fetchCountries is failure`() = runTest {
        webServerRule.loadMockResponse(responseCode = 400)

        val result = countriesRemoteDataSource.fetchCountries().lastOrNull()

        webServerRule.assertRequestMethod(
            path = EXPECT_COUNTRY_ENDPOINT,
            method = MockWebServerRule.GET
        )
        assertThatIsInstanceOf<DataException.CountriesException>(result?.exceptionOrNull())
    }
}
