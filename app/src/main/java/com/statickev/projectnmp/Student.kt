package com.statickev.projectnmp

data class Student (var name: String,
                    var NRP: String,
                    var program: String,
                    var biodata: String,
                    var courses: List<String>,
                    var experiences: List<String>,
                    var imageId: Int
    )