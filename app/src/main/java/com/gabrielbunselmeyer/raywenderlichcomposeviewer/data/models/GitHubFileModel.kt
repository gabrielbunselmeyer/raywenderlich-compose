package com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.models

import android.util.Base64
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

/**
 *  Model for the GitHub API returns when getting specific files through the /repos/:owner/:repo/contents/:file_path endpoint.
 *  Note that [content] is encoded in Base64.
 */
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
    val links: GitHubFileLinks,
)

data class GitHubFileLinks(
    val self: String,
    val git: String,
    val html: String,
)

/**
 * Another way of doing this would be to add a Gson annotation with a converter to the [GitHubFileModel.content] property.
 * I think this reads a bit better though as a single base64 -> string -> model operation.
 */
fun GitHubFileModel.convertContentToTutorialModel(): TutorialContentModel {
    val decodedContent = String(Base64.decode(this.content, Base64.NO_WRAP))
    val jsonObject = JsonParser().parse(decodedContent).asJsonObject
    return Gson().fromJson(jsonObject, TutorialContentModel::class.java)
}