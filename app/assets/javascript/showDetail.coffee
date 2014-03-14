mod = angular.module('showDetail',[])

ShowDetailController = ($scope,$http,$routeParams) ->
  $scope.show = {}
    
  showId = $routeParams.showId
  
  $http.get('/shows/'+showId).success((data) ->
      $scope.show = data
    )
  
  findEpisode = (seasonNumber,episodeNumber) ->
    seasons = (season for season in $scope.show.seasons when season.number == seasonNumber)

    if(seasons? and seasons.length==1)
      season = seasons[0]
      episodes = (episode for episode in season.episodes when episode.number == episodeNumber)

      if(episodes? and episodes.length == 1)
        episode = episodes[0]
        return episode
        
    return nil
  
  $scope.toggleWatch = (seasonNumber,episode) ->
      episode.watched = not episode.watched
  
  $scope.watchActionText = (episode) ->
    if(episode.watched)
      "Set unseen"
    else
      "Seen"
  
  $scope.watchedHighlight = (episode) ->
    "watchedHighlight" if episode.watched
    

mod.controller("ShowDetailController",ShowDetailController)