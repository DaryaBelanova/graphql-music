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
import org.springframework.data.domain.PageRequest
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import se.hse.musicservicegraphql.musictFeature.playlist.Playlist
import se.hse.musicservicegraphql.musictFeature.playlist.PlaylistRepository


@Controller
class SongController(private val songRepository: SongRepository, private val playlistRepository: PlaylistRepository) {

    @QueryMapping
    fun songs(): List<Song> {
        println("DEBUG: Select all songs from DB")
        return songRepository.findAll()
    }

    @QueryMapping
    fun pagedSongs(@Argument page: Int, @Argument size: Int): List<Song> {
        println("DEBUG: Select all songs from DB with pagination")
        var req = PageRequest.of(page, size)
        return songRepository.findAll(req).toList()
    }

    @QueryMapping
    fun song(@Argument id: Long): Song? {
        println("DEBUG: Select a single song from DB")
        return songRepository.findById(id).orElse(null)
    }

    @MutationMapping
    fun addSong(@Argument title: String, @Argument artist: String): Song {
        println("DEBUG: Insert new song into DB")
        return songRepository.save(Song(title = title, artist = artist, playlists = mutableListOf()))
    }
}