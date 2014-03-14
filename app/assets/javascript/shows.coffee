mod = angular.module("shows",[])

ShowListController = ($scope,$http,$location) ->
  $scope.library = {}
  
  $http.get('/shows/librarySummary').success((data) ->
    $scope.library = data
  )
  
  $scope.userChanged = (newUser) ->
    console.log("received UCE " + newUser.name)
    
  $scope.refresh = () ->
    
    $http.get('/shows/librarySummary/latest').success((data) ->
      $scope.library = data
    )

mod.controller("ShowListController",ShowListController)

