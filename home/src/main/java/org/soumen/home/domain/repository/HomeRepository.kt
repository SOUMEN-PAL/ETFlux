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
                lastUpdated = data.last_updated?:"",
                metadata = data.metadata?:"",
                mostActivelyTraded = data.most_actively_traded?.map {
                   Data(
                       changeAmount = it.change_amount,
                       changePercentage = it.change_percentage,
                       price = it.price,
                       ticker = it.ticker,
                       volume = it.volume
                   )
                } ?: emptyList(),
                topGainers = data.top_gainers?.map{
                    Data(
                        changeAmount = it.change_amount,
                        changePercentage = it.change_percentage,
                        price = it.price,
                        ticker = it.ticker,
                        volume = it.volume
                    )
                } ?: emptyList(),
                topLosers = data.top_losers?.map{
                    Data(
                        changeAmount = it.change_amount,
                        changePercentage = it.change_percentage,
                        price = it.price,
                        ticker = it.ticker,
                        volume = it.volume
                    )
                } ?: emptyList()
            )
            return Result.success(mappedData)
        }catch (e: Exception){
            return Result.failure(e)
        }
    }

}