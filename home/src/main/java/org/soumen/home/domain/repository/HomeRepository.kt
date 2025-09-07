package org.soumen.home.domain.repository

import android.util.Log
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.soumen.core.db.dao.BookmarkEntityDao
import org.soumen.core.db.dao.GainersLosersEntityDao
import org.soumen.core.db.dao.MonthlyStockEntityDao
import org.soumen.core.db.dao.TickerEntityDao
import org.soumen.core.db.dao.WatchlistEntityDao
import org.soumen.core.db.entities.BookmarkEntity
import org.soumen.core.db.entities.GainersEntity
import org.soumen.core.db.entities.LosersEntity
import org.soumen.core.db.entities.MonthlyStockEntity
import org.soumen.core.db.entities.TickerInfoEntity
import org.soumen.core.db.entities.WatchlistEntity
import org.soumen.home.data.networking.api.HomeScreenApiService
import org.soumen.home.domain.dataModels.Data
import org.soumen.home.domain.dataModels.HomeData
import org.soumen.home.domain.dataModels.MonthlyTimeSeriesData
import org.soumen.home.domain.dataModels.TickerData
import org.soumen.home.domain.dataModels.WatchListData
import org.soumen.shared.data.network.TickerInfoApiService
import kotlin.String


class HomeRepository(
    private val apiService: HomeScreenApiService,
    private val tickerInfoApiService: TickerInfoApiService,
    private val gainersLosersEntityDao: GainersLosersEntityDao,
    private val tickerEntityDao: TickerEntityDao,
    private val monthlyStockEntityDao: MonthlyStockEntityDao,
    private val watchListEntityDao: WatchlistEntityDao,
    private val bookmarkEntityDao: BookmarkEntityDao
) {

    suspend fun getTopGainersAdnLosers(): Result<HomeData> {
        try {
            val (gainers, losers) = withContext(Dispatchers.IO) {
                gainersLosersEntityDao.getAllGainers() to gainersLosersEntityDao.getAllLosers()
            }


            val response = if (losers.isNotEmpty() && gainers.isNotEmpty()) {
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
            } else {
                val data = apiService.getTopGainersLosers()
                withContext(Dispatchers.IO + CoroutineName("DB Data addition")) {
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
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun getGainers(): Result<List<Data>> {
        return try {
            val gainers =
                gainersLosersEntityDao.getAllGainers()


            val data = gainers.map { it ->
                Data(
                    changeAmount = it.changeAmount,
                    changePercentage = it.changePercentage,
                    price = it.price,
                    ticker = it.ticker,
                    volume = it.volume
                )
            }

            Result.success(data)

        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    suspend fun getLosers(): Result<List<Data>> {
        return try {
            val losers =
                gainersLosersEntityDao.getAllLosers()


            val data = losers.map { it ->
                Data(
                    changeAmount = it.changeAmount,
                    changePercentage = it.changePercentage,
                    price = it.price,
                    ticker = it.ticker,
                    volume = it.volume
                )
            }

            Result.success(data)

        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }


    suspend fun getTickerInfo(ticker: String): Result<TickerData> {
        return try {
            val dbData = tickerEntityDao.getTickerData(ticker)

            if (dbData != null) {
                // Found in DB → return directly
                val data = TickerData(
                    AssetType = dbData.AssetType,
                    CIK = dbData.CIK,
                    Country = dbData.Country,
                    Currency = dbData.Currency,
                    Description = dbData.Description,
                    Exchange = dbData.Exchange,
                    Name = dbData.Name,
                    Symbol = dbData.Symbol
                )
                Result.success(data)
            } else {
                // Fetch from API
                val tickerInfo = tickerInfoApiService.getTicketData(ticker)

                val data = TickerData(
                    AssetType = tickerInfo.AssetType ?: "",
                    CIK = tickerInfo.CIK ?: "",
                    Country = tickerInfo.Country ?: "",
                    Currency = tickerInfo.Currency ?: "",
                    Description = tickerInfo.Description ?: "",
                    Exchange = tickerInfo.Exchange ?: "",
                    Name = tickerInfo.Name ?: "",
                    Symbol = tickerInfo.Symbol ?: ""
                )

                // Cache into DB
                withContext(Dispatchers.IO) {
                    tickerEntityDao.upsertTickerInfo(
                        TickerInfoEntity(
                            AssetType = data.AssetType,
                            CIK = data.CIK,
                            Country = data.Country,
                            Currency = data.Currency,
                            Description = data.Description,
                            Exchange = data.Exchange,
                            Name = data.Name,
                            Symbol = data.Symbol
                        )
                    )
                }

                Result.success(data)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getMonthlyData(ticker: String , limit : Int): Result<List<MonthlyTimeSeriesData>> {
        return try {
            val dbResponse = monthlyStockEntityDao.getAll(limit = limit , ticker = ticker)

            val successData = if(dbResponse != null && dbResponse.isNotEmpty()){
                val presentationData = dbResponse.map { data->
                    MonthlyTimeSeriesData(
                        symbol = data.symbol,
                        date = data.date,
                        open = data.open,
                        high = data.high,
                        low = data.low,
                        close = data.close,
                        volume = data.volume
                    )
                }
                presentationData
            }else{
                val apiResponse = apiService.getTickerMonthlyData(ticker)
                val symbol = ticker
//                Log.e("MonthlyDataTag" , "API Response Size: ${apiResponse.monthlyTimeSeries.size}")
                withContext(Dispatchers.IO + CoroutineName("DB Data addition")) {
                    monthlyStockEntityDao.upsertAll(
                        entities = apiResponse.monthlyTimeSeries.map { (data , timeSeriesData)->
                            MonthlyStockEntity(
                                symbol = symbol,
                                date = data,
                                open = timeSeriesData.open.toDouble(),
                                high = timeSeriesData.high.toDouble(),
                                low = timeSeriesData.low.toDouble(),
                                close = timeSeriesData.close.toDouble(),
                                volume = timeSeriesData.volume.toLong()
                            )
                        }
                    )
                }

                val presentationData = apiResponse.monthlyTimeSeries.map { (data , timeSeriesData)->
                    MonthlyTimeSeriesData(
                        symbol = symbol,
                        date = data,
                        open = timeSeriesData.open.toDouble(),
                        high = timeSeriesData.high.toDouble(),
                        low = timeSeriesData.low.toDouble(),
                        close = timeSeriesData.close.toDouble(),
                        volume = timeSeriesData.volume.toLong()
                    )
                }
                presentationData

            }

//            Log.e("MonthlyDataTag" , "Data Size: ${successData.size}")
            Result.success(successData.take(limit))


        } catch (e: Exception) {
            Log.e("MonthlyDataTag" , "Error: ${e.message}")
            Result.failure(e)

        }
    }

    fun isBookmarked(ticker : String) : Flow<Boolean> =
        bookmarkEntityDao.isBookmarkedFlow(ticker = ticker).map { count-> count>0 }

    fun getWatchlist(): Flow<List<WatchListData>> =
        watchListEntityDao.getAllWatchlist()
            .map { list ->
                list.map { entity ->
                    WatchListData(
                        watchlistId = entity.watchlistId,
                        watchlistName = entity.watchlistName
                    )
                }
            }

    suspend fun addWatchlist(
        watchlistName : String
    ){
        watchListEntityDao.upsertWatchlist(
            WatchlistEntity(
                watchlistName = watchlistName
            )
        )
    }

    suspend fun saveToBookmark(watchlistID: Long , ticker : Data) {

        bookmarkEntityDao.insertBookmark(
            BookmarkEntity(
                ticker = ticker.ticker,
                changeAmount = ticker.changeAmount,
                changePercentage = ticker.changePercentage,
                price = ticker.price,
                volume = ticker.volume,
                watchlistId = watchlistID
            )
        )


    }

    suspend fun removeBookmark(ticker: String) {
        bookmarkEntityDao.deleteFormBookmark(ticker = ticker)
    }
}

