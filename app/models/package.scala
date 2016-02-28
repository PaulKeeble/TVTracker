package object models {
  type EpisodeId = Long
  
  implicit class WatchedEpisode(episode:Episode) {
    def watched(implicit watchedSet:Set[EpisodeId]) = watchedSet.contains(episode.id)
  }
}