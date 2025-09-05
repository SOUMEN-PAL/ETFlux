package org.soumen.home.domain.repository

import org.soumen.home.data.networking.api.HomeScreenApiService
import org.soumen.home.domain.dataModels.Data
import org.soumen.home.domain.dataModels.HomeData

class HomeRepository(
    private val apiService: HomeScreenApiService
) {

    suspend fun getTopGainersAdnLosers(): Result<HomeData>{
        try {
            val data = apiService.getTopGainersLosers()
            val mappedData = HomeData(
                lastUpdated = data.lastUpdated,
                metadata = data.metadata,
                mostActivelyTraded = data.mostActivelyTraded.map {
                   Data(
                       changeAmount = it.changeAmount,
                       changePercentage = it.changePercentage,
                       price = it.price,
                       ticker = it.ticker,
                       volume = it.volume
                   )
                },
                topGainers = data.topGainers.map{
                    Data(
                        changeAmount = it.changeAmount,
                        changePercentage = it.changePercentage,
                        price = it.price,
                        ticker = it.ticker,
                        volume = it.volume
                    )
                },
                topLosers = data.topLosers.map{
                    Data(
                        changeAmount = it.changeAmount,
                        changePercentage = it.changePercentage,
                        price = it.price,
                        ticker = it.ticker,
                        volume = it.volume
                    )
                }
            )

            return Result.success(mappedData)
        }catch (e: Exception){
            return Result.failure(e)
        }
    }

}