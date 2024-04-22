package se.hse.musicservicegraphql.musictFeature.song

import jakarta.persistence.Column

data class SongInput(val title: String, val artist: String, var playlistId: Long?)
