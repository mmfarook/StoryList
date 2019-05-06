package com.example.storylist.repository

enum class ItemType(val type: String) {
    COLLECTION("collection"),
    STORY("story"),
    COLLECTION_SUB_STORY("collection_sub_story");

    override fun toString(): String {
        return type
    }}