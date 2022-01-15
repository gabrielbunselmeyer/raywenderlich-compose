package com.gabrielbunselmeyer.raywenderlichcomposeviewer.data.model

import com.google.gson.annotations.SerializedName

/**
 * This file holds the model for the video and article content JSON files.
 * It was generated automatically through a handy Json-to-Kotlin-data-class due to time constraints.
 * A more robust solution would probably simplify most of these classes.
 */
data class TutorialContentModel(
    @SerializedName("data") val data: List<TutorialData>,
    @SerializedName("included") val included: List<Included>,
    @SerializedName("links") val links: Links,
    @SerializedName("meta") val meta: Meta
)

data class TutorialData(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("attributes") val attributes: TutorialAttributes,
    @SerializedName("relationships") val relationships: Relationships,
    @SerializedName("links") val links: Links
)

data class TutorialAttributes(
    @SerializedName("uri") val uri: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("released_at") val released_at: String,
    @SerializedName("free") val free: Boolean,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("content_type") val content_type: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("popularity") val popularity: Int,
    @SerializedName("technology_triple_string") val technology_triple_string: String,
    @SerializedName("contributor_string") val contributor_string: String,
    @SerializedName("ordinal") val ordinal: String,
    @SerializedName("professional") val professional: Boolean,
    @SerializedName("description_plain_text") val description_plain_text: String,
    @SerializedName("video_identifier") val video_identifier: String,
    @SerializedName("parent_name") val parent_name: String,
    @SerializedName("card_artwork_url") val card_artwork_url: String
)

data class Relationships(
    @SerializedName("domains") val domains: Domains,
    @SerializedName("child_contents") val childContent: ChildContent,
    @SerializedName("progression") val progression: Progression,
    @SerializedName("bookmark") val bookmark: Bookmark
)

data class Links(
    @SerializedName("self") val self: String,
    @SerializedName("first") val first: String,
    @SerializedName("prev") val prev: String,
    @SerializedName("next") val next: String,
    @SerializedName("last") val last: String
)

data class Included(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("attributes") val attributes: Attributes
)

data class Domains(
    @SerializedName("data") val data: List<DomainData>
)

data class DomainData(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
//    @SerializedName("attributes") val attributes : Attributes,
//    @SerializedName("relationships") val relationships : Relationships,
//    @SerializedName("links") val links : Links
)

data class Attributes(
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("description") val description: String,
    @SerializedName("level") val level: String,
    @SerializedName("ordinal") val ordinal: Int
)

data class ChildContent(
    @SerializedName("meta") val meta: Meta
)

data class Meta(
    @SerializedName("total_result_count") val totalResultCount: Int
)

data class Progression(
    @SerializedName("data") val data: ProgressionData
)

data class ProgressionData(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
)

data class Bookmark(
    @SerializedName("data") val data: String
)
