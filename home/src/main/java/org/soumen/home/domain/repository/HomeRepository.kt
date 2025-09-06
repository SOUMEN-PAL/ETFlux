package org.soumen.home.domain.repository

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.soumen.core.db.dao.GainersLosersEntityDao
import org.soumen.core.db.entities.GainersEntity
import org.soumen.core.db.entities.LosersEntity
import org.soumen.home.data.networking.api.HomeScreenApiService
import org.soumen.home.domain.dataModels.Data
import org.soumen.home.domain.dataModels.HomeData



class HomeRepository(
    private val apiService: HomeScreenApiService,
    private val gainersLosersEntityDao: GainersLosersEntityDao
) {

    suspend fun getTopGainersAdnLosers(): Result<HomeData>{
        try {
            val (gainers, losers) = withContext(Dispatchers.IO) {
                gainersLosersEntityDao.getAllGainers() to gainersLosersEntityDao.getAllLosers()
            }


            val response = if(losers.isNotEmpty() && gainers.isNotEmpty()){
                HomeData(
                    lastUpdated = "",
                    metadata = "",
                    topGainers = gainers.map {
                        Data(
                            changeAmount = it.changeAmount,
                            changePercentage = it.changePercentage,
                            price = it.price,
                            ticker = it.ticker,
                            volume = it.volume
                        )
                    },
                    topLosers = losers.map {
                        Data(
                            changeAmount = it.changeAmount,
                            changePercentage = it.changePercentage,
                            price = it.price,
                            ticker = it.ticker,
                            volume = it.volume
                        )
                    }
                )
            }else {
                val data = apiService.getTopGainersLosers()
                withContext(Dispatchers.IO + CoroutineName("DB Data addition")){
                    gainersLosersEntityDao.upsertGainers(
                        entities = data.top_gainers?.map {
                            GainersEntity(
                                changeAmount = it.change_amount,
                                changePercentage = it.change_percentage,
                                price = it.price,
                                ticker = it.ticker,
                                volume = it.volume
                            )
                        } ?: emptyList()
                    )

                    gainersLosersEntityDao.upsertLosers(
                        entities = data.top_losers?.map {
                            LosersEntity(
                                changeAmount = it.change_amount,
                                changePercentage = it.change_percentage,
                                price = it.price,
                                ticker = it.ticker,
                                volume = it.volume
                            )
                        } ?: emptyList()
                    )
                }
                HomeData(
                    lastUpdated = data.last_updated ?: "",
                    metadata = data.metadata ?: "",
                    topGainers = data.top_gainers?.map {
                        Data(
                            changeAmount = it.change_amount,
                            changePercentage = it.change_percentage,
                            price = it.price,
                            ticker = it.ticker,
                            volume = it.volume
                        )
                    } ?: emptyList(),
                    topLosers = data.top_losers?.map {
                        Data(
                            changeAmount = it.change_amount,
                            changePercentage = it.change_percentage,
                            price = it.price,
                            ticker = it.ticker,
                            volume = it.volume
                        )
                    } ?: emptyList()
                )


            }
            return Result.success(response)
        }catch (e: Exception){
            return Result.failure(e)
        }
    }

    suspend fun getGainers(): Result<List<Data>> {
        return try{
            val gainers =
                gainersLosersEntityDao.getAllGainers()


            val data = gainers.map { it->
                Data(
                    changeAmount = it.changeAmount,
                    changePercentage = it.changePercentage,
                    price = it.price,
                    ticker = it.ticker,
                    volume = it.volume
                )
            }

            Result.success(data)

        }catch (e : Exception){
            Result.failure(exception = e)
        }
    }

    suspend fun getLosers(): Result<List<Data>> {
        return try{
            val losers =
                gainersLosersEntityDao.getAllLosers()


            val data = losers.map { it->
                Data(
                    changeAmount = it.changeAmount,
                    changePercentage = it.changePercentage,
                    price = it.price,
                    ticker = it.ticker,
                    volume = it.volume
                )
            }

            Result.success(data)

        }catch (e : Exception){
            Result.failure(exception = e)
        }
    }

}

