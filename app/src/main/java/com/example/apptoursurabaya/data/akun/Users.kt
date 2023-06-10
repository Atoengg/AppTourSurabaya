package com.example.apptoursurabaya.data.akun

data class Users(
    val id: String,
    val namaLengkap: String?,
    val alamat: String?,
    val jenisKelamin: String?,
    val email: String,
    val password: String
) {
    constructor():this("","","","","","")
}
