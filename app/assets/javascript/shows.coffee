mod = angular.module("shows",['users'])

ShowListController = ($scope,$http,$location,userService) ->
  $scope.library = {}
  
  userName = () -> userService.currentUser.name
  
  $http.get('/shows/librarySummary/'+userName()).success((data) ->
    $scope.library = data
  )
  
  $scope.userChanged = (newUser) ->
    console.log("received UCE " + userName())
    
  $scope.refresh = () ->
    
    $http.get('/shows/librarySummary/'+userService.currentUser.name+'/latest').success((data) ->
      $scope.library = data
    )

mod.controller("ShowListController",ShowListController)

