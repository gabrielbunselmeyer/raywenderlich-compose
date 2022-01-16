package com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model

enum class Difficulty(val displayName: String) {
    ANY("Any"),
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced")
}

enum class ContentType(val displayName: String) {
    ANY("Any"),
    COLLECTION("Video"),
    ARTICLE("Article")
}

enum class AccessLevel(val displayName: String) {
    ANY("Any"),
    FREE("Free"),
    PRO("Pro")
}

enum class Ordering(val displayName: String) {
    NEWEST("Newest"),
    OLDEST("Oldest")
}