package com.statickev.projectnmp

import java.io.Serializable

data class Student (var id: Int,
                    var name: String,
                    var NRP: String,
                    var program: String,
                    var aboutMe: String,
                    var courses: List<String>,
                    var organization: List<String>,
                    var imgUrl: String
    ): Serializable