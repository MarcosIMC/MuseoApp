package com.example.museoapp.model

class GalleryModelSerializable(item : GalleryModel) : java.io.Serializable {
    var key:String? = null
        get() {
            return field
        }
        set(value) {
            field = value}

    var audio:String? = null
        get() {
            return field
        }
        set(value) {
            field = value}

    var image:String? = null
        get() {
            return field
        }
        set(value) {
            field = value}

    var long_description:String? = null
        get() {
            return field
        }
        set(value) {
            field = value}


    var sort_description:String? = null
        get() {
            return field
        }
        set(value) {
            field = value}

    var name:String? = null
        get() {
            return field
        }
        set(value) {
            field = value}

    var qr_code:String? = null
        get() {
            return field
        }
        set(value) {
            field = value}

    init {
        this.key = item.key
        this.name = item.name
        this.audio = item.audio
        this.qr_code = item.qr_code
        this.long_description = item.long_description
        this.sort_description = item.short_description
        this.image = item.image
    }
}