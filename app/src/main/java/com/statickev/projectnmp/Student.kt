package com.statickev.projectnmp

import java.io.Serializable

data class Student (var name: String,
                    var NRP: String,
                    var program: String,
                    var aboutMe: String,
                    var courses: List<String>,
                    var organization: List<String>,
                    var imageId: Int,
                    var isFriend: Boolean
    ): Serializable