package se.hse.musicservicegraphql.musictFeature.song

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import se.hse.musicservicegraphql.musictFeature.playlist.Playlist

@Controller
class SongController(private val songRepository: SongRepository) {

    @QueryMapping
    fun songs(): List<Song> {
        println("DEBUG: ")
        return songRepository.findAll()
    }

}