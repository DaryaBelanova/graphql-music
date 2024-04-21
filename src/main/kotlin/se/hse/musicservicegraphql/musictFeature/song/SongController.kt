package se.hse.musicservicegraphql.musictFeature.song

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import se.hse.musicservicegraphql.musictFeature.playlist.Playlist
import se.hse.musicservicegraphql.musictFeature.playlist.PlaylistRepository

@Controller
class SongController(private val songRepository: SongRepository, private val playlistRepository: PlaylistRepository) {

    @QueryMapping
    fun songs(): List<Song> {
        println("DEBUG: ")
        return songRepository.findAll()
    }

//    @SchemaMapping(typeName = "Song")
//    fun playlist(song: Song): Playlist? {
//        println("DEBUG: Retrieve playlist of song ${song.title} by id")
//        if (song.playlistId != null) {
//            return playlistRepository.findById(song.playlistId!!).orElse(null)
//        }
//
//        return null
//    }

}