mod = angular.module('showDetail',[])

ShowDetailController = ($scope,$http,$location) ->
  $scope.show = 
    {'name':'ShowDetail','location':'/home/share/Series/AShow','seasons':
      [
        {'number':1,'episodes':
          [
            {'number':1,'name':'epi 1-1','filename':'Additional Seasons - S01E01 - epi1-1.mkv','watched':false,'createdDate':'2014-01-03'},
            {'number':2,'name':'epi 1-2','filename':'Additional Seasons - S01E02 - epi1-2.mkv','watched':false,'createdDate':'2014-01-11'}
          ]
        },
        {'number':2,'episodes':
          [
            {'number':1,'name':'epi 2-1','filename':'Additional Seasons - S02E01 - epi2-1.mkv','watched':false,'createdDate':'2014-02-01'},
            {'number':2,'name':'epi 2-2','filename':'Additional Seasons - S02E02 - epi2-2.mkv','watched':false,'createdDate':'2014-02-02'}
          ]
        }
      ]
    }
  
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