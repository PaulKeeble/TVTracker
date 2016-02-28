mod = angular.module('showDetail',['users'])

ShowDetailController = ($scope,$http,$routeParams,userService) ->
  $scope.show = {}
    
  showId = $routeParams.showId
  
  userName = () -> userService.currentUser.name
  
  $http.get('/shows/'+userName+'/'+showId).success((data) ->
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
      
      user = userService.currentUser
      if(episode.watched)
        $http.post('/episode/'+episode.id+'/watch',user)
      else
        $http.post('/episode/'+episode.id+'/unwatch',user)
  
  $scope.watchActionText = (episode) ->
    if(episode.watched)
      "Set unseen"
    else
      "Seen"
  
  $scope.watchedHighlight = (episode) ->
    "watchedHighlight" if episode.watched
  
  $scope.userChanged = (newUser) ->
    console.log("received UCE " + newUser.name)
    

mod.controller("ShowDetailController",ShowDetailController)