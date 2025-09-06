package org.soumen.home.data.networking.api

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import org.soumen.core.api.AlphaVantageApi
import org.soumen.core.api.BaseApiService
import org.soumen.home.data.networking.homeApiDataModels.HomeApiResponse

class HomeScreenApiService: BaseApiService() {


    val jsonResponse = """
        {
            "metadata": "Top gainers, losers, and most actively traded US tickers",
            "last_updated": "2025-09-05 16:16:00 US/Eastern",
            "top_gainers": [
                {
                    "ticker": "CNF",
                    "price": "4.76",
                    "change_amount": "2.36",
                    "change_percentage": "98.3333%",
                    "volume": "1069162"
                },
                {
                    "ticker": "HOUR",
                    "price": "3.65",
                    "change_amount": "1.76",
                    "change_percentage": "93.1217%",
                    "volume": "206058994"
                },
                {
                    "ticker": "PRSO",
                    "price": "1.38",
                    "change_amount": "0.562",
                    "change_percentage": "68.7042%",
                    "volume": "185008741"
                },
                {
                    "ticker": "SSM",
                    "price": "14.55",
                    "change_amount": "5.76",
                    "change_percentage": "65.529%",
                    "volume": "2348339"
                },
                {
                    "ticker": "NVFY",
                    "price": "4.4",
                    "change_amount": "1.73",
                    "change_percentage": "64.794%",
                    "volume": "20509379"
                },
                {
                    "ticker": "GELS",
                    "price": "1.56",
                    "change_amount": "0.6",
                    "change_percentage": "62.5%",
                    "volume": "71394155"
                },
                {
                    "ticker": "PAPL",
                    "price": "6.67",
                    "change_amount": "2.53",
                    "change_percentage": "61.1111%",
                    "volume": "62736966"
                },
                {
                    "ticker": "MSPRZ",
                    "price": "0.0297",
                    "change_amount": "0.0107",
                    "change_percentage": "56.3158%",
                    "volume": "880"
                },
                {
                    "ticker": "GNPX",
                    "price": "0.2295",
                    "change_amount": "0.0738",
                    "change_percentage": "47.3988%",
                    "volume": "121543355"
                },
                {
                    "ticker": "VEEE",
                    "price": "2.84",
                    "change_amount": "0.9",
                    "change_percentage": "46.3918%",
                    "volume": "79076292"
                },
                {
                    "ticker": "CIGL",
                    "price": "4.24",
                    "change_amount": "1.33",
                    "change_percentage": "45.7045%",
                    "volume": "16015023"
                },
                {
                    "ticker": "PFAI",
                    "price": "2.6",
                    "change_amount": "0.8",
                    "change_percentage": "44.4444%",
                    "volume": "4880743"
                },
                {
                    "ticker": "AUUDW",
                    "price": "0.0195",
                    "change_amount": "0.0059",
                    "change_percentage": "43.3824%",
                    "volume": "100"
                },
                {
                    "ticker": "BCTXW",
                    "price": "0.0288",
                    "change_amount": "0.0086",
                    "change_percentage": "42.5743%",
                    "volume": "5070"
                },
                {
                    "ticker": "HSPOR",
                    "price": "0.209",
                    "change_amount": "0.059",
                    "change_percentage": "39.3333%",
                    "volume": "21"
                },
                {
                    "ticker": "SCAGW",
                    "price": "0.0721",
                    "change_amount": "0.02",
                    "change_percentage": "38.3877%",
                    "volume": "627920"
                },
                {
                    "ticker": "FLYX+",
                    "price": "0.29",
                    "change_amount": "0.08",
                    "change_percentage": "38.0952%",
                    "volume": "24688"
                },
                {
                    "ticker": "NVVEW",
                    "price": "0.0189",
                    "change_amount": "0.0052",
                    "change_percentage": "37.9562%",
                    "volume": "101385"
                },
                {
                    "ticker": "HTCR",
                    "price": "1.12",
                    "change_amount": "0.3012",
                    "change_percentage": "36.7855%",
                    "volume": "1176049"
                },
                {
                    "ticker": "RVPHW",
                    "price": "0.0696",
                    "change_amount": "0.0184",
                    "change_percentage": "35.9375%",
                    "volume": "356240"
                }
            ],
            "top_losers": [
                {
                    "ticker": "YAAS",
                    "price": "0.1025",
                    "change_amount": "-0.1821",
                    "change_percentage": "-63.9845%",
                    "volume": "91442683"
                },
                {
                    "ticker": "WLDSW",
                    "price": "1.65",
                    "change_amount": "-1.27",
                    "change_percentage": "-43.4932%",
                    "volume": "2491"
                },
                {
                    "ticker": "IBG",
                    "price": "0.3604",
                    "change_amount": "-0.1945",
                    "change_percentage": "-35.0514%",
                    "volume": "2125506"
                },
                {
                    "ticker": "RBOT+",
                    "price": "0.0236",
                    "change_amount": "-0.0119",
                    "change_percentage": "-33.5211%",
                    "volume": "2000"
                },
                {
                    "ticker": "FACTW",
                    "price": "0.2006",
                    "change_amount": "-0.0994",
                    "change_percentage": "-33.1333%",
                    "volume": "201"
                },
                {
                    "ticker": "ARQQW",
                    "price": "0.11",
                    "change_amount": "-0.0511",
                    "change_percentage": "-31.7194%",
                    "volume": "369219"
                },
                {
                    "ticker": "BFRIW",
                    "price": "0.1",
                    "change_amount": "-0.0454",
                    "change_percentage": "-31.2242%",
                    "volume": "1154"
                },
                {
                    "ticker": "CYCUW",
                    "price": "0.0431",
                    "change_amount": "-0.0194",
                    "change_percentage": "-31.04%",
                    "volume": "283478"
                },
                {
                    "ticker": "BRIA",
                    "price": "2.92",
                    "change_amount": "-1.23",
                    "change_percentage": "-29.6386%",
                    "volume": "807354"
                },
                {
                    "ticker": "RDZNW",
                    "price": "0.056",
                    "change_amount": "-0.023",
                    "change_percentage": "-29.1139%",
                    "volume": "10000"
                },
                {
                    "ticker": "PSFE+",
                    "price": "0.0135",
                    "change_amount": "-0.0053",
                    "change_percentage": "-28.1915%",
                    "volume": "170600"
                },
                {
                    "ticker": "PXSAW",
                    "price": "0.0048",
                    "change_amount": "-0.0018",
                    "change_percentage": "-27.2727%",
                    "volume": "5036"
                },
                {
                    "ticker": "SUPX",
                    "price": "49.51",
                    "change_amount": "-18.49",
                    "change_percentage": "-27.1912%",
                    "volume": "345822"
                },
                {
                    "ticker": "MGIH",
                    "price": "1.59",
                    "change_amount": "-0.54",
                    "change_percentage": "-25.3521%",
                    "volume": "265749"
                },
                {
                    "ticker": "CURV",
                    "price": "1.785",
                    "change_amount": "-0.595",
                    "change_percentage": "-25.0%",
                    "volume": "6145321"
                },
                {
                    "ticker": "AIHS",
                    "price": "2.38",
                    "change_amount": "-0.77",
                    "change_percentage": "-24.4444%",
                    "volume": "1374899"
                },
                {
                    "ticker": "OUSTW",
                    "price": "0.0072",
                    "change_amount": "-0.0023",
                    "change_percentage": "-24.2105%",
                    "volume": "42582"
                },
                {
                    "ticker": "GGROW",
                    "price": "0.02",
                    "change_amount": "-0.006",
                    "change_percentage": "-23.0769%",
                    "volume": "32800"
                },
                {
                    "ticker": "TRON",
                    "price": "2.7",
                    "change_amount": "-0.8",
                    "change_percentage": "-22.8571%",
                    "volume": "5126839"
                },
                {
                    "ticker": "KVACW",
                    "price": "0.0701",
                    "change_amount": "-0.0199",
                    "change_percentage": "-22.1111%",
                    "volume": "5"
                }
            ],
            "most_actively_traded": [
                {
                    "ticker": "OPEN",
                    "price": "6.65",
                    "change_amount": "0.69",
                    "change_percentage": "11.5772%",
                    "volume": "562628623"
                },
                {
                    "ticker": "NXTT",
                    "price": "0.1468",
                    "change_amount": "0.0038",
                    "change_percentage": "2.6573%",
                    "volume": "248478280"
                },
                {
                    "ticker": "NVDA",
                    "price": "167.02",
                    "change_amount": "-4.64",
                    "change_percentage": "-2.703%",
                    "volume": "221264496"
                },
                {
                    "ticker": "HOUR",
                    "price": "3.65",
                    "change_amount": "1.76",
                    "change_percentage": "93.1217%",
                    "volume": "206058994"
                },
                {
                    "ticker": "TSLL",
                    "price": "13.51",
                    "change_amount": "0.9",
                    "change_percentage": "7.1372%",
                    "volume": "192530397"
                },
                {
                    "ticker": "SOXS",
                    "price": "6.99",
                    "change_amount": "-0.23",
                    "change_percentage": "-3.1856%",
                    "volume": "188672739"
                },
                {
                    "ticker": "PRSO",
                    "price": "1.38",
                    "change_amount": "0.562",
                    "change_percentage": "68.7042%",
                    "volume": "185008741"
                },
                {
                    "ticker": "KVUE",
                    "price": "18.66",
                    "change_amount": "-1.88",
                    "change_percentage": "-9.1529%",
                    "volume": "135141800"
                },
                {
                    "ticker": "SQQQ",
                    "price": "17.45",
                    "change_amount": "-0.04",
                    "change_percentage": "-0.2287%",
                    "volume": "128936240"
                },
                {
                    "ticker": "GNPX",
                    "price": "0.2295",
                    "change_amount": "0.0738",
                    "change_percentage": "47.3988%",
                    "volume": "121543355"
                },
                {
                    "ticker": "ISPC",
                    "price": "0.8536",
                    "change_amount": "0.1837",
                    "change_percentage": "27.422%",
                    "volume": "121038486"
                },
                {
                    "ticker": "SOXL",
                    "price": "26.51",
                    "change_amount": "0.86",
                    "change_percentage": "3.3528%",
                    "volume": "110020367"
                },
                {
                    "ticker": "TSLA",
                    "price": "350.84",
                    "change_amount": "12.31",
                    "change_percentage": "3.6363%",
                    "volume": "108277649"
                },
                {
                    "ticker": "TSLZ",
                    "price": "1.215",
                    "change_amount": "-0.095",
                    "change_percentage": "-7.2519%",
                    "volume": "106243477"
                },
                {
                    "ticker": "SNAP",
                    "price": "7.305",
                    "change_amount": "0.215",
                    "change_percentage": "3.0324%",
                    "volume": "101896908"
                },
                {
                    "ticker": "SOUN",
                    "price": "14.26",
                    "change_amount": "0.97",
                    "change_percentage": "7.2987%",
                    "volume": "94921631"
                },
                {
                    "ticker": "YAAS",
                    "price": "0.1025",
                    "change_amount": "-0.1821",
                    "change_percentage": "-63.9845%",
                    "volume": "91442683"
                },
                {
                    "ticker": "AAL",
                    "price": "13.08",
                    "change_amount": "0.22",
                    "change_percentage": "1.7107%",
                    "volume": "90126514"
                },
                {
                    "ticker": "TQQQ",
                    "price": "91.82",
                    "change_amount": "0.3",
                    "change_percentage": "0.3278%",
                    "volume": "85816286"
                },
                {
                    "ticker": "RIVN",
                    "price": "14.21",
                    "change_amount": "0.51",
                    "change_percentage": "3.7226%",
                    "volume": "85141222"
                }
            ]
        }
    """.trimIndent()

    suspend fun getTopGainersLosers(): HomeApiResponse {
        val data =  client.get {
            url {
                // endpoint function parameter
                parameters.append("function", AlphaVantageApi.TOP_GAINERS_LOSERS)

                // the authPlugin already appends API key
            }
        }


        Log.e("HomeScreenApiService", "Response: ${data.bodyAsText()} ${data.status.value}" )

        return data.body()

//        val response = Json { ignoreUnknownKeys = true }.decodeFromString<HomeApiResponse>(jsonResponse)
//
//        println("Mock Response: $response") // optional log
//        return response
    }

}