package com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.models

/**
 *  Model for the GitHub API returns when getting specific files through the /repos/:owner/:repo/contents/:file_path endpoint.
 *
 *  Note that [content] is encoded in Base64.
 * */
data class GitHubFileModel(
    val name: String,
    val path: String,
    val sha: String,
    val size: Long,
    val url: String,
    val htmlUrl: String,
    val gitUrl: String,
    val downloadUrl: String,
    val type: String,
    val content: String,
    val encoding: String,
    val links: Links,
)

data class Links(
    val self: String,
    val git: String,
    val html: String,
)
