package se.hse.musicservicegraphql.musictFeature.playlist

import graphql.execution.AbortExecutionException
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import se.hse.musicservicegraphql.musictFeature.song.Song
import se.hse.musicservicegraphql.musictFeature.song.SongRepository

@Controller
class PlaylistController(private val playlistRepository: PlaylistRepository, private val songRepository: SongRepository) {

    @QueryMapping
    fun playlists(): List<Playlist> {
        println("DEBUG: Select all playlists from DB")
        return playlistRepository.findAll()
    }

    @MutationMapping
    fun addSongToPlaylist(@Argument playlistId: Long, @Argument songId: Long): Song {
        println("DEBUG: Add song with id $songId to playlist with id $playlistId")
        val song = songRepository.findById(songId).orElseThrow { AbortExecutionException("Song with id $songId not found") }
        val playlist = playlistRepository.findById(playlistId).orElseThrow { AbortExecutionException("Playlist with id $playlistId not found") }
        song.playlists.add(playlist)
        return songRepository.save(song)
    }

    @MutationMapping
    fun addSongsToPlaylist(@Argument playlistId: Long, @Argument songsIds: List<Long>): List<Song> {
        println("DEBUG: Add songs with ids $songsIds to playlist with id $playlistId")
        val playlist = playlistRepository.findById(playlistId).orElseThrow { AbortExecutionException("Playlist with id $playlistId not found") }
        val songs = songRepository.findAllById(songsIds)
        songs.forEach { it.playlists.add(playlist) }
        return songRepository.saveAll(songs)
    }

}