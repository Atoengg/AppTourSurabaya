package com.example.apptoursurabaya.data

import com.google.android.gms.maps.model.LatLng

object CardList {
    fun getCardList(): List<Card> {
        val list = ArrayList<Card>()
        list.addAll(
            listOf(
                Card(
                    12341,
                    "Kebun Binatang Surabaya",
                    "Kebun Binatang Surabaya, juga dikenal sebagai KBS, adalah kebun binatang yang terletak di Surabaya, Jawa Timur. Didirikan pada tahun 1916, KBS adalah kebun binatang tertua di Indonesia dan merupakan tempat yang populer bagi wisatawan dan penduduk setempat. KBS menawarkan pengalaman mendalam untuk melihat dan belajar tentang berbagai jenis hewan dari seluruh dunia. Dengan luas sekitar 37 hektar, kebun binatang ini menyediakan habitat yang luas dan nyaman bagi hewan-hewan yang tinggal di dalamnya.",
                    "Jl. Setail No. 1, Surabaya",
                    "https://i.postimg.cc/zf6Nt6L4/kbsby.png",
                    LatLng(-7.296, 112.73658)
                ),
                Card(
                    12342,
                    "Monumen Kapal Selam",
                    "Monumen Kapal Selam Surabaya, juga dikenal sebagai Monkasel, adalah monumen yang berlokasi di Surabaya, Jawa Timur, Indonesia. Monumen ini merupakan bekas kapal selam perang, KRI Pasopati-410, yang diubah menjadi museum dan menjadi salah satu daya tarik wisata yang populer di Surabaya.",
                    "Jl. Pemuda No.39, Embong Kaliasin, Genteng",
                    "https://i.postimg.cc/VLYxRmGd/kapalselam.png",
                    LatLng(-7.265430392731409, 112.75025155092129)
                ),
                Card(
                    12343,
                    "Tugu Pahlawan dan Museum 10 Nopember",
                    "Tugu Pahlawan adalah sebuah monumen yang didirikan untuk mengenang para pahlawan yang gugur dalam Pertempuran Surabaya pada tahun 1945. Monumen ini menjadi salah satu ikon kota Surabaya dan menjadi simbol keberanian dan semangat perjuangan rakyat Surabaya. Tugu Pahlawan terletak di Taman Pahlawan yang luas, yang juga merupakan tempat peringatan dan penghormatan kepada pahlawan-pahlawan tersebut.",
                    "Jl. Pahlawan, Embong Kaliasin, Genteng",
                    "https://i.postimg.cc/GpYgR57W/tugupahlawan.png",
                    LatLng(-7.245744604331569, 112.73776405092113)
                ),
                Card(
                    12344,
                    "Taman Bungkul",
                    "Taman Bungkul merupakan salah satu taman publik yang populer di Surabaya. Taman ini dinamai berdasarkan nama pohon bungkul yang banyak tumbuh di sekitar area taman. Dengan luas sekitar 9 hektar, Taman Bungkul menjadi tempat rekreasi yang populer bagi penduduk setempat dan pengunjung dari berbagai daerah.",
                    "Jl. Raya Darmo, Dukuh Pakis, Surabaya",
                    "https://i.postimg.cc/zXggxYyY/tamanbungkul.png",
                    LatLng(-7.29123646982433, 112.73980609914851)
                ),
                Card(
                    12345,
                    "Wisata Sunan Ampel",
                    "Wisata Sunan Ampel adalah destinasi wisata yang terkait dengan sejarah keagamaan Islam di Surabaya. Tempat ini terkenal sebagai makam Sunan Ampel, salah satu dari sembilan wali songo yang berperan penting dalam penyebaran agama Islam di pulau Jawa.",
                    "Jl. Ampel Masjid No. 100, Ampel, Semampir",
                    "https://i.postimg.cc/mrFJDDD6/sunanampel.png",
                    LatLng(-7.229590459214849, 112.74250520164654)
                ),
                Card(
                    12346,
                    "Food Junction Grand Pakuwon",
                    "Food Junction Grand Pakuwon adalah sebuah kompleks kuliner yang menawarkan berbagai pilihan makanan dan minuman yang lezat. Tempat ini menjadi tujuan favorit bagi para pengunjung yang ingin menikmati berbagai hidangan dari berbagai jenis masakan.",
                    "Jl. Puncak Indah Lontar No. 2, Tandes",
                    "https://i.postimg.cc/htCQjDwx/foodjunction.png",
                    LatLng(-7.250936848464518, 112.66194092208559)
                )
            )
        )

        return list
    }
}