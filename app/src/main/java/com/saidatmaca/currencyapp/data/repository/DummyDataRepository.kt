package com.saidatmaca.currencyapp.data.repository

import com.saidatmaca.currencyapp.domain.model.Coin

object DummyDataRepository {

    /**
     * uygulama develop aşamasında kullanılmak için yazılmıştır.
     * Gradle dosyasında bulunan DUMMY_DATAS_ACTIVE değerine bağlıdır.
     * Release versiyonda mutlaka KAPALI olmalıdır.
     */


    val dummyCoin = Coin(
        "Qwsogvtv82FCd",
        "BTC",
        "Bitcoin",
        "#f7931A",
        "https://cdn.coinranking.com/bOabBYkcX/bitcoin_btc.svg",
        "1313484864383",
        "66675.48226616521",
        1330214400L,
        1,
        "2.47",
        1
    )





}