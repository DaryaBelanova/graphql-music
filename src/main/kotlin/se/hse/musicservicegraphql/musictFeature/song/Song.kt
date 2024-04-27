package se.hse.musicservicegraphql.musictFeature.song

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import se.hse.musicservicegraphql.musictFeature.playlist.Playlist

@Entity
@Table(name = "songs")
data class Song(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val title: String,

    val artist: String,

    @ManyToMany()
    @JoinTable(
        name = "song_playlist",
        joinColumns = [JoinColumn(name = "song_id")],
        inverseJoinColumns = [JoinColumn(name = "playlist_id")]
    )
    var playlists: MutableList<Playlist> = mutableListOf()
)