mod = angular.module("shows",[])

ShowListController = ($scope,$http,$location) ->
  $scope.shows = [
    {'name':'Mixed Watching','episodes':[
        {'season':1,'episode':1,'name':'epi 1','filename':'Mixed Watching - S01E01 - epi1.mkv','watched':true,'createdDate':'2014-01-01'},
        {'season':1,'episode':2,'name':'epi 2','filename':'Mixed Watching - S01E02 - epi2.mkv','watched':false,'createdDate':'2014-01-08'}
      ]
    },
    {'name':'Additional Seasons','episodes':[
        {'season':1,'episode':1,'name':'epi 1-1','filename':'Additional Seasons - S01E01 - epi1-1.mkv','watched':false,'createdDate':'2014-01-03'},
        {'season':2,'episode':1,'name':'epi 2-1','filename':'Additional Seasons - S02E01 - epi2-1.mkv','watched':false,'createdDate':'2014-01-11'},
      ]
    },
    {'name':'No episodes','episodes':[]}
  ]
  
  $scope.lastEpisodeDate = (show) ->
    "2014-01-01"
  
  $scope.episodeSummary = (show) ->
    "1 / 45"
  
  $scope.navConfig = () -> $location.path("/configuration")

ShowDetailController = ($scope,$http) ->

mod.controller("ShowListController",ShowListController)
mod.controller("ShowDetailController",ShowDetailController)

