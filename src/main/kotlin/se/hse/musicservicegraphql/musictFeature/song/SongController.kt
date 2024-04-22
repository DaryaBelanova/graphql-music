package se.hse.musicservicegraphql.musictFeature.song

import graphql.ExecutionResult
import graphql.execution.AbortExecutionException
import graphql.execution.instrumentation.InstrumentationContext
import graphql.execution.instrumentation.SimpleInstrumentation
import graphql.execution.instrumentation.SimpleInstrumentationContext
import graphql.execution.instrumentation.parameters.InstrumentationExecuteOperationParameters
import graphql.language.OperationDefinition
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
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

    @BatchMapping
    fun playlist(songs: List<Song>): Map<Song, Playlist?> {

        // A kinda better approach

        println("DEBUG: Retrieve playlists for all songs")
        val map: MutableMap<Song, Playlist?> = HashMap<Song, Playlist?>()
        val allPlaylistIDs = songs.map { song: Song -> song.playlistId }
        val playlists = playlistRepository.findAllById(allPlaylistIDs)
        songs.forEach { song: Song ->
            val tmp = playlists.firstOrNull { it.id == song.playlistId}
            if (tmp != null) {
                map[song] = tmp
            }
        }

        return map

        // A solution that seems to work, but it actually doesn't
//        return songs.associateBy({it},{ it ->
//            if (it.playlistId != null) {
//                 playlistRepository.findById(it.playlistId!!).orElse(null)
//            } else {
//                null
//            }
//        }
//        )


    }

}